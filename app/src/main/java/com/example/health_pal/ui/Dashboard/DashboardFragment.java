package com.example.health_pal.ui.Dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.health_pal.AuthManager;
import com.example.health_pal.R;
import com.example.health_pal.databinding.FragmentDashBinding;

import java.util.Timer;
import java.util.TimerTask;

public class DashboardFragment extends Fragment {

    private FragmentDashBinding binding;
    private static final String TAG = "DashFragment";


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
        //view model object
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        //inflate binding and get root
        binding = FragmentDashBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        //view objects
        final TextView tvWelcome = binding.tvWelcome, tvWelcomeMessage = binding.tvWelcomeMessage,
                tvCalorieCount = binding.tvCalorieCount, tvProCount = binding.tvProCount,
                tvCarbCount = binding.tvCarbCount, tvFatCount = binding.tvFatCount;

        //set text size
        tvWelcome.setTextSize(30);
        tvWelcomeMessage.setTextSize(30);
        tvCalorieCount.setTextSize(30);
        tvCarbCount.setTextSize(30);
        tvProCount.setTextSize(30);
        tvFatCount.setTextSize(30);


        //set text to textviews
        dashboardViewModel.getTextWelcome().observe(getViewLifecycleOwner(), tvWelcome::setText);
        dashboardViewModel.getTextIntake().observe(getViewLifecycleOwner(), tvWelcomeMessage::setText);
        dashboardViewModel.getTextProCount().observe(getViewLifecycleOwner(), tvProCount::setText);
        dashboardViewModel.getTextCalorieCount().observe(getViewLifecycleOwner(), tvCalorieCount::setText);
        dashboardViewModel.getTextCarbCount().observe(getViewLifecycleOwner(), tvCarbCount::setText);
        dashboardViewModel.getTextFatCount().observe(getViewLifecycleOwner(), tvFatCount::setText);


        return root;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}