package com.amz.blog.services.impl;

import com.amz.blog.config.AppConstants;
import com.amz.blog.entities.Role;
import com.amz.blog.entities.User;
import com.amz.blog.exceptions.ResourceNotFoundException;
import com.amz.blog.payloads.UserDto;
import com.amz.blog.repositories.RoleRepo;
import com.amz.blog.repositories.UserRepo;
import com.amz.blog.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDto createUser(UserDto userDto) {
        try{
            userDto.setId(UUID.randomUUID().toString());
            User user = this.dtoTOUser(userDto);
            User savedUser = this.userRepo.save(user);
            return this.userToDto(savedUser);
        }
        catch (Exception e){
//            e.printStackTrace();
            System.out.println(e.getMessage());
            return null;
        }

    }

    @Override
    public UserDto updateUser(UserDto userDto, String id) {
        try{
            User user = this.userRepo.findById(id).
                    orElseThrow(()-> new ResourceNotFoundException("User","id",id));
            user.setId(userDto.getId());
            user.setName(userDto.getName());
            user.setEmail(userDto.getEmail());
            user.setPassword(userDto.getPassword());
            user.setAbout(userDto.getAbout());

            return this.userToDto(user);

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public UserDto getUserById(String id) {
        User user = this.userRepo.findById(id).
                orElseThrow(()-> new ResourceNotFoundException("User","id",id));
        return this.userToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = this.userRepo.findAll();
        List<UserDto> userDtos =users
                .stream().map(user->this.userToDto(user))
                .collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public boolean deleteUser(String id) {
       try{
           User user = this.userRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("User","Id",id));
           this.userRepo.delete(user);
           return true;
       }
       catch (Exception e){
           e.printStackTrace();
       }
       return false;
    }

    @Override
    public UserDto registerNewUser(UserDto userDto) {

        User user = this.modelMapper.map(userDto, User.class);

        // encoded the password
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));

        // roles
        Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();

        user.getRoles().add(role);

        User newUser = this.userRepo.save(user);

        return this.modelMapper.map(newUser, UserDto.class);
    }


    public User dtoTOUser(UserDto userDto){
        User user = modelMapper.map(userDto,User.class);

//        user.setId(userDto.getId());
//        user.setName(userDto.getName());
//        user.setEmail(userDto.getEmail());
//        user.setPassword(userDto.getPassword());
//        user.setAbout(userDto.getAbout());

        return user;
    }

    public UserDto userToDto(User user){
        UserDto userDto = modelMapper.map(user,UserDto.class);
//        userDto.setId(user.getId());
//        userDto.setName(user.getName());
//        userDto.setEmail(user.getEmail());
//        userDto.setPassword(user.getPassword());
//        userDto.setAbout(user.getAbout());

        return userDto;
    }
}
