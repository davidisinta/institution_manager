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


    @PostMapping("/addCourse/institution/{id}")
    public void addCourseToInstitution(@RequestBody Course course)
    {
        // take in id of institution and name of course,


        //check if coursename Already exists in that institution


        //if not, go ahead and add the course to the institution
    }

    @GetMapping("/courses/institutions/{id}")
    public Optional<List<Course>> getCoursesByInstitution()
    {

    }


    // filter courses by searching. Be able to change the institution and have the list
    // of courses change to reflect this


    // sort the courses by name in ascending and descending order


    // Delete a course - do not delete a course that has been assigned to atleast one student
    // show relevant errors if user tries to delete a course that has students assigned


    // edit name of a course - do not edit a course that has been assigned to atleast one student
    //    // show relevant errors if user tries to delete a course that has students assigned

}
