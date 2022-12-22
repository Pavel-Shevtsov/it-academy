package org.example.dao.inter;
import org.example.model.User;

import java.util.List;

public interface UserDAO extends DAO<User,Integer> {
    List<User> allUsers();
    User getUserByUserName(String userName);
    User getUserByEmail(String email);
    User getUserByUserPassword(String password);
    User getUserByIdWithTopic(int id);
}
