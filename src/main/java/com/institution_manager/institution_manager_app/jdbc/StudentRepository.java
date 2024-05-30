package com.institution_manager.institution_manager_app.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.support.KeyHolder;


import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class StudentRepository {


    @Autowired
    private JdbcTemplate springJdbcTemplate;

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
}
