package com.example.health_pal;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthManager {
    /* this class is used to tell if a user is signed in or not*/
    private static FirebaseAuth mAuth;
    private static FirebaseUser currentUser;

    //constructor
    public AuthManager(){
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
    }
    //methods
    public static void signOut() { //signs current user out
        mAuth.signOut();
    }
    public static boolean isLoggedIn(){ //returns boolean true if a user is signed in, false otherwise
        return currentUser != null;
    }
}
