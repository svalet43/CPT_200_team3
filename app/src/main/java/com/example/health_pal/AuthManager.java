package com.example.health_pal;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthManager {
    private static FirebaseAuth mAuth;
    private static FirebaseUser currentUser;
    public AuthManager(){
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
    }

    public static void signOut() {
        mAuth.signOut();
    }
    public static boolean isLoggedIn(){
        return currentUser != null;
    }
}
