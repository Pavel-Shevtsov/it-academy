package org.example.dao.impl;

import org.example.constant.Constant;
import org.example.dao.UserDAO;
import org.example.connection.FileConnection;
import org.example.model.User;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class UserDAOImpl implements UserDAO, Constant {

    FileConnection connection = new FileConnection();
    User user = new User();


    @Override
    public User getUserByUserName(String userName) throws IOException {

        try (BufferedReader reader = connection.getReader(fileName)) {

            boolean contains;
            String resultSearch;


            while ((resultSearch = reader.readLine()) != null) {
                String[] result = resultSearch.split(",");
                user.setId(Integer.parseInt((result[0])));
                user.setUserName(result[1]);
                user.setPassword(result[2]);
                user.setEmail(result[3]);
                user.setRole(result[4]);

                contains = user.getUserName().equalsIgnoreCase(userName);

                if (contains) {
                    return user;
                }

            }
        }
        return null;
    }

    @Override
    public User getUserByEmail(String email) throws IOException {

        try (BufferedReader reader = connection.getReader(fileName)) {

            boolean contains;
            String resultSearch;

            while ((resultSearch = reader.readLine()) != null) {
                String[] result = resultSearch.split(",");
                user.setId(Integer.parseInt(result[0]));
                user.setUserName(result[1]);
                user.setPassword(result[2]);
                user.setEmail(result[3]);
                user.setRole(result[4]);

                contains = user.getEmail().equalsIgnoreCase(email);
                if (contains) {
                    return user;
                }

            }

        }
        return null;
    }




    @Override
    public User getUserByUserPassword(String password) {
        try (BufferedReader reader = connection.getReader(fileName)) {

            boolean contains;
            String resultSearch;

            while ((resultSearch = reader.readLine()) != null) {
                String[] result = resultSearch.split(",");
                user.setId(Integer.parseInt(result[0]));
                user.setUserName(result[1]);
                user.setPassword(result[2]);
                user.setEmail(result[3]);
                user.setRole(result[4]);

                contains = user.getPassword().equals(password);
                if (contains) {
                    return user;
                }

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
