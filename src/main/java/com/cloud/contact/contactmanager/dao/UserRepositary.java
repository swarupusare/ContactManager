
package com.cloud.contact.contactmanager.dao;


import com.cloud.contact.contactmanager.Entities.Contact;
import com.cloud.contact.contactmanager.Entities.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepositary extends JpaRepository<User, Integer> {
    
     @Query("select u from User u where u.email = :email ")
     public User getUserByUserName(@Param("email") String email);

     @Query("select c from Contact c where c.user.id = :uid ")
     public List<Contact> getallContactByUserID(@Param("uid") int userId);
    
    @Query("select u from User u where u.email = :uemail and u.role = :urole")
    public User getUserByUserNameAndRole(@Param("uemail")String email,@Param("urole") String role);
}
