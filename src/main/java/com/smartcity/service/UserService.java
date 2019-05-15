package com.smartcity.service;

import org.springframework.stereotype.Service;

import com.smartcity.dto.UserDto;

@Service
public interface UserService {

    UserDto create(UserDto user);
}
