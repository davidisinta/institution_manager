package com.institution_manager.institution_manager_app.jdbc;

import com.institution_manager.institution_manager_app.Course;
import com.institution_manager.institution_manager_app.Student;

import java.util.ArrayList;


public class Institution
{
    private String name;
    private String president;

    private int staffCount;

    private int studentCount;

    public Institution() {
    }

    public Institution(String name, String president, int staffCount, int studentCount) {
        this.name = name;
        this.president = president;
        this.staffCount = staffCount;
        this.studentCount = studentCount;
    }

    public void setPresident(String president) {
        this.president = president;
    }

    public void setStaffCount(int staffCount) {
        this.staffCount = staffCount;
    }

    public void setStudentCount(int studentCount) {
        this.studentCount = studentCount;
    }

    public String getPresident() {
        return president;
    }

    public int getStaffCount() {
        return staffCount;
    }

    public int getStudentCount() {
        return studentCount;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }


}
