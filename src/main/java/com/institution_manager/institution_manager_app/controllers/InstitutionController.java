package com.institution_manager.institution_manager_app.controllers;

import com.institution_manager.institution_manager_app.jdbc.Institution;
import com.institution_manager.institution_manager_app.jdbc.InstitutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class InstitutionController
{

    @Autowired
    private InstitutionRepository repo;

    //add a new institution - create(POST)
    @RequestMapping("/institutions")
    public List<Institution> getInstitutions()
    {
        System.out.println("get institutions called!!");
        return repo.getAllInstitutions();

    }

    // get all institutions - read(GET)


    //search for an institution from list of institutions - READ(GET)


    //sort list of institutions by name(ascending and descending) - read(GET)


    // delete an institution - Delete("/institutions/id")


    // edit name of institution - update(PATCH)


}
