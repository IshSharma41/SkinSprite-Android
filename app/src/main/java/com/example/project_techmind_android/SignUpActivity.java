package com.example.project_techmind_android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SignUpActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "UserPrefs";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        final EditText emailEditText = findViewById(R.id.mailId);
        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.Password);
        final CheckBox rememberMeCheckbox = findViewById(R.id.rememberMe);
        Button signupButton = findViewById(R.id.login); // Renamed the button to 'signupButton'

        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                boolean rememberMe = rememberMeCheckbox.isChecked();

                Connection connection = DatabaseHelper.getConnection();
                if (connection != null) {
                    try {
                        // Check if email already exists
                        String checkEmailSql = "SELECT * FROM credentials WHERE email = ?";
                        PreparedStatement checkEmailStatement = connection.prepareStatement(checkEmailSql);
                        checkEmailStatement.setString(1, email);
                        ResultSet emailResultSet = checkEmailStatement.executeQuery();

                        if (emailResultSet.next()) {
                            // Email already exists
                            Toast.makeText(SignUpActivity.this, "Email already exists. Please log in.", Toast.LENGTH_SHORT).show();
                        } else {
                            // Email does not exist, proceed with signup
                            String insertSql = "INSERT INTO credentials (email, username, password) VALUES (?, ?, ?)";
                            PreparedStatement insertStatement = connection.prepareStatement(insertSql);
                            insertStatement.setString(1, email);
                            insertStatement.setString(2, username);
                            insertStatement.setString(3, password);
                            int rowsAffected = insertStatement.executeUpdate();
                            if (rowsAffected > 0) {
                                // Signup successful
                                if (rememberMe) {
                                    // Save user information in SharedPreferences
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString(KEY_EMAIL, email);
                                    editor.putString(KEY_USERNAME, username);
                                    editor.putString(KEY_PASSWORD, password);
                                    editor.apply();
                                }

                                // Add your logic to navigate to the next screen
                                Toast.makeText(SignUpActivity.this, "Signup successful. Please log in.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignUpActivity.this, FeaturesPageActivity.class);
                                startActivity(intent);
                                finish();

                            } else {
                                // Signup failed
                                Toast.makeText(SignUpActivity.this, "Signup failed. Please try again.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
