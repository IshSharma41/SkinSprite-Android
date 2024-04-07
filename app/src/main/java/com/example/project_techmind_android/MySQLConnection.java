package com.example.project_techmind_android;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MySQLConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/skinsprite";
    private static final String USER = "chomu";
    private static final String PASSWORD = "mahachomu";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Setting up SSL/TLS properties for encryption
            Properties properties = new Properties();
            properties.setProperty("useSSL", "true");
            properties.setProperty("verifyServerCertificate", "true");
            properties.setProperty("requireSSL", "true");
            properties.setProperty("user", USER);
            properties.setProperty("password", PASSWORD);

            connection = DriverManager.getConnection(URL, properties);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    // Example method to authenticate user with username and password
    public static boolean authenticateUser(String username, String password) {
        // Your implementation to authenticate the user with the server's MySQL database
        // This could involve making a network request to a server-side API

        // For demonstration purposes, assuming authentication succeeds for user "admin" with password "admin123"
        return username.equals("admin") && password.equals("admin123");
    }

    // Example method to check if a user exists with the given username
    public static boolean isUserExists(String username) {
        // Your implementation to check if the user exists in the database
        // This could involve querying the database or making a network request

        // For demonstration purposes, assuming the user exists if the username is "admin"
        return username.equals("admin");
    }
}
