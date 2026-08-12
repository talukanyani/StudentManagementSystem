# Student Management System

A Java Swing desktop application for managing student records in the Faculty of Science, Engineering and Agriculture.

## Features

- Administrator login and sign-out
- Add students with automatically generated student numbers
- View all student records
- Update and delete student records
- Search by student number, first name, last name, or programme
- Input validation for names, email addresses, and phone numbers
- Persistent data storage in MySQL using JDBC and prepared statements

## Technology stack

- Java 26
- Java Swing
- Maven
- MySQL
- MySQL Connector/J 9.7.0

## Project structure

```text
StudentManagementSystem/
|-- database/
|   `-- schema.sql
|-- src/main/java/com/groupf/studentmanagementsystem/
|   |-- StudentManagementSystem.java  # Application entry point
|   |-- LoginFrame.java               # Administrator login interface
|   |-- MainFrame.java                # Main student-management interface
|   |-- Admin.java                    # Administrator model
|   |-- Student.java                  # Student model
|   |-- AdminDAO.java                 # Administrator queries
|   |-- StudentDAO.java               # Student CRUD operations
|   |-- ProgramDAO.java               # Programme queries
|   |-- DatabaseConnection.java       # MySQL connection configuration
|   |-- DatabaseInitializer.java      # Table creation and data seeding
|   `-- DatabaseException.java        # Database error wrapper
`-- pom.xml                           # Maven configuration
```

## Setup

### Prerequisites

Install the following before running the project:

- JDK 26
- Apache NetBeans 30
- MySQL Server and MySQL Workbench

### 1. Set up the database in MySQL Workbench

1. Start **MySQL Workbench**.
2. Open your local MySQL connection, normally `Local instance MySQL80`.
3. Enter the MySQL password you selected when installing MySQL Server.
4. Select **File > Open SQL Script**.
5. Open `database/schema.sql` from this project.
6. Click the lightning-bolt **Execute** button to run the complete script.
7. In the **Schemas** panel, click **Refresh**. A schema named `student_management_system` should appear.
8. Expand the schema and confirm that it contains the `admins`, `programs`, and `students` tables.

The script creates the `student_management_system` database, its three tables, a programme list, sample students, and sample administrator accounts. On each launch, the application also creates any missing tables and inserts its seed records without replacing existing records.

### 2. Configure the database connection

The default connection settings are:

| Setting | Default value |
| --- | --- |
| URL | `jdbc:mysql://localhost:3306/student_management_system?useSSL=false&serverTimezone=Africa/Johannesburg` |
| Username | `root` |
| Password | `mysqlp@ssword` |

**Important**: Your MySQL username or password could be different, open `src/main/java/com/groupf/studentmanagementsystem/DatabaseConnection.java` file from this project and update these values before running the application:

```java
private static final String USERNAME = "root";
private static final String PASSWORD = "your-mysql-password";
```

The database URL normally does not need to change. For a different server, port, or database name, update `DEFAULT_URL` in the same file. The application also supports the `DB_URL`, `DB_USER`, and `DB_PASSWORD` environment variables.

### 3. Open the project in Apache NetBeans 30

1. Start **Apache NetBeans 30**.
2. Select **File > Open Project**.
3. Browse to the `StudentManagementSystem` folder and click **Open Project**.
4. If NetBeans asks for a Java platform, select JDK 26. If it is not listed:
   - Select **Tools > Java Platforms**.
   - Click **Add Platform** and select the installed JDK 26 folder.
   - Right-click the project, select **Properties**, and assign JDK 26 as its Java platform.
5. In the **Projects** panel, expand **Source Packages > `com.groupf.studentmanagementsystem`**.

### 4. Run the application

1. Open `StudentManagementSystem.java`.
2. Right-click inside the file and select **Run File**, or click **Run Project** (Play Icon) button.
3. The login window should open. Keep MySQL Server running while using the application.
4. To login, use either of these accounts (These credentials are demonstration data only. Passwords are currently stored as plain text):

| Staff number | Password |
| --- | --- |
| `26072901` | `p@ss1` |
| `26072902` | `p@ss2` |
