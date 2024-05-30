package com.institution_manager.institution_manager_app.controllers;

import com.institution_manager.institution_manager_app.jdbc.Course;
import com.institution_manager.institution_manager_app.jdbc.CourseRepository;
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

    @Autowired
    private CourseRepository courseRepo;

    //add a new institution - create(POST)
    @PostMapping("/institution/create")
    public ResponseEntity<?> createInstitution(@RequestBody Institution institution)
    {


        try{
            //query db to check if Institution already exists
            String currentInstitutionName = institution.getName();

            Optional<Institution> existingInstitution = repo.searchInstitution(currentInstitutionName);

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



    //filter for an institution(s) from list of institutions - READ(GET)
    @GetMapping("/institutions/filter/name/{name}")
    public List<Optional<Institution>> filterInstitutionByName(@PathVariable String name)
    {
        System.out.println("filter institution by name called!!");
        return repo.filterInstitution(name);
    }

    //search for an institution(s) from list of institutions - READ(GET)
    @GetMapping("/institutions/search/name/{name}")
    public Optional<Institution> searchInstitutionByName(@PathVariable String name)
    {
        System.out.println("search institution by name called!!");
        return repo.searchInstitution(name);
    }



    // delete an institution - Delete("/institutions/id")
    @DeleteMapping("/institutions/{id}")
    public ResponseEntity<?> deleteInstitution(@PathVariable int id)
    {
        //come back and check if institution has a course before deleting
        System.out.println("Delete institution called!!");

        Optional<List<Course>> potentialCourses = courseRepo.getAnInstitutionsCourses(id);

        if(potentialCourses.isPresent()){
            System.out.println("Institution not deleted because there is a course" +
                    "assigned to it");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Institution not " +
                    "deleted because there is a course" +
                    "assigned to it");
        }
        else{

            System.out.println("Institution deleted!! ");

            try {
                repo.deleteById(id);
                return ResponseEntity.ok().build();
            } catch (EmptyResultDataAccessException ex) {
                return ResponseEntity.notFound().build();
            }

        }



    }


    // edit name of institution - update(PATCH)
    @PatchMapping("/institutions/{id}")
    public ResponseEntity<?> updateInstitution(@PathVariable int id, @RequestBody Institution updatedInstitution)
    {

        try {
            Optional<Institution> existingInstitution = repo.getInstitution(id);

            Optional<Institution> otherInstitutionWithGivenName = repo.searchInstitution(updatedInstitution.getName());

            if(otherInstitutionWithGivenName.isPresent())
            {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Sorry!" +
                        "The institution with name " + updatedInstitution.getName() + " Already Exists." +
                        "You cant change the name of an institution to that of an existing institution");

            }


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


    //sort list of institutions by name(ascending and descending) - read(GET)
    @GetMapping("/institutions/sort/ascend")
    public List<Institution> sortInstitutionsAscending()
    {
        System.out.println("sorting institutions by ascending order!!");
        return repo.getInstitutionsAscending();
    }

    @GetMapping("/institutions/sort/descend")
    public List<Institution> sortInstitutionsDescending()
    {
        System.out.println("sorting institutions by descending order!!");

        return repo.getInstitutionsDescending();
    }


}
