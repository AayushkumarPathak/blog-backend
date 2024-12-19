package com.amz.blog.repositories;

import com.amz.blog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;


public interface UserRepo extends JpaRepository<User,String> {
    Optional<User> findByEmail(String email);
}
