package com.institution_manager.institution_manager_app.controllers;

import com.institution_manager.institution_manager_app.jdbc.Institution;
import com.institution_manager.institution_manager_app.jdbc.InstitutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class InstitutionController
{

    @Autowired
    private InstitutionRepository repo;

    //add a new institution - create(POST)
    @PostMapping("/institution/create")
    public ResponseEntity<?> createInstitution(@RequestBody Institution institution)
    {


        try{
            //query db to check if Institution already exists
            String currentInstitutionName = institution.getName();

            List<Optional<Institution>> existingInstitution = repo.searchInstitution(currentInstitutionName);

            return ResponseEntity.status(HttpStatus.CONFLICT).body("Sorry!" +
                    "The institution with name " + currentInstitutionName + " Already Exists");

        }
        catch(Exception ex)
        {
        // if it does not exist, create it
        repo.createInstitution(institution);

        return ResponseEntity.status(HttpStatus.CREATED).build();

    }


    }


    // get all institutions - read(GET)
    @GetMapping("/institutions")
    public List<Institution> getInstitutions()
    {
        System.out.println("get institutions called!!");
        return repo.getAllInstitutions();

    }


    //get institution by id
    @GetMapping("institutions/id/{id}")
    public ResponseEntity<?> getInstitutionById(@PathVariable int id) {
        try {
            Optional<Institution> institution = repo.getInstitution(id);
            return ResponseEntity.ok(institution.get());

        } catch (EmptyResultDataAccessException ex) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", HttpStatus.NOT_FOUND.value());
            errorResponse.put("message", "Institution with ID " + id + " not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }



    //search for an institution from list of institutions - READ(GET)
    @GetMapping("/institutions/name/{name}")
    public List<Optional<Institution>> searchInstitutionByName(@PathVariable String name)
    {
        System.out.println("search institution by name called!!");
        return repo.searchInstitution(name);
    }


    //sort list of institutions by name(ascending and descending) - read(GET)


    // delete an institution - Delete("/institutions/id")
    @DeleteMapping("/institutions/{id}")
    public ResponseEntity<?> deleteInstitution(@PathVariable int id)
    {
        //come back and check if institution has a course before deleting

        System.out.println("Delete institution called!!");
        try {
            repo.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (EmptyResultDataAccessException ex) {
            return ResponseEntity.notFound().build();
        }
    }


    // edit name of institution - update(PATCH)
    @PatchMapping("/institutions/{id}")
    public ResponseEntity<?> updateInstitution(@PathVariable int id, @RequestBody Institution updatedInstitution)
    {
        //come back and check if there is an institution with the new name you want to assign first
        // before performing the edit

        try {
            Optional<Institution> existingInstitution = repo.getInstitution(id);

            if (existingInstitution.isPresent()) {

                repo.update(id, updatedInstitution);

                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (EmptyResultDataAccessException ex) {
            return ResponseEntity.notFound().build();
        }
    }


}
