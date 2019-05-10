package com.smartcity.dao;

import com.smartcity.domain.Role;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleDao {

    Role create(Role role);

    Role get(Long id);

    Role update(Role role);

    boolean delete(Long id);
}
