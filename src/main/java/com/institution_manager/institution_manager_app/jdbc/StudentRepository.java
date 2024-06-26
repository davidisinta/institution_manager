package com.institution_manager.institution_manager_app.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.support.KeyHolder;


import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class StudentRepository {



    @Autowired
    private JdbcTemplate springJdbcTemplate;

    @Autowired
    private CourseRepository courseRepo;

    private static final String CREATE_STUDENT_QUERY = """
INSERT INTO Student (studentName, institution_id)
VALUES (?,?);

""";

    private static final String GET_ALL_STUDENTS_QUERY = """
SELECT * FROM Student

""";

    private static final String ENROLL_STUDENT_TO_COURSE_QUERY = """
INSERT INTO Enrollment (studentId, courseId)
VALUES (?, ?);


""";

    private static final String DELETE_STUDENT_QUERY = """
DELETE FROM Student
WHERE studentId = ?;

""";

    private static final String DELETE_ENROLLMENT_QUERY = "DELETE FROM Enrollment WHERE studentId = ?";

    private static final String GET_STUDENT_BY_ID_QUERY = """
SELECT * FROM Student
WHERE studentId = ?;
""";

    private static final String UPDATE_STUDENT_QUERY = """
UPDATE Student SET studentName = ? WHERE studentId = ?

""";

    private static final String GET_INSTITUTIONS_STUDENTS_QUERY = """
SELECT s.*
FROM Student s
JOIN Institution i ON s.institution_id = i.institution_id
WHERE i.institution_id = ?

""";

    String GET_STUDENT_BY_NAME_QUERY = "SELECT * FROM Student WHERE institution_id = ? AND studentName = ?";

    String GET_STUDENTS_BY_COURSE_QUERY = """
    SELECT s.*
    FROM Student s
    JOIN Enrollment e ON s.studentId = e.studentId
    JOIN InstitutionCourse ic ON e.courseId = ic.courseId
    WHERE s.institution_id = ? AND ic.courseId = ?
    """;

    String CHANGE_STUDENT_COURSE_QUERY = "UPDATE Enrollment SET courseId = ? WHERE studentId = ?";

    String TRANSFER_STUDENT_QUERY = "UPDATE Student SET institution_id = ? WHERE studentId = ?";

    String UPDATE_COURSE_QUERY = "UPDATE Enrollment SET courseId = ? WHERE studentId = ?";


    private static final String GET_COURSES_STUDENTS_QUERY = "SELECT s.studentId, s.studentName FROM Student s JOIN Enrollment e ON s.studentId = e.studentId WHERE e.courseId = ?";


    public Student createStudent(int id, Student student)
    {
        System.out.println("a student has been created!!");
        KeyHolder keyHolder = new GeneratedKeyHolder();
        springJdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(CREATE_STUDENT_QUERY, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, student.getStudentName());
            ps.setInt(2, id);
            return ps;
        }, keyHolder);
        int studentId = keyHolder.getKey().intValue();
        student.setStudentId(studentId);
        return student;
    }

    public void enrollStudentToCourse(@org.jetbrains.annotations.NotNull Student student, int courseId)
    {
        System.out.println("student has been enrolled to course!");
        System.out.println("The student id is:" + student.getStudentId() );
        springJdbcTemplate.update(ENROLL_STUDENT_TO_COURSE_QUERY, student.getStudentId(), courseId);
    }

    public Optional<List<Student>> getAllStudents()
    {
        return Optional.of(springJdbcTemplate.query(GET_ALL_STUDENTS_QUERY, new BeanPropertyRowMapper<>(Student.class)));

    }

    public void deleteStudentById(int id)
    {

        springJdbcTemplate.update(DELETE_ENROLLMENT_QUERY, id);
        springJdbcTemplate.update(DELETE_STUDENT_QUERY, id);
    }

    public Optional<Student> getStudentById(int id)
    {
        try {
            Student student = springJdbcTemplate.queryForObject(
                    GET_STUDENT_BY_ID_QUERY,
                    new BeanPropertyRowMapper<>(Student.class),
                    id
            );
            return Optional.ofNullable(student);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }


    public void updateStudent(int id, Student updatedStudent)
    {
        String newName = updatedStudent.getStudentName();
        springJdbcTemplate.update(UPDATE_STUDENT_QUERY, newName, id);
    }

    public List<Student> getAnInstitutionsStudents(int institutionId, int page, int size) {
        // Validate page and size parameters
        if (page < 1 || size < 1) {
            throw new IllegalArgumentException("Page and size must be greater than 0");
        }

        List<Student> allStudents = springJdbcTemplate.query(
                GET_INSTITUTIONS_STUDENTS_QUERY,
                new BeanPropertyRowMapper<>(Student.class),
                institutionId
        );

        int fromIndex = (page - 1) * size;
        int toIndex = Math.min(fromIndex + size, allStudents.size());

        if (fromIndex >= allStudents.size()) {
            return Collections.emptyList();
        }

        return allStudents.subList(fromIndex, toIndex);
    }

    public Optional<Student> getStudentInInstitutionByName(int institutionId, Student student) {


        List<Student> students = springJdbcTemplate.query(
                GET_STUDENT_BY_NAME_QUERY,
                new BeanPropertyRowMapper<>(Student.class),
                institutionId,
                student.getStudentName()
        );

        if (students.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(students.get(0));
        }
    }

    public Optional<List<Student>> getInstitutionsStudentsByCourse(int institutionId, int courseId) {


        List<Student> students = springJdbcTemplate.query(
                GET_STUDENTS_BY_COURSE_QUERY,
                new BeanPropertyRowMapper<>(Student.class),
                institutionId,
                courseId
        );

        if (students.isEmpty()) {
            System.out.println("no student takes this course");
            return Optional.empty();
        } else {
            System.out.println("we have found some students taking this course");
            return Optional.of(students);
        }
    }

    public void changeStudentCourse(int studentId, int courseId) {


        int rowsAffected = springJdbcTemplate.update(CHANGE_STUDENT_COURSE_QUERY, courseId, studentId);

        if (rowsAffected > 0) {
            System.out.println("Course changed successfully for studentId: " + studentId);
        } else {
            System.out.println("No enrollment found for studentId: " + studentId);
        }
    }



    public Optional<Course> searchCourseByName(String courseName, int studentId) {
        try {

            Optional<Student> currStudent = getStudentById(studentId);

            if (currStudent.isPresent()) {
                Student stu = currStudent.get();
                int institutionId = stu.getInstitution_id();

                return courseRepo.searchCourseByInstitution(courseName, institutionId);
            } else {
                System.out.println("Student with ID " + studentId + " not found.");
                return Optional.empty();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An error occurred while searching for the course: " + e.getMessage());
            return Optional.empty();
        }
    }

    public void transferStudent(int studentId, int newInstitutionId, Course newCourse) {



        try {

            Optional<Student> currStudent = getStudentById(studentId);

            if (currStudent.isPresent()) {

                int studentRowsAffected = springJdbcTemplate.update(TRANSFER_STUDENT_QUERY, newInstitutionId, studentId);

                if (studentRowsAffected > 0) {
                    System.out.println("Student institution updated successfully for studentId: " + studentId);


                    // CHECK IF COURSE EXISTS IN NEW INSTITUTION FIRST
                    Optional<Course> validCourse = courseRepo.searchCourseByInstitution(newCourse.getCourseName(), newInstitutionId);

                    if(validCourse.isPresent()){
                        Course certifiedCourse = validCourse.get();

                        // Update the courseId in the Enrollment table
                        int enrollmentRowsAffected = springJdbcTemplate.update(UPDATE_COURSE_QUERY, certifiedCourse.getCourseId(), studentId);

                        if (enrollmentRowsAffected > 0) {
                            System.out.println("Enrollment updated successfully for studentId: " + studentId);
                        } else {
                            System.out.println("No enrollment found for studentId: " + studentId);
                        }


                    }


                } else {
                    System.out.println("No student found with studentId: " + studentId);
                }
            } else {
                System.out.println("Student with ID " + studentId + " not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An error occurred while transferring the student: " + e.getMessage());
        }
    }

    public Optional<List<Student>> getCoursesStudents(int courseId)
    {
        List<Student> students = springJdbcTemplate.query(
                GET_COURSES_STUDENTS_QUERY,
                new BeanPropertyRowMapper<>(Student.class),
                courseId
        );

        return students.isEmpty() ? Optional.empty() : Optional.of(students);

    }
}
