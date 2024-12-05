package com.example.g_50projectimplementation;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ClientJobsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_client_and_jobs);

        // Get the data from the Intent
        String clientName = getIntent().getStringExtra("client_name");
        String clientLocation = getIntent().getStringExtra("client_location");
        String clientContact = getIntent().getStringExtra("client_contact");
        String clientContactNumber = getIntent().getStringExtra("client_contact_number");

        // Set the data to the UI
        TextView clientNameView = findViewById(R.id.clientName);
        TextView clientLocationView = findViewById(R.id.clientLocation);

        clientNameView.setText(clientName);
        clientLocationView.setText(clientLocation);

        // Additional views for contact info
        TextView contactView = findViewById(R.id.clientContact);
        TextView contactNumberView = findViewById(R.id.clientContactNumber);

        if (contactView != null) contactView.setText(clientContact);
        if (contactNumberView != null) contactNumberView.setText(clientContactNumber);
    }
}
