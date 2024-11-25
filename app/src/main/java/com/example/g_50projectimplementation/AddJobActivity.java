package com.example.g_50projectimplementation;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddJobActivity extends Activity {

    private EditText inputStartDateTime, inputEndDateTime;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_job);

        Button saveJobButton = findViewById(R.id.btnAssignTask);
        TextView addJobLaterLink = findViewById(R.id.linkAssignTaskLater);
        TextView cancelLink = findViewById(R.id.cancel_link);
        TextView saveJobLink = findViewById(R.id.save_job_link);

        cancelLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to ClientJobsActivity
                navigateToClientJobsActivity();
                //finish();
            }
        });

        saveJobLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to ClientJobsActivity
                navigateToClientJobsActivity();
            }
        });

        saveJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to ClientJobsActivity
                navigateToClientJobsActivity();
            }
        });

        addJobLaterLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to ClientJobsActivity
                navigateToClientJobsActivity();
            }
        });



        inputStartDateTime = findViewById(R.id.input_start_datetime);
        inputEndDateTime = findViewById(R.id.input_end_datetime);

        // Initialize Calendar
        calendar = Calendar.getInstance();

        // Start Date-Time Picker
        inputStartDateTime.setOnClickListener(v -> showDateTimePicker(inputStartDateTime));

        // End Date-Time Picker
        inputEndDateTime.setOnClickListener(v -> showDateTimePicker(inputEndDateTime));

        RadioGroup radioGroupRepeat = findViewById(R.id.radioGroupRepeat);
        RadioButton radioRepeat = findViewById(R.id.radioRepeat);
        RadioButton radioNever = findViewById(R.id.radioNever);

        // Check which option is selected when needed (for example, when saving the form)
        int selectedId = radioGroupRepeat.getCheckedRadioButtonId();

        if (selectedId == radioRepeat.getId()) {
            // User selected "Repeat"
            // Handle the logic for the repeat task here
        } else if (selectedId == radioNever.getId()) {
            // User selected "Never"
            // Handle logic for never repeating here
        }

        // Spinner reference
        Spinner supervisorSpinner = findViewById(R.id.spinnerSupervisor);

        // Hardcoded list of supervisors
        String[] supervisors = {"Samuel Gallejo", "Genius Aladdin", "Code Messiah", "Neanderthal Opps"};

        // ArrayAdapter to populate the Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, supervisors);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set the adapter to the Spinner
        supervisorSpinner.setAdapter(adapter);
    }

    // Method to navigate to ClientJobsActivity
    private void navigateToClientJobsActivity() {
        Intent intent = new Intent(AddJobActivity.this, ClientJobsActivity.class);
        startActivity(intent);
        //finish(); // Optional
    }

    private void showDateTimePicker(EditText targetEditText) {
        // Show Date Picker Dialog
        new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            // Show Time Picker Dialog after selecting the date
            new TimePickerDialog(this, (timeView, hourOfDay, minute) -> {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);

                // Set the chosen date and time in the EditText
                String dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
                        .format(calendar.getTime());
                targetEditText.setText(dateTime);

            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();

        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }






}
