package com.example.crud.services;

import com.example.crud.dto.EditUserDto;
import com.example.crud.dto.UserDto;
import com.example.crud.entity.User;

import java.util.List;

public interface UserService {

    UserDto saveUser(UserDto userDto);
    User findUserByEmail(String email);
    UserDto findUserByUsername(String username);
    List<UserDto> getAllUsers();
    EditUserDto editUser(Long id, EditUserDto userDto);
    UserDto getUserById(Long id);
    EditUserDto getEditUserById(Long id);
    boolean deleteUserById(Long id);
}
