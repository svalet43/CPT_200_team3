package com.example.health_pal.ui.signIn;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.health_pal.R;
import com.example.health_pal.User;
import com.example.health_pal.databinding.FragmentSigninBinding;
import com.example.health_pal.ui.Dashboard.DashboardViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignInFragment extends Fragment {
    public static User user;
    private FragmentSigninBinding binding;
    private FirebaseAuth mAuth;
    private String email, password, password2,
            passwordReq = "Password must be at least 8 characters long and contain the following: " +
                    "\n- One digit (0-9)\n- One Special character(!@#$%^&*?_=+)\n- One lowercase letter " +
                    "(a-z)\n- One uppercase letter(A-Z)";
    private static final String TAG = "SignInFragment";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        //view model object
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        //inflate binding and get root
        binding = FragmentSigninBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //edit text
        final EditText ETemail = binding.ETEmail, ETpass = binding.ETPassword,
                ETpass2 = binding.ETPassword2;

        //buttons
        Button btSignIn = binding.btSignIn, btCreateEmailAcc = binding.btCreateAcc,
                btCreateEmailAcc2 = binding.btCreateAcc2;

        //set visibilities
        ETpass2.setVisibility(View.GONE);
        btCreateEmailAcc2.setVisibility(View.GONE);

        //nav controller
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);

        //click listeners
        btSignIn.setOnClickListener(new View.OnClickListener() { //sign in
            @Override
            public void onClick(View view) {
                email = ETemail.getText().toString();
                password = ETpass.getText().toString();
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    user = new User(mAuth);
                                    // change fragment
                                    navController.navigate(R.id.action_nav_signIn_to_nav_dash);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Snackbar.make(view, "Authentication Failed", Snackbar.LENGTH_LONG).show();
                                }
                            }
                        });

            }
        });
        btCreateEmailAcc.setOnClickListener(new View.OnClickListener() { //create email account
            @Override
            public void onClick(View view) {
                //update layout objects
                ETpass2.setVisibility(View.VISIBLE);
                btCreateEmailAcc2.setVisibility(View.VISIBLE);

                btSignIn.setVisibility(View.GONE);
                btCreateEmailAcc.setVisibility(View.GONE);
            }
        });
        btCreateEmailAcc2.setOnClickListener(new View.OnClickListener() { //secondary account creation
            @Override
            public void onClick(View view) {
                //get inputs
                email = ETemail.getText().toString();
                password = ETpass.getText().toString();
                password2 = ETpass2.getText().toString();

                //validate email
                if(!email.contains("@") || !email.contains(".")){
                    Snackbar.make(view, "Invalid Email", Snackbar.LENGTH_LONG).show();
                }
                else{
                    //password params
                    if(password.equals(password2)){ //passwords match
                        if(Password_Validation(password)){//valid password
                            mAuth.createUserWithEmailAndPassword(email, password)
                                    .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {
                                                // Sign in success, update UI with the signed-in user's information
                                                user = new User(mAuth);
                                                //change fragment
                                                navController.navigate(R.id.action_nav_signIn_to_nav_dash);
                                            } else {
                                                // If sign in fails, display a message to the user.
                                                Snackbar.make(view, "Authentication Failed", Snackbar.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                        }
                        else{//invalid pasword
                            //snack bar object
                            Snackbar snackbar = Snackbar.make(view, passwordReq, Snackbar.LENGTH_LONG);
                            //set snackbar height
                            snackbar.setTextMaxLines(6);
                            //show snackbar
                            snackbar.show();
                        }
                    }
                    else{
                        Snackbar.make(view, "Passwords do not match.", Snackbar.LENGTH_LONG).show();
                    }
                }
            }
        });



        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
            navController.navigate(R.id.action_nav_signIn_to_nav_dash);
        }
    }
    public static boolean Password_Validation(String password) { //returns true if password meets requirements
        if(password.length()>=8){ // if length is at least 8
            //contains one lowercase, one uppercase, and one special character
            Pattern lower = Pattern.compile("[a-z]");
            Pattern upper = Pattern.compile("[A-Z]");
            Pattern digit = Pattern.compile("[0-9]");
            Pattern special = Pattern.compile ("[!@#$%&*_+=?]");

            Matcher hasLower = lower.matcher(password);
            Matcher hasUpper = upper.matcher(password);
            Matcher hasDigit = digit.matcher(password);
            Matcher hasSpecial = special.matcher(password);

            return hasLower.find() &&hasUpper.find() && hasDigit.find() && hasSpecial.find();

        }
        else
            return false;

    }
    public static void signOut(){
        FirebaseAuth.getInstance().signOut();
    }
}