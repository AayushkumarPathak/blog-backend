package com.amz.blog.services;

import com.amz.blog.entities.User;
import com.amz.blog.payloads.UserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto user);
    UserDto updateUser(UserDto user,String id);

    UserDto getUserById(String id);

    List<UserDto> getAllUsers();

    boolean deleteUser(String id);
    UserDto registerNewUser(UserDto userDto);
}
