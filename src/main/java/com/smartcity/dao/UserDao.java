package com.smartcity.dao;

import com.smartcity.domain.User;

public interface UserDao {

    User create(User user);

    User get(Long id);

    User findByEmail(String email);

    User update(User user);

    boolean delete(Long id);

}
