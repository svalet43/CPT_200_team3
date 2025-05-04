package com.example.health_pal.ui.calorie;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.health_pal.R;
import com.example.health_pal.databinding.FragLogIntroBinding;
import com.example.health_pal.databinding.FragNutrilogBinding;

public class FoodLogFragment extends Fragment {
    private FragNutrilogBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragNutrilogBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
        //navController.navigate(R.id.action_nav_calorie_to_nav_dash);

        return root;
    }
}