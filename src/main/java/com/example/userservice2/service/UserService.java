package com.example.userservice2.service;

import com.example.userservice2.dto.UserDto;
import com.example.userservice2.jpa.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService { //UserDetailsService는 스프링시큐리티
    UserDto createUser(UserDto userDto);
    UserDto getUserByUserId(String userId);
    Iterable<UserEntity> getUserByAll();
    UserDto getUserDetailsByEmail(String username);
}
