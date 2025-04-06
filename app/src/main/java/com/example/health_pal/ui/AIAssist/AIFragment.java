package com.example.health_pal.ui.AIAssist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.health_pal.databinding.FragmentAiBinding;
import com.example.health_pal.databinding.FragmentCalorieBinding;
import com.example.health_pal.ui.AIAssist.AIViewModel;

public class AIFragment extends Fragment {
    private FragmentAiBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AIViewModel aivewmodel  =
                new ViewModelProvider(this).get(AIViewModel.class);

        binding = FragmentAiBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
