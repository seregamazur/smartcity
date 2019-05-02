package com.smartcity.dao;

import com.smartcity.domain.Role;
import com.smartcity.exceptions.DbOperationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RoleDaoImplTest {

    private RoleDaoImpl roleDao;
    private Role role;
    DriverManagerDataSource dataSource;


    @BeforeEach
    void setUp() {

        //Setting up DataBase
        dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://172.17.0.2:3306/smartcity");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        roleDao = new RoleDaoImpl(dataSource);

        //creating test role
        role = new Role();

        role.setId((long) Integer.MAX_VALUE);
        role.setName("Admin");

    }

    @AfterEach
    void tearDown() {

        //deleting all data from DB
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.update("DELETE from Roles");
        jdbcTemplate.update("ALTER table Roles AUTO_INCREMENT = 1");
    }


    @Test
    void whenCreatingRoleReturnsRole() {
        assertEquals(role, roleDao.create(role).get());
    }

    @Test
    void whenCreatingRoleGetRoleReturnsCorrectRole() {

        roleDao.create(role);

        Role testRole = roleDao.get(Integer.MAX_VALUE).get();


        assertThat(role).isEqualToIgnoringGivenFields(testRole, "created_date", "updated_date");
        role.toString();
        testRole.toString();
    }

    @Test
    void whenDeleteExistingRole() {
        roleDao.create(role);

        assertTrue(roleDao.delete(Integer.MAX_VALUE));
    }

    @Test
    void whenDeleteUnexistingRole() {
        assertFalse(roleDao.delete(Integer.MAX_VALUE));
    }

    @Test
    void whenDeleteRoleThrowsException() {

        //create role in db
        roleDao.create(role);

        //delete role from db
        roleDao.delete(Integer.MAX_VALUE);

        //try get deleted role in db
        assertThrows(DbOperationException.class, () -> roleDao.get(Integer.MAX_VALUE).get());
    }

    @Test
    void whenUpdateRoleGetUpdatedRole() {

        //create role in db
        roleDao.create(role);

        //created updated role
        Role updateRole = new Role();
        updateRole.setId((long) Integer.MAX_VALUE);
        updateRole.setName("Supervisor");


        roleDao.update(updateRole);

        //get updated role from db
        Role resultRole = roleDao.get(Integer.MAX_VALUE).get();

        assertThat(updateRole).isEqualToIgnoringGivenFields(resultRole, "created_date", "updated_date");

    }
}