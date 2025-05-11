package com.example.health_pal.ui.Settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.health_pal.R;
import android.util.Log;

import android.widget.Toast; //debugging purposes

import com.example.health_pal.databinding.FragmentSettingsBinding;
import com.example.health_pal.ui.signIn.SignInFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

// TODO: Dataflow Diagram - IB
// TODO: Fix this mess, figure out making it actually work.
public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SettingsViewModel settingsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        Toast.makeText(getContext(), "Fragment loaded", Toast.LENGTH_SHORT).show();


        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        //View view = binding.getRoot();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}