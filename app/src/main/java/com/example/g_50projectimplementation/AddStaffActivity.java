package com.example.g_50projectimplementation;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.OpenableColumns;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.g_50projectimplementation.database.AppDatabase;
import com.example.g_50projectimplementation.database.entity.Client;
import com.example.g_50projectimplementation.database.entity.Staff;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class AddStaffActivity extends AppCompatActivity {

    private AppDatabase db;

    private TextInputEditText employeeNameInput, employeePhoneInput, emergencyContactNameInput, emergencyContactPhoneInput;
    private Button btnAddImage;
    private ImageView imgAddImage;
    private CardView cardAddImg;
    private MaterialAutoCompleteTextView employeeRoleDropdown;

    private Uri imageUri = null;

    private final ActivityResultLauncher<Intent> pickImageLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(androidx.activity.result.ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri selectedImageUri = result.getData().getData();

                        File copiedImageFile = copyImageToAppStorage(selectedImageUri, AddStaffActivity.this);

                        if (copiedImageFile != null) {
                            // Set the copied image URI to ImageView
                            imageUri = Uri.fromFile(copiedImageFile);
                            imgAddImage.setImageURI(imageUri);
                            Log.i("IMAGE", imageUri.toString());
                        } else {
                            Log.e("AddStaffActivity", "Failed to copy image");
                            return;
                        }

                        imgAddImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_staff);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Util.fixStatusBarColorLight(getWindow(), this);

        employeeRoleDropdown = findViewById(R.id.auto_complete_text_view);

        db = AppDatabase.getInstance(this);

        initDropDown();

        employeeNameInput = findViewById(R.id.employeeName);
        employeePhoneInput = findViewById(R.id.employeePhone);
        emergencyContactNameInput = findViewById(R.id.emergencyContactName);
        emergencyContactPhoneInput = findViewById(R.id.emergencyContactPhone);

        btnAddImage = findViewById(R.id.btnAddImage);
        imgAddImage = findViewById(R.id.imgAddImage);
        cardAddImg = findViewById(R.id.cardAddImage);
        btnAddImage.setOnClickListener(v -> {
            imgOnClick();
        });
        cardAddImg.setOnClickListener(l -> {
            imgOnClick();
        });

        MaterialButton addButton = findViewById(R.id.btn_save);

        addButton.setOnClickListener(v -> {
            String employeeName = Objects.requireNonNull(employeeNameInput.getText()).toString().trim();
            String employeePhone = Objects.requireNonNull(employeePhoneInput.getText()).toString().trim();
            String emergencyContactName = Objects.requireNonNull(emergencyContactNameInput.getText()).toString().trim();
            String emergencyContactPhone = Objects.requireNonNull(emergencyContactPhoneInput.getText()).toString().trim();
            String employeeRole = employeeRoleDropdown.getText().toString();

            if(employeeName.isBlank()) {
                Toast.makeText(this, "Employee name cannot be empty", Toast.LENGTH_LONG).show();
                return;
            }
            if(employeePhone.isBlank()) {
                Toast.makeText(this, "Employee phone cannot be empty", Toast.LENGTH_LONG).show();
                return;
            }
            if(emergencyContactName.isBlank()) {
                Toast.makeText(this, "Emergency Contact is required", Toast.LENGTH_LONG).show();
                return;
            }
            if(emergencyContactPhone.isBlank()) {
                Toast.makeText(this, "Please provide a valid emergency contact phone number.", Toast.LENGTH_LONG).show();
                return;
            }

            //TODO: Check edit mode
            Staff newStaff = new Staff(employeeName, employeeRole, employeePhone,
                    imageUri != null ? imageUri.toString() : null);
            new Thread(() -> db.staffDao().insert(newStaff)).start();

            finish(); // Return to the previous activity
        });

        MaterialButton cancelBtn = findViewById(R.id.btn_cancel);
        cancelBtn.setOnClickListener(l -> {
            finish(); // Go back
        });

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

    private void initDropDown() {
        String[] options = {"Staff", "Supervisor", "Management"};
        // Create an ArrayAdapter and set it to the dropdown
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, options);
        employeeRoleDropdown.setAdapter(adapter);
        employeeRoleDropdown.setText(options[0], false);
    }

    private void imgOnClick() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        pickImageLauncher.launch(intent);
    }
}