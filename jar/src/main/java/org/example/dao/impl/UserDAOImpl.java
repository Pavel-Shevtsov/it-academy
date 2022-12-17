package org.example.dao.impl;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.example.dao.AbstractJPADAO;
import org.example.dao.UserDAO;
import org.example.model.User;

public class UserDAOImpl extends AbstractJPADAO implements UserDAO {

    @Override
    public User getUserByUserName(String userName) {
        User user;
        init();
        TypedQuery<User> namedQuery = em.createNamedQuery("User.getUserByUserName",User.class)
                .setParameter("userName",userName);
        try {
             user = namedQuery.getSingleResult();
        }catch (NoResultException e){
            return null;
        }finally {
            close();
        }
        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        User user;
        init();
        TypedQuery<User> namedQuery = em.createNamedQuery("User.getUserByEmail",User.class)
                .setParameter("email",email);
        try {
            user = namedQuery.getSingleResult();
        }catch (NoResultException e){
            return null;
        }finally {
            close();
        }
        return user;
    }

    @Override
    public User getUserByUserPassword(String password) {
        User user;
        init();
        TypedQuery<User> namedQuery = em.createNamedQuery("User.getUserByPassword",User.class)
                .setParameter("password",password);
        try {
            user = namedQuery.getSingleResult();
        }catch (NoResultException e){
            return null;
        }finally {
            close();
        }
        return user;
    }

    @Override
    public User getUserByIdWithTopic(int id) {
        User user;
        init();
        TypedQuery<User> namedQuery = em.createNamedQuery("User.getUserByIdWithTopic",User.class)
                .setParameter("userId",id);
        try {
            user = namedQuery.getSingleResult();
        }catch (NoResultException e){
            return null;
        }finally {
            close();
        }
        return user;
    }
}
