package com.institution_manager.institution_manager_app.controllers;

import com.institution_manager.institution_manager_app.jdbc.Course;
import com.institution_manager.institution_manager_app.jdbc.Student;
import com.institution_manager.institution_manager_app.jdbc.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


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
    @PostMapping("/create/student/institution/{institution_id}/course/{course_id}")
    public ResponseEntity<?> addStudentToInstitutionAndCourse(@PathVariable("institution_id") int institutionId,
                                                              @PathVariable("course_id") int courseId,
                                                              @RequestBody Student student)
    {
        // Create student and add them to an institution
        Student createdStudent = studentRepo.createStudent(institutionId, student);

        // Assign student to a particular course
        studentRepo.enrollStudentToCourse(createdStudent, courseId);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/students")
    public Optional<List<Student>> getAllStudents()
    {
        System.out.println("get students called!!");
        return studentRepo.getAllStudents();
    }


    //delete a student
    @DeleteMapping("/students/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable int id)
    {

        System.out.println("Delete student called!!");
        try {
            studentRepo.deleteStudentById(id);
            return ResponseEntity.ok().build();
        } catch (EmptyResultDataAccessException ex) {
            return ResponseEntity.notFound().build();
        }
    }



    //edit name of a student
    @PatchMapping("/students/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable int id, @RequestBody Student updatedStudent)
    {

        try {
            Optional<Student> existingStudent = studentRepo.getStudentById(id);


            if (existingStudent.isPresent()) {

                studentRepo.updateStudent(id, updatedStudent);

                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (EmptyResultDataAccessException ex) {
            return ResponseEntity.notFound().build();
        }
    }



    //change a course a student is doing within the same institution


    //transfer student to another institution and assign them a course

    //list all students in each institution (show 10 students at a time)

    //search for a student in an institution


    //filter list of students by course
}
