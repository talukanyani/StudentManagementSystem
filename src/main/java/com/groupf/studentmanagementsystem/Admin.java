package com.groupf.studentmanagementsystem;

public class Admin {
    private String staffNumber;
    private String firstName;
    private String lastName;
    private String password;

    public Admin(String staffNumber, String firstName, String lastName, String password) {
        this.staffNumber = staffNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }

    public String getStaffNumber() {
        return staffNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
