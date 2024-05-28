package com.institution_manager.institution_manager_app;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InstitutionController
{

    //add a new institution - create(POST)
    @RequestMapping("/home")
    public String createInstitution()
    {
        return "Heyyy theree, you have created an instituiton named Yale uni! yaaahhh!! lets get it";
    }

    // get all institutions - read(GET)


    //search for an institution from list of institutions - READ(GET)


    //sort list of institutions by name(ascending and descending) - read(GET)


    // delete an institution - Delete("/institutions/id")


    // edit name of institution - update(PATCH)


}
