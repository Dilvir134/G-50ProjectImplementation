//ADD CLIENT ACTIVITY
package com.example.g_50projectimplementation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.g_50projectimplementation.database.AppDatabase;
import com.example.g_50projectimplementation.database.entity.Client;

public class AddClientActivity extends AppCompatActivity {
    private EditText companyNameInput, addressInput, contactNameInput, contactNumberInput;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);

        db = AppDatabase.getInstance(this);

        companyNameInput = findViewById(R.id.company_name_input);
        addressInput = findViewById(R.id.address_input);
        contactNameInput = findViewById(R.id.first_name_input);
        contactNumberInput = findViewById(R.id.add_contact_input);

        Button addClientButton = findViewById(R.id.add_client_button);
        addClientButton.setOnClickListener(v -> {
            String name = companyNameInput.getText().toString();
            String location = addressInput.getText().toString();
            String contactName = contactNameInput.getText().toString();
            String contactNumber = contactNumberInput.getText().toString();

            Client newClient = new Client(name, location, contactName, contactNumber, null);
            new Thread(() -> db.clientDao().insert(newClient)).start(); // Database operation on a background thread

            finish(); // Return to the previous activity
        });
    }
}
