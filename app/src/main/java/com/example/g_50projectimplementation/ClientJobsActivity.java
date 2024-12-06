package com.example.g_50projectimplementation;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ClientJobsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_client_and_jobs);

        // Fetch client data
        String clientName = getIntent().getStringExtra("client_name");
        String clientLocation = getIntent().getStringExtra("client_location");

        // Set client data
        ((TextView) findViewById(R.id.clientName)).setText(clientName);
        ((TextView) findViewById(R.id.clientLocation)).setText(clientLocation);

        // Get jobs data (example hardcoded list for now)
        String[] jobs = {"Clean Lobby #1", "Clean Lobby #2", "Clean Building A", "Clean Building B"};

        LinearLayout jobsList = findViewById(R.id.jobsList);
        for (String job : jobs) {
            TextView jobItem = new TextView(this);
            jobItem.setText(job);
            jobItem.setPadding(16, 16, 16, 16);
            jobItem.setBackgroundResource(R.drawable.job_item_background);
            jobsList.addView(jobItem);
        }
    }

}
