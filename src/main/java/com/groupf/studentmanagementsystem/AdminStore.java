package com.groupf.studentmanagementsystem;

import java.util.ArrayList;
import java.util.List;

public class AdminStore {
    private final List<Admin> admins = new ArrayList<>();

    public AdminStore() {
        admins.add(new Admin("26072901", "Vhahangwele", "Tshipuke", "Tamatisi1"));
        admins.add(new Admin("26072902", "Tshifhiwa", "Nyadzanga", "Tamatisi2"));
    }

    public List<Admin> getAdmins() {
        return new ArrayList<>(admins);
    }

    public Admin getDefaultAdmin() {
        return admins.isEmpty() ? null : admins.get(0);
    }
}
