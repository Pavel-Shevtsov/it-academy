package org.exemple.dao.impl;

import org.example.config.AppContext;
import org.example.dao.inter.UserDAO;
import org.example.model.User;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.Assert.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppContext.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class UserDAOTest {
    @Autowired
    UserDAO userDAO;

    User testUser = new User();
    public static final String TEST_USER_NAME = "TESTUSer";
    public static final String TEST_USER_PASSWORD = "TestPassword007";
    public static final String TEST_USER_EMAIL = "pavlik@mail.com";

    @BeforeAll
    public void setUp(){
        testUser.setUserName(TEST_USER_NAME);
        testUser.setPassword(TEST_USER_PASSWORD);
        testUser.setEmail(TEST_USER_EMAIL);
    }
    @Ignore
    @Test
    public void addUser(){
         userDAO.add(testUser);
        User userByUserName = userDAO.getUserByUserName(TEST_USER_NAME);
        Assertions.assertEquals(TEST_USER_NAME,testUser.getUserName());
    }
    @Test
    public void deleteUserTest() {
        userDAO.delete(10);
        Optional<User> user = Optional.ofNullable(userDAO.getById(10));
        Assertions.assertFalse(user.isPresent());
    }
    @AfterAll
    public void destroy(){
        userDAO.delete(11);
    }

    @Test
    @Ignore
    public void getTest(){
        Assert.assertEquals(userDAO.getById(1).getUserName(),"user1");
    }


    @Test
    @Ignore
    public void getUserWithTopicTest(){
        User user = userDAO.getUserByIdWithTopic(4);
        assertNotNull(user.getTopics());
    }

    @Test
    @Ignore
    public void getAllUserTest(){
        assertNotNull(userDAO.allUsers());
    }

 }


