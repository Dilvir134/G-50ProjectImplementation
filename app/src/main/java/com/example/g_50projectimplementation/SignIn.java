package com.example.g_50projectimplementation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
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

public class SignIn extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in);
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

        email = findViewById(R.id.emailEditText);
        password = findViewById(R.id.passwordEditText);
        login = findViewById(R.id.loginButton);

        SharedPreferences sharedPref = getSharedPreferences(
        "com.gbc.g50.EMAILPASS", Context.MODE_PRIVATE);

        login.setOnClickListener(v -> {
            String userEmail = email.getText().toString();
            String userPass = password.getText().toString();
            if(userPass == null || userPass.isBlank()) {
                Toast.makeText(this, "Please enter your password", Toast.LENGTH_LONG).show();
                return;
            }
            if(userEmail == null || userEmail.isBlank()) {
                Toast.makeText(this, "Please enter your email", Toast.LENGTH_LONG).show();
                return;
            }
            String savedPass = sharedPref.getString(userEmail, "");
            if(savedPass.isEmpty()) {
                Toast.makeText(this, "Invalid Email", Toast.LENGTH_LONG).show();
                return;
            }
            if(savedPass.trim().equals(userPass.trim())) {
                // Allow login
                goToCompanyPage();
                return;
            }
            Toast.makeText(this, "Inavlid Password", Toast.LENGTH_LONG).show();
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public void goToSignUpPage(View view) {
        Intent intent = new Intent(SignIn.this, SignUp.class);
        startActivity(intent);
    }

    public void signIn(View view) {
        if (email.getText().toString().equals("admin@admin.com")) {
            if (password.getText().toString().equals("password@123")) {
                goToCompanyPage();
            }
        }
        else {
            goToCompanyPage();
        }
        //Toast toast = Toast.makeText(this, "Wrong email or password", Toast.LENGTH_SHORT);
       // toast.show();
    }

    public void goToCompanyPage() {
        Intent intent = new Intent(this, HomeManager.class);
        startActivity(intent);
    }
}