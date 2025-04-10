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
    //EditText email, password, password2;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        Log.d(TAG, "onCreateView: SignInFragment view creating.");
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        //edit text obj
        //final EditText email = binding.ETEmail;
        //String username = email.getText().toString();
        // Create new fragment and transaction
        //FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        //FragmentTransaction transaction = fragmentManager.beginTransaction();
        //transaction.setReorderingAllowed(true);


        //view model object
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        //inflate binding and get root
        binding = FragmentSigninBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //sign in
        Button btSignIn = binding.btSignIn;
        btSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: SignIn button clicked.");
                if(userAuth){
                    FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();

                    // Replace whatever is in the nav_host_fragment_content_main with the DashboardFragment
                    transaction.replace(R.id.content_main, new DashboardFragment());
                    Log.d(TAG, "onClick: Replace transaction initiated.");

                    // Commit the transaction
                    transaction.commit();
                    Log.d(TAG, "onClick: Replace transaction committed.");
                }
                else{}

            }
        });
        //create email account
        Button btCreateEmailAcc = binding.btCreateAcc;
        btCreateEmailAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open account creation fragment
                //call creation method
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