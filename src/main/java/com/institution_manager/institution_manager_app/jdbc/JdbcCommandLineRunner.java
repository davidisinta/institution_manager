package com.institution_manager.institution_manager_app.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class JdbcCommandLineRunner implements CommandLineRunner {

    @Autowired
    private InstitutionRepository repo;


    @Override
    public void run(String... args) throws Exception
    {
        repo.addInstitution(new Institution("Yale University", "Salovey", 3003, 12889));
        repo.addInstitution(new Institution("Bush", "Pozee", 302, 2000));
        repo.addInstitution(new Institution("Harvard University", "Litmannen", 4000, 20002));
        repo.addInstitution(new Institution("UON", "Maina", 17000, 53029));
        repo.addInstitution(new Institution("MKU", "Obed", 3022, 20200));
        repo.addInstitution(new Institution("Tufts University", "Osano", 302, 2020));
        repo.addInstitution(new Institution("Mangu High School", "Mignolet", 302, 1928));

    }
}

