package org.example.dao;
import org.example.model.User;

public interface UserDAO {
    User getUserByUserName(String userName);
    User getUserByEmail(String email);
    User getUserByUserPassword(String password);
    User getUserByIdWithTopic(int id);
}
