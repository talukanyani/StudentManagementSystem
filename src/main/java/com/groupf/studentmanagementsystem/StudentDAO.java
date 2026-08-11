package com.groupf.studentmanagementsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides database-backed CRUD operations for student records.
 */
public class StudentDAO {

    public void addStudent(Student student) {
        String sql = "INSERT INTO students (student_number, first_name, last_name, gender, email, phone_number, program_id) "
                + "VALUES (?, ?, ?, ?, ?, ?, (SELECT id FROM programs WHERE name = ?))";
        try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            setStudentParameters(statement, student);
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new DatabaseException("Could not add the student record.", exception);
        }
    }

    public void updateStudent(String studentNumber, Student student) {
        String sql = "UPDATE students SET first_name = ?, last_name = ?, gender = ?, email = ?, "
                + "phone_number = ?, program_id = (SELECT id FROM programs WHERE name = ?) WHERE student_number = ?";
        try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, student.getFirstName());
            statement.setString(2, student.getLastName());
            statement.setString(3, student.getGender());
            statement.setString(4, student.getEmail());
            statement.setString(5, student.getPhoneNumber());
            statement.setString(6, student.getProgram());
            statement.setString(7, studentNumber);
            ensureRecordChanged(statement.executeUpdate(), "Student record was not found.");
        } catch (SQLException exception) {
            throw new DatabaseException("Could not update the student record.", exception);
        }
    }

    public void deleteStudent(String studentNumber) {
        String sql = "DELETE FROM students WHERE student_number = ?";
        try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, studentNumber);
            ensureRecordChanged(statement.executeUpdate(), "Student record was not found.");
        } catch (SQLException exception) {
            throw new DatabaseException("Could not delete the student record.", exception);
        }
    }

    public List<Student> getStudents() {
        String sql = "SELECT s.student_number, s.first_name, s.last_name, s.gender, s.email, s.phone_number, p.name AS program "
                + "FROM students s JOIN programs p ON p.id = s.program_id ORDER BY s.student_number";
        List<Student> students = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql); ResultSet results = statement.executeQuery()) {
            while (results.next()) {
                students.add(new Student(
                        results.getString("student_number"),
                        results.getString("first_name"),
                        results.getString("last_name"),
                        results.getString("gender"),
                        results.getString("email"),
                        results.getString("phone_number"),
                        results.getString("program")
                ));
            }
            return students;
        } catch (SQLException exception) {
            throw new DatabaseException("Could not load student records.", exception);
        }
    }

    public int size() {
        String sql = "SELECT COUNT(*) FROM students";
        try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql); ResultSet results = statement.executeQuery()) {
            results.next();
            return results.getInt(1);
        } catch (SQLException exception) {
            throw new DatabaseException("Could not count student records.", exception);
        }
    }

    private void setStudentParameters(PreparedStatement statement, Student student) throws SQLException {
        statement.setString(1, student.getStudentNumber());
        statement.setString(2, student.getFirstName());
        statement.setString(3, student.getLastName());
        statement.setString(4, student.getGender());
        statement.setString(5, student.getEmail());
        statement.setString(6, student.getPhoneNumber());
        statement.setString(7, student.getProgram());
    }

    private void ensureRecordChanged(int changedRows, String errorMessage) throws SQLException {
        if (changedRows == 0) {
            throw new SQLException(errorMessage);
        }
    }
}
