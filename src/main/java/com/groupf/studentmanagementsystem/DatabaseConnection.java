package com.groupf.studentmanagementsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Creates MySQL connections from environment variables or JVM system
 * properties.
 */
public final class DatabaseConnection {

    private static final String DEFAULT_URL
            = "jdbc:mysql://localhost:3306/student_management_system?useSSL=false&serverTimezone=Africa/Johannesburg";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "mysqlp@ssword";

    private DatabaseConnection() {
    }

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException exception) {
            throw new SQLException("MySQL Connector/J was not found. Run Maven to download project dependencies.", exception);
        }

        return DriverManager.getConnection(
                getSetting("db.url", "DB_URL", DEFAULT_URL),
                getSetting("db.user", "DB_USER", USERNAME),
                getSetting("db.password", "DB_PASSWORD", PASSWORD)
        );
    }

    private static String getSetting(String systemProperty, String environmentVariable, String defaultValue) {
        String value = System.getProperty(systemProperty);

        if (value == null || value.isBlank()) {
            value = System.getenv(environmentVariable);
        }

        return value == null || value.isBlank() ? defaultValue : value;
    }
}
