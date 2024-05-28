package com.institution_manager.institution_manager_app.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InstitutionRepository
{
    @Autowired
    private JdbcTemplate springJdbcTemplate;


    private String ADD_INSTITUTION_QUERY = """
            INSERT INTO institutions (name, president, staffCount, studentCount)
            VALUES ( ?, ? , ?,  ?);
                        
            """;


    private String GET_ALL_INSTITUTIONS_QUERY = """
            SELECT * FROM INSTITUTIONS
            """;

    private String GET_INSTITUTION_BY_ID_QUERY = """
            SELECT * FROM INSTITUTIONS
            WHERE id = ?
            """;
    public void addInstitution(Institution institution)
    {
        System.out.println("institution added");

        springJdbcTemplate.update(ADD_INSTITUTION_QUERY, institution.getName(),
                institution.getPresident(), institution.getStaffCount(), institution.getStudentCount());
    }


    public List<Institution> getAllInstitutions()
    {
       return springJdbcTemplate.query(GET_ALL_INSTITUTIONS_QUERY,new BeanPropertyRowMapper<>(Institution.class));

    }

    public Institution getInstitution(int id)
    {
        return springJdbcTemplate.queryForObject(GET_INSTITUTION_BY_ID_QUERY,
                new BeanPropertyRowMapper<>(Institution.class),id);
    }
}
