package com.example.g_50projectimplementation;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.g_50projectimplementation.adapters.ClientGroupedListParentAdapter;
import com.example.g_50projectimplementation.adapters.StaffGroupedListParentAdapter;
import com.example.g_50projectimplementation.adapters.model.ClientListCard;
import com.example.g_50projectimplementation.adapters.model.ClientListCardGroup;
import com.example.g_50projectimplementation.adapters.model.StaffListCard;
import com.example.g_50projectimplementation.adapters.model.StaffListCardGroup;
import com.example.g_50projectimplementation.database.AppDatabase;
import com.example.g_50projectimplementation.database.entity.Client;
import com.example.g_50projectimplementation.database.entity.Staff;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class StaffListActivity extends AppCompatActivity {
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_staff_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Util.fixStatusBarColorLight(getWindow(), this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("My Staff");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        db = AppDatabase.getInstance(this);

        refreshData();

        // Add Staff Button - Navigates to AddStaffActivity
        ExtendedFloatingActionButton addStaffButton = findViewById(R.id.extendedFab);
        addStaffButton.setOnClickListener(v -> {
            Log.d("StaffListActivity", "Add Staff Button Clicked");
            Intent intent = new Intent(StaffListActivity.this, AddStaffActivity.class);
            startActivity(intent);
        });
    }

    private void refreshData() {
        RecyclerView recyclerView = findViewById(R.id.parentRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        new Thread(() -> {
            List<Staff> staffList = db.staffDao().getAllStaff(); // Fetch data from DAO
            if (staffList == null) {
                staffList = new ArrayList<>(); // Ensure a non-null list for the adapter
            }

            HashMap<String, List<StaffListCard>> groupsDict = new HashMap<>();
            for (Staff staff: staffList ) {
                //TODO: Only supervisors need to be a category
                String cat = staff.getPosition() != null ? staff.getPosition() : "Staff";
                if(!groupsDict.containsKey(cat)) {
                    groupsDict.put(cat, new ArrayList<>());
                }
                Objects.requireNonNull(groupsDict.get(cat))
                        .add(new StaffListCard(staff.getId(), staff.getName(), staff.getPosition(),
                                staff.getImageUrl() != null ? Uri.parse(staff.getImageUrl()) : null));
            }

            List<StaffListCardGroup> groups = new ArrayList<>();
            boolean hasSomething = false;
            for (String key: groupsDict.keySet() ) {
                groups.add(new StaffListCardGroup(key, groupsDict.get(key)));
                hasSomething = true;
            }
            if(!hasSomething) {
                groups.add(new StaffListCardGroup("0 Employees", new ArrayList<>()));
            }

            runOnUiThread(() -> recyclerView.setAdapter(new StaffGroupedListParentAdapter(groups)));

        }).start();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}