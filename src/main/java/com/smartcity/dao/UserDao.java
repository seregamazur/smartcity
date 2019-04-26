package com.smartcity.dao;

import com.smartcity.domain.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {

    User save(User user);

    User findById(Integer id);

    boolean deleteById(Integer id);

}
