package com.smartcity.dao;

import com.smartcity.domain.Organization;
import com.smartcity.exceptions.DbOperationException;
import com.smartcity.exceptions.NotFoundException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class OrganizationDaoImplTest extends BaseTest {

    private static OrganizationDaoImpl organizationDao;

    private Organization organization = new Organization(1L,
            "Комунальна служба",
            "Сахарова 13",
            LocalDateTime.now(), LocalDateTime.now());


    @BeforeAll
    public static void start() {
        setup();
        organizationDao = new OrganizationDaoImpl(dataSource);
    }

    @Test
    public void testCreateOrganization() {
        organizationDao.create(organization);
        assertThat(organizationDao.create(organization)).isEqualToIgnoringGivenFields(organization,
                "createdDate", "updatedDate");
    }

    @Test
    public void testCreateOrganization_missingCreatedDate() {
        organization.setCreatedDate(null);
        organizationDao.create(organization);
        assertNotEquals(organizationDao.get(organization.getId()).getCreatedDate(), null);
    }

    @Test
    public void testCreateOrganization_emptyOrganization(){
        Organization emptyOrganiation = new Organization();
        assertThrows(DbOperationException.class, () -> organizationDao.create(emptyOrganiation));
    }

    @Test
    public void testGetOrganization() {
        organizationDao.create(organization);
        assertThat(organizationDao.get(organization.getId())).isEqualToIgnoringGivenFields(organization,
                "createdDate", "updatedDate");
    }

    @Test
    public void testGetOrganization_invalidId() {
        assertThrows(NotFoundException.class, () -> organizationDao.get(Long.MAX_VALUE));
    }

    @Test
    public void testUpdateOrganization() {
        // Creating updateOrganization
        organizationDao.create(organization);
        Organization updatedOrganization = new Organization();
        updatedOrganization.setId(organization.getId());
        updatedOrganization.setName("Комунальна служба");
        updatedOrganization.setAddress("Вовчинецька 28А");
        updatedOrganization.setUpdatedDate(LocalDateTime.now());

        // Updating organization
        organizationDao.update(updatedOrganization);

        // Checking if both organization are equal
        assertThat(organizationDao.get(updatedOrganization.getId())).isEqualToIgnoringGivenFields(updatedOrganization,
                "createdDate", "updatedDate");
    }

    @Test
    public void testUpdateOrganization_invalidId() {
        // Creating updateOrganization
        Organization updatedOrganization = new Organization();
        updatedOrganization.setId(Long.MAX_VALUE);
        updatedOrganization.setName("Комунальна служба");
        updatedOrganization.setAddress("Вовчинецька 28А");
        updatedOrganization.setUpdatedDate(LocalDateTime.now());

        // Updating organization
        assertThrows(NotFoundException.class, () -> organizationDao.update(updatedOrganization));
    }

    @Test
    public void testDeleteOrganization() {
        organizationDao.create(organization);
        // Deleting organization from db
        assertTrue(organizationDao.delete(organization.getId()));
    }

    @Test
    public void testDeleteOrganization_invalidId() {
        assertThrows(NotFoundException.class, () -> organizationDao.delete(Long.MAX_VALUE));
    }

    @AfterEach
    public void cleanOrganization() {
        clearTables("Organizations");
    }

    @AfterAll
    public static void cleanUp() {
        tearDown();
    }
}