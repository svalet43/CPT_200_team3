package com.example.health_pal.ui.Dashboard;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.health_pal.DayStats;
import com.example.health_pal.User;
import com.example.health_pal.sDate;
import com.example.health_pal.ui.AIAssist.gemini;
import com.example.health_pal.ui.AIAssist.responseCallback;
import com.example.health_pal.ui.calorie.StatLogFragment;
import com.example.health_pal.ui.signIn.SignInFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
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

    public static DayStats currDay;

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

        User user = SignInFragment.user;

        if (user == null) { user = new User(mAuth.getCurrentUser()); }
        userName = user.getUsername();

        Date date = new Date();
        sDate dateString = new sDate(date);
        currDay = new DayStats(db, dateString, mAuth.getCurrentUser());

        currDay.loadAllDataFromDatabase(new DayStats.DayStatsLoadCallback() {
            @Override
            public void onDataLoaded(DayStats loadedDayStats) {
                calCount = currDay.getCal();
                proCount = currDay.getProtein();
                carbCount = currDay.getCarb();
                fatCount = currDay.getFat();

                tvWelcome.setValue("Hello " + userName);

                tvCalorieCount.setValue(String.valueOf(calCount));
                tvProCount.setValue(String.valueOf(proCount));
                tvCarbCount.setValue(String.valueOf(carbCount));
                tvFatCount.setValue(String.valueOf(fatCount));
            }
            @Override
            public void onError(Exception e) {
                Log.e("DashboardViewModel", "Error loading data", e);
            }
        });
        generateWelcomeMessage();

    }

    public void refresh() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Date date = new Date();
        sDate dateString = new sDate(date);

        tvWelcome.setValue("Hello " + userName);
        //generateWelcomeMessage();

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

                                    tvWelcomeMessage.setValue(message);
                                    tvCalorieCount.setValue(String.valueOf(cal));
                                    tvProCount.setValue(String.valueOf(protein));
                                    tvCarbCount.setValue(String.valueOf(carb));
                                    tvFatCount.setValue(String.valueOf(fat));
                                } catch (Exception e) {
                                    Log.e("DashboardViewModel", "Error parsing data from Firestore", e);
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

    public void generateWelcomeMessage() {
        gemini model = new gemini();
        String prompt = "Write a unique motivational welcome message that is 15 words or less";

        model.getResponse(prompt, new responseCallback() {
            @Override
            public void onResponse(String response) {
                tvWelcomeMessage.postValue(response);
            }

            @Override
            public void onError(Throwable throwable) {
                Log.e("DashboardViewModel", "Error generating welcome message", throwable);
                tvWelcomeMessage.postValue("Could not generate welcome message.");
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