package com.example.health_pal.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> tvWelcome , tvIntakeTitle, tvCalorieCount, tvCalorieHeader, tvGoalHeader , tvGoalProgress;

    public HomeViewModel() {
        tvWelcome = new MutableLiveData<>();
        tvIntakeTitle = new MutableLiveData<>();
        tvCalorieCount = new MutableLiveData<>();
        tvCalorieHeader = new MutableLiveData<>();
        tvGoalHeader = new MutableLiveData<>();
        tvGoalProgress = new MutableLiveData<>();

        //get values for text
        //FIXME: need to use database to pull data too display
        //set text
        tvWelcome.setValue("Hello userX");
        tvIntakeTitle.setValue("Intake Metrics");
        tvCalorieHeader.setValue("Total Calories Today");
        tvCalorieCount.setValue("500");
        tvGoalHeader.setValue("Goals");
        tvGoalProgress.setValue("");
    }

    public LiveData<String> getTextWelcome() {
        return tvWelcome;
    }
    public LiveData<String> getTextIntake() {
        return tvIntakeTitle;
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