package com.smartcity.service;

import com.smartcity.dao.UserDao;
import com.smartcity.domain.User;
import com.smartcity.dto.UserDto;
import com.smartcity.mapperDto.UserDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private UserDtoMapper userDtoMapper;
    private UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao, UserDtoMapper userDtoMapper) {
        this.userDao = userDao;
        this.userDtoMapper = userDtoMapper;
    }

    @Override
    public UserDto create(UserDto userDto) {
        User user = userDtoMapper.convertUserDtoIntoUser(userDto);
        return userDtoMapper.convertUserIntoUserDto(userDao.create(user));
    }

    @Override
    public UserDto get(Long id) {
        return userDtoMapper.convertUserIntoUserDto(userDao.get(id));
    }

    @Override
    public UserDto findByEmail(String email) {
        return userDtoMapper.convertUserIntoUserDto(userDao.findByEmail(email));
    }

    @Override
    public UserDto update(UserDto userDto) {
        User user = userDtoMapper.convertUserDtoIntoUser(userDto);
        return userDtoMapper.convertUserIntoUserDto(userDao.update(user));
    }

    @Override
    public boolean delete(Long id) {
        return userDao.delete(id);
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDao.findByEmail(username);
    }

    @Override
    public boolean updatePassword(Long userId, String newPassword) {
        return userDao.updatePassword(userId, newPassword);
    }
}