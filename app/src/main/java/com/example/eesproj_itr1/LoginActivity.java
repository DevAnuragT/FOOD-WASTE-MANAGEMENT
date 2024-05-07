package com.example.eesproj_itr1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LoginActivity extends AppCompatActivity {

    EditText editTextUsername, editTextPassword;
    Button buttonSignIn, buttonSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize views
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonSignIn = findViewById(R.id.buttonSignIn);
        buttonSignUp = findViewById(R.id.buttonSignUp);

        // Set click listener for the sign-in button
        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get username and password entered by the user
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();

                // Dummy validation (you should implement your own validation logic)
                if (username.isEmpty() || password.isEmpty()) {
                    // Display a toast message indicating that fields are required
                    Toast.makeText(LoginActivity.this, "Username and password are required", Toast.LENGTH_SHORT).show();
                } else {
                    login();
                }
            }
        });

        // Set click listener for the sign-up button
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
            }
        });
    }
    private void login() {
        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();
        try {
            FileInputStream fis = openFileInput("userData.csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String line;
            boolean found = false;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3 && parts[0].equals(username) && parts[2].equals(password)) {
                    found = true;
                    break;
                }
            }
            if (found) {
                Toast.makeText(this, "Login successful.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this, Navigate.class));
            } else {
                Toast.makeText(this, "Invalid username or password.", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            Toast.makeText(this, "Error occurred while logging in.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

//        try (BufferedReader reader = new BufferedReader(new FileReader("userData.csv"))) {
//            String line;
//            boolean found = false;
//            while ((line = reader.readLine()) != null) {
//                String[] parts = line.split(",");
//                if (parts.length >= 3 && parts[0].equals(username) && parts[2].equals(password)) {
//                    found = true;
//                    break;
//                }
//            }
//            if (found) {
//                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(LoginActivity.this,Navigate.class));
//            } else {
//                Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
//            }
//        } catch (IOException e) {
//            Toast.makeText(this, "Error occurred while logging in", Toast.LENGTH_SHORT).show();
//            e.printStackTrace();
//        }
}
