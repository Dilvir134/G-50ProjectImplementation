package com.example.g_50projectimplementation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Objects;

public class Signup2Activity extends AppCompatActivity {

    private EditText fullNameInput;
    private EditText displayNameInput;
    private Button actionBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Util.fixStatusBarColorLight(getWindow(), this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        fullNameInput = findViewById(R.id.inputfullName);
        displayNameInput = findViewById(R.id.inputDisplayName);
        actionBtn = findViewById(R.id.actionBtn);


        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        SharedPreferences currentUserPref = getSharedPreferences("com.gbc.g50.CurrentUser", Context.MODE_PRIVATE);

         String phone = currentUserPref.getString("Phone", "");

         if(phone.equals("")){
             throw new IllegalStateException("Phone number not set!");
         }


        actionBtn.setOnClickListener(v -> {
            String fullName = fullNameInput.getText().toString();

            if(fullName == null || fullName.trim().isEmpty()){
                Toast.makeText(this, "Please enter your full name", Toast.LENGTH_LONG).show();
                return;
            }

            String displayName = displayNameInput.getText().toString();

            if(displayName == null || displayName.trim().isEmpty()) {
                String[] parts = fullName.split(" ");
                displayName = parts[0].trim();
            }

            SharedPreferences.Editor editor = currentUserPref.edit();
            editor.putString("FullName", fullName);
            editor.putString("DisplayName", displayName);
            editor.apply();

            SharedPreferences sharedPref = getSharedPreferences(
                    "com.gbc.g50.PHONENAME", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor2 = sharedPref.edit();
            editor2.putString(phone, fullName);
            editor2.apply();


            SharedPreferences sharedPref3 = getSharedPreferences(
                    "com.gbc.g50.PHONEDISPLAY", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor3 = sharedPref3.edit();
            editor3.putString(phone, displayName);
            editor3.apply();

            goToHome();

        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void goToHome(){
        Intent intent = new Intent(Signup2Activity.this, HomeManager.class);
        startActivity(intent);
    }
}