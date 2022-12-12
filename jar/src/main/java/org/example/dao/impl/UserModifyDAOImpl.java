package org.example.dao.impl;
import jakarta.persistence.TypedQuery;
import org.example.dao.AbstractJPADAO;
import org.example.dao.UserModifyDAO;
import org.example.model.User;
import java.util.List;

public class UserModifyDAOImpl extends AbstractJPADAO implements UserModifyDAO  {

    @Override
    public void addUser(User user) {
        init();
        em.persist(user);
        close();
    }

    @Override
    public void deleteUser(int id) {
        init();
        em.remove(em.find(User.class,id));
        close();
    }

    @Override
    public void updateUser( User newUser) {
        init();
        em.merge(newUser);
        close();
    }

    @Override
    public List<User> allUsers() {
        init();
        TypedQuery<User> namedQuery = em.createNamedQuery("User.getAll",User.class);
        List<User>users = namedQuery.getResultList();
        close();
        return users;
    }

    @Override
    public User getUserById(int id) {
        init();
        User userById = em.find(User.class,id);
        close();
        return userById;

    }
}
