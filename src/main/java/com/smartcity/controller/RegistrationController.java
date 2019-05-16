package com.smartcity.controller;

import com.smartcity.dto.UserDto;
import com.smartcity.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {

    @Autowired
    private UserServiceImpl userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/registration")
    public UserDto registerUser(@RequestBody UserDto user) {

        user.setActive(true);
        return userService.create(user);
    }

}
