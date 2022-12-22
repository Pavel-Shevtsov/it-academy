package org.exemple.dao.impl;

import org.example.config.AppContext;
import org.example.dao.inter.TopicDAO;
import org.example.model.Topic;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppContext.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TopicDAOTest {

    public static final String TEST_TOPIC_NAME = "TopicTest";
    public static final String TEST_TOPIC_NEW_NAME = "TopicNewTest";
    private static int id;

    @Autowired
    TopicDAO topicDAO;

    @BeforeAll
    public void setUpTopicTest(){
        Topic topic = new Topic();
        topic.setName(TEST_TOPIC_NAME);
        topicDAO.add(topic);
        id = topicDAO.getTopicByTopicName(TEST_TOPIC_NAME).getId();
    }
    @Test
    public void addTopicTest(){
        Assertions.assertEquals(topicDAO.getTopicByTopicName(TEST_TOPIC_NAME).getName(),TEST_TOPIC_NAME);
    }

    @Test
    public void getAllTopicTest(){
        Assertions.assertNotNull(topicDAO.allTopic());
    }
    @Test
    public  void getTopicByTopicNameTest(){
        Assertions.assertEquals(topicDAO.getTopicByTopicName(TEST_TOPIC_NAME).getName(),TEST_TOPIC_NAME);
    }

    @Test
    public void updateTopicTest(){
        Topic topicByTopicName = topicDAO.getTopicByTopicName(TEST_TOPIC_NAME);
        topicByTopicName.setName(TEST_TOPIC_NEW_NAME);
        topicDAO.update(topicByTopicName);
        Assertions.assertNotNull(topicDAO.getTopicByTopicName(TEST_TOPIC_NEW_NAME));
    }

    @Test
    public void getByIdTest(){
        Assertions.assertNotNull(topicDAO.getById(id));
    }

    @AfterAll
    public  void deleteTest(){
        topicDAO.delete(id);
        Assertions.assertNull(topicDAO.getTopicByTopicName(TEST_TOPIC_NEW_NAME));
    }
}
