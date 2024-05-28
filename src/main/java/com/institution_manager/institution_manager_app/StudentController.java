package com.institution_manager.institution_manager_app;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class StudentController
{
    @RequestMapping("/hello")
    public String greeting()
    {
        return "heyy therrrreeee!!!!!";
    }
}
