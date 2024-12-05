package com.example.g_50projectimplementation;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.g_50projectimplementation.database.AppDatabase;
import com.example.g_50projectimplementation.database.entity.Client;

public class ClientDetailsActivity extends AppCompatActivity {

    private AppDatabase db;
    private TextView clientNameTextView, clientLocationTextView, clientContactTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_details);

        // Initialize views
        clientNameTextView = findViewById(R.id.clientNameTextView);
        clientLocationTextView = findViewById(R.id.clientLocationTextView);
        clientContactTextView = findViewById(R.id.clientContactTextView);

        // Get the passed clientId
        int clientId = getIntent().getIntExtra("CLIENT_ID", -1);

        if (clientId != -1) {
            db = AppDatabase.getInstance(this);
            // Fetch client data from database
            new Thread(() -> {
                Client client = db.clientDao().getClientById(clientId);
                runOnUiThread(() -> {
                    if (client != null) {
                        clientNameTextView.setText(client.getName());
                        clientLocationTextView.setText(client.getLocation());
                        clientContactTextView.setText(client.getContactName()); // Assuming a field 'contactInfo'
                    }
                });
            }).start();
        }
    }
}
