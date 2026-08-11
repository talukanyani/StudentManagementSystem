package com.groupf.studentmanagementsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Retrieves administrator accounts from the MySQL database.
 */
public class AdminDAO {

    public Admin authenticate(String staffNumber, String password) {
        String sql = "SELECT staff_number, first_name, last_name, password FROM admins "
                + "WHERE staff_number = ? AND password = ?";

        try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, staffNumber);
            statement.setString(2, password);
            try (ResultSet results = statement.executeQuery()) {
                return results.next() ? toAdmin(results) : null;
            }
        } catch (SQLException exception) {
            throw new DatabaseException("Could not verify the login details.", exception);
        }
    }

    public Admin getDefaultAdmin() {
        String sql = "SELECT staff_number, first_name, last_name, password FROM admins ORDER BY staff_number LIMIT 1";

        try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql); ResultSet results = statement.executeQuery()) {
            return results.next() ? toAdmin(results) : null;
        } catch (SQLException exception) {
            throw new DatabaseException("Could not load the default administrator.", exception);
        }
    }

    private Admin toAdmin(ResultSet results) throws SQLException {
        return new Admin(
                results.getString("staff_number"),
                results.getString("first_name"),
                results.getString("last_name"),
                results.getString("password")
        );
    }
}
