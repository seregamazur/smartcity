package com.smartcity.dao;

import com.smartcity.domain.Organization;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationDao {

    Organization create(Organization org);

    Organization get(long id);

    Organization update(Organization org);

    boolean delete(long id);
}
