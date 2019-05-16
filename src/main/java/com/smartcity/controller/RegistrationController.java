package com.smartcity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.smartcity.dto.UserDto;
import com.smartcity.service.UserServiceImpl;

@Controller
public class RegistrationController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/registration")
    public String registration() {
        return "/registration";
    }

    @PostMapping("/registration")
    public String registerUser(@RequestBody UserDto user) {

        user.setActive(true);
        userService.create(user);
        return "redirect:/login";

    }

}
