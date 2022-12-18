package org.example.dao.impl;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.example.dao.inter.TopicDAO;
import org.example.model.Topic;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@Transactional
public class TopicDAOImpl extends BaseDAO<Topic,Integer> implements TopicDAO {

    public TopicDAOImpl() {
        super();
        clazz = Topic.class;
    }

    @Override
    public List<Topic> allTopic() {
        TypedQuery<Topic> namedQuery = em.createNamedQuery("Topic.getAll",Topic.class);
        List<Topic>topics = namedQuery.getResultList();
        return topics;
    }


    @Override
    public Topic getTopicByTopicName(String topicName) {
        Topic topic;
        TypedQuery<Topic> namedQuery = em.createNamedQuery("Topic.getTopicByTopicName",Topic.class)
                .setParameter("topicName",topicName);
        try {
            topic = namedQuery.getSingleResult();
        }catch (NoResultException e) {
            return null;
        }
        return topic;
    }


}
