package com.institution_manager.institution_manager_app.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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


    private static final String FILTER_INSTITUTION_BY_NAME_QUERY = """
            SELECT * FROM Institution WHERE name LIKE CONCAT('%', ?, '%');
            """;


    private static final String SEARCH_INSTITUTION_BY_NAME_QUERY = """
            SELECT * FROM Institution WHERE name = ?;
            """;

    private static final String DELETE_INSTITUTION_QUERY = """
    DELETE FROM Institution
    WHERE institution_id = ?
""";

    String UPDATE_INSTITUTION_QUERY = "UPDATE Institution SET name = ? WHERE institution_id = ?";

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

    public List<Optional<Institution>> filterInstitution(String name) {
        List<Institution> institutions = springJdbcTemplate.query(
                FILTER_INSTITUTION_BY_NAME_QUERY,
                new BeanPropertyRowMapper<>(Institution.class),
                name
        );




        List<Optional<Institution>> optionalInstitutions = institutions.stream()
                .map(Optional::ofNullable)
                .collect(Collectors.toList());

        return optionalInstitutions;
    }

    public void deleteById(int id)
    {
        springJdbcTemplate.update(DELETE_INSTITUTION_QUERY, id);

    }

    public void update(int id, Institution newInstitution)
    {
        String newName = newInstitution.getName();
        springJdbcTemplate.update(UPDATE_INSTITUTION_QUERY, newName, id);

    }


    public Optional<Institution> searchInstitution(String name) {
        List<Institution> institutions = springJdbcTemplate.query(
                SEARCH_INSTITUTION_BY_NAME_QUERY,
                new BeanPropertyRowMapper<>(Institution.class),
                name
        );

        if (!institutions.isEmpty()) {
            return Optional.of(institutions.get(0));
        } else {
            return Optional.empty();
        }
    }

}
