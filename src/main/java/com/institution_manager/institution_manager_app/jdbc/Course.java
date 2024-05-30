package com.institution_manager.institution_manager_app.jdbc;

public class Course
{
    private int courseId;
    private String courseName;

    public Course() {

    }
    public Course(String courseName) {
        this.courseName = courseName;
    }

    public int getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
