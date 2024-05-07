package com.example.eesproj_itr1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class SignUpActivity extends AppCompatActivity {

    private EditText editTextUsername, editTextEmail, editTextPassword, editTextConfirmPassword;
    private Button buttonSignUp;
    private Button SignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize EditText fields
        editTextUsername = findViewById(R.id.editTextUsernameSignUp);
        editTextEmail = findViewById(R.id.editTextEmailSignUp);
        editTextPassword = findViewById(R.id.editTextPasswordSignUp);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPasswordSignUp);

        // Initialize Sign Up Button
        buttonSignUp = findViewById(R.id.buttonSignUpSignUp);
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
        SignIn=findViewById(R.id.s_in);
        SignIn.setOnClickListener(v -> {
            startActivity((new Intent(SignUpActivity.this, LoginActivity.class)));
        });
    }

    // Method to handle sign-up process
    private void signUp() {
        String username = editTextUsername.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String confirmPassword = editTextConfirmPassword.getText().toString().trim();

        // Validate if all fields are filled
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        if(password.length()<8){
            Toast.makeText(this, "Password must be of minimum 8 characters", Toast.LENGTH_SHORT).show();
            return;
        }
        // Validate if password and confirm password match
        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        else{
            checkCredential();
        }
        // Clear EditText fields after successful sign-up
        editTextUsername.setText("");
        editTextEmail.setText("");
        editTextPassword.setText("");
        editTextConfirmPassword.setText("");
    }
    private void checkCredential() {
        String username = editTextUsername.getText().toString();
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        String data = username + "," + email + "," + password + "\n";
        FileOutputStream outputStream = null;
        OutputStreamWriter writer = null;
        try {
            outputStream = openFileOutput("userData.csv", MODE_APPEND);
            writer = new OutputStreamWriter(outputStream);
            writer.write(data);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(SignUpActivity.this, "Error while Signing Up!", Toast.LENGTH_SHORT).show();
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
        Toast.makeText(SignUpActivity.this, "Sign-Up Successful!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(SignUpActivity.this, Navigate.class));
    }
}
