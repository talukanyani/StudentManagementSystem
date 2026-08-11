/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.groupf.studentmanagementsystem;

/**
 *
 * @author tmuts
 */
public class StudentManagementSystem {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            try {
                DatabaseInitializer.initialize();
            } catch (java.sql.SQLException exception) {
                javax.swing.JOptionPane.showMessageDialog(null,
                        "Could not connect to MySQL. Run database/schema.sql first, then configure DB_URL, DB_USER and DB_PASSWORD if needed.\n\n"
                        + exception.getMessage(),
                        "Database Connection Error",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
                return;
            }
            LoginFrame panel = new LoginFrame();

            panel.setLocationRelativeTo(null);
            panel.setVisible(true);
        });
    }
}
