package com.example.project_techmind_android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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
                // Start the AsyncTask to establish database connection and perform signup
                new DatabaseConnectionTask().execute(emailEditText.getText().toString().trim(),
                        usernameEditText.getText().toString().trim(),
                        passwordEditText.getText().toString().trim(),
                        String.valueOf(rememberMeCheckbox.isChecked()));
            }
        });
    }

    private class DatabaseConnectionTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {
            String email = params[0];
            String username = params[1];
            String password = params[2];
            boolean rememberMe = Boolean.parseBoolean(params[3]);

            Connection connection = MySQLConnection.getConnection();
            if (connection != null) {
                try {
                    // Check if email already exists
                    String checkEmailSql = "SELECT * FROM credentials WHERE email = ?";
                    PreparedStatement checkEmailStatement = connection.prepareStatement(checkEmailSql);
                    checkEmailStatement.setString(1, email);
                    ResultSet emailResultSet = checkEmailStatement.executeQuery();

                    if (emailResultSet.next()) {
                        // Email already exists
                        return false;
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
                            return true;
                        } else {
                            // Signup failed
                            return false;
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    // Close the connection
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean signupSuccessful) {
            if (signupSuccessful) {
                // Signup successful
                Toast.makeText(SignUpActivity.this, "Signup successful. Please log in.", Toast.LENGTH_SHORT).show();
                // Navigate to next screen if signup is successful
                Intent intent = new Intent(SignUpActivity.this, FeaturesPageActivity.class);
                startActivity(intent);
                finish();
            } else {
                // Signup failed
                Toast.makeText(SignUpActivity.this, "Signup failed. Please try again.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
