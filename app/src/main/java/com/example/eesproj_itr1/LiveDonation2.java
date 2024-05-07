package com.example.eesproj_itr1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class LiveDonation2 extends AppCompatActivity {

    private final String FileName = "donation_data.csv";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_donation2);

        TextView textView = findViewById(R.id.textView); // Assuming you have a TextView in your layout XML file

        // Read the CSV file from internal storage
        try {
            FileInputStream fis = openFileInput(FileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String line;
            StringBuilder overall = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                Log.d("CSV", "Read line: " + line); // Logging each line for debugging
                // Split the line into parts
                String[] parts = line.split("!@");
                // Append the data from the line to the StringBuilder
                overall.append("\n\n")
                        .append("Item name : ").append(parts[0])
                        .append("\nTime of Preparation : ").append(parts[1])
                        .append("\nQuantity : ").append(parts[2])
                        .append("\nAddress : ").append(parts[3])
                        .append("\nUtensils Required : ").append(parts[4]);
            }
            reader.close();

            // Set the final text to the TextView
            textView.setText(overall.toString());
        } catch (IOException e) {
            e.printStackTrace();
            textView.setText("NO DONATIONS MADE YET");
        }

        Button deleteDataButton = findViewById(R.id.delete_data_button); // Assuming you have a button with this ID in your layout XML
        deleteDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCsvFile();
            }
        });
        Button access = findViewById(R.id.accessDonation);
        access.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // code for notification sending to delivery partner
                Toast.makeText(LiveDonation2.this, "Notification sent to Delivery Partner", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void deleteCsvFile() {
        File file = new File(getFilesDir(), "donation_data.csv");
        if (file.exists()) {
            boolean deleted = file.delete();
            if (deleted) {
                Toast.makeText(this, "Food Details deleted successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LiveDonation2.this, Navigate.class));
            } else {
                Toast.makeText(this, "Failed to delete Food Details", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "NO DONATIONS MADE YET", Toast.LENGTH_SHORT).show();
        }
    }

}
