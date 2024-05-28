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
        repo.addInstitution(new Institution("Yale", "Salovey", 3003, 12889));
        repo.addInstitution(new Institution("Bush", "Pozee", 302, 2000));

    }
}

