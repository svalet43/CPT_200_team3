package com.example.health_pal.ui.Dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.health_pal.databinding.FragmentDashBinding;

public class DashboardFragment extends Fragment {

    private FragmentDashBinding binding;
    private static final String TAG = "DashFragment";


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        Log.d(TAG, "onCreateView: DashboardFragment view creating.");
        //view model object
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        //inflate binding and get root
        binding = FragmentDashBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        //view objects
        final TextView tvWelcome = binding.tvWelcome, tvWelcomeMessage = binding.tvWelcomeMessage,
                tvCalorieCount = binding.tvCalorieCount, tvCalorieHeader = binding.tvCalorieHeader,
                tvGoalHeader = binding.tvGoalHeader, tvGoalProgress = binding.tvGoalProgress;

        //set text size
        tvWelcome.setTextSize(30);
        tvWelcomeMessage.setTextSize(30);
        tvCalorieCount.setTextSize(30);
        tvCalorieHeader.setTextSize(30);
        tvGoalHeader.setTextSize(30);
        tvGoalProgress.setTextSize(30);


        //set text to textviews
        dashboardViewModel.getTextWelcome().observe(getViewLifecycleOwner(), tvWelcome::setText);
        dashboardViewModel.getTextIntake().observe(getViewLifecycleOwner(), tvWelcomeMessage::setText);
        dashboardViewModel.getTextCalorieHeader().observe(getViewLifecycleOwner(), tvCalorieHeader::setText);
        dashboardViewModel.getTextCalorieCount().observe(getViewLifecycleOwner(), tvCalorieCount::setText);
        dashboardViewModel.getTextGoalHeader().observe(getViewLifecycleOwner(), tvGoalHeader::setText);
        dashboardViewModel.getTextGoalProgress().observe(getViewLifecycleOwner(), tvGoalProgress::setText);


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}