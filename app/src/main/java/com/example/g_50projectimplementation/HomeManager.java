package com.example.g_50projectimplementation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.window.OnBackInvokedDispatcher;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.g_50projectimplementation.databinding.ActivityHomeManagerBinding;

public class HomeManager extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityHomeManagerBinding binding;

    private TextView namelabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeManagerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarHomeManager.toolbar);
        binding.appBarHomeManager.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .setAnchorView(R.id.fab).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home_manager);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        Util.fixStatusBarColorLight(getWindow(), this);

        ImageButton clientsBtn = findViewById(R.id.btn_clients);
        clientsBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, ClientListActivity.class);
            startActivity(intent);
        });

        ImageButton resBtn = findViewById(R.id.btn_team);
        resBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, TeamResourcesActivity.class);
            startActivity(intent);
        });

        namelabel = findViewById(R.id.label_userDisplayName);
        SharedPreferences currentUserPref = getSharedPreferences("com.gbc.g50.CurrentUser", Context.MODE_PRIVATE);
        String displayName = currentUserPref.getString("DisplayName", "");
        if(displayName.isBlank()) {
            Toast.makeText(this, "Please login or signup to continue", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, CMHomePage.class);
            startActivity(intent);
        }
        namelabel.setText(displayName);

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Do nothing to block the back button
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_manager, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        return true;
    }

}