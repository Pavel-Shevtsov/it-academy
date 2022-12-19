package org.exemple.dao.impl;

import org.example.config.AppContext;
import org.example.dao.inter.UserDAO;
import org.example.model.User;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppContext.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserDAOTest {
    @Autowired
    UserDAO userDAO;

    User testUser = new User();
    public static final String TEST_USER_NAME = "TestUser";
    public static final String TEST_NEW_USER_NAME ="TestNewName";
    public static final String TEST_USER_PASSWORD = "TestPass";
    public static final String TEST_USER_EMAIL = "test@mail.com";
    public static final String TEST_USER_ROLE = "User";
    private static int id;


    @BeforeAll
    public void setUpUserTest(){
        testUser.setUserName(TEST_USER_NAME);
        testUser.setPassword(TEST_USER_PASSWORD);
        testUser.setEmail(TEST_USER_EMAIL);
        testUser.setRole(TEST_USER_ROLE);
        userDAO.add(testUser);
        id = userDAO.getUserByUserName(TEST_USER_NAME).getId();
    }

    @Test
    public void addUserTest(){
        Assertions.assertEquals(userDAO.getUserByUserName(TEST_USER_NAME).getUserName(),TEST_USER_NAME);
    }

    @Test
    public void getByIDTest(){
        Assertions.assertEquals(userDAO.getById(id).getUserName(),"TestUser");
    }
    @Test
    public void updateTopicTest(){
        User userByUserName = userDAO.getUserByUserName(TEST_USER_NAME);
        userByUserName.setUserName(TEST_NEW_USER_NAME);
        userDAO.update(userByUserName);
        Assertions.assertNotNull(userDAO.getUserByUserName(TEST_NEW_USER_NAME));
    }

    @AfterAll
    public void deleteUserTest() {
        userDAO.delete(id);
        Assertions.assertNull(userDAO.getUserByUserName(TEST_NEW_USER_NAME));
    }

    @Test
    public void getAllUserTest(){
        Assertions.assertNotNull(userDAO.allUsers());
    }

    @Test
    public void getUserWithTopicTest(){
        User user = userDAO.getUserByIdWithTopic(id);
        Assertions.assertNotNull(user.getTopics());
    }

    @Test
    public void getUserByPasswordTest(){
        Assertions.assertEquals(userDAO.getUserByUserPassword(TEST_USER_PASSWORD).getPassword(),TEST_USER_PASSWORD);
    }

    @Test
    public void getUserByEmailTest(){
        Assertions.assertEquals( userDAO.getUserByEmail(TEST_USER_EMAIL).getEmail(),TEST_USER_EMAIL);
    }

 }


