package com.example.health_pal;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class User {
    /*this class is for creating a user object
    * Variables:
    * email = users email pulled from firebase
    * username = generated from email*/
    //variables
    private String email, username = "", ID = "";
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    //constructor
    public User(FirebaseAuth m_Auth) {
        if(AuthManager.isLoggedIn()) { //check
            mAuth = m_Auth;
            currentUser = mAuth.getCurrentUser();
            email = currentUser.getEmail();

            if(email != null){
                int amperIndex = 0;
                for(int i=0; i<email.length();i++){
                    if(email.charAt(i) == '@'){ amperIndex = i; }
                }
                username = email.substring(0, amperIndex);
                ID = currentUser.getUid();
            }
        }
    }
    //getters
    public String getEmail(){ return email; }
    public String getUsername(){ return username; }
    public String getID(){ return ID;}
    //methods
    public static void updateUser(User user){ //updates user info in app
    }
}
