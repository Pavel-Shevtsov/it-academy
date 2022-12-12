package org.example.dao;
import org.example.model.User;
import java.util.List;

public interface UserModifyDAO {
    void addUser(User user);
    void deleteUser(int id);
    void updateUser(User newUser);
    List <User> allUsers();
    User getUserById(int id);
}