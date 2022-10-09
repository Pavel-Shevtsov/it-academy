package org.example.dao;

import org.example.model.User;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface UserDAO {

    User getUserByUserName(String userName) throws FileNotFoundException, IOException;
    User getUserByEmail(String email) throws IOException;
    User getUserByUserPassword(String password);
}
