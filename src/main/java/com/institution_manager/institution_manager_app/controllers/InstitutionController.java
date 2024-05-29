package com.institution_manager.institution_manager_app.controllers;

import com.institution_manager.institution_manager_app.jdbc.Institution;
import com.institution_manager.institution_manager_app.jdbc.InstitutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class InstitutionController
{

    @Autowired
    private InstitutionRepository repo;

    //add a new institution - create(POST)
    @PostMapping("/institution/create")
    public ResponseEntity<?> createInstitution(@RequestBody Institution institution)
    {

        //query db to check if Institution already exists

        // if it does not exist, create it
        repo.createInstitution(institution);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    // get all institutions - read(GET)
    @GetMapping("/institutions")
    public List<Institution> getInstitutions()
    {
        System.out.println("get institutions called!!");
        return repo.getAllInstitutions();

    }


    //get institution by id
    @GetMapping("institutions/{id}")
    public Institution getInstitutionById(@PathVariable int id)
    {
        System.out.println("get institution by id called!!");
        return repo.getInstitution(id);
    }

    //search for an institution from list of institutions - READ(GET)
    @GetMapping("/institution/{name}")
    public Institution searchInstitutionByName(@PathVariable String name)
    {
        System.out.println("search institution by name called!!");
        return repo.searchInstitution(name);
    }


    //sort list of institutions by name(ascending and descending) - read(GET)


    // delete an institution - Delete("/institutions/id")


    // edit name of institution - update(PATCH)


}
