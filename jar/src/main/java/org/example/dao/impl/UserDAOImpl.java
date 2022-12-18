package org.example.dao.impl;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.example.dao.inter.UserDAO;
import org.example.model.User;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@Transactional
public class UserDAOImpl extends BaseDAO <User,Integer> implements UserDAO {

    public UserDAOImpl() {
        super();
        clazz = User.class;
    }

    @Override
    public List<User> allUsers() {
        List<User>users;
        TypedQuery<User> namedQuery = em.createNamedQuery("User.getAll",User.class);
        try {
            users = namedQuery.getResultList();
        }catch (NoResultException e){
            return null;
        }
        return users;
    }

    @Override
    public User getById(Integer id) {
        User userById;
        try {
            userById = em.find(User.class,id);
        }catch (NoResultException e){
            return null;
        }
        return userById;
    }

    @Override
    public User getUserByUserName(String userName) {
        User user;
        TypedQuery<User> namedQuery = em.createNamedQuery("User.getUserByUserName",User.class)
                .setParameter("userName",userName);
        try {
             user = namedQuery.getSingleResult();
        }catch (NoResultException e){
            return null;
        }
        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        User user;
        TypedQuery<User> namedQuery = em.createNamedQuery("User.getUserByEmail",User.class)
                .setParameter("email",email);
        try {
            user = namedQuery.getSingleResult();
        }catch (NoResultException e){
            return null;
        }
        return user;
    }

    @Override
    public User getUserByUserPassword(String password) {
        User user;

        TypedQuery<User> namedQuery = em.createNamedQuery("User.getUserByPassword",User.class)
                .setParameter("password",password);
        try {
            user = namedQuery.getSingleResult();
        }catch (NoResultException e){
            return null;
        }
        return user;
    }

    @Override
    public User getUserByIdWithTopic(int id) {
        User user;
        TypedQuery<User> namedQuery = em.createNamedQuery("User.getUserByIdWithTopic",User.class)
                .setParameter("userId",id);
        try {
            user = namedQuery.getSingleResult();
        }catch (NoResultException e){
            return null;
        }
        return user;
    }
}
