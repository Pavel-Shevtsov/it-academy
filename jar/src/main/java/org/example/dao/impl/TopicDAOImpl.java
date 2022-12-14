package org.example.dao.impl;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.example.dao.AbstractJPADAO;
import org.example.dao.TopicModifyDAO;
import org.example.model.Topic;
import org.example.model.User;

import java.util.List;

public class TopicDAOImpl extends AbstractJPADAO implements TopicModifyDAO {
    @Override
    public void addTopic(Topic topic) {
        init();
        em.persist(topic);
        close();
    }

    @Override
    public void deleteTopic(int id) {
        init();
        em.remove(em.find(Topic.class,id));
        close();
    }

    @Override
    public void updateTopic(Topic newTopic) {
        init();
        em.merge(newTopic);
        close();

    }

    @Override
    public List<Topic> allTopic() {
        init();
        TypedQuery<Topic> namedQuery = em.createNamedQuery("Topic.getAll",Topic.class);
        List<Topic>topics = namedQuery.getResultList();
        close();
        return topics;
    }

    @Override
    public Topic getTopicById(int id) {
        Topic topic;
        init();
        TypedQuery<Topic> namedQuery = em.createNamedQuery("Topic.getTopicById",Topic.class)
                .setParameter("id",id);
        try {
            topic = namedQuery.getSingleResult();
        }catch (NoResultException e){
            return null;
        }finally {
            close();
        }
        return topic;
    }

    @Override
    public Topic getTopicByTopicName(String topicName) {
        Topic topic;
        init();
        TypedQuery<Topic> namedQuery = em.createNamedQuery("Topic.getTopicByTopicName",Topic.class)
                .setParameter("topicName",topicName);
        try {
            topic = namedQuery.getSingleResult();
        }catch (NoResultException e){
            return null;
        }finally {
            close();
        }
        return topic;
    }


}
