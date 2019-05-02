package com.smartcity.dao;

import com.smartcity.domain.Role;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleDao {

    Optional<Role> create(Role role);

    Optional<Role> get(long id);

    Optional<Role> update(Role role);

    boolean delete(long id);
}
