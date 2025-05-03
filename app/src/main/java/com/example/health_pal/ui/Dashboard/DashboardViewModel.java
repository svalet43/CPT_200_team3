package com.example.health_pal.ui.Dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.health_pal.DayStats;
import com.example.health_pal.User;
import com.example.health_pal.ui.signIn.SignInFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class DashboardViewModel extends ViewModel {

    private final MutableLiveData<String> tvWelcome ,tvWelcomeMessage , tvCalorieCount, tvCalorieHeader,
            tvGoalHeader , tvGoalProgress;
    private String userName = "user", message = "Keep up the good work!";
    private int calCount = 0;

    public DashboardViewModel() {
        //initialize variables
        tvWelcome = new MutableLiveData<>();
        tvWelcomeMessage = new MutableLiveData<>();
        tvCalorieCount = new MutableLiveData<>();
        tvCalorieHeader = new MutableLiveData<>();
        tvGoalHeader = new MutableLiveData<>();
        tvGoalProgress = new MutableLiveData<>();

        //get values for text inputs
        //FIXME: need to use database to pull data too display
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Date date = new Date();
        DayStats dayStats = new DayStats(db, date, mAuth.getCurrentUser());
        User user = new User(mAuth);
        userName = user.getUsername();
        calCount = dayStats.getCal();

        //set text
        tvWelcome.setValue("Hello " + userName);
        tvWelcomeMessage.setValue(message);
        tvCalorieHeader.setValue("Calories Today:");
        tvCalorieCount.setValue(String.valueOf(calCount));
    }

    public LiveData<String> getTextWelcome() {
        return tvWelcome;
    }
    public LiveData<String> getTextIntake() {
        return tvWelcomeMessage;
    }
    public LiveData<String> getTextCalorieHeader() {
        return tvCalorieHeader;
    }
    public LiveData<String> getTextCalorieCount() {
        return tvCalorieCount;
    }
    public LiveData<String> getTextGoalHeader() {
        return tvGoalHeader;
    }
    public LiveData<String> getTextGoalProgress() {
        return tvGoalProgress;
    }
}