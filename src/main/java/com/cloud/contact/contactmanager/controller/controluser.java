package com.cloud.contact.contactmanager.controller;

import com.cloud.contact.contactmanager.Entities.Contact;
import com.cloud.contact.contactmanager.Entities.User;
import com.cloud.contact.contactmanager.dao.ContactRepository;
import com.cloud.contact.contactmanager.dao.PageContactRepositary;
import com.cloud.contact.contactmanager.dao.UserRepositary;
import com.cloud.contact.contactmanager.helper.Message;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/user")
public class controluser {

    @Autowired
    private UserRepositary userRepositary;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private PageContactRepositary pageContactRepositary;

//    add data to every url that comes after /user (Common Data)
    @ModelAttribute
    public void addcommondata(Model md, Principal principal) {

        String name = principal.getName();
        System.out.println("Name :" + name);
//         Class<? extends Principal> c = principal.getClass();
//         System.out.println("class" +c);
//         String s = principal.toString();
//         System.out.println("string full : " +s );
        User user = userRepositary.getUserByUserName(name);
        System.out.println("user : " + user);
        md.addAttribute("user", user);
        System.out.println("common user data...........................");
    }

//      Normal User Dashboard
    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String userdasboard(Model md, Principal principal) {

        md.addAttribute("title", "User Dashboard");

        System.out.println("this is user dashboard");
        return "normal/User_Dashboard";

    }

//    //payment accept 
//    @RequestMapping(value = "/makepayment", method = RequestMethod.GET)
//    public String userpayment(Model md, @RequestParam("amount") String amount) throws RazorpayException {
//        md.addAttribute("title", "User Dashboard");
//        System.out.println("AMOUNT of Money : " + amount);
//        RazorpayClient client = new RazorpayClient("rzp_test_3cu0AsMC3QBebI","F2aoxq8eFWw1EoUU5h130mBI");
//        try {
//            JSONObject orderRequest = new JSONObject();
//            orderRequest.put("amount", amount); // amount in the smallest currency unit
//            orderRequest.put("currency", "INR");
//            orderRequest.put("receipt", "order_rcptid_11");
//
//            Order order = client.Orders.create(orderRequest);
//            System.out.println("Order : " + order);
//        } catch (RazorpayException e) {
//            // Handle Exception
//            System.out.println(e.getMessage());
//        }
//        return "normal/User_Dashboard";
//    }
     //payment accept 

    /**
     *
     * @param data
     * @return
     * @throws RazorpayException
     */
    @RequestMapping(value = "/makepayment", method = RequestMethod.POST)
    @ResponseBody
    public String userpayment(@RequestBody Map<String,Object> data) throws RazorpayException {
    
  
        
        int amt=Integer.parseInt(data.get("amount").toString());
            System.out.println("AMOUNT of Money  " + amt);
        RazorpayClient client = new RazorpayClient("rzp_test_7SrSJRuV2pQ0sg","wItUAZ9iLoQScRNWiH8RUzz7");
           String ord = null;
        try {
            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", amt*100); // amount in the smallest currency unit
            orderRequest.put("currency", "INR");
         
            orderRequest.put("receipt", "order_rcptid_11");

            Order order = client.Orders.create(orderRequest);
             ord=order.toString();
            System.out.println("Order : " + order);
        } catch (RazorpayException e) {
            // Handle Exception
            System.out.println(e.getMessage());
        }
    
     
        return ord;
    }

//     Normal User AddContact
    @RequestMapping(value = "/AddContact", method = RequestMethod.GET)
    public String usercontactform(Model md) {

        md.addAttribute("title", "Add Contact");
        System.out.println("this is user add contact");
        return "normal/User_AddContact";

    }
//    Normal User AddContact processing

    @PostMapping("/contact_process")
    public String contactprocessform(@ModelAttribute("contactdata") Contact contact, @RequestParam("profilename") MultipartFile file, Model md, Principal principal) {

        String uemail = principal.getName();
        System.out.println("name" + uemail);
        User user = userRepositary.getUserByUserName(uemail);
        System.out.println("user : " + user);

        User u = new User();

        List<Contact> ls = new ArrayList<>();

        Contact c = new Contact();

//        ----Copying File--------
        String filename = file.getOriginalFilename();

        System.out.println("Image Name :" + filename);

        try {
            if (file.isEmpty()) {
                System.out.println("File is Empty");
                c.setImage("contact.png");
            } else {
                System.out.println("entering........");
                File filenamepath = new ClassPathResource("static/Img").getFile();
                System.out.println("Path :" + filenamepath);
                Path path = Paths.get(filenamepath.getAbsolutePath() + File.separator + filename);
                System.out.println("second path :" + path);
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Image is uploaded ................");
                c.setImage(filename);
            }

        } catch (IOException ex) {
            Logger.getLogger(controluser.class.getName()).log(Level.SEVERE, null, ex);
        }

        c.setUser(user);
        c.setName(contact.getName());
        c.setEmail(contact.getEmail());
        c.setPhone(contact.getPhone());
        c.setSecondName(contact.getSecondName());

        c.setWork(contact.getWork());
        c.setDescription(contact.getDescription());
        u.setContacts(ls);
        ls.add(c);
        userRepositary.save(u);
        md.addAttribute("msg", new Message("New Contact added successfully !", "success"));
        System.out.println("contact name" + contact.getName());
        System.out.println("contact Email" + contact.getEmail());
        System.out.println("contact work" + contact.getWork());
        md.addAttribute("title", "Add Contact");
        System.out.println("contact processing...");
        return "normal/User_AddContact";

    }
//       Normal User View contact

    @GetMapping("/ViewContact/{pageNo}")
    public String viewcontactprocess(@PathVariable("pageNo") int pageno, Model md, Principal principal) {
        String uemail = principal.getName();
        System.out.println("name" + uemail);
        User user = userRepositary.getUserByUserName(uemail);
        System.out.println("user : " + user);
//        List<Contact> lcbyuid = userRepositary.getallContactByUserID(user.getId());

        md.addAttribute("title", "View Contact");
        System.out.println("View All Contact...");
        System.out.println("pagination.......");

//       --------pagination--------
//            3 is pageSize
        Pageable paging = PageRequest.of(pageno, 3);
        Page<Contact> pagedResult = pageContactRepositary.getallContactByUserID(user.getId(), paging);
//        Page<Contact> pagedResult = pageContactRepositary.findAll(paging);

        md.addAttribute("contacts", pagedResult.toList());
        md.addAttribute("currentpage", pageno);
        md.addAttribute("totalpages", pagedResult.getTotalPages());
        return "normal/User_ViewContact";

    }

//    Normal User Delete Contact with its contact id
    @GetMapping("/delete/{cid}")
    public String delete(@PathVariable("cid") int contactid) {
//        int a = JOptionPane.showConfirmDialog(null, "Are you sure to delete this contact", "delete contact", 1);
//        System.out.println(" a : " + a);
//        System.out.println("id : " + contactid);
//
//        if (a == 0) {
//
//            int dc = contactRepository.deletecontactbyID(contactid);
//            System.out.println("result :" + dc);
//            System.out.println("conatct deleted............");
//        } else {
//            System.out.println("not deleted ..........");
//        }

        int dc = contactRepository.deletecontactbyID(contactid);
        System.out.println("result :" + dc);
        System.out.println("conatct deleted............");
        return "redirect:/user/ViewContact/0";
    }
//    Normal User Updata Contact 

    @PostMapping("/EditContact")
    public String updatacontact(@ModelAttribute("editdetails") Contact contact, Principal principal) {

        Contact oldcontact = contactRepository.getcontactbyID(contact.getCid());

        System.out.println("previous contact Name " + oldcontact.getName());
        System.out.println("updated contact Name  : " + contact.getName());
        System.out.println("updating.........");
        oldcontact.setName(contact.getName());
        oldcontact.setSecondName(contact.getSecondName());
        oldcontact.setPhone(contact.getPhone());
        oldcontact.setWork(contact.getWork());
        oldcontact.setEmail(contact.getEmail());
        oldcontact.setDescription(contact.getDescription());
        Contact up = contactRepository.save(oldcontact);
        System.out.println("database updated contact name : " + up.getName());
        System.out.println("contact updated........");
        return "redirect:/user/ViewContact/0";

    }

    @PostMapping("/SearchContact")
    public String searchcontact(@RequestParam("scn") String cname, Model md, Principal principal) {

        String uemail = principal.getName();
        System.out.println("name" + uemail);
        User user = userRepositary.getUserByUserName(uemail);
        System.out.println("user : " + user);
        System.out.println("searching.......");
        Pageable paging = PageRequest.of(0, 10);
        Page<Contact> pagedResult = pageContactRepositary.getallContactByUserIDandCName(user.getId(), cname, paging);
        List<Contact> lc = pagedResult.toList();

        for (Contact c : lc) {
            System.out.println("enter contact....");
            System.out.println("name : " + c.getName());
            System.out.println("phone : " + c.getPhone());
        }

//        Page<Contact> pagedResult = pageContactRepositary.findAll(paging);
        System.out.println("searching Done.......");
        md.addAttribute("contacts", pagedResult.toList());
        md.addAttribute("currentpage", 0);
        md.addAttribute("totalpages", pagedResult.getTotalPages());

        return "normal/User_ViewContact";

    }

}
