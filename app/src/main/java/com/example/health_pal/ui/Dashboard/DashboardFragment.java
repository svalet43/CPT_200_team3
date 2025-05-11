package com.example.health_pal.ui.Dashboard;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.health_pal.R;
import com.example.health_pal.Settings;
import com.example.health_pal.databinding.FragmentDashBinding;

public class DashboardFragment extends Fragment {

    private FragmentDashBinding binding;
    private Handler refreshHandler;
    private Runnable refreshRunnable;
    private static final int refresh = 10000;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

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
        tvWelcomeMessage.setTextSize(20);
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

        //init refresh
        refreshHandler = new Handler(Looper.getMainLooper());
        refreshRunnable = new Runnable() {
            @Override
            public void run() {
                dashboardViewModel.refresh();
                refreshHandler.postDelayed(this, refresh);
            }
        };

        return root;
    }
    @Override
    public void onResume() {
        super.onResume();
        if(Settings.signedOut){
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
            navController.navigate(R.id.action_nav_dash_to_nav_signIn);
        }
        // update user data
        refreshHandler.postDelayed(refreshRunnable, refresh);

    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        refreshHandler.postDelayed(refreshRunnable, refresh);
    }

    @Override
    public void onPause() {
        super.onPause();
        refreshHandler.removeCallbacks(refreshRunnable);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        refreshHandler.removeCallbacks(refreshRunnable);
        binding = null;
    }
}