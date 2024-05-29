package com.institution_manager.institution_manager_app.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CourseRepository {


    @Autowired
    private JdbcTemplate springJdbcTemplate;

    private static final String CREATE_COURSE_QUERY = """
            INSERT INTO Course (courseName)
            VALUES (?)
            """ ;

    private static final String SEARCH_COURSE_BY_NAME_QUERY = """
            SELECT * FROM Course WHERE name = ?;
            """;

    private static final String GET_ALL_COURSES_QUERY = """
SELECT * FROM Course
""" ;



    public void createCourse(Course course)
    {
        System.out.println("a course has been created!!");
        springJdbcTemplate.update(CREATE_COURSE_QUERY, course.getCourseName());

    }

    public Optional<Course> searchCourse(String currentCourseName)
    {
        List<Course> courses = springJdbcTemplate.query(
                SEARCH_COURSE_BY_NAME_QUERY,
                new BeanPropertyRowMapper<>(Course.class),
                currentCourseName
        );

        if (!courses.isEmpty()) {
            return Optional.of(courses.get(0));
        } else {
            return Optional.empty();
        }
    }

    public Optional<List<Course>> getAllCourses()
    {
        return Optional.of(springJdbcTemplate.query(GET_ALL_COURSES_QUERY, new BeanPropertyRowMapper<>(Course.class)));

    }
}
