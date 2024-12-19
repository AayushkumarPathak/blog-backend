package com.amz.blog.controllers;

import com.amz.blog.payloads.JwtAuthRequest;
import com.amz.blog.payloads.JwtAuthResponse;
import com.amz.blog.payloads.UserDto;
import com.amz.blog.security.JwtTokenHelper;
import com.amz.blog.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager  authenticationManager;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> createToken(@RequestBody JwtAuthRequest request) throws Exception {
        try {
            this.authenticate(request.getUsername(),request.getPassword());
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
            String token = this.jwtTokenHelper.generateToken(userDetails);
            JwtAuthResponse response = new JwtAuthResponse();
            response.setToken(token);

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(Map.of("Login Token: ","Unable to process Login Token (AuthController), Credentials Not Found"),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@Valid  @RequestBody UserDto userDto) {
        UserDto registeredUser = this.userService.registerNewUser(userDto);
        return new ResponseEntity<UserDto>(registeredUser, HttpStatus.CREATED);
    }

    private void authenticate(String username, String password) throws Exception {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,password);

        try {
            this.authenticationManager.authenticate(authenticationToken);
        }
        catch (BadCredentialsException e){
            System.out.println("Invalid Credentials");
            throw  new Exception("Invalid credentials");
        }
    }
}
