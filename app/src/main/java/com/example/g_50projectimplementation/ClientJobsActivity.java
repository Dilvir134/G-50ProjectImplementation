package com.example.g_50projectimplementation;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ClientJobsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_client_and_jobs);

        Button addJobButton = findViewById(R.id.addJobButton);

        addJobButton.setOnClickListener(v -> {
            Intent intent = new Intent(ClientJobsActivity.this, AddJobActivity.class);
            startActivity(intent);
        });

        // References to UI elements
        LinearLayout jobsContainer = findViewById(R.id.jobsContainer);

        // Reference the ImageView and set the logo
        ImageView clientLogo = findViewById(R.id.clientLogo);
        clientLogo.setImageResource(R.drawable.hard_rock_cafe_logo);

        // Sample job data
        String[] jobNames = {"Clean Lobby #1", "Clean Lobby #2", "Clean Building A", "Clean Building B"};
        String[] jobDates = {"10/11/24 - 10/11/24", "10/11/24 - 10/11/24", "10/11/24 - 10/11/24", "10/11/24 - 10/11/24"};

        // Dynamically add jobs to the scrollable container
        for (int i = 0; i < jobNames.length; i++) {
            View jobItem = LayoutInflater.from(this).inflate(R.layout.job_item, jobsContainer, false);

            TextView jobTitle = jobItem.findViewById(R.id.jobTitle);
            TextView jobDate = jobItem.findViewById(R.id.jobDate);

            jobTitle.setText(jobNames[i]);
            jobDate.setText(jobDates[i]);

            jobsContainer.addView(jobItem);
        }

        // Set up buttons
        ImageButton callClientButton = findViewById(R.id.callClientIcon);
        callClientButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:1234567890"));
            startActivity(intent);
        });

        ImageButton clientNotesButton = findViewById(R.id.clientNotesIcon);
        clientNotesButton.setOnClickListener(v -> {
            // Navigate to client notes (implement this activity)
        });
    }
}
