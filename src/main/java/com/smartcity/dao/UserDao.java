package com.smartcity.dao;

import com.smartcity.domain.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao {

    User create(User user);

    User get(Long id);

    User update(User user);

    boolean delete(Long id);

}
