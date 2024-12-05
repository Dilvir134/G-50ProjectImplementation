//ADD CLIENT ACTIVITY
package com.example.g_50projectimplementation;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.g_50projectimplementation.database.AppDatabase;
import com.example.g_50projectimplementation.database.entity.Client;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class AddClientActivity extends AppCompatActivity {
    private TextInputEditText companyNameInput, addressInput;
    private AppDatabase db;

    private static final int PICK_CONTACT_REQUEST = 1;
    private static final int IMAGE_PICK_REQUEST = 2;
    private static final int PERMISSION_IMG_REQUEST_CODE = 3;

    private TextInputEditText contactNameField;
    private TextInputEditText contactPhoneField;

    private Button btnAddImage;
    private ImageView imgAddImage;
    private CardView cardAddImg;


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

    private final ActivityResultLauncher<Intent> pickImageLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(androidx.activity.result.ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri selectedImageUri = result.getData().getData();

                        File copiedImageFile = copyImageToAppStorage(selectedImageUri, AddClientActivity.this);

                        if (copiedImageFile != null) {
                            // Set the copied image URI to ImageView
                            imgAddImage.setImageURI(Uri.fromFile(copiedImageFile));
                            Log.i("IMAGE", Uri.fromFile(copiedImageFile).toString());
                        } else {
                            Log.e("AddClientActivity", "Failed to copy image");
                            return;
                        }

                        imgAddImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
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

        btnAddImage = findViewById(R.id.btnAddImage);
        imgAddImage = findViewById(R.id.imgAddImage);
        cardAddImg = findViewById(R.id.cardAddImage);
        btnAddImage.setOnClickListener(v -> {
            imgOnClick();
        });
        cardAddImg.setOnClickListener(l -> {
            imgOnClick();
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

    private void imgOnClick() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        pickImageLauncher.launch(intent);
    }

    public File copyImageToAppStorage(Uri imageUri, Context context) {
        try {
            // Get the file name from the URI
            String fileName = getFileName(imageUri, context);

            // Create a new file in your app's private storage
            File appStorageDirectory = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File newFile = new File(appStorageDirectory, fileName);

            // Open an InputStream for the selected image file
            InputStream inputStream = context.getContentResolver().openInputStream(imageUri);
            FileOutputStream outputStream = new FileOutputStream(newFile);

            // Copy the file content
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            // Close the streams
            inputStream.close();
            outputStream.close();

            return newFile;

        } catch (IOException e) {
            Log.e("ImageCopy", "Error copying image", e);
        }

        return null;
    }

    private String getFileName(Uri uri, Context context) {
        String fileName = null;
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(uri, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            if (columnIndex != -1) {
                fileName = cursor.getString(columnIndex);
            }
            cursor.close();
        }

        if (fileName == null) {
            fileName = uri.getLastPathSegment();
        }

        return fileName;
    }
}
