package com.example.health_pal.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.health_pal.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //view model object
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        //inflate binding and get root
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //text view objects
        final TextView tvWelcome = binding.tvWelcome, tvIntakeTitle = binding.tvIntakeTitle,
                tvCalorieCount = binding.tvCalorieCount, tvCalorieHeader = binding.tvCalorieHeader,
                tvGoalHeader = binding.tvGoalHeader, tvGoalProgress = binding.tvGoalProgress;

        //set text size
        tvWelcome.setTextSize(30);
        tvIntakeTitle.setTextSize(30);
        tvCalorieCount.setTextSize(30);
        tvCalorieHeader.setTextSize(30);
        tvGoalHeader.setTextSize(30);
        tvGoalProgress.setTextSize(30);
        //set text to textviews
        homeViewModel.getTextWelcome().observe(getViewLifecycleOwner(), tvWelcome::setText);
        homeViewModel.getTextIntake().observe(getViewLifecycleOwner(), tvIntakeTitle::setText);
        homeViewModel.getTextCalorieHeader().observe(getViewLifecycleOwner(), tvCalorieHeader::setText);
        homeViewModel.getTextCalorieCount().observe(getViewLifecycleOwner(), tvCalorieCount::setText);
        homeViewModel.getTextGoalHeader().observe(getViewLifecycleOwner(), tvGoalHeader::setText);
        homeViewModel.getTextGoalProgress().observe(getViewLifecycleOwner(), tvGoalProgress::setText);


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}