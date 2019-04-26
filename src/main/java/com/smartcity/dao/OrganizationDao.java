package com.smartcity.dao;

import com.smartcity.domain.Organization;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationDao {

    Organization save(Organization org);

    Organization findById(Integer id);

    boolean deleteById(Integer id);
}
