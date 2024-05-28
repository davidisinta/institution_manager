package com.institution_manager.institution_manager_app.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class InstitutionRepository
{
    @Autowired
    private JdbcTemplate springJdbcTemplate;


    private String ADD_INSTITUTION_QUERY = """
            INSERT INTO institutions (name, president, staffCount, studentCount)
            VALUES ( ?, ? , ?,  ?);
                        
            """;

    public void addInstitution(Institution institution)
    {
        System.out.println("institution added");

        springJdbcTemplate.update(ADD_INSTITUTION_QUERY, institution.getName(),
                institution.getPresident(), institution.getStaffCount(), institution.getStudentCount());
    }
}
