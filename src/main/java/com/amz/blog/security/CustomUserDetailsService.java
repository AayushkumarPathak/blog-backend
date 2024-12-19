package com.amz.blog.security;


import com.amz.blog.entities.User;
import com.amz.blog.exceptions.ResourceNotFoundException;
import com.amz.blog.repositories.UserRepo;
import com.amz.blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service

public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
   private UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //load user from Db
        User user = this.userRepo.findByEmail(username).orElseThrow(
                () -> new ResourceNotFoundException("User", "email", username));


        return user;
    }
}
