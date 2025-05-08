package com.example.health_pal;

import static java.lang.Double.parseDouble;

import android.util.Log;

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
       private sDate date;
    //constructor
    public DayStats(FirebaseFirestore inputDB, sDate inputDate, FirebaseUser inputUser){
        db = inputDB;
        date = inputDate;
        user = inputUser;
        DayStatsConstructorHelper();
    }
    private void DayStatsConstructorHelper(){
        //if date is not already in database then create new row, otherwise pull date data
        db.collection("users")
                .document(user.getUid()) //
                .collection("days")
                .document(date.getDate())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                pullFromDatabase(document);
                            }
                            else {
                                initializeNewDay();
                            }
                        }
                        else {
                            initializeNewDay();
                        }
                    }
                });
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
        if(height == null){
            DayStats prevDay = getPreviousDay(date);
            height = prevDay.getHeight();
        }
        else{ dayData.put("height", height); }
        if(weight == 0){
            DayStats prevDay = getPreviousDay(date);
            weight = prevDay.getWeight();
        }
        else{ dayData.put("weight", weight); }
        db.collection("users")
                .document(user.getUid())
                .collection("stats")
                .document(date.getDate())
                .set(dayData)
                .addOnSuccessListener(aVoid -> {
                    Log.w("DayStats", "Success writing document");
                })
                .addOnFailureListener(e -> {
                    Log.w("DayStats", "Error writing document", e);
                });
    }
    public DocumentSnapshot getDoc(){
        final DocumentSnapshot[] document = {null};
        db.collection("users")
                .document(user.getUid()) //
                .collection("days")
                .document(date.getDate())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            document[0] = task.getResult();
                        }
                    }
                });
        return document[0];
    }
    public void pullFromDatabase(DocumentSnapshot doc){
        // Retrieve the data from the DocumentSnapshot
        cal = doc.getLong("cal").intValue();
        protein = doc.getLong("protein").intValue();
        carb = doc.getLong("carb").intValue();
        fat = doc.getLong("fat").intValue();
        try{
        weight = doc.getDouble("weight");}
        catch(Exception e){
            weight = 0;
        }
        try{
        height = doc.getString("height");}
        catch(Exception e){
            height = null;
        }

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
    private void initializeNewDay() {
        // Set initial values for a new day
        cal = 0;
        protein = 0;
        carb = 0;
        fat = 0;
        //look for height and weight in user profile
        try {
            DayStats prevDay = getPreviousDay(date);
            height = prevDay.getHeight();
            weight = prevDay.getWeight();
        }catch(Exception e){
            height = null;
            weight = 0;
        }
    }
    public DayStats getPreviousDay(sDate currDate) {
        //get previous day
        sDate prevDate = currDate;
        String dateString = prevDate.getDate();

        String dateStringMonth = dateString.substring(0, 2);
        String dateStringDay = dateString.substring(3,5);
        String dateStringYear = dateString.substring(6);
        int month = Integer.parseInt(dateStringMonth);
        int day = Integer.parseInt(dateStringDay);
        int year = Integer.parseInt(dateStringYear);

        if(day == 1){ //if first day
            if(month != 1) { //if not first month
                month--;
                if (month == 2) { day = 28; }
                else if (month == 4 || month == 6 || month == 9 || month == 11) { day = 30; }
                else if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) { day = 31; }
            }
            else{ //if first month
                month = 12;
                day = 31;
                year--;}
        }
        else{ day--; } //if first day

        prevDate = new sDate(month, day, year);
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
    public sDate getDate(){ return date; }
}
