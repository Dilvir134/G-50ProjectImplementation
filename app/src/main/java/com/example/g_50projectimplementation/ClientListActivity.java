package com.example.g_50projectimplementation;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.g_50projectimplementation.adapters.ClientAdapter;
import com.example.g_50projectimplementation.database.AppDatabase;
import com.example.g_50projectimplementation.database.entity.Client;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ClientListActivity extends AppCompatActivity {
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_list);

        db = AppDatabase.getInstance(this);

        RecyclerView recyclerView = findViewById(R.id.parentRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Fetch clients from the database
        new Thread(() -> {
            List<Client> clients = db.clientDao().getAllClients(); // Fetch data from DAO
            if (clients == null) {
                clients = new ArrayList<>(); // Ensure a non-null list for the adapter
            }
            // Update UI with the client list
            List<Client> finalClients = clients;
            runOnUiThread(() -> recyclerView.setAdapter(new ClientAdapter(ClientListActivity.this, finalClients)));
        }).start();

        // Add Client Button - Navigates to AddClientActivity
        ExtendedFloatingActionButton addClientButton = findViewById(R.id.extendedFab);
        addClientButton.setOnClickListener(v -> {
            Intent intent = new Intent(ClientListActivity.this, AddClientActivity.class);
            startActivity(intent);
        });
    }
}
