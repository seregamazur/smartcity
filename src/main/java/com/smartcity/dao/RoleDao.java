package com.smartcity.dao;

import com.smartcity.domain.Role;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao {

    Role create(Role role);

    Role get(long id);

    Role update(Role role);

    boolean delete(long id);
}
