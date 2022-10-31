package org.example.dao;

import org.example.exception.UserModifyException;
import org.example.model.User;

import java.io.IOException;

import java.sql.SQLException;
import java.util.List;

public interface UserModifyDAO {

    void addUser(User user) throws IOException, SQLException;
    void deleteUser(int id) throws UserModifyException, SQLException;
    User updateUser(int id) throws IOException;
    List <User> allUsers() throws SQLException;
    int checkUserId();
    User checkUserRole(String userName);
    User checkUserById(int id);


}
