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

import com.example.health_pal.DayStats;
import com.example.health_pal.R;
import com.example.health_pal.User;
import com.example.health_pal.databinding.FragLogIntroBinding;
import com.example.health_pal.databinding.FragNutrilogBinding;
import com.example.health_pal.sDate;
import com.example.health_pal.ui.Dashboard.DashboardViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class FoodLogFragment extends Fragment {
    private FragNutrilogBinding binding;
    EditText etCal, etPro, etFat, etCarb;
    int cal, pro, fat, carb;

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

        DayStats currDay = DashboardViewModel.currDay;


        //click listener
        Button btSub = binding.btSubmitNutri;
        btSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cal = Integer.parseInt(etCal.getText().toString());
                pro = Integer.parseInt(etPro.getText().toString());
                fat = Integer.parseInt(etFat.getText().toString());
                carb = Integer.parseInt(etCarb.getText().toString());

                currDay.addNutrient("cal", cal);
                currDay.addNutrient("protein", pro);
                currDay.addNutrient("fat", fat);
                currDay.addNutrient("carb", carb);

                currDay.pushToNutriDatabase();
                //navigate back to dashboard
                navController.navigate(R.id.action_nav_food_log_to_nav_dash);
            }
        });

        return root;
    }
}