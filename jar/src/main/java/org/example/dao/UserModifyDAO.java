package org.example.dao;
import org.example.model.User;
import java.sql.SQLException;
import java.util.List;

public interface UserModifyDAO {

    void addUser(User user) throws SQLException;
    void deleteUser(int id) throws SQLException;
    void updateUser(User oldUser, User newUser) throws SQLException;
    List <User> allUsers() throws SQLException;
    User checkUserById(int id) throws SQLException;


}
