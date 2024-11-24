package com.example.g_50projectimplementation;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowInsetsController;
import android.view.WindowManager;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Objects;

public class CMHomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cm_homepage);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Util.fixStatusBarColorLight(getWindow(), this);
    }

    public void goToSignInPage(View view) {
        Intent intent = new Intent(CMHomePage.this, SignIn.class);
        startActivity(intent);
    }
    public void goToGoogle(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://google.com"));
        startActivity(intent);
    }
    public void goToSignUpPage(View view) {
        Intent intent = new Intent(CMHomePage.this, SignUp.class);
        startActivity(intent);
    }
}