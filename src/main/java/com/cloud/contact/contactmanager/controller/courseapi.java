/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cloud.contact.contactmanager.controller;

import com.cloud.contact.contactmanager.Entities.Course;
import com.cloud.contact.contactmanager.dao.CourseRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class courseapi {

    @Autowired
    private CourseRepository courseRepository;
    
  
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/AddCourse")
    public Course AddCourse(@RequestBody Course course )
    {
        Course c = courseRepository.save(course);
        return c ;
    }
 
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(value = "/getAllCourses")
    public List<Course> getallcoursesm() {
          
        List<Course> lic = (List<Course>) courseRepository.findAll();
        return lic ;
    }
    
    
}
