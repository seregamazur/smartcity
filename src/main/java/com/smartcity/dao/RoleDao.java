package com.smartcity.dao;

import com.smartcity.domain.Role;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao {

    Role save(Role role);

    Role findById(Integer id);

    boolean deleteById(Integer id);

}
