package com.amz.blog.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amz.blog.payloads.UserDto;
import com.amz.blog.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        try {
            // userDto.setId(UUID.randomUUID().toString());
            UserDto createdUser = this.userService.createUser(userDto);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    // Update user
    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable String userId) {
        UserDto updatedUser = userService.updateUser(userDto, userId);
        return ResponseEntity.ok(updatedUser);
    }

    // delete user
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable String userId) {
        boolean isDeleted = userService.deleteUser(userId);
        if (isDeleted) {
            return new ResponseEntity<>(Map.of("Message", "User deleted Successfully."),
                    HttpStatus.OK);
        }
        return new ResponseEntity<>(Map.of("Message", "Deletion Failed"),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // getAllUsers
    @GetMapping("/")
    public ResponseEntity<?> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // get user
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUser(@PathVariable String userId) {
        UserDto user = userService.getUserById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
