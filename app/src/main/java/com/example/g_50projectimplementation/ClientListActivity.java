package com.example.g_50projectimplementation;

import android.os.Bundle;
import android.view.Menu;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.g_50projectimplementation.adapters.ClientGroupedListParentAdapter;
import com.example.g_50projectimplementation.adapters.model.ClientListCard;
import com.example.g_50projectimplementation.adapters.model.ClientListCardGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClientListActivity extends AppCompatActivity {

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

        List<ClientListCard> cards1 = new ArrayList<>();
        cards1.add(new ClientListCard("Casino Niagara", "Niagrara Falls, ON"));
        cards1.add(new ClientListCard("Caesors Windsor", "Windosor, ON"));

        List<ClientListCard> cards2 = new ArrayList<>();
        cards2.add(new ClientListCard("Hard Rock", "Miami, Fl"));
        cards2.add(new ClientListCard("Hilton Hotel", "Toronto, ON"));

        List<ClientListCard> cards3 = new ArrayList<>();
        cards3.add(new ClientListCard("Wayne's Apartment", "Richmondhill, ON"));
        cards3.add(new ClientListCard("Jonny's Palace", "Toronto, ON"));

        List<ClientListCardGroup> groups = new ArrayList<>();
        groups.add(new ClientListCardGroup("Casinos", cards1));
        groups.add(new ClientListCardGroup("Hotels", cards2));
        groups.add(new ClientListCardGroup("AirBnBs", cards3));

        // Set up RecyclerView
        RecyclerView parentRecyclerView = findViewById(R.id.parentRecyclerView);
        parentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        parentRecyclerView.setAdapter(new ClientGroupedListParentAdapter(groups));

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