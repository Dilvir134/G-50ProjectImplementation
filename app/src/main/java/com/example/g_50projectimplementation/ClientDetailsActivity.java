package com.example.g_50projectimplementation;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.g_50projectimplementation.database.AppDatabase;
import com.example.g_50projectimplementation.database.entity.Client;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Objects;

public class ClientDetailsActivity extends AppCompatActivity {

    private AppDatabase db;
    private TextView clientNameTextView, clientLocationTextView;
    private TextView contactName, contactPhone;
    private ImageView clientLogo;
    private Button btnCall, btnDelete, btnEdit;
    private int clientId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_client_details);
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
        clientNameTextView = findViewById(R.id.clientNameTextView);
        clientLocationTextView = findViewById(R.id.clientLocationTextView);
        contactName = findViewById(R.id.contactName);
        contactPhone = findViewById(R.id.contactPhone);
        clientLogo  = findViewById(R.id.clientLogo);
        btnCall = findViewById(R.id.btnCall);
        btnDelete = findViewById(R.id.btn_delete);
        btnEdit = findViewById(R.id.btn_edit);

        btnCall.setOnClickListener(l -> {
            String phone = contactPhone.getText().toString().trim();
            Log.d("PHONE", phone);
            if(!phone.isEmpty()) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phone));
                startActivity(intent);
            }
        });

        clientId = getIntent().getIntExtra("CLIENT_ID", -1);

        if(clientId == -1) {
            throw new IllegalArgumentException("CLIENT_ID not passed in!"); // Crash
        }
        db = AppDatabase.getInstance(this);

        btnDelete.setOnClickListener(l -> {
            AlertDialog dialog = new MaterialAlertDialogBuilder(this, R.style.CustomAlertDialog)
                    .setTitle("Confirm Delete")
                    .setPositiveButton("Delete", (dialog1, which) -> {
                        new Thread(() -> {
                            Client client = db.clientDao().getClientById(clientId);
                            Log.d("ClientDetails", "Deleting Client " + clientId);
                            if(client != null) {
                                db.clientDao().delete(client);
                                runOnUiThread(this::finish);
                            } else {
                                Log.e("ClientDetails", "Failed to delete client. Client is null");
                                runOnUiThread( () -> {
                                    Toast.makeText(this, "Failed to delete client. Client is null", Toast.LENGTH_LONG).show();
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

        btnEdit.setOnClickListener(l -> {
            Intent intent = new Intent(this, AddClientActivity.class);
            intent.putExtra("CLIENT_ID", clientId);
            startActivity(intent);
        });

        refreshData(clientId);
    }

    private void refreshData(int clientId) {
        // Fetch client data from database
        new Thread(() -> {
            Client client = db.clientDao().getClientById(clientId);
            runOnUiThread(() -> {
                if (client != null) {
                    clientNameTextView.setText(client.getName());
                    clientLocationTextView.setText(client.getLocation());
                    contactName.setText(client.getContactName());
                    contactPhone.setText(client.getContactNumber());
                    if(client.getLogoUrl() != null) {
                        clientLogo.setImageURI(Uri.parse(client.getLogoUrl()));
                    }
                }
            });
        }).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshData(clientId);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
