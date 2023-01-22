package org.exemple.repository;

import org.example.config.TestAppContext;
import org.example.model.Topic;
import org.example.repository.TopicJpaRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestAppContext.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TopicJpaRepositoryTest {

    public static final String TEST_TOPIC_NAME = "TopicTest";
    public static final String TEST_TOPIC_NEW_NAME = "TopicNewTest";
    private static int id;

    @Autowired
    TopicJpaRepository topicJpaRepository;

    @BeforeAll
    public void setUpTopicTest(){
        Topic topic = new Topic();
        topic.setName(TEST_TOPIC_NAME);
        topicJpaRepository.save(topic);
        id = topicJpaRepository.findByName(topic.getName()).getId();
    }
    @Test
    public void addTopicTest(){
        Assertions.assertEquals(topicJpaRepository.findByName(TEST_TOPIC_NAME).getName(),TEST_TOPIC_NAME);
    }

    @Test
    public void getAllTopicTest(){
        Assertions.assertNotNull(topicJpaRepository.findAll());
    }
    @Test
    public  void getTopicByTopicNameTest(){
        Assertions.assertEquals(topicJpaRepository.findByName(TEST_TOPIC_NAME).getName(),TEST_TOPIC_NAME);
    }

    @Test
    public void updateTopicTest(){
        Topic topicByTopicName = topicJpaRepository.findByName(TEST_TOPIC_NAME);
        topicByTopicName.setName(TEST_TOPIC_NEW_NAME);
        topicJpaRepository.save(topicByTopicName);
        Assertions.assertNotNull(topicJpaRepository.findByName(TEST_TOPIC_NEW_NAME));
    }

    @Test
    public void getByIdTest(){
        Assertions.assertNotNull(topicJpaRepository.findById(id));
    }

    @AfterAll
    public  void deleteTest(){
        Topic byName = topicJpaRepository.findByName(TEST_TOPIC_NEW_NAME);
        topicJpaRepository.delete(byName);
        Assertions.assertNull(topicJpaRepository.findByName(TEST_TOPIC_NEW_NAME));
    }
}
