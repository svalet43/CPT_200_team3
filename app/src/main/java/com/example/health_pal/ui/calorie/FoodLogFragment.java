package com.example.health_pal.ui.calorie;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.health_pal.R;
import com.example.health_pal.databinding.FragLogIntroBinding;
import com.example.health_pal.databinding.FragNutrilogBinding;

public class FoodLogFragment extends Fragment {
    private FragNutrilogBinding binding;
    EditText etCal, etPro, etFat, etCarb;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragNutrilogBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
        etCal = binding.ETCal;
        etPro = binding.ETPro;
        etFat = binding.ETFat;
        etCarb = binding.ETCarb;

        //click listener
        Button btSub = binding.btSubmitNutri;
        btSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check if day is in database
                //if yes pull data and add new data
                //if not create new day and push to database
                //navigate back to dashboard
                navController.navigate(R.id.action_nav_food_log_to_nav_dash);
            }
        });

        return root;
    }
}