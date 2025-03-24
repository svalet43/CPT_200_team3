package com.example.health_pal.ui.signIn;

import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.credentials.CredentialManager;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.health_pal.databinding.FragmentDashBinding;
import com.example.health_pal.databinding.FragmentSigninBinding;
import com.example.health_pal.ui.Dashboard.DashboardViewModel;

public class SignInFragment extends Fragment {

    private FragmentSigninBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //view model object
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        //inflate binding and get root
        binding = FragmentSigninBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        CredentialManager credentialManager = CredentialManager.create(requireContext());

        //view objects




        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}