//ADD CLIENT ACTIVITY
package com.example.g_50projectimplementation;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.g_50projectimplementation.database.AppDatabase;
import com.example.g_50projectimplementation.database.entity.Client;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class AddClientActivity extends AppCompatActivity {
    private TextInputEditText companyNameInput, addressInput;
    private AppDatabase db;

    private static final int PICK_CONTACT_REQUEST = 1;

    private TextInputEditText contactNameField;
    private TextInputEditText contactPhoneField;

    private final ActivityResultLauncher<Intent> contactPickerLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri contactUri = result.getData().getData();
                    Cursor cursor = getContentResolver().query(contactUri, null, null, null, null);

                    if (cursor != null && cursor.moveToFirst()) {
                        // Get the contact name and phone number from the cursor
                        int nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                        int phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

                        String name = cursor.getString(nameIndex);
                        String phone = cursor.getString(phoneIndex);

                        // Set the contact details into the EditText fields
                        contactNameField.setText(name);
                        contactPhoneField.setText(phone);

                        cursor.close();
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);

        db = AppDatabase.getInstance(this);

        contactNameField = findViewById(R.id.contactName);
        contactPhoneField = findViewById(R.id.contactPhone);
        MaterialButton btnPickContact = findViewById(R.id.btnPickContact);

        btnPickContact.setOnClickListener(v -> {
            // Trigger the contact picker by launching the intent
            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
            contactPickerLauncher.launch(intent);
        });

        companyNameInput = findViewById(R.id.companyName);
        addressInput = findViewById(R.id.companyAddress);

        MaterialButton addClientButton = findViewById(R.id.btn_save);
        addClientButton.setOnClickListener(v -> {
            String name = Objects.requireNonNull(companyNameInput.getText()).toString().trim();
            String location = Objects.requireNonNull(addressInput.getText()).toString().trim();
            String contactName = Objects.requireNonNull(contactNameField.getText()).toString().trim();
            String contactNumber = Objects.requireNonNull(contactPhoneField.getText()).toString().trim();

            if(name == null || name.isEmpty()) {
                Toast.makeText(this, "Please provide a name for this client", Toast.LENGTH_SHORT).show();
                return;
            }

            if(location == null || location.isEmpty()) {
                Toast.makeText(this, "Please provide a location", Toast.LENGTH_SHORT).show();
                return;
            }

            if(contactName == null || contactName.isEmpty()) {
                Toast.makeText(this, "Please input the name of the contact person", Toast.LENGTH_SHORT).show();
                return;
            }

            if(contactNumber == null || contactNumber.isEmpty()) {
                Toast.makeText(this, "Please input the phone number of the contact person", Toast.LENGTH_SHORT).show();
                return;
            }

            Client newClient = new Client(name, location, contactName, contactNumber, null);
            new Thread(() -> db.clientDao().insert(newClient)).start(); // Database operation on a background thread

            finish(); // Return to the previous activity
        });

        MaterialButton cancelBtn = findViewById(R.id.btn_cancel);
        cancelBtn.setOnClickListener(l -> {
            finish(); // Go back
        });
    }
}
