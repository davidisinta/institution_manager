package com.institution_manager.institution_manager_app.controllers;

import com.institution_manager.institution_manager_app.jdbc.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CourseController
{
    @Autowired
    private CourseRepository repo;

    @Autowired
    private StudentRepository studentRepo;



    @PostMapping("/course/create")
    public ResponseEntity<?> createCourse(@RequestBody Course course) {
        String currentCourseName = course.getCourseName();

        Optional<Course> existingCourse = repo.searchCourse(currentCourseName);

        if (existingCourse.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Sorry! The Course with name " + currentCourseName + " Already Exists");
        } else {
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
    public ResponseEntity<?> addCourseToInstitution(@PathVariable("id") int institutionId, @RequestBody Course course) {


        String proposedCourseName = course.getCourseName();

        Optional<Course> existingCourse = repo.searchCourseByInstitution(proposedCourseName, institutionId);

        if (existingCourse.isPresent())
        {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Course " + proposedCourseName + " already exists in this institution.");
        }

        //check if course exists before adding it
        Optional<Course> validCourse = repo.searchCourse(proposedCourseName);

        if (validCourse.isPresent()) {
            Course stagedCourse = validCourse.orElseThrow(); // This will throw NoSuchElementException if validCourse is empty
            repo.addCourseToInstitution(stagedCourse, institutionId);

            return ResponseEntity.status(HttpStatus.CREATED).body("Course added to institution successfully.");
        }

        else{
            return ResponseEntity.status(HttpStatus.CONFLICT).body("This course has not yet been created " +
                    "hence cannot be added to this institution");

        }


    }


    @GetMapping("/courses/institutions/{id}")
    public Optional<List<Course>> getCoursesByInstitution(@PathVariable("id") int id)
    {
        System.out.println("GET all courses by institution called");

        return repo.getAnInstitutionsCourses(id);

    }


    // filter courses by searching. Be able to change the institution and have the list
    // of courses change to reflect this
    @PostMapping("/courses/institutions/{id}")
    public Optional<List<Course>> filterInstitutionsCourses(@PathVariable int id, @RequestBody Course course)
    {
        System.out.println("filtering an institutions courses by name");

        return repo.filterInstitutionsCoursesByName(id,course.getCourseName());

    }


    // sort the courses by name in ascending and descending order
    @GetMapping("/institutions/{id}/courses/sort/ascend")
    public Optional<List<Course>> sortCoursesAscending(@PathVariable int id)
    {
        System.out.println("sorting institutions by ascending order!!");
        return repo.getCoursesAscending(id);
    }

    @GetMapping("/institutions/{id}/courses/sort/descend")
    public Optional<List<Course>> sortCoursesDescending(@PathVariable int id)
    {
        System.out.println("sorting institutions by descending order!!");
        return repo.getCoursesDescending(id);
    }


    // Delete a course - do not delete a course that has been assigned to atleast one student
    // show relevant errors if user tries to delete a course that has students assigned
    @DeleteMapping("/courses/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable int id)
    {
        //come back and check if course has a student before deleting


        Optional<List<Student>> potentialStudents = studentRepo.getCoursesStudents(id);

        if(potentialStudents.isPresent()){
            System.out.println("Course not edited because it has students");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Course not " +
                    "edited because there is a student" +
                    "assigned to it");
        }

        else{

            System.out.println("Course deleted");
            try {
                repo.deleteById(id);
                return ResponseEntity.ok().build();
            } catch (EmptyResultDataAccessException ex) {
                return ResponseEntity.notFound().build();
            }

        }





    }


    // edit name of a course - do not edit a course that has been assigned to atleast one student
    // show relevant errors if user tries to delete a course that has students assigned

    @PatchMapping("/courses/{id}")
    public ResponseEntity<?> updateCourse(@PathVariable int id, @RequestBody Course updatedCourse)
    {
        //come back and check if there is a course that has
        // been assigned to atleast one student before editing

        Optional<List<Student>> potentialStudents = studentRepo.getCoursesStudents(id);

        if(potentialStudents.isPresent()){
            System.out.println("Course not edited because it has students");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Course not " +
                    "edited because there is a student" +
                    "assigned to it");
        }

        else{
            try {
                Optional<Course> existingCourse = repo.getCourseById(id);

                Optional<Course> otherCourseWithGivenName = repo.searchCourse(updatedCourse.getCourseName());

                if(otherCourseWithGivenName.isPresent())
                {
                    return ResponseEntity.status(HttpStatus.CONFLICT).body("Sorry! " +
                            "The course with name " + updatedCourse.getCourseName() + " Already Exists.");
                }

                if (existingCourse.isPresent()) {

                    repo.updateCourse(id, updatedCourse);

                    return ResponseEntity.ok().build();
                } else {
                    return ResponseEntity.notFound().build();
                }
            } catch (EmptyResultDataAccessException ex) {
                return ResponseEntity.notFound().build();
            }

        }



    }


}
