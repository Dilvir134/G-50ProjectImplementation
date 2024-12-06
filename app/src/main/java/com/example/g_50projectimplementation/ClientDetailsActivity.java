package com.example.g_50projectimplementation;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.g_50projectimplementation.database.AppDatabase;
import com.example.g_50projectimplementation.database.entity.Client;

public class ClientDetailsActivity extends AppCompatActivity {

    private AppDatabase db;
    private TextView clientNameTextView, clientLocationTextView;
    private TextView contactName, contactPhone;
    private ImageView clientLogo;
    private Button btnCall;
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

        // Initialize views
        clientNameTextView = findViewById(R.id.clientNameTextView);
        clientLocationTextView = findViewById(R.id.clientLocationTextView);
        contactName = findViewById(R.id.contactName);
        contactPhone = findViewById(R.id.contactPhone);
        clientLogo  = findViewById(R.id.clientLogo);
        btnCall = findViewById(R.id.btnCall);

        btnCall.setOnClickListener(l -> {
            String phone = contactPhone.getText().toString().trim();
            Log.d("PHONE", phone);
            if(!phone.isEmpty()) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phone));
                startActivity(intent);
            }
        });

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
                        contactName.setText(client.getContactName());
                        contactPhone.setText(client.getContactNumber());
                        if(client.getLogoUrl() != null) {
                            clientLogo.setImageURI(Uri.parse(client.getLogoUrl()));
                        }
                    }
                });
            }).start();
        }
    }
}
