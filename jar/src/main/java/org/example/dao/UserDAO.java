package org.example.dao;

import org.example.model.User;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

public interface UserDAO {

    User getUserByUserName(String userName) throws FileNotFoundException, IOException, SQLException;
    User getUserByEmail(String email) throws IOException, SQLException;
    User getUserByUserPassword(String password) throws SQLException;
}
