
package com.cloud.contact.contactmanager.config;


import com.cloud.contact.contactmanager.Entities.User;
import java.util.Arrays;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


public abstract class CustomUserDetails implements UserDetails{
    
    private User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }
    
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        
        SimpleGrantedAuthority sga = new SimpleGrantedAuthority(user.getRole());
        System.out.println("setting : " +Arrays.asList(sga));
        return Arrays.asList(sga);
     
    }

    @Override
    public String getPassword() {
       return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true ;
       
    }

    @Override
    public boolean isAccountNonLocked() {
       return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
       return true;
    }
    
}
