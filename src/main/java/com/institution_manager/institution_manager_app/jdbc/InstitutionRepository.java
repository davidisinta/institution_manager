package com.institution_manager.institution_manager_app.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class InstitutionRepository
{

    @Autowired
    private JdbcTemplate springJdbcTemplate;


    private String ADD_INSTITUTION_QUERY = """
            INSERT INTO Institution (name, president, staffCount, studentCount)
            VALUES ( ?, ? , ?,  ?);
                        
            """;


    private String GET_ALL_INSTITUTIONS_QUERY = """
            SELECT * FROM INSTITUTION
            """;

    private String GET_INSTITUTION_BY_ID_QUERY = """
            SELECT * FROM INSTITUTION
            WHERE institution_id = ?
            """;

    private String CREATE_INSTITUTION_QUERY = """
            INSERT INTO Institution (name, president, staffCount, studentCount)
            VALUES (?, ?, ?, ?);          
            """;


    private static final String GET_INSTITUTION_BY_NAME_QUERY = """
            SELECT * FROM Institution WHERE name LIKE '%?%';
            """;

    public void addInstitution(Institution institution)
    {
        System.out.println("institution " + institution.getName() + " added");

        springJdbcTemplate.update(ADD_INSTITUTION_QUERY, institution.getName(),
                institution.getPresident(), institution.getStaffCount(), institution.getStudentCount());
    }


    public List<Institution> getAllInstitutions()
    {
       return springJdbcTemplate.query(GET_ALL_INSTITUTIONS_QUERY,new BeanPropertyRowMapper<>(Institution.class));

    }

    public Optional<Institution> getInstitution(int id) {
        return Optional.ofNullable(springJdbcTemplate.queryForObject(GET_INSTITUTION_BY_ID_QUERY,
                new BeanPropertyRowMapper<>(Institution.class), id));
    }


    public void createInstitution(Institution institution)
    {
        springJdbcTemplate.update(CREATE_INSTITUTION_QUERY, institution.getName(),
                institution.getPresident(), institution.getStaffCount(), institution.getStudentCount());
    }

    public Optional<Institution> searchInstitution(String name)
    {
        return Optional.ofNullable(springJdbcTemplate.queryForObject(GET_INSTITUTION_BY_NAME_QUERY,
                new BeanPropertyRowMapper<>(Institution.class), name));

    }
}
