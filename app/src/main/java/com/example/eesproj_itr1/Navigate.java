package com.example.eesproj_itr1;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.NavigableMap;

public class Navigate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigate);

        // Find the "Make Donation" button
        Button makeDonationButton = findViewById(R.id.makeDonation);

        // Set click listener for the "Make Donation" button
        makeDonationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start the NewDonationActivity
                Intent intent = new Intent(Navigate.this, NewDonationActivity.class);
                // Start the NewDonationActivity
                startActivity(intent);
            }
        });

        // Find the "See Donations" button
        Button seeDonationsButton = findViewById(R.id.seeDonations);

        // Set click listener for the "See Donations" button
        seeDonationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start the LiveDonationActivity
                Intent intent = new Intent(Navigate.this, LiveDonation2.class);
                // Start the LiveDonationActivity
                startActivity(intent);
            }
        });
    }
}
