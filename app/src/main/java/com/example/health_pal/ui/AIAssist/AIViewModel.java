package com.example.health_pal.ui.AIAssist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AIViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public AIViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("AI ASSIST TEST");
    }



    public LiveData<String> getText() {
        return mText;
    }
}
