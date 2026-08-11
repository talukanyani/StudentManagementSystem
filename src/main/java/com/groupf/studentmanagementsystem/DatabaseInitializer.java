package com.groupf.studentmanagementsystem;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Ensures the application tables exist after the database itself has been
 * created.
 */
public final class DatabaseInitializer {

    private DatabaseInitializer() {
    }

    public static void initialize() throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS programs (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(160) NOT NULL UNIQUE
                    )
                    """);

            statement.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS students (
                        student_number VARCHAR(9) PRIMARY KEY,
                        first_name VARCHAR(50) NOT NULL,
                        last_name VARCHAR(50) NOT NULL,
                        gender VARCHAR(20) NOT NULL,
                        email VARCHAR(254) NOT NULL,
                        phone_number VARCHAR(20) NOT NULL,
                        program_id INT NOT NULL,
                        CONSTRAINT fk_students_program
                            FOREIGN KEY (program_id) REFERENCES programs(id)
                    )
                    """);

            statement.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS admins (
                        staff_number VARCHAR(20) PRIMARY KEY,
                        first_name VARCHAR(50) NOT NULL,
                        last_name VARCHAR(50) NOT NULL,
                        password VARCHAR(255) NOT NULL
                    )
                    """);

            statement.executeUpdate("""
                    INSERT IGNORE INTO admins (staff_number, first_name, last_name, password)
                    VALUES ('26072101', 'Vhahangwele', 'Tshipuke', 'p@ss1'),
                           ('26072102', 'Tshifhiwa', 'Nyadzanga', 'p@ss2')
                    """);

            statement.executeUpdate("""
                    INSERT IGNORE INTO programs (name)
                    VALUES ('BSc in Biochemistry and Biology'),
                           ('BSc in Microbiology and Botany'),
                           ('BSc in Mathematics and Applied Mathematics'),
                           ('BSc in Mathematics and Physics'),
                           ('BSc in Mathematics and Statistics'),
                           ('BSc in Physics and Chemistry'),
                           ('BSc in Chemistry and Mathematics'),
                           ('BSc in Chemistry and Biochemistry'),
                           ('BSc in Chemistry'),
                           ('BSc in Botany and Zoology'),
                           ('BSc in Computer Science'),
                           ('BSc in Computer Science and Mathematics'),
                           ('Bachelor of Environmental Sciences'),
                           ('Bachelor of Earth Sciences in Mining and Environmental Geology'),
                           ('Bachelor of Earth Sciences in Hydrology and Water Resources'),
                           ('Bachelor of Urban and Regional Planning'),
                           ('Bachelor of Environmental Sciences in Disaster Risk Reduction'),
                           ('BSc in Agriculture (Agricultural Economics)'),
                           ('BSc in Agriculture (Agribusiness Management)'),
                           ('BSc in Agriculture (Animal Science)'),
                           ('BSc in Agriculture (Horticultural Sciences)'),
                           ('BSc in Agriculture (Plant Production)'),
                           ('BSc in Soil Science'),
                           ('BSc in Forestry'),
                           ('BSc in Agricultural and Biosystems Engineering')
                    """);

            statement.executeUpdate("""
                    INSERT IGNORE INTO students
                        (student_number, first_name, last_name, gender, email, phone_number, program_id)
                    VALUES
                        ('260725001', 'Dakalo', 'Mudau', 'Male', 'dakalo@gmail.com', '0723456789',
                            (SELECT id FROM programs WHERE name = 'BSc in Mathematics and Statistics')),
                        ('260725002', 'Lufuno', 'Tshikovhi', 'Male', 'ltshikovhi@outlook.com', '0745678901',
                            (SELECT id FROM programs WHERE name = 'Bachelor of Environmental Sciences')),
                        ('260725003', 'Fulufhelo', 'Ndou', 'Female', 'fulundou@gmail.com', '0756789012',
                            (SELECT id FROM programs WHERE name = 'BSc in Agriculture (Animal Science)'))
                    """);
        }
    }
}
