package org.example.repository;

import org.example.model.Topic;
import org.springframework.stereotype.Service;

public interface TopicJpaRepository extends BaseRepository<Topic, Integer> {
    Topic findByName(String userName);
    Topic findById(int id);
}
