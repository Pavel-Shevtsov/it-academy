package org.exemple.dao.impl;

import org.example.config.TestAppContext;
import org.example.model.Topic;
import org.example.model.User;
import org.example.repository.UserJpaRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestAppContext.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserDAOTest {
    @Autowired
    UserJpaRepository userJpaRepository;

    User testUser = new User();
    public static final String TEST_USER_NAME = "TestUser";
    public static final String TEST_NEW_USER_NAME ="TestNewName";
    public static final String TEST_USER_PASSWORD = "TestPass";
    public static final String TEST_USER_EMAIL = "test@mail.com";
    public static final String TEST_USER_ROLE = "User";
    private static Integer id;


    @BeforeAll
    public void setUpUserTest(){
        List<Topic> topics = new ArrayList<>();
        Topic topic = new Topic();
        topic.setName("TestTopic");
        topics.add(topic);
        testUser.setUserName(TEST_USER_NAME);
        testUser.setPassword(TEST_USER_PASSWORD);
        testUser.setEmail(TEST_USER_EMAIL);
        testUser.setRole(TEST_USER_ROLE);
        testUser.setTopics(topics);

        userJpaRepository.save(testUser);
        id = userJpaRepository.findByUserName(TEST_USER_NAME).getId();
    }

    @Test
    public void addUserTest(){
        Assertions.assertEquals(userJpaRepository.findByUserName(TEST_USER_NAME).getUserName(),TEST_USER_NAME);
    }

    /*@Test
    public void getByIDTest(){
        Assertions.assertEquals(userJpaRepository.findById(id),"TestUser");
    }*/
    @Test
    public void updateTopicTest(){
        User userByUserName = userJpaRepository.findByUserName(TEST_USER_NAME);
        userByUserName.setUserName(TEST_NEW_USER_NAME);
        userJpaRepository.saveAndFlush(userByUserName);
        Assertions.assertNotNull(userJpaRepository.findByUserName(TEST_NEW_USER_NAME));
    }

    @AfterAll
    public void deleteUserTest() {
        userJpaRepository.delete(testUser);
        Assertions.assertNull(userJpaRepository.findByUserName(TEST_NEW_USER_NAME));
    }
    @Test

    public void getUserWhitTopic(){
       Assertions.assertEquals( userJpaRepository.getUserByIdWithTopic(id).getTopics().size(),1);
    }
    /*@Test
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
    }*/

 }


