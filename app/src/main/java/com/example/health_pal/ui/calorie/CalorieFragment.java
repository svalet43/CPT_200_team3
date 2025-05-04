package com.example.health_pal.ui.calorie;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.health_pal.R;
import com.example.health_pal.databinding.FragLogIntroBinding;
import com.example.health_pal.databinding.FragmentCalorieBinding;

public class CalorieFragment extends Fragment {

    private FragLogIntroBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CalorieViewModel calorieViewModel =
                new ViewModelProvider(this).get(CalorieViewModel.class);

        binding = FragLogIntroBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
        //click listeners
        Button btStat = binding.btHw;
        btStat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_nav_calorie_to_nav_stat_log);
            }
        });
        Button btNutri = binding.btNutrition;
        btNutri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_nav_calorie_to_nav_food_log);
            }
        });
        
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}