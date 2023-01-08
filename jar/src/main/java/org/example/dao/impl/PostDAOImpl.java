package org.example.dao.impl;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.example.dao.inter.PostDAO;
import org.example.model.Post;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@Transactional
public class PostDAOImpl extends BaseDAO <Post,Integer> implements PostDAO {

    public PostDAOImpl() {
        super();
        clazz = Post.class;
    }


    @Override
    public List <Post> getPostByUserTopic(int userId, int topicId) {
        List<Post> posts;
        TypedQuery<Post> namedQuery = em.createNamedQuery("Post.getPostByUserTopic",Post.class)
                .setParameter("idTopic",topicId).setParameter("idUser", userId);
        try {
            posts = namedQuery.getResultList();
        }catch (NoResultException e){
            return null;
        }
        return posts;
    }
}
