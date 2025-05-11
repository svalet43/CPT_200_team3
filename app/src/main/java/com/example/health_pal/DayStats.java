package com.example.health_pal;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class DayStats {
    /*this class creates a DayStats object and
    *pushes/pulls data from firebase
    *stores each day as an object with that day's nutrients
    * tracks  cal, protein, carb, fat, height, weight, date*/
       private FirebaseFirestore db;
       private FirebaseUser user;
       private int cal, protein, carb, fat;
       private double weight;
       private String height;
       private sDate date;
    public interface DayStatsLoadCallback {
        void onDataLoaded(DayStats dayStats);
        void onError(Exception e);
    }

    //constructor
    public DayStats(FirebaseFirestore inputDB, sDate inputDate, FirebaseUser inputUser){
        db = inputDB;
        date = inputDate;
        user = inputUser;
    }
    //methods
    public void pushToNutriDatabase(){
        // map to hold the data
        Map<String, Object> dayData = new HashMap<>();
        dayData.put("cal", cal);
        dayData.put("protein", protein);
        dayData.put("carb", carb);
        dayData.put("fat", fat);

        db.collection("users")
                .document(user.getUid())
                .collection("days")
                .document(date.getDate())
                .set(dayData)
                .addOnSuccessListener(aVoid -> {
                    Log.w("DayStats", "Success writing document");
                })
                .addOnFailureListener(e -> {
                    Log.w("DayStats", "Error writing document", e);
                });
    }
    public void pushHWDatabase(){
        // map to hold the data
        Map<String, Object> dayData = new HashMap<>();
        if(height != null){ dayData.put("height", height); }
        else {
            initHW();
            dayData.put("height", height);
        }

        if(weight != 0){ dayData.put("weight", weight); }
        else {
            initHW();
            dayData.put("weight", weight);
        }

        // Add the server timestamp field
        dayData.put("timestamp", FieldValue.serverTimestamp()); // Add this line
        db.collection("users")
                .document(user.getUid())
                .collection("stats")
                .document(date.getDate())
                .set(dayData);
    }
    public void addNutrient(String nutrient, int amount){
        switch (nutrient){
            case "carb":
                carb += amount;
                break;
            case "protein":
                protein += amount;
                break;
            case "fat":
                fat += amount;
                break;
            case "cal":
                cal += amount;
                break;
            default:
                break;
        }
    }
    public void updateWeight(double newWeight){
        weight = newWeight;
    }
    public void updateHeight(String newHeight){
        height = newHeight;
    }
    private void initNewDay() {
        // Set initial values for a new day
        cal = 0;
        protein = 0;
        carb = 0;
        fat = 0;
    }
    private Task<Void> initHW() {
        final TaskCompletionSource<Void> tcs = new TaskCompletionSource<>();

        // use timestamp to get most recent stats
        db.collection("users")
                .document(user.getUid())
                .collection("stats")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .limit(1)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (!task.getResult().isEmpty()) { // if there are previous stats
                                QueryDocumentSnapshot document = (QueryDocumentSnapshot) task.getResult().getDocuments().get(0);
                                String h = document.getString("height");
                                Double w = document.getDouble("weight");
                                height = h;
                                if (w != null) { weight = w; }
                                else { weight = 0.0; }

                                Log.d("DayStats", "Loaded most recent stats data.");
                            }
                            else { // if there are no previous stats
                                height = null;
                                weight = 0.0;
                                Log.d("DayStats", "No previous stats data found. Initializing to defaults.");
                            }
                            tcs.setResult(null);
                        }
                        else { // if there was an error
                            Log.w("DayStats", "Error getting most recent stats document.", task.getException());
                            height = null;
                            weight = 0.0;
                        }
                    }
                });
        return tcs.getTask();
    }
    public void loadAllDataFromDatabase(DayStatsLoadCallback callback) {
        //get nutri data for today and set task
        DocumentReference nutriDocRef = db.collection("users")
                .document(user.getUid())
                .collection("days")
                .document(date.getDate());
        Task<DocumentSnapshot> nutriTask = nutriDocRef.get();

        // get stats data for today and set task
        DocumentReference statsDocRefToday = db.collection("users")
                .document(user.getUid())
                .collection("stats")
                .document(date.getDate());
        Task<DocumentSnapshot> statsTaskToday = statsDocRefToday.get();

        //wait for tasks to complete
        Tasks.whenAll(nutriTask, statsTaskToday)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) { // if both tasks successful
                            DocumentSnapshot nutriDoc = nutriTask.getResult();
                            DocumentSnapshot statsDocToday = statsTaskToday.getResult();

                            // set nutri data
                            if (nutriDoc != null && nutriDoc.exists()) { //if document exists
                                try {
                                    cal = nutriDoc.getLong("cal").intValue();
                                    protein = nutriDoc.getLong("protein").intValue();
                                    carb = nutriDoc.getLong("carb").intValue();
                                    fat = nutriDoc.getLong("fat").intValue();
                                    Log.d("DayStats", "Nutrient data loaded successfully for today.");
                                }
                                catch (Exception e) { //fallback to defaults on error
                                    Log.w("DayStats", "Error processing nutrient data for today.", e);
                                    initNewDay();
                                }
                            }
                            else { //init new day if document doesn't exist
                                Log.d("DayStats", "Nutrient document does not exist for " +
                                        date.getDate() + ". Initializing to defaults.");
                                initNewDay();
                            }

                            //set stats data
                            if (statsDocToday != null && statsDocToday.exists()) { //if document exists
                                try {
                                    height = statsDocToday.getString("height");

                                    Double fetchedWeight = statsDocToday.getDouble("weight");
                                    if (fetchedWeight != null) { weight = fetchedWeight; }
                                    else { weight = 0.0; }

                                    Log.d("DayStats", "Stats data loaded successfully for today.");
                                    callback.onDataLoaded(DayStats.this);
                                }
                                catch (Exception e) {
                                    Log.w("DayStats", "Error processing stats data for today.", e);
                                    height = null;
                                    weight = 0.0;
                                    callback.onError(e);
                                }
                            }
                            else { //init HW if document doesn't exist
                                Log.d("DayStats", "Stats document does not exist for "
                                        + date.getDate() + ". Attempting to get most recent stats.");

                                initHW().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> initTask) {
                                        if (initTask.isSuccessful()) {
                                            Log.d("DayStats", "Most recent stats loading completed.");
                                            callback.onDataLoaded(DayStats.this);
                                        }
                                        else {
                                            Log.w("DayStats", "Error loading most recent stats.", initTask.getException());
                                            height = null;
                                            weight = 0.0;
                                            callback.onError(initTask.getException());
                                        }
                                    }
                                });
                            }

                        }
                        else { //one or more tasks failed
                            Log.w("DayStats", "Error loading initial data from Firestore.", task.getException());
                            callback.onError(task.getException());
                        }
                    }
                });
    }

    //getters
    public int getCal(){ return cal; }
    public int getProtein(){ return protein; }
    public int getCarb(){ return carb; }
    public int getFat(){ return fat; }
    public double getWeight(){ return weight; }
    public String getHeight(){ return height; }

}
