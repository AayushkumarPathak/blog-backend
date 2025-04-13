package com.amz.blog.services;

import java.util.List;

import com.amz.blog.payloads.UserDto;

public interface UserService {
    UserDto createUser(UserDto user);
    UserDto updateUser(UserDto user,String id);

    UserDto getUserById(String id);

    List<UserDto> getAllUsers();

    boolean deleteUser(String id);
    UserDto registerNewUser(UserDto userDto);
}
