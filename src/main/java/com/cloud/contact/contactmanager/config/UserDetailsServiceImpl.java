
package com.cloud.contact.contactmanager.config;

import com.cloud.contact.contactmanager.Entities.User;
import com.cloud.contact.contactmanager.dao.UserRepositary;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    private UserRepositary userRepositary;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //fetching user from database
        User user = userRepositary.getUserByUserName(username);
        
        if(user == null){
            throw new UsernameNotFoundException("Could not found user !! ");
        }
        
       CustomUserDetails customUserDetails=new CustomUserDetails(user) {};
        
        return customUserDetails;

    }
    
}
