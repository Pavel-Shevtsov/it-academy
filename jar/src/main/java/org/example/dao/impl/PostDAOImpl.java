package org.example.dao.impl;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.example.dao.AbstractJPADAO;
import org.example.dao.PostModifyDAO;
import org.example.model.Post;

import java.util.List;



public class PostDAOImpl extends AbstractJPADAO implements PostModifyDAO {
    @Override
    public void addPost(Post post) {
        init();
        em.merge(post);
        close();
    }

    @Override
    public void deletePost(int id) {
        init();
        em.remove(em.find(Post.class,id));
        close();
    }

    @Override
    public void updatePost(Post newPost) {
        init();
        em.merge(newPost);
        close();
    }
    @Override
    public Post getPostById(int id) {
        Post post;
        init();
        TypedQuery<Post> namedQuery = em.createNamedQuery("Post.getTopicById",Post.class)
                .setParameter("id",id);
        try {
            post = namedQuery.getSingleResult();
        }catch (NoResultException e){
            return null;
        }finally {
            close();
        }
        return post;
    }

    @Override
    public List <Post> getByUserTopic(int userId, int topicId) {
        List<Post> posts;
        init();
        TypedQuery<Post> namedQuery = em.createNamedQuery("Post.getByUserTopic",Post.class)
                .setParameter("idTopic",topicId).setParameter("idUser", userId);
        try {
            posts = namedQuery.getResultList();
        }catch (NoResultException e){
            return null;
        }finally {
            close();
        }
        return posts;

    }
}
