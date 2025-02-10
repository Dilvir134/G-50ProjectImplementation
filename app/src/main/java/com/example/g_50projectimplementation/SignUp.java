package com.example.g_50projectimplementation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.g_50projectimplementation.util.Constants;

import java.util.Objects;

public class SignUp extends AppCompatActivity {

    private EditText phoneInput;
    private EditText passwordInput;
    private EditText confirmPasswordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
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

        phoneInput = findViewById(R.id.inputPhone);
        passwordInput = findViewById(R.id.inputPassword);
        confirmPasswordInput = findViewById(R.id.inputConfirmPassword);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public void attemptSignUp(View view) {

        /*
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api.github.com/users/mralexgray/repos";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        String r = ("Response is: " + response.substring(0,500));
                        Toast toast = Toast.makeText(SignUp.this, r, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //phoneInput.setText(error.getMessage());
                Toast toast = Toast.makeText(SignUp.this, "Didnt work" + error.getMessage(), Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        queue.add(stringRequest);
        */

        if(validatePhone(phoneInput.getText().toString())) {
            if(validateMatchingPasswords(passwordInput.getText().toString(), confirmPasswordInput.getText().toString())) {

                SharedPreferences sharedPref = getSharedPreferences(
                        "com.gbc.g50.PHONEPASS", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(phoneInput.getText().toString().trim(), passwordInput.getText().toString().trim());
                editor.apply();
                goToSignInPage();
                return;
            }
            Toast toast = Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        Toast toast = Toast.makeText(this, "Invalid Phone Number", Toast.LENGTH_SHORT);
        toast.show();

    }

    private void goToSignInPage() {
        Intent intent = new Intent(SignUp.this, SignIn.class);
        startActivity(intent);
    }

    private boolean validateMatchingPasswords(String password, String confirmPassword) {
        return password.equals(confirmPassword) && !password.isBlank();
    }

    private boolean validatePhone(String phoneNumber) {
        return (!TextUtils.isEmpty(phoneNumber) && Patterns.PHONE.matcher(phoneNumber).matches());
    }

    public void goToGoogle(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://google.com"));
        startActivity(intent);
    }


}