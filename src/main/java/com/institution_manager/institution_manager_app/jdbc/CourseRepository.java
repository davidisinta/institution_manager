package com.institution_manager.institution_manager_app.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
            SELECT * FROM Course WHERE courseName = ?;
            """;

    private static final String GET_ALL_COURSES_QUERY = """
SELECT * FROM Course
""" ;

    private static final String GET_COURSE_BY_INSTITUTION_QUERY = """
            SELECT c.*
            FROM Course c
            JOIN InstitutionCourse ic ON c.courseId = ic.courseId
            WHERE c.courseName = ? AND ic.institution_id = ?;         
            """;

    private static final String ADD_COURSE_TO_INSTITUTION_QUERY = """             
            INSERT INTO InstitutionCourse(institution_id, courseId)
            VALUES (?, ?);
                               
            """;


    private static final String GET_INSTITUTIONS_COURSES_QUERY = """ 
            SELECT Course.courseId, Course.courseName
            FROM Course
            JOIN InstitutionCourse ON Course.courseId = InstitutionCourse.courseId
            WHERE InstitutionCourse.institution_id = ?;          
            """;

    private static final String FILTER_INSTITUTIONS_COURSES_QUERY = """
SELECT c.courseId, c.courseName
FROM Course c
JOIN InstitutionCourse ic ON c.courseId = ic.courseId
JOIN Institution i ON ic.institution_id = i.institution_id
WHERE c.courseName = ?  AND i.institution_id = ?;
""";

    private static final String GET_ALL_COURSES_ASCENDING_QUERY = """
            SELECT Course.courseId, Course.courseName
            FROM Course
            JOIN InstitutionCourse ON Course.courseId = InstitutionCourse.courseId
            WHERE InstitutionCourse.institution_id = ?
            ORDER BY courseName ASC;   

""";

    private static final String GET_ALL_COURSES_DESCENDING_QUERY = """
            SELECT Course.courseId, Course.courseName
            FROM Course
            JOIN InstitutionCourse ON Course.courseId = InstitutionCourse.courseId
            WHERE InstitutionCourse.institution_id = ?
            ORDER BY courseName DESC;   
""";

    private static final String DELETE_COURSE_QUERY = """
DELETE FROM Course
    WHERE CourseId = ?
""";

    private static final String GET_COURSE_BY_ID_QUERY = """
    SELECT * FROM Course
    WHERE CourseId = ?;
""";

    private static final String UPDATE_COURSE_QUERY =
            """
UPDATE Course SET courseName = ? WHERE courseId = ?

""";



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

    public Optional<Course> searchCourseByInstitution(String proposedCourseName, int institutionId) {
        try {
            Course course = springJdbcTemplate.queryForObject(
                    GET_COURSE_BY_INSTITUTION_QUERY,
                    new BeanPropertyRowMapper<>(Course.class),
                    proposedCourseName, institutionId
            );
            return Optional.of(course);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }


    }


    public void addCourseToInstitution (Course course, int institutionId)
    {
        int courseId = course.getCourseId();
        System.out.println("Course id is: " + courseId);
        System.out.println("Institution id is: " + institutionId);

        springJdbcTemplate.update(ADD_COURSE_TO_INSTITUTION_QUERY, institutionId, courseId);

    }

    public Optional<List<Course>> getAnInstitutionsCourses(int institutionId) {
        List<Course> courses = springJdbcTemplate.query(
                GET_INSTITUTIONS_COURSES_QUERY,
                new BeanPropertyRowMapper<>(Course.class),
                institutionId
        );

        return courses.isEmpty() ? Optional.empty() : Optional.of(courses);
    }

    public Optional<List<Course>> filterInstitutionsCoursesByName(int id, String courseName)
    {
        List<Course> courses = springJdbcTemplate.query(
                FILTER_INSTITUTIONS_COURSES_QUERY,
                new BeanPropertyRowMapper<>(Course.class),
                 courseName, id
        );

        return courses.isEmpty() ? Optional.empty() : Optional.of(courses);

    }

    public Optional<List<Course>> getCoursesAscending(int id)
    {
        List<Course> courses = springJdbcTemplate.query(
                GET_ALL_COURSES_ASCENDING_QUERY,
                new BeanPropertyRowMapper<>(Course.class),id
        );

        return courses.isEmpty() ? Optional.empty() : Optional.of(courses);


    }

    public Optional<List<Course>> getCoursesDescending(int id)
    {
        List<Course> courses = springJdbcTemplate.query(
                GET_ALL_COURSES_DESCENDING_QUERY,
                new BeanPropertyRowMapper<>(Course.class),id
        );

        return courses.isEmpty() ? Optional.empty() : Optional.of(courses);

    }

    public void deleteById(int id)
    {
        springJdbcTemplate.update(DELETE_COURSE_QUERY, id);
    }

    public Optional<Course> getCourseById(int id) {
        try {
            Course course = springJdbcTemplate.queryForObject(
                    GET_COURSE_BY_ID_QUERY,
                    new BeanPropertyRowMapper<>(Course.class),
                    id
            );
            return Optional.ofNullable(course);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void updateCourse(int id, Course updatedCourse)
    {
        String newName = updatedCourse.getCourseName();
        springJdbcTemplate.update(UPDATE_COURSE_QUERY, newName, id);
    }
}
