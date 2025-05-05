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
import com.example.health_pal.databinding.FragNutrilogBinding;
import com.example.health_pal.databinding.FragStatLogBinding;
import com.example.health_pal.sDate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class StatLogFragment extends Fragment {
    private FragStatLogBinding binding;
    EditText etFt, etIn, etWeight;
    String ft, in, fullHeight;
    double weight;

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

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        User user = new User(mAuth);
        Date date = new Date();
        sDate dateString = new sDate(date);
        DayStats currDay = new DayStats(db, dateString, mAuth.getCurrentUser());

        Button btSub = binding.btSubmitStat;
        btSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ft = etFt.getText().toString();
                in = etIn.getText().toString();
                if(ft.length() != 0){ fullHeight = ft + "'" + in + "\""; }
                if(fullHeight != null){ currDay.updateHeight(fullHeight); }

                weight = Double.parseDouble(etWeight.getText().toString());
                if(weight != 0){ currDay.updateWeight(weight); }

                currDay.pushToDatabase();
                //navigate back to dashboard
                navController.navigate(R.id.action_nav_stat_log_to_nav_dash);
            }
        });

        return root;
    }
}