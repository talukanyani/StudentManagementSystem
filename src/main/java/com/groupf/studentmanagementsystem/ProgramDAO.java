package com.groupf.studentmanagementsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Retrieves programme names for the user-interface dropdowns.
 */
public class ProgramDAO {

    public List<String> getPrograms() {
        List<String> programs = new ArrayList<>();
        String sql = "SELECT name FROM programs ORDER BY name";
        try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql); ResultSet results = statement.executeQuery()) {
            while (results.next()) {
                programs.add(results.getString("name"));
            }
            return programs;
        } catch (SQLException exception) {
            throw new DatabaseException("Could not load programmes.", exception);
        }
    }
}
