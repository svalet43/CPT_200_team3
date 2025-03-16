package com.example.health_pal.ui.Dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DashboardViewModel extends ViewModel {

    private final MutableLiveData<String> tvWelcome ,tvWelcomeMessage , tvCalorieCount, tvCalorieHeader,
            tvGoalHeader , tvGoalProgress;
    private String userName = "user", message = "Keep up the good work!";
    private int calCount = 0;

    public DashboardViewModel() {
        tvWelcome = new MutableLiveData<>();
        tvWelcomeMessage = new MutableLiveData<>();
        tvCalorieCount = new MutableLiveData<>();
        tvCalorieHeader = new MutableLiveData<>();
        tvGoalHeader = new MutableLiveData<>();
        tvGoalProgress = new MutableLiveData<>();

        //get values for text inputs
        //FIXME: need to use database to pull data too display
        //set text
        tvWelcome.setValue("Hello " + userName);
        tvWelcomeMessage.setValue(message);
        tvCalorieHeader.setValue("Calories Today:");
        tvCalorieCount.setValue(String.valueOf(calCount));
        tvGoalHeader.setValue("Goals:");
        tvGoalProgress.setValue("");
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