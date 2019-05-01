package com.smartcity.dao;

import com.smartcity.domain.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {

    User create(User user);

    User get(long id);

    User update(User user);

    boolean delete(long id);

}
