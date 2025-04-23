package com.example.health_pal.ui.signIn;

import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.health_pal.R;
import com.example.health_pal.databinding.FragmentSigninBinding;
import com.example.health_pal.ui.Dashboard.DashboardFragment;
import com.example.health_pal.ui.Dashboard.DashboardViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInFragment extends Fragment {

    private FragmentSigninBinding binding;
    private FirebaseAuth mAuth;
    private boolean userAuth = true;
    private static final String TAG = "SignInFragment";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        Log.d(TAG, "onCreateView: SignInFragment view creating.");
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        //view model object
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        //inflate binding and get root
        binding = FragmentSigninBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //edit text
        final EditText email = binding.ETEmail, pass = binding.ETPassword, pass2 = binding.ETPassword2;

        //buttons
        Button btSignIn = binding.btSignIn, btCreateEmailAcc = binding.btCreateAcc, btCreateEmailAcc2 = binding.btCreateAcc2;

        //set visibilities
        pass2.setVisibility(View.GONE);
        btCreateEmailAcc2.setVisibility(View.GONE);

        //click listeners
        btSignIn.setOnClickListener(new View.OnClickListener() { //sign in
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: SignIn button clicked.");
                if(userAuth){
                    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
                    navController.navigate(R.id.action_nav_signIn_to_nav_dash);
                }
                else{}

            }
        });
        btCreateEmailAcc.setOnClickListener(new View.OnClickListener() { //create email account
            @Override
            public void onClick(View view) {
                //update layout objects
                pass2.setVisibility(View.VISIBLE);
                btCreateEmailAcc2.setVisibility(View.VISIBLE);

                btSignIn.setVisibility(View.GONE);
                btCreateEmailAcc.setVisibility(View.GONE);
            }
        });
        btCreateEmailAcc2.setOnClickListener(new View.OnClickListener() { //secondary account creation
            @Override
            public void onClick(View view) {

            }
        });



        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView: SignInFragment view destroyed.");
        binding = null;
    }
    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: SignInFragment started.");
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
    }

    public void signIn(){

    }
}