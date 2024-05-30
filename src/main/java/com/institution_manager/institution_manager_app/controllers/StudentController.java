package com.institution_manager.institution_manager_app.controllers;

import com.institution_manager.institution_manager_app.jdbc.Course;
import com.institution_manager.institution_manager_app.jdbc.Student;
import com.institution_manager.institution_manager_app.jdbc.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class StudentController
{

    @Autowired
    private StudentRepository studentRepo;

    @RequestMapping("/hello")
    public String greeting()
    {
        return "heyy therrrreeee!!!!!";
    }


    // add a student and assign them a course - this implies that you must enroll a student to
    //an institution before assigning them a course
    @PostMapping("/create/student/institution/{id}")
    public ResponseEntity<?> addStudentToInstitutionAndCourse(@PathVariable int id, @RequestBody Course course)
    {
        //create student and add them to an institution
        Student student = studentRepo.createStudent();

        //assign student to a particular course

    }


    //delete a student

    //edit name of a student

    //change a course a student is doing within the same institution


    //transfer student to another institution and assign them a course

    //list all students in each institution (show 10 students at a time)

    //search for a student in an institution


    //filter list of students by course
}
