package com.example.g_50projectimplementation;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.g_50projectimplementation.database.AppDatabase;
import com.example.g_50projectimplementation.database.entity.Client;
import com.example.g_50projectimplementation.database.entity.Staff;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Objects;

public class StaffDetailsActivity extends AppCompatActivity {

    private AppDatabase db;
    private TextView staffNameTextView, staffPositionTextView;
    private TextView contactPhone;
    private ImageView staffImg;
    private Button btnCall, btnDelete, btnEdit, btnEmergency;
    private int staffId;
    private String emergencyPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_staff_details);
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

        // Initialize views
        staffNameTextView = findViewById(R.id.staffNameTextView);
        staffPositionTextView = findViewById(R.id.staffPositionTextView);
        contactPhone = findViewById(R.id.contactPhone);
        staffImg = findViewById(R.id.staffImg);
        btnCall = findViewById(R.id.btnCall);
        btnDelete = findViewById(R.id.btn_delete);
        btnEdit = findViewById(R.id.btn_edit);
        btnEmergency = findViewById(R.id.btnEmergencyCall);

        staffId = getIntent().getIntExtra("STAFF_ID", -1);

        if(staffId == -1) {
            throw new IllegalArgumentException("STAFF_ID not passed in!"); // Crash
        }
        db = AppDatabase.getInstance(this);

        btnCall.setOnClickListener(l -> {
            String phone = contactPhone.getText().toString().trim();
            Log.d("PHONE", phone);
            if(!phone.isEmpty()) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phone));
                startActivity(intent);
            }
        });

        btnEdit.setOnClickListener(l -> {
            Intent intent = new Intent(StaffDetailsActivity.this, AddStaffActivity.class);
            intent.putExtra("STAFF_ID", staffId);
            startActivity(intent);
        });

        btnDelete.setOnClickListener(l -> {
            AlertDialog dialog = new MaterialAlertDialogBuilder(this, R.style.CustomAlertDialog)
                    .setTitle("Confirm Delete")
                    .setPositiveButton("Delete", (dialog1, which) -> {
                        new Thread(() -> {
                            Staff staff = db.staffDao().getStaffById(staffId);
                            Log.d("StaffDetails", "Deleting Staff " + staffId);
                            if(staff != null) {
                                db.staffDao().delete(staff);
                                runOnUiThread(this::finish);
                            } else {
                                Log.e("StaffDetails", "Failed to delete staff. Staff is null");
                                runOnUiThread( () -> {
                                    Toast.makeText(this, "Failed to delete staff. Staff is null", Toast.LENGTH_LONG).show();
                                });
                            }
                        }).start();
                    })
                    .setMessage("This action is permanent and cannot be reversed.")
                    .setNegativeButton("Cancel", (dialog1, which) -> {
                        dialog1.dismiss();
                    })
                    .create();
            dialog.setOnShowListener(alertDialog -> {
                Button positiveButton = ((AlertDialog)alertDialog).getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setTextColor(getColor(R.color.error));
            });

            dialog.show();

        });

        btnEmergency.setOnClickListener(l -> {
            String phone = emergencyPhone;
            Log.d("PHONE", phone);
            if(!phone.isEmpty()) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phone));
                startActivity(intent);
            }
        });


        refreshData(staffId);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshData(staffId);
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void refreshData(int staffId) {

        new Thread(() -> {
            Staff staff = db.staffDao().getStaffById(staffId);
            if(staff == null) {
                throw new IllegalArgumentException("Staff not found in database");
            }
            runOnUiThread(() -> {
                staffNameTextView.setText(staff.getName());
                staffPositionTextView.setText(staff.getPosition());
                contactPhone.setText(staff.getPhone());
                emergencyPhone = staff.getPhone();
                if(staff.getImageUrl() != null) {
                    staffImg.setImageURI(Uri.parse(staff.getImageUrl()));
                }
            });
        }).start();
    }
}