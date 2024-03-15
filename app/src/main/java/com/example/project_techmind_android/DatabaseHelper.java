package com.example.project_techmind_android;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseHelper {
    private static final String DB_URL = "jdbc:postgresql://dermapixie-db.cpcmg02emzd0.us-west-1.rds.amazonaws.com:5432/dermapixie-db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "Sagnik@09";

    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
}
