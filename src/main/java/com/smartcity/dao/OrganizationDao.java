package com.smartcity.dao;

import com.smartcity.domain.Organization;

public interface OrganizationDao {

    Organization create(Organization organization);

    Organization get(Long id);

    Organization update(Organization organization);

    boolean delete(Long id);
}
