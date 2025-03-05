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
                tvCalorieCount = binding.tvCalorieCount, tvCalories = binding.tvCalories,
                tvGoalHeader = binding.tvGoalHeader, tvGoalProgress = binding.tvGoalProgress;

        homeViewModel.getText().observe(getViewLifecycleOwner(), tvWelcome::setText);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}