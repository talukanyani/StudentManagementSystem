/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.groupf.studentmanagementsystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.AbstractCellEditor;

public class MainFrame extends JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MainFrame.class.getName());
    private static final Pattern NAME_PATTERN = Pattern.compile("^[\\p{L}][\\p{L} .'-]{0,49}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+?[0-9][0-9 ()-]{6,18}$");
    private static final String[] PROGRAMMES = {
"BSc in Agricultural and Biosystems Engineering",
"BSc in Agriculture (Agribusiness Management)",
"BSc in Agriculture (Agricultural Economics)",
"BSc in Agriculture (Animal Science)",
"BSc in Agriculture (Horticultural Sciences)",
"BSc in Agriculture (Plant Production)",
"BSc in Biochemistry and Biology",
"BSc in Biochemistry and Microbiology",
"BSc in Botany and Zoology",
"BSc in Chemistry",
"BSc in Chemistry and Biochemistry",
"BSc in Chemistry and Mathematics",
"BSc in Computer Science",
"BSc in Computer Science and Mathematics",
"BSc in Forestry",
"BSc in Mathematics and Applied Mathematics",
"BSc in Mathematics and Physics",
"BSc in Mathematics and Statistics",
"BSc in Microbiology and Botany",
"BSc in Physics and Chemistry",
"BSc in Soil Science",
"Bachelor of Earth Sciences in Hydrology and Water Resources",
"Bachelor of Earth Sciences in Mining and Environmental Geology",
"Bachelor of Environmental Sciences",
"Bachelor of Environmental Sciences in Disaster Risk Reduction",
"Bachelor of Urban and Regional Planning",
"Extended BSc in Biochemistry and Biology",
"Extended BSc in Biochemistry and Microbiology",
"Extended BSc in Botany and Zoology",
"Extended BSc in Chemistry and Applied Chemistry",
"Extended BSc in Chemistry and Biochemistry",
"Extended BSc in Chemistry and Mathematics",
"Extended BSc in Computer Science",
"Extended BSc in Mathematics and Applied Mathematics",
"Extended BSc in Mathematics and Physics",
"Extended BSc in Mathematics and Statistics",
"Extended BSc in Microbiology and Botany",
"Extended BSc in Physics and Chemistry"
    };

    private final StudentStore studentStore = new StudentStore();
    private final AdminStore adminStore = new AdminStore();
    private Admin currentAdmin;
    private JTabbedPane tabbedPane;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JComboBox<String> genderCombo;
    private JTextField emailField;
    private JTextField phoneField;
    private JComboBox<String> programCombo;
    private JButton saveButton;
    private JLabel statusLabel;
    private JTable studentsTable;
    private DefaultTableModel studentsTableModel;

    private JTextField searchStudentNumberField;
    private JTextField searchFirstNameField;
    private JTextField searchLastNameField;
    private JComboBox<String> searchProgramCombo;
    private JTable searchResultsTable;
    private DefaultTableModel searchResultsTableModel;
    private JLabel searchStatusLabel;

    private int editingStudentIndex = -1;

    public MainFrame() {
        currentAdmin = adminStore.getDefaultAdmin();
        initComponents();
    }

    public MainFrame(Admin admin) {
        currentAdmin = admin != null ? admin : adminStore.getDefaultAdmin();
        initComponents();
    }

    private void initComponents() {
        setTitle("Student Management System");
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(1100, 750);
        setMinimumSize(new Dimension(1000, 700));

        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        tabbedPane.addTab("Home", buildHomePanel());
        tabbedPane.addTab("Add Student", buildAddStudentPanel());
        tabbedPane.addTab("View Students", buildViewStudentsPanel());
        tabbedPane.addTab("Search Student", buildSearchPanel());
        tabbedPane.addTab("Account", buildAccountPanel());

        setContentPane(tabbedPane);
        setLocationRelativeTo(null);
        refreshStudentTable();
    }

    private JPanel buildHomePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JLabel title = new JLabel("WELCOME", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 48));

        JLabel subtitle = new JLabel("Welcome to the Faculty of Science, Engineering and Agriculture Student Management System.", JLabel.CENTER);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 24));

        JLabel instructions = new JLabel("Use the tabs above to navigate.", JLabel.CENTER);
        instructions.setFont(new Font("Segoe UI", Font.PLAIN, 24));

        JPanel content = new JPanel();
        content.setLayout(new GridBagLayout());
        content.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.gridx = 0;
        gbc.gridy = 0;
        content.add(title, gbc);
        gbc.gridy = 1;
        content.add(subtitle, gbc);
        gbc.gridy = 2;
        content.add(instructions, gbc);

        panel.add(content, BorderLayout.CENTER);
        return panel;
    }

    private JPanel buildAddStudentPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 20));
        panel.setBorder(new EmptyBorder(24, 24, 24, 24));
        panel.setBackground(new Color(245, 245, 245));

        JLabel title = new JLabel("Add Student");
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        panel.add(title, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(245, 245, 245));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("First Name"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        firstNameField = new JTextField(28);
        formPanel.add(firstNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        formPanel.add(new JLabel("Last Name"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        lastNameField = new JTextField(28);
        formPanel.add(lastNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        formPanel.add(new JLabel("Gender"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        genderCombo = new JComboBox<>(new DefaultComboBoxModel<>(new String[]{"Male", "Female"}));
        genderCombo.setPreferredSize(new Dimension(250, 38));
        formPanel.add(genderCombo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        formPanel.add(new JLabel("Email"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        emailField = new JTextField(28);
        formPanel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        formPanel.add(new JLabel("Phone Number"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        phoneField = new JTextField(28);
        formPanel.add(phoneField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        formPanel.add(new JLabel("Program"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        programCombo = new JComboBox<>(new DefaultComboBoxModel<>(PROGRAMMES));
        programCombo.setPreferredSize(new Dimension(250, 38));
        formPanel.add(programCombo, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        buttonPanel.setBackground(new Color(245, 245, 245));
        saveButton = new JButton("Add Student");
        saveButton.setBackground(new Color(165, 120, 94));
        saveButton.setForeground(Color.WHITE);
        saveButton.addActionListener(evt -> saveStudent());

        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(evt -> clearForm());

        buttonPanel.add(saveButton);
        buttonPanel.add(clearButton);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 3;
        formPanel.add(buttonPanel, gbc);

        statusLabel = new JLabel(" ");
        statusLabel.setForeground(new Color(22, 119, 22));
        gbc.gridy = 7;
        formPanel.add(statusLabel, gbc);

        panel.add(formPanel, BorderLayout.CENTER);
        return panel;
    }

    private JPanel buildViewStudentsPanel() {
        JPanel panel = new JPanel(new BorderLayout(12, 12));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Student Records");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        panel.add(title, BorderLayout.NORTH);

        studentsTableModel = new DefaultTableModel(
                new Object[]{"Student Number", "First Name", "Last Name", "Gender", "Email", "Phone Number", "Program", "Actions"},
                0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 7;
            }
        };

        studentsTable = new JTable(studentsTableModel);
        studentsTable.setRowHeight(32);
        studentsTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        studentsTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        studentsTable.getColumnModel().getColumn(7).setPreferredWidth(180);
        studentsTable.getColumnModel().getColumn(7).setCellRenderer(new ActionsCell());
        studentsTable.getColumnModel().getColumn(7).setCellEditor(new ActionsCell());

        JScrollPane scrollPane = new JScrollPane(studentsTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private JPanel buildSearchPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 16));
        panel.setBorder(new EmptyBorder(24, 24, 24, 24));
        panel.setBackground(new Color(245, 245, 245));

        JLabel title = new JLabel("Search Student");
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        panel.add(title, BorderLayout.NORTH);

        JPanel searchPanel = new JPanel(new GridBagLayout());
        searchPanel.setBackground(new Color(245, 245, 245));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        searchPanel.add(new JLabel("Student Number"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        searchStudentNumberField = new JTextField(24);
        searchPanel.add(searchStudentNumberField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        searchPanel.add(new JLabel("First Name"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        searchFirstNameField = new JTextField(24);
        searchPanel.add(searchFirstNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        searchPanel.add(new JLabel("Last Name"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        searchLastNameField = new JTextField(24);
        searchPanel.add(searchLastNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        searchPanel.add(new JLabel("Program"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        searchProgramCombo = new JComboBox<>(new DefaultComboBoxModel<>(getSearchProgrammes()));
        searchProgramCombo.setPreferredSize(new Dimension(250, 38));
        searchPanel.add(searchProgramCombo, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        buttonPanel.setBackground(new Color(245, 245, 245));
        JButton searchButton = new JButton("Search");
        searchButton.setBackground(new Color(165, 120, 94));
        searchButton.setForeground(Color.WHITE);
        searchButton.addActionListener(evt -> searchStudents());

        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(evt -> clearSearchFields());

        buttonPanel.add(searchButton);
        buttonPanel.add(clearButton);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        searchPanel.add(buttonPanel, gbc);

        searchStatusLabel = new JLabel(" ");
        searchStatusLabel.setForeground(new Color(22, 119, 22));
        gbc.gridy = 5;
        searchPanel.add(searchStatusLabel, gbc);

        panel.add(searchPanel, BorderLayout.CENTER);

        searchResultsTableModel = new DefaultTableModel(
                new Object[]{"Student Number", "First Name", "Last Name", "Gender", "Email", "Phone Number", "Program"},
                0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        searchResultsTable = new JTable(searchResultsTableModel);
        searchResultsTable.setRowHeight(32);
        searchResultsTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchResultsTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));

        JScrollPane scrollPane = new JScrollPane(searchResultsTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Search Results"));
        scrollPane.setPreferredSize(new Dimension(950, 300));
        panel.add(scrollPane, BorderLayout.SOUTH);

        return panel;
    }

    private void searchStudents() {
        String studentNumber = searchStudentNumberField.getText().trim().toLowerCase();
        String firstName = searchFirstNameField.getText().trim().toLowerCase();
        String lastName = searchLastNameField.getText().trim().toLowerCase();
        String program = searchProgramCombo.getSelectedItem() == null ? "" : searchProgramCombo.getSelectedItem().toString().trim().toLowerCase();

        List<Student> students = studentStore.getStudents();
        List<Student> filtered = new java.util.ArrayList<>();

        for (Student student : students) {
            boolean matches = true;

            if (!studentNumber.isEmpty() && !student.getStudentNumber().toLowerCase().contains(studentNumber)) {
                matches = false;
            }
            if (!firstName.isEmpty() && !student.getFirstName().toLowerCase().contains(firstName)) {
                matches = false;
            }
            if (!lastName.isEmpty() && !student.getLastName().toLowerCase().contains(lastName)) {
                matches = false;
            }
            if (!program.isEmpty() && !student.getProgram().toLowerCase().contains(program)) {
                matches = false;
            }

            if (matches) {
                filtered.add(student);
            }
        }

        if (filtered.isEmpty()) {
            searchStatusLabel.setText("No students found.");
            searchStatusLabel.setForeground(Color.RED);
        } else {
            searchStatusLabel.setText(filtered.size() + " student(s) found.");
            searchStatusLabel.setForeground(new Color(22, 119, 22));
        }

        refreshSearchResults(filtered);
    }

    private void clearSearchFields() {
        searchStudentNumberField.setText("");
        searchFirstNameField.setText("");
        searchLastNameField.setText("");
        searchProgramCombo.setSelectedIndex(0);
        searchStatusLabel.setText(" ");
        refreshSearchResults(java.util.Collections.emptyList());
    }

    private void refreshSearchResults(List<Student> students) {
        searchResultsTableModel.setRowCount(0);
        for (Student student : students) {
            searchResultsTableModel.addRow(new Object[]{
                    student.getStudentNumber(),
                    student.getFirstName(),
                    student.getLastName(),
                    student.getGender(),
                    student.getEmail(),
                    student.getPhoneNumber(),
                    student.getProgram()
            });
        }
    }

    private JPanel buildAccountPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 16));
        panel.setBorder(new EmptyBorder(24, 24, 24, 24));
        panel.setBackground(new Color(245, 245, 245));

        JLabel title = new JLabel("Account");
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        panel.add(title, BorderLayout.NORTH);

        JPanel infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setBackground(new Color(245, 245, 245));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        infoPanel.add(new JLabel("Staff Number:"), gbc);
        gbc.gridx = 1;
        JLabel staffLabel = new JLabel(currentAdmin != null ? currentAdmin.getStaffNumber() : "N/A");
        staffLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        infoPanel.add(staffLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        infoPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        JLabel nameLabel = new JLabel(currentAdmin != null ? currentAdmin.getFullName() : "Unknown");
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        infoPanel.add(nameLabel, gbc);

        panel.add(infoPanel, BorderLayout.CENTER);

        JButton signOutButton = new JButton("Sign Out");
        signOutButton.setBackground(new Color(165, 120, 94));
        signOutButton.setForeground(Color.WHITE);
        signOutButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        signOutButton.addActionListener(evt -> signOut());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        buttonPanel.setBackground(new Color(245, 245, 245));
        buttonPanel.add(signOutButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void signOut() {
        int choice = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to sign out?",
                "Sign Out",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (choice == JOptionPane.YES_OPTION) {
            dispose();
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setLocationRelativeTo(null);
            loginFrame.setVisible(true);
        }
    }

    private void saveStudent() {
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String email = emailField.getText().trim();
        String phoneNumber = phoneField.getText().trim();

        if (!validateStudentInput(firstName, lastName, email, phoneNumber)) {
            return;
        }

        Student student = new Student();
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setGender(genderCombo.getSelectedItem().toString());
        student.setEmail(email);
        student.setPhoneNumber(phoneNumber);
        student.setProgram(programCombo.getSelectedItem().toString());

        if (editingStudentIndex >= 0) {
            Student current = studentStore.getStudents().get(editingStudentIndex);
            student.setStudentNumber(current.getStudentNumber());
            studentStore.updateStudent(editingStudentIndex, student);
            statusLabel.setText("Student updated successfully.");
            saveButton.setText("Add Student");
            editingStudentIndex = -1;
        } else {
            student.setStudentNumber(generateStudentNumber());
            studentStore.addStudent(student);
            statusLabel.setText("Student added successfully.");
        }

        refreshStudentTable();
        clearForm();
        statusLabel.setForeground(new Color(22, 119, 22));
    }

    private void clearForm() {
        firstNameField.setText("");
        lastNameField.setText("");
        genderCombo.setSelectedIndex(0);
        emailField.setText("");
        phoneField.setText("");
        programCombo.setSelectedIndex(0);
        saveButton.setText("Add Student");
        editingStudentIndex = -1;
    }

    private boolean validateStudentInput(String firstName, String lastName, String email, String phoneNumber) {
        if (!NAME_PATTERN.matcher(firstName).matches()) {
            showValidationError("Enter a valid first name (letters, spaces, apostrophes and hyphens only).", firstNameField);
            return false;
        }
        if (!NAME_PATTERN.matcher(lastName).matches()) {
            showValidationError("Enter a valid last name (letters, spaces, apostrophes and hyphens only).", lastNameField);
            return false;
        }
        if (email.length() > 254 || !EMAIL_PATTERN.matcher(email).matches()) {
            showValidationError("Enter a valid email address.", emailField);
            return false;
        }

        String digitsOnly = phoneNumber.replaceAll("\\D", "");
        if (!PHONE_PATTERN.matcher(phoneNumber).matches() || digitsOnly.length() < 10 || digitsOnly.length() > 15) {
            showValidationError("Enter a valid phone number with 10 to 15 digits.", phoneField);
            return false;
        }
        return true;
    }

    private void showValidationError(String message, JComponent field) {
        statusLabel.setText(message);
        statusLabel.setForeground(Color.RED);
        field.requestFocusInWindow();
    }

    private String[] getSearchProgrammes() {
        String[] searchProgrammes = new String[PROGRAMMES.length + 1];
        searchProgrammes[0] = "";
        System.arraycopy(PROGRAMMES, 0, searchProgrammes, 1, PROGRAMMES.length);
        return searchProgrammes;
    }

    private String generateStudentNumber() {
        String todayPrefix = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
        Pattern pattern = Pattern.compile("^(\\d{6})(\\d{2})$");
        int highestSuffix = 0;

        for (Student student : studentStore.getStudents()) {
            String studentNumber = student.getStudentNumber();
            if (studentNumber != null) {
                Matcher matcher = pattern.matcher(studentNumber);
                if (matcher.matches() && matcher.group(1).equals(todayPrefix)) {
                    int suffix = Integer.parseInt(matcher.group(2));
                    if (suffix > highestSuffix) {
                        highestSuffix = suffix;
                    }
                }
            }
        }

        int nextSuffix = highestSuffix + 1;
        return String.format("%s%02d", todayPrefix, nextSuffix);
    }

    private void refreshStudentTable() {
        studentsTableModel.setRowCount(0);
        List<Student> students = studentStore.getStudents();
        for (Student student : students) {
            studentsTableModel.addRow(new Object[]{
                    student.getStudentNumber(),
                    student.getFirstName(),
                    student.getLastName(),
                    student.getGender(),
                    student.getEmail(),
                    student.getPhoneNumber(),
                    student.getProgram(),
                    ""
            });
        }
        studentsTable.repaint();
    }

    private void handleUpdateStudent(int rowIndex) {
        List<Student> students = studentStore.getStudents();
        if (rowIndex < 0 || rowIndex >= students.size()) {
            return;
        }

        Student student = students.get(rowIndex);
        editingStudentIndex = rowIndex;
        firstNameField.setText(student.getFirstName());
        lastNameField.setText(student.getLastName());
        genderCombo.setSelectedItem(student.getGender());
        emailField.setText(student.getEmail());
        phoneField.setText(student.getPhoneNumber());
        programCombo.setSelectedItem(student.getProgram());
        saveButton.setText("Save Changes");
        statusLabel.setText("Editing student " + student.getStudentNumber());
        statusLabel.setForeground(new Color(22, 119, 22));
        tabbedPane.setSelectedIndex(1);
    }

    private void handleDeleteStudent(int rowIndex) {
        List<Student> students = studentStore.getStudents();
        if (rowIndex < 0 || rowIndex >= students.size()) {
            return;
        }

        int option = JOptionPane.showConfirmDialog(this,
                "Delete this student record?",
                "Delete Student",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (option == JOptionPane.YES_OPTION) {
            studentStore.deleteStudent(rowIndex);
            if (editingStudentIndex == rowIndex) {
                editingStudentIndex = -1;
            } else if (editingStudentIndex > rowIndex) {
                editingStudentIndex--;
            }
            refreshStudentTable();
            statusLabel.setText("Student deleted successfully.");
            statusLabel.setForeground(new Color(22, 119, 22));
        }
    }

    private class ActionsCell extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {
        private final JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 0));
        private final JButton updateButton = new JButton("Update");
        private final JButton deleteButton = new JButton("Delete");
        private int rowIndex = -1;

        private ActionsCell() {
            updateButton.setFocusPainted(false);
            deleteButton.setFocusPainted(false);
            updateButton.addActionListener(evt -> {
                if (rowIndex >= 0) {
                    handleUpdateStudent(rowIndex);
                }
                fireEditingStopped();
            });
            deleteButton.addActionListener(evt -> {
                if (rowIndex >= 0) {
                    handleDeleteStudent(rowIndex);
                }
                fireEditingStopped();
            });
            panel.add(updateButton);
            panel.add(deleteButton);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            rowIndex = row;
            return panel;
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            rowIndex = row;
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return rowIndex;
        }
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> new MainFrame().setVisible(true));
    }
}
