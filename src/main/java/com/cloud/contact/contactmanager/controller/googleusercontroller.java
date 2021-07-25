
package com.cloud.contact.contactmanager.controller;

import com.cloud.contact.contactmanager.Entities.User;
import com.cloud.contact.contactmanager.dao.UserRepositary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/googleuser")
public class googleusercontroller {
    
    
    @Autowired
    private UserRepositary userRepositary;
    
    @RequestMapping(value = "/dashboard/{name}/{email}", method = RequestMethod.GET)
    public String userdasboard(@PathVariable("name") String name,@PathVariable("email") String email ,Model md) {
      
        System.out.println("Google User Name : " + name );
        System.out.println("Google User Email : " + email );
        String role="User_google";
        User gu = userRepositary.getUserByUserNameAndRole(email,role);
          System.out.println("this is user dashboard");
        if(gu == null){
            User u1=new User();
            u1.setName(name);
            u1.setEmail(email);
            u1.setEnabled(true);
            u1.setImageUrl("default.png");
            u1.setRole(role);
            u1.setPassword("googleidentity");
            u1.setAbout("I am Google User.");
            userRepositary.save(u1);
               md.addAttribute("title", "User Dashboard");
            md.addAttribute("user", u1);
            return "normal/User_Dashboard";
        }
        else{
               md.addAttribute("user", gu);
               md.addAttribute("title", "User Dashboard");
            return "normal/User_Dashboard";
        }
        
     

    }


}
