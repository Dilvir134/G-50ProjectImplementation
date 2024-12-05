package com.example.g_50projectimplementation;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.g_50projectimplementation.adapters.ClientAdapter;
import com.example.g_50projectimplementation.adapters.ClientGroupedListParentAdapter;
import com.example.g_50projectimplementation.adapters.model.ClientListCard;
import com.example.g_50projectimplementation.adapters.model.ClientListCardGroup;
import com.example.g_50projectimplementation.database.AppDatabase;
import com.example.g_50projectimplementation.database.entity.Client;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ClientListActivity extends AppCompatActivity {
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_client_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        Util.fixStatusBarColorLight(getWindow(), this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("My Clients");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        db = AppDatabase.getInstance(this);

        refreshData();

        // Add Client Button - Navigates to AddClientActivity
        ExtendedFloatingActionButton addClientButton = findViewById(R.id.extendedFab);
        addClientButton.setOnClickListener(v -> {
            Intent intent = new Intent(ClientListActivity.this, AddClientActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshData();
    }

    private void refreshData() {
        RecyclerView recyclerView = findViewById(R.id.parentRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        new Thread(() -> {
            List<Client> clients = db.clientDao().getAllClients(); // Fetch data from DAO
            if (clients == null) {
                clients = new ArrayList<>(); // Ensure a non-null list for the adapter
            }

            HashMap<String, List<ClientListCard>> groupsDict = new HashMap<>();
            for (Client client: clients ) {
                String cat = client.getCategory() != null ? client.getCategory() : "Ungrouped";
                if(!groupsDict.containsKey(cat)) {
                    groupsDict.put(cat, new ArrayList<>());
                }
                Objects.requireNonNull(groupsDict.get(cat))
                        .add(new ClientListCard(client.getName(), client.getLocation(),
                                client.getLogoUrl() != null ? Uri.parse(client.getLogoUrl()) : null));
            }

            List<ClientListCardGroup> groups = new ArrayList<>();
            boolean hasSomething = false;
            for (String key: groupsDict.keySet() ) {
                groups.add(new ClientListCardGroup(key, groupsDict.get(key)));
                hasSomething = true;
            }
            if(!hasSomething) {
                groups.add(new ClientListCardGroup("No Clients yet.", new ArrayList<>()));
            }

            runOnUiThread(() -> recyclerView.setAdapter(new ClientGroupedListParentAdapter(groups)));

        }).start();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_manager, menu);
        return true;
    }
}