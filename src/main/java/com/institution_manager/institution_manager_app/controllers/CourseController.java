package com.institution_manager.institution_manager_app.controllers;

import com.institution_manager.institution_manager_app.jdbc.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.institution_manager.institution_manager_app.jdbc.Course;

import java.util.List;
import java.util.Optional;

@RestController
public class CourseController
{
    @Autowired
    private CourseRepository repo;



    @PostMapping("/course/create")
    public ResponseEntity<?> createCourse(@RequestBody Course course) {


        try {
            //query db to check if course already exists
            String currentCourseName = course.getCourseName();

            Optional<Course> existingCourse = repo.searchCourse(currentCourseName);

            return ResponseEntity.status(HttpStatus.CONFLICT).body("Sorry!" +
                    "The Course with name " + currentCourseName + " Already Exists");

        } catch (Exception ex) {
            repo.createCourse(course);
            return ResponseEntity.status(HttpStatus.CREATED).build();

        }
    }

    @GetMapping("/courses")
    public Optional<List<Course>> getAllCourses()
    {
        System.out.println("get courses called!!");
        return repo.getAllCourses();
    }

}
