package com.smartcity.dao;

import com.smartcity.domain.User;
import com.smartcity.exceptions.DbOperationException;
import com.smartcity.exceptions.NotFoundException;
import com.smartcity.utils.EncryptionUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class UserDaoImplTest extends BaseTest {

    @Autowired
    private UserDao userDao;

    private User user;

    @BeforeEach
    void beforeEachSetUp() {
        // Initializing test user
        user = new User();
        user.setEmail("example@gmail.com");
        user.setPassword("12345");
        user.setSurname("Johnson");
        user.setName("John");
        user.setPhoneNumber("0626552521415");

        // Creating user
        userDao.create(user);
    }

    @Test
    void testCreate_successFlow() {
        // should return reference to that same object
        assertEquals(user, userDao.create(user));
        assertThat(user.getId()).isNotNull();
        assertTrue(user.isActive());
    }

    @Test
    void testCreate_omittedNotNullFields() {
        // Creating empty user item
        User emptyUserItem = new User();
        assertThrows(DbOperationException.class, () -> userDao.create(emptyUserItem));
    }

    @Test
    void testGet_successFlow() {
        // Getting user
        User resultUser = userDao.get(user.getId());

        // Encrypting user password
        user.setPassword(EncryptionUtil.encryptPassword(user.getPassword()));

        assertThat(user).isEqualToIgnoringGivenFields(resultUser, "id", "createdDate", "updatedDate");
    }

    @Test
    void testGetUser_invalidId() {
        assertThrows(NotFoundException.class, () -> userDao.get(Long.MAX_VALUE));
    }

    @Test
    void testFindByEmail_successFlow() {
        User resultUser = userDao.findByEmail(user.getEmail());

        // Encrypting user password
        user.setPassword(EncryptionUtil.encryptPassword(user.getPassword()));

        assertThat(user).isEqualToIgnoringGivenFields(resultUser, "id", "createdDate", "updatedDate");
    }

    @Test
    void testFindByEmail_incorrectEmail() {
        assertThrows(NotFoundException.class, () -> userDao.findByEmail("not_existing_email@gmail.com"));
    }

    @Test
    void testUpdate_successFlow() {
        // Creating updated user
        User updatedUser = new User();
        updatedUser.setId(user.getId());
        updatedUser.setEmail("updated@gmail.com");
        updatedUser.setSurname("Smith");
        updatedUser.setName("Den");
        updatedUser.setPhoneNumber("0333333333");
        updatedUser.setCreatedDate(user.getCreatedDate());

        // Updating
        userDao.update(updatedUser);

        // Getting user from db
        User resultUser = userDao.get(user.getId());

        // Checking if both are equal
        assertThat(updatedUser).isEqualToIgnoringGivenFields(resultUser,
                "password", "createdDate", "updatedDate");
    }

    @Test
    void testUpdate_invalidId() {
        user.setId(Long.MAX_VALUE);
        assertThrows(NotFoundException.class, () -> userDao.update(user));
    }

    @Test
    void testUpdate_omittedNotNullFieldsExceptId() {
        // Creating empty user item
        User someUser = new User();

        // Setting id of an existing user
        someUser.setId(user.getId());

        assertThrows(DbOperationException.class, () -> userDao.update(someUser));
    }

    @Test
    void testDelete_successFlow() {
        // Deleting user from db
        assertTrue(userDao.delete(user.getId()));
    }

    @Test
    void testDelete_invalidId() {
        // Deleting not existing user from db
        assertThrows(NotFoundException.class, () -> userDao.delete(Long.MAX_VALUE));
    }

    @Test
    void testUpdatePassword_successFlow() {
        String newPassword = "new_pa$$word";

        // Updating user password
        assertTrue(userDao.updatePassword(user.getId(), newPassword));

        // Check if it was updated
        assertEquals(
                EncryptionUtil.encryptPassword("new_pa$$word"),
                userDao.get(user.getId()).getPassword());
    }

    @Test
    void testUpdatePassword_invalidId() {
        // Updating password for non-existent user from db
        assertThrows(NotFoundException.class, () -> userDao.updatePassword(Long.MAX_VALUE, "qwerty"));
    }

    @AfterEach
    void AfterEachTearDown() {
        clearTables("Users");
    }

}