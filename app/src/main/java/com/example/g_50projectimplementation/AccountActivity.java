package com.example.g_50projectimplementation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

import java.util.Objects;

public class AccountActivity extends AppCompatActivity {

    private TextView usernameTextView;
    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_account);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Util.fixStatusBarColorLight(getWindow(), this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        usernameTextView = findViewById(R.id.usernameTextView);
        logoutButton = findViewById(R.id.logoutBtn);

        logoutButton.setOnClickListener(v -> {
            SharedPreferences currentUserPref = getSharedPreferences("com.gbc.g50.CurrentUser", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = currentUserPref.edit();
            editor.clear();
            editor.apply();
            Toast.makeText(this, "Logged out successfully", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, CMHomePage.class);
            startActivity(intent);
        });

        SharedPreferences currentUserPref = getSharedPreferences("com.gbc.g50.CurrentUser", Context.MODE_PRIVATE);
        String displayName = currentUserPref.getString("DisplayName", "");
        if(displayName.isBlank()) {
            Toast.makeText(this, "Please login or signup to continue", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, CMHomePage.class);
            startActivity(intent);
        }
        usernameTextView.setText(displayName);
    }
}