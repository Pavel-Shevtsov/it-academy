package org.example.dao;

import org.example.exception.UserModifyException;
import org.example.model.User;

import java.io.IOException;

import java.util.List;

public interface UserModifyDAO {

    void addUser(User user) throws IOException;
    void deleteUser(int id) throws UserModifyException;
    User updateUser(int id) throws IOException;
    List <User> allUsers();
    int checkUserId();
    User checkUserRole(String userName);
    User checkUserById(int id);


}
