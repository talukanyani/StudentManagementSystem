package com.groupf.studentmanagementsystem;

import java.util.ArrayList;
import java.util.List;

public class StudentStore {
    private final List<Student> students = new ArrayList<>();

    public void addStudent(Student student) {
        students.add(student);
    }

    public void updateStudent(int index, Student student) {
        if (index >= 0 && index < students.size()) {
            students.set(index, student);
        }
    }

    public void deleteStudent(int index) {
        if (index >= 0 && index < students.size()) {
            students.remove(index);
        }
    }

    public List<Student> getStudents() {
        return new ArrayList<>(students);
    }

    public int size() {
        return students.size();
    }
}
