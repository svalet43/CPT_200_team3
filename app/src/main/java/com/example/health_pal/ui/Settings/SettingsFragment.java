package com.example.health_pal.ui.Settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.health_pal.R;
import android.util.Log;

import android.widget.Toast; //debugging purposes

import com.example.health_pal.databinding.FragmentSettingsBinding;
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
        SwitchCompat dark_switch;

        dark_switch = view.findViewById(R.id.switch1);
        Toast.makeText(getContext(), "Fragment loaded", Toast.LENGTH_SHORT).show();

        dark_switch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Toast.makeText(getContext(), "TEST NX ON", Toast.LENGTH_SHORT).show();
                Log.d("TEST", savedInstanceState.toString());
            }
            else {
                Toast.makeText(getContext(), "TEST NX OFF", Toast.LENGTH_SHORT).show();
            }
        });

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