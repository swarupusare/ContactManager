package com.cloud.contact.contactmanager.controller;

import com.cloud.contact.contactmanager.Entities.User;
import com.cloud.contact.contactmanager.dao.UserRepositary;
import com.cloud.contact.contactmanager.helper.Message;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class contactcontroller {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @Autowired
    private UserRepositary userRepositary;

    @GetMapping("/home")
    public String homepage2(Model md) {

        return "redirect:/";
    }

    @GetMapping("/")
    public String homepage(Model md) {
        md.addAttribute("title", "digital-contact-manager");

        return "home";
    }

    @GetMapping("/about")
    public String aboutpage(Model md) {
        md.addAttribute("title", "digital-contact-manager");

        return "about";
    }
     @GetMapping("/signin")
    public String loginpage(Model md) {
        md.addAttribute("title", "Login - Digital-contact-manager");
      
        return "login";
    }
    
    
    @GetMapping("/signup")
    public String signuppage(Model md) {
        md.addAttribute("title", "Register - digital-contact-manager");
        md.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/do_register")
    public String doregister( @ModelAttribute("userinfo") User user, @RequestParam(value = "agreement", defaultValue = "false") boolean agreement, Model md, HttpSession session) {
        try {
            
            md.addAttribute("title", "Register - digital-contact-manager");
            user.setEnabled(agreement);
            user.setRole("ROLE_USER");
            user.setImageUrl("default.png");
            System.out.println("email :" +user.getEmail());
            System.out.println("password :"+user.getPassword());
                    
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            if (!agreement) {
                System.out.println("you are not accept terms and condition !!!!! ");
                throw new Exception("you are not accept terms and condition !!!!! ");
            }
//            if(result.hasErrors()){
//                System.out.println("Error :" +result.toString());
//                md.addAttribute("user", new User());
//                return "signup";
//            }
            System.out.println("User :" + user);
            System.out.println("agreement :" + agreement);
            md.addAttribute("user", new User());
            User s = userRepositary.save(user);
            if (s == user) {

                session.setAttribute("msg", new Message("Registration Successfully Done !!", "alert-success"));
                return "signup";
            }
        } catch (Exception e) {
            e.printStackTrace();
            md.addAttribute("user", new User());
            session.setAttribute("msg", new Message("something went wrong !!" + e.getMessage(), "alert-danger"));
            return "signup";
        }

        return "signup";
    }
    
}
