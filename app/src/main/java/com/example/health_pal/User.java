package com.example.health_pal;

import com.google.firebase.auth.FirebaseUser;

public class User {
    //variables
    private String ID, cal, protein, carb, fat, weight, date;
    public String username;
    public String email;
    //constructors

    public User() {} //default

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }
    //methods
    public static void updateUser(FirebaseUser user){ //pulls user info from firebase and database

    }
}
