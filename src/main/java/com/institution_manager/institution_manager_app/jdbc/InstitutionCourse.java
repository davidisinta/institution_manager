package com.institution_manager.institution_manager_app.jdbc;

public class InstitutionCourse
{
    private int institutionId;
    private int courseId;


    public InstitutionCourse() {
    }


    public InstitutionCourse(int institutionId, int courseId) {
        this.institutionId = institutionId;
        this.courseId = courseId;
    }


    public int getInstitutionId() {
        return institutionId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setInstitutionId(int institutionId) {
        this.institutionId = institutionId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    @Override
    public String toString() {
        return "InstitutionCourse{" +
                "institutionId=" + institutionId +
                ", courseId=" + courseId +
                '}';
    }
}

