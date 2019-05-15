package com.smartcity.dao;

import com.smartcity.domain.Role;
import com.smartcity.exceptions.DbOperationException;
import com.smartcity.exceptions.NotFoundException;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class RoleDaoImplTest extends BaseTest {

    private static RoleDaoImpl roleDao;
    private Role role = new Role(Long.MAX_VALUE, "User", LocalDateTime.now(), LocalDateTime.now());

    @BeforeAll
    public static void start() {
        setup();
        roleDao = new RoleDaoImpl(dataSource);
    }

    @Test
    public void createRole() {

        assertEquals(role, roleDao.create(role));
    }

    @Test
    public void createRole_NameIsNull() {
        role.setName(null);
        assertThrows(DbOperationException.class, () -> roleDao.create(role));
    }

    @Test
    public void getRole() {
        assertThat(role).isEqualToIgnoringGivenFields(roleDao.get(role.getId()),
                "createdDate", "updatedDate");
    }

    @Test
    public void getRole_unexistingRole() {
        role.setId(Long.MAX_VALUE - 2);
        assertThrows(NotFoundException.class, () -> roleDao.get(role.getId()));
    }

    @Test
    public void deleteRole() {
        assertTrue(roleDao.delete(role.getId()));
    }

    @Test
    public void deleteRole_unexistingRole() {
        role.setId(Long.MAX_VALUE - 2);
        assertThrows(NotFoundException.class, () -> roleDao.delete(role.getId()));
    }

    @Test
    public void updateRole() {
        role.setName("Supervisor");
        assertThat(role).isEqualToIgnoringGivenFields(roleDao.update(role), "updatedDate");
    }

    @Test
    public void updateRole_unexistingRole() {

        Role updatedRole = new Role(Long.MAX_VALUE - 2, "Supervisor", LocalDateTime.now(), LocalDateTime.now());
        assertThrows(NotFoundException.class, () -> roleDao.update(updatedRole));
    }

    @Test
    public void updateRole_NameIsNull() {

        role.setName(null);
        assertThrows(DbOperationException.class, () -> roleDao.update(role));
    }

    @AfterAll
    public static void cleanUp() {
        tearDown();
    }

    @BeforeEach
    public void createTestRole() {
        roleDao.create(role);
    }

    @AfterEach
    public void cleanRoles() {
        clearTables("Roles");
    }


}