package com.smartcity.service;

import com.smartcity.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public interface UserService  {

    UserDto create(UserDto user);

    UserDto get(Long id);

    UserDto findByEmail(String email);

    UserDto update(UserDto user);

    boolean delete(Long id);

    boolean updatePassword(Long userId, String newPassword);
}
