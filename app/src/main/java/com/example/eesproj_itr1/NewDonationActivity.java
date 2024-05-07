package com.example.eesproj_itr1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import android.os.Build;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Locale;

public class NewDonationActivity extends AppCompatActivity {
    private String selectedAddress;
    private final String FileName="donation_data.csv";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_donation);
        Intent intent=getIntent();
        EditText itemNameEditText = findViewById(R.id.ItemName);
        EditText timeOfPreparationEditText = findViewById(R.id.TimeOfPreparation);
        EditText quantityEditText = findViewById(R.id.Quantity);
        RadioButton yesRadioButton = findViewById(R.id.radio_yes);
        RadioGroup utensilsRadioGroup = findViewById(R.id.radio_group);
        Spinner addressSpinner = findViewById(R.id.address_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.address_options_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        addressSpinner.setAdapter(adapter);
        addressSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedAddress = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do something when nothing is selected
            }
        });
        // Handle submit button click (implement your logic here)
        Button submitButton = findViewById(R.id.submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user input from EditTexts and RadioGroup
                String itemName = itemNameEditText.getText().toString();
                String timeOfPreparation = timeOfPreparationEditText.getText().toString();
                String quantity = quantityEditText.getText().toString();
                boolean isUtensilRequired = yesRadioButton.isChecked();
                if (itemName.isEmpty() || timeOfPreparation.isEmpty() || quantity.isEmpty()) {
                    // Display a toast message indicating that all fields are required
                    Toast.makeText(NewDonationActivity.this, "Please fill in all details", Toast.LENGTH_SHORT).show();
                    return; // Exit the onClick method without further processing
                }
                // Check if the time is in the format of HH:MM and in 24-hour format
                if (!isValidTimeFormat(timeOfPreparation)) {
                    // Display a toast message indicating the correct time format
                    Toast.makeText(NewDonationActivity.this, "Please enter the time in HH:MM format and in 24-hour format", Toast.LENGTH_SHORT).show();
                    return; // Exit the onClick method without further processing
                }
                // Format data as CSV string
                String showingData= String.format(Locale.getDefault(), "%s,%s,%s,%s,%s%n",
                        itemName, timeOfPreparation, quantity,selectedAddress , (isUtensilRequired ? "Yes" : "No"));
                String csvData = String.format(Locale.getDefault(), "%s!@%s!@%s!@%s!@%s%n",
                        itemName, timeOfPreparation, quantity,selectedAddress , (isUtensilRequired ? "Yes" : "No"));

                // Implement your logic to process the data (e.g., display a toast message, save to database)
                Toast.makeText(NewDonationActivity.this, showingData, Toast.LENGTH_SHORT).show();

                // Save data to CSV file
                saveDataToCSV(csvData);

                // Clear EditText fields and RadioGroup selection
                itemNameEditText.setText("");
                timeOfPreparationEditText.setText("");
                quantityEditText.setText("");
                addressSpinner.setSelection(0);
                utensilsRadioGroup.clearCheck();
                startActivity(new Intent(NewDonationActivity.this,Navigate.class));
            }

            private void saveDataToCSV(String data) {
                FileOutputStream outputStream = null;
                OutputStreamWriter writer = null;
                try {
                    // Open output stream in append mode
                    outputStream = openFileOutput(FileName, MODE_APPEND);
                    writer = new OutputStreamWriter(outputStream);
                    writer.write(data);
                    Toast.makeText(NewDonationActivity.this, "Data saved successfully!", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(NewDonationActivity.this, "Error saving data!", Toast.LENGTH_SHORT).show();
                } finally {
                    try {
                        if (writer != null) {
                            writer.close();
                        }
                        if (outputStream != null) {
                            outputStream.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            private boolean isValidTimeFormat(String time) {
                // Regular expression to validate time format (HH:MM)
                String timeRegex = "([01]?[0-9]|2[0-3]):[0-5][0-9]";
                return time.matches(timeRegex);
            }

        });
    }
}
