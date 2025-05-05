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
import com.example.health_pal.databinding.FragNutrilogBinding;
import com.example.health_pal.databinding.FragStatLogBinding;

public class StatLogFragment extends Fragment {
    private FragStatLogBinding binding;
    EditText etFt, etIn, etWeight;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragStatLogBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
        etFt = binding.ETFt;
        etIn = binding.ETIn;
        etWeight = binding.ETWeight;

        Button btSub = binding.btSubmitStat;
        btSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check if day is in database
                //if yes pull data and add new data
                //if not create new day and push to database
                //navigate back to dashboard
                navController.navigate(R.id.action_nav_stat_log_to_nav_dash);
            }
        });

        return root;
    }
}