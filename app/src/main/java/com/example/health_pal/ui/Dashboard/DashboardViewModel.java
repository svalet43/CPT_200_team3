package com.example.health_pal.ui.Dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.health_pal.DayStats;
import com.example.health_pal.User;
import com.example.health_pal.sDate;
import com.example.health_pal.ui.signIn.SignInFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class DashboardViewModel extends ViewModel {

    private final MutableLiveData<String> tvWelcome ,tvWelcomeMessage , tvCalorieCount, tvProCount,
            tvCarbCount , tvFatCount;
    private String userName = "user", message = "Keep up the good work!";
    private int calCount = 0, proCount = 0, carbCount = 0, fatCount = 0;

    public DashboardViewModel() {
        //initialize variables
        tvWelcome = new MutableLiveData<>();
        tvWelcomeMessage = new MutableLiveData<>();
        tvCalorieCount = new MutableLiveData<>();
        tvProCount = new MutableLiveData<>();
        tvCarbCount = new MutableLiveData<>();
        tvFatCount = new MutableLiveData<>();

        //get values for text inputs
        //FIXME: need to use database to pull data too display
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Date date = new Date();
        sDate dateString = new sDate(date);
        DayStats currDay = new DayStats(db, dateString, mAuth.getCurrentUser());
        User user = new User(mAuth);
        userName = user.getUsername();

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