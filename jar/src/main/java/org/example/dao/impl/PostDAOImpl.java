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

    }

    @Override
    public void updatePost(Post oldPost, Post newPost) {

        if (newPost.getName()==null){
            newPost.setName(oldPost.getName());
        }
        if (newPost.getUser()==null){
            newPost.setText(oldPost.getText());
        }
        if (newPost.getTopic()==null){
            newPost.setTopic(oldPost.getTopic());
        }
        init();
        em.merge(newPost);
        close();
    }

    @Override
    public List<Post> allPost() {
        init();
        TypedQuery<Post> namedQuery = em.createNamedQuery("Post.getAll",Post.class);
        List<Post>post = namedQuery.getResultList();
        close();
        return post;
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
