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

    //list all students in each institution (show 10 students at a time)
    @GetMapping("/institutions/{id}/students")
    public ResponseEntity<List<Student>> getStudentsByInstitution(
            @PathVariable int id,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        System.out.println("GET all students by institution called");

        List<Student> students = studentRepo.getAnInstitutionsStudents(id, page, size);

        if (students.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(students);
    }


    //search for a student in an institution
    @PostMapping("students/institutions/{id}")
    public Optional<Student> getStudentInInstitution(@PathVariable int id,
                                                     @RequestBody Student student)
    {
        System.out.println("searching for student in the institution");

        return studentRepo.getStudentInInstitutionByName(id, student);



    }


    //filter list of students by course
    @GetMapping("/students/institutions/{institutionId}/courses/{courseId}")
    public Optional<List<Student>> filterStudentsByCourse(@PathVariable("institutionId") int institutionId,
                                                          @PathVariable("courseId") int courseId)
    {

        System.out.println("filtering students by course!!");

        return studentRepo.getInstitutionsStudentsByCourse(institutionId,courseId);

    }

    //change a course a student is doing within the same institution
    @PostMapping("/students/{id}/change/course")
    public ResponseEntity<String> changeStudentCourse(@PathVariable int id, @RequestBody Course course) {
        // Check if course exists in the institution
        Optional<Course> potentialCourse = studentRepo.searchCourseByName(course.getCourseName(), id);

        // If the course exists, effect the change
        if (potentialCourse.isPresent()) {
            int courseId = potentialCourse.map(Course::getCourseId)
                    .orElseThrow(() -> new IllegalStateException("Course ID not available"));
            studentRepo.changeStudentCourse(id, courseId);
            return ResponseEntity.ok("Course changed successfully");
        } else {
            // If the course doesn't exist, return an error message
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");
        }
    }




    //transfer student to another institution and assign them a course
    @PostMapping("/transfer/students/{studentId}/institutions/{institutionId}")
    public void transferStudent(@PathVariable("studentId") int studentId,
                                @PathVariable("institutionId") int newInstitutionId,
                                @RequestBody Course newCourse)
    {
        System.out.println("Transferring student to a new institution");

        studentRepo.transferStudent(studentId, newInstitutionId, newCourse);
    }






}
