package com.example.mitraproject;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Optional: Navigation setup if you're adding bottom navigation
        // NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
        //         .findFragmentById(R.id.nav_host_fragment);
        // NavController navController = navHostFragment.getNavController();
        // NavigationUI.setupActionBarWithNavController(this, navController);
    }

    // Optional: To handle up navigation
    @Override
    public boolean onSupportNavigateUp() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        return navController.navigateUp() || super.onSupportNavigateUp();
    }
}
