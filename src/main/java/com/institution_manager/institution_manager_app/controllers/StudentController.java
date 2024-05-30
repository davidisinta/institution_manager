package com.institution_manager.institution_manager_app.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class StudentController
{
    @RequestMapping("/hello")
    public String greeting()
    {
        return "heyy therrrreeee!!!!!";
    }


    // add a student and assign them a course - this implies that you must enroll a student to
    //an institution before assigning them a course


    //delete a student

    //edit name of a student

    //change a course a student is doing within the same institution


    //transfer student to another institution and assign them a course

    //list all students in each institution (show 10 students at a time)

    //search for a student in an institution


    //filter list of students by course
}
