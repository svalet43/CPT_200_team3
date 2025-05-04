package com.example.health_pal;

import static java.lang.Double.parseDouble;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Date;
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
       private Date date;
    //constructor
    public DayStats(FirebaseFirestore inputDB, Date inputDate, FirebaseUser inputUser){
        db = inputDB;
        date = inputDate;
        user = inputUser;
        DayStatsConstructorHelper();
    }
    private void DayStatsConstructorHelper(){
        //if date is not already in database then create new row, otherwise pull date data
        db.collection("users")
                .document(user.getUid()) // Use getUid() for user document
                .collection("days")
                .document(date.toString()) // Assuming date.toString() provides a unique document ID
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                // Data exists, pull it
                                pullFromDatabase(document);
                            }
                            else {
                                // Data doesn't exist for this date, initialize and push
                                initializeNewDay();
                                pushToDatabase();
                            }
                        }
                        else {
                            // Handle potential errors when fetching the document
                            // You might want to log the error or handle it based on your app's needs
                            // Log.e("DayStats", "Error getting day stats", task.getException());
                            // Depending on the error, you might still want to initialize a new day
                            initializeNewDay();
                            pushToDatabase();
                        }
                    }
                });
    }
    //methods
    public void pushToDatabase(){
        // Create a Map to hold the data
        Map<String, Object> dayData = new HashMap<>();
        dayData.put("cal", cal);
        dayData.put("protein", protein);
        dayData.put("carb", carb);
        dayData.put("fat", fat);
        dayData.put("height", height);
        dayData.put("weight", weight);
        // You don't need to store the date field explicitly in the document
        // if the document ID is the date string.

        db.collection("users")
                .document(user.getUid()) // Use getUid() for user document
                .collection("days")
                .document(date.toString()) // Assuming date.toString() provides a unique document ID
                .set(dayData) // Use set() to create or overwrite the document
                .addOnSuccessListener(aVoid -> {
                    // Document successfully written
                    // You might want to add a success log or callback
                })
                .addOnFailureListener(e -> {
                    // Handle potential errors when writing the document
                    // Log.w("DayStats", "Error writing document", e);
                });
    }
    public void pullFromDatabase(DocumentSnapshot doc){
        // Retrieve the data from the DocumentSnapshot
        cal = Integer.parseInt(doc.getString("cal"));;
        protein = Integer.parseInt(doc.getString("protein"));
        carb = Integer.parseInt(doc.getString("carb"));
        fat = Integer.parseInt(doc.getString("fat"));
        weight = parseDouble(doc.getString("weight"));

    }
    public void addNutrient(String nutrient, int amount){
        switch (nutrient){
            case "carb":
                carb += amount;
                pushToDatabase();
                break;
            case "protein":
                protein += amount;
                pushToDatabase();
                break;
            case "fat":
                fat += amount;
                pushToDatabase();
                break;
            case "cal":
                cal += amount;
                pushToDatabase();
                break;
            default:
                break;
        }
    }
    public void updateStats(){

    }
    private void initializeNewDay() {
        // Set initial values for a new day
        cal = 0;
        protein = 0;
        carb = 0;
        fat = 0;
        //look for height and weight in user profile
        DayStats prevDay = getPreviousDay();
        height = prevDay.getHeight();
        weight = prevDay.getWeight();
    }
    public DayStats getPreviousDay() {
        //get previous day
        Date prevDate;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_WEEK, -1);
        prevDate = calendar.getTime();

        DayStats returnDay = new DayStats(db, prevDate, user);
        return returnDay;
    }
    //getters
    public int getCal(){ return cal; }
    public int getProtein(){ return protein; }
    public int getCarb(){ return carb; }
    public int getFat(){ return fat; }
    public double getWeight(){ return weight; }
    public String getHeight(){ return height; }
    public Date getDate(){ return date; }
}
