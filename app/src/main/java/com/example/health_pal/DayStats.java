package com.example.health_pal;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class DayStats {
    /*this class creates a DayStats object and
    *pushes/pulls data from firebase
    *stores each day as an object with that day's nutrients
    * tracks  cal, protein, carb, fat, weight, date*/
       private FirebaseFirestore db;
       private FirebaseUser user;
       private String cal, protein, carb, fat, weight;
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
    }
    //methods
    public void PushToDatabase(){
        db.collection("users").document(user.toString()).collection("days").document(date.toString());
    }
    public void PullFromDatabase(){}

}
