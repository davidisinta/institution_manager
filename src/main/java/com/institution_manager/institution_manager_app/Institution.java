package com.institution_manager.institution_manager_app;

import java.util.ArrayList;

public class Institution
{
    private String name;
    private String headMaster;

    private int noOfTeachers;

    private int noOfStudents;

    private ArrayList<Course> courses;
    private ArrayList<Student> students;

    public Institution(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHeadMaster(String headMaster) {
        this.headMaster = headMaster;
    }

    public void setNoOfTeachers(int noOfTeachers) {
        this.noOfTeachers = noOfTeachers;
    }

    public void setNoOfStudents(int noOfStudents) {
        this.noOfStudents = noOfStudents;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    public String getName() {
        return name;
    }

    public String getHeadMaster() {
        return headMaster;
    }

    public int getNoOfTeachers() {
        return noOfTeachers;
    }

    public int getNoOfStudents() {
        return noOfStudents;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public String addCourseToInstitution(String courseName){

        return "You have added " + courseName + "to the institution";

    }





}
