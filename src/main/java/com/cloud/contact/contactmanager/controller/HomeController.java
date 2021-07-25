
package com.cloud.contact.contactmanager.controller;

import com.cloud.contact.contactmanager.Entities.Contact;
import com.cloud.contact.contactmanager.Entities.User;
import com.cloud.contact.contactmanager.dao.UserRepositary;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
    
    @Autowired
    private UserRepositary userRepositary;
        
    @GetMapping("/hitdemo")
    @ResponseBody
    public String home(){
        User u=new User();
        u.setName("Arjun kumar");
        u.setEmail("arjunclg2@gmail.com");
       
        Contact c=new Contact();
        c.setName("jhon");
        c.setPhone("8090990785");
        
        Contact c1=new Contact();
        c1.setName("amit");
        c1.setPhone("8467093476");
        List<Contact> lc=new ArrayList<>();
        lc.add(c);
        lc.add(c1);
                
        u.setContacts(lc);
        userRepositary.save(u);
        return "working fine" ;
    }
}
