package org.example.dao.inter;

import org.example.model.Topic;
import java.util.List;

public interface TopicDAO extends DAO<Topic,Integer> {
    List<Topic> allTopic();
    Topic getTopicByTopicName(String topicName);
}
