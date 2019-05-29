package com.smartcity.dao;

import com.smartcity.domain.Organization;
import com.smartcity.domain.User;

import java.util.List;

public interface OrganizationDao {

    Organization create(Organization organization);

    Organization get(Long id);

    Organization update(Organization organization);

    boolean delete(Long id);

    List<Organization> getAll();

    boolean addUserToOrganization(Organization organization, User user);
}
