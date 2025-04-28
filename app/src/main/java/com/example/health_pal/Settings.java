package com.example.health_pal;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.health_pal.ui.signIn.SignInFragment;
import com.google.android.material.snackbar.Snackbar;

// Should receive search query, search your data, and display search results
// TODO: Cleanup

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_settings);

        // enable back button in the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //sign out button
        Button btSignOut = findViewById(R.id.btSignOut);
        btSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignInFragment.signOut();
                Snackbar.make(view, "Signed out", Snackbar.LENGTH_LONG).show();
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_action_settings_to_nav_signIn);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // Close the activity and return to the previous screen
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
