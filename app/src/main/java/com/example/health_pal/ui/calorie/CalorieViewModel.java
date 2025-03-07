package com.example.health_pal.ui.calorie;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CalorieViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public CalorieViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Enter the name of your food:");
    }

    public LiveData<String> getText() {
        return mText;
    }
}