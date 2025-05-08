package com.example.health_pal.ui.Dashboard;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.health_pal.DayStats;
import com.example.health_pal.User;
import com.example.health_pal.sDate;
import com.example.health_pal.ui.signIn.SignInFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class DashboardViewModel extends ViewModel {

    private final MutableLiveData<String> tvWelcome;
    private final MutableLiveData<String> tvWelcomeMessage;
    private static MutableLiveData<String> tvCalorieCount = null;
    private static MutableLiveData<String> tvProCount = null;
    private static MutableLiveData<String> tvCarbCount = null;
    private static MutableLiveData<String> tvFatCount = null;
    private String userName = "user", message = "Keep up the good work!";
    private static int calCount = 0;
    private static int proCount = 0;
    private static int carbCount = 0;
    private static int fatCount = 0;

    public DashboardViewModel() {
        //initialize variables
        tvWelcome = new MutableLiveData<>();
        tvWelcomeMessage = new MutableLiveData<>();
        tvCalorieCount = new MutableLiveData<>();
        tvProCount = new MutableLiveData<>();
        tvCarbCount = new MutableLiveData<>();
        tvFatCount = new MutableLiveData<>();

        //get values for text inputs
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Date date = new Date();
        sDate dateString = new sDate(date);
        DayStats currDay = new DayStats(db, dateString, mAuth.getCurrentUser());

        //if user signed in this session
        User user;
        if(SignInFragment.signedInThisSession){
            user = SignInFragment.user;
            userName = user.getUsername();
        }
        else{
            user = new User(mAuth);
            userName = user.getUsername();

        }

        calCount = currDay.getCal();
        proCount = currDay.getProtein();
        carbCount = currDay.getCarb();
        fatCount = currDay.getFat();

        //set text
        tvWelcome.setValue("Hello " + userName);
        tvWelcomeMessage.setValue(message);
        tvCalorieCount.setValue(String.valueOf(calCount));
        tvProCount.setValue(String.valueOf(proCount));
        tvCarbCount.setValue(String.valueOf(carbCount));
        tvFatCount.setValue(String.valueOf(fatCount));
    }
    public void refresh() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Date date = new Date();
        sDate dateString = new sDate(date);

        // Update the MutableLiveData instances
        User user = new User(mAuth);
        userName = user.getUsername();
        tvWelcome.setValue("Hello " + userName);

        if (mAuth.getCurrentUser() == null) {
            tvCalorieCount.setValue("0");
            tvProCount.setValue("0");
            tvCarbCount.setValue("0");
            tvFatCount.setValue("0");
            return;
        }
        db.collection("users")
                .document(mAuth.getCurrentUser().getUid())
                .collection("days")
                .document(dateString.getDate())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                // Data exists, pull it and update LiveData
                                try {
                                    int cal = document.getLong("cal").intValue();
                                    int protein = document.getLong("protein").intValue();
                                    int carb = document.getLong("carb").intValue();
                                    int fat = document.getLong("fat").intValue();

                                    tvWelcome.setValue("Hello " + userName);
                                    tvWelcomeMessage.setValue(message);
                                    tvCalorieCount.setValue(String.valueOf(cal));
                                    tvProCount.setValue(String.valueOf(protein));
                                    tvCarbCount.setValue(String.valueOf(carb));
                                    tvFatCount.setValue(String.valueOf(fat));
                                } catch (Exception e) {
                                    // Handle potential errors in casting or reading data
                                    Log.e("DashboardViewModel", "Error parsing data from Firestore", e);
                                    // Optionally set LiveData to indicate an error or default values
                                    tvCalorieCount.setValue("Error");
                                    tvProCount.setValue("Error");
                                    tvCarbCount.setValue("Error");
                                    tvFatCount.setValue("Error");
                                }
                            }
                        }
                    }
                });
    }

    public LiveData<String> getTextWelcome() {
        return tvWelcome;
    }
    public LiveData<String> getTextIntake() {
        return tvWelcomeMessage;
    }
    public LiveData<String> getTextProCount() {
        return tvProCount;
    }
    public LiveData<String> getTextCalorieCount() {
        return tvCalorieCount;
    }
    public LiveData<String> getTextCarbCount() {
        return tvCarbCount;
    }
    public LiveData<String> getTextFatCount() {
        return tvFatCount;
    }
}