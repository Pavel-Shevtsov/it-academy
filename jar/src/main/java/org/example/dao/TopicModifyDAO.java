package org.example.dao;

import org.example.model.Topic;
import org.example.model.User;


import java.util.List;

public interface TopicModifyDAO {
    void addTopic(Topic topic);
    void deleteTopic(int id);
    void updateTopic(Topic newTopic);
    List<Topic> allTopic();
    Topic getTopicById(int id);
    Topic getTopicByTopicName(String topicName);

}
