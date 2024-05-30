package com.institution_manager.institution_manager_app.jdbc;

public class Student
{
    private String studentName;
    private int studentId;

    private int institution_id;


    public Student(){

    }

    public Student(String studentName, int studentId, int institution_id) {
        this.studentName = studentName;
        this.studentId = studentId;
        this.institution_id = institution_id;
    }


    public String getStudentName() {
        return studentName;
    }

    public int getStudentId() {
        return studentId;
    }

    public int getInstitution_id() {
        return institution_id;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public void setInstitution_id(int institution_id) {
        this.institution_id = institution_id;
    }
}
