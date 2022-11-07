package org.example.dao.impl;

import org.example.connection.DataBaseConnection;
import org.example.connection.FileConnection;
import org.example.constant.Constant;
import org.example.dao.UserModifyDAO;
import org.example.model.User;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class UserModifyDAOImpl  implements UserModifyDAO, Constant  {
    FileConnection connection = new FileConnection();
    DataBaseConnection dataBaseConnection = new DataBaseConnection();
    User user;



    @Override
    public void addUser(User user) throws  SQLException {

        PreparedStatement preparedStatement;

        try (Connection connection = dataBaseConnection.getConnection()) {

            preparedStatement = connection.prepareStatement(SQL_ADD_USER);

            preparedStatement.setString(1,user.getUserName());
            preparedStatement.setString(2,user.getPassword());
            preparedStatement.setString(3,user.getRole());
            preparedStatement.setString(4,user.getEmail());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }

    }

    @Override
    public void deleteUser(int id) throws SQLException {

        PreparedStatement preparedStatement;

        try (Connection connection = dataBaseConnection.getConnection()) {
            preparedStatement = connection.prepareStatement(SQL_DELETE_USER);
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        preparedStatement.close();

    }


    @Override
    public void updateUser(User oldUser, User newUser) throws SQLException {

        PreparedStatement preparedStatement;

        try (Connection connection = dataBaseConnection.getConnection()) {
            preparedStatement = connection.prepareStatement(SQL_UPDATE_USER);
            if (newUser.getUserName()==null){
                newUser.setUserName(oldUser.getUserName());
            }
            if (newUser.getPassword()==null){
                newUser.setPassword(oldUser.getPassword());
            }
            if (newUser.getEmail()==null){
                newUser.setEmail(oldUser.getEmail());
            }

            preparedStatement.setString(1,newUser.getUserName());
            preparedStatement.setString(2,newUser.getPassword());
            preparedStatement.setString(3,newUser.getEmail());
            preparedStatement.setInt(4,oldUser.getId());
            preparedStatement.executeUpdate();

        }
        preparedStatement.close();

    }

    @Override
    public ArrayList<User> allUsers() throws SQLException {

        ArrayList<User> users = new ArrayList<>();

        PreparedStatement preparedStatement;

        try (Connection connection = dataBaseConnection.getConnection()) {

            preparedStatement = connection.prepareStatement(SQL_ALL_USERS);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
               int id = resultSet.getInt("id");
               String name = resultSet.getString("userName");
               String password = resultSet.getString("password");
               String role = resultSet.getString("role");
               String email = resultSet.getString("email");
               user = new User(id,name,password,email,role);
               users.add(user);
            }
            preparedStatement.close();
            resultSet.close();
        }
        return users;
    }

    @Override
    public User checkUserById(int id) throws SQLException {
        PreparedStatement preparedStatement;

        try (Connection connection = dataBaseConnection.getConnection()) {
            preparedStatement = connection.prepareStatement(SQL_CHECK_USER_BY_ID);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
              int userId = (resultSet.getInt("id"));
              String name =  (resultSet.getString("userName"));
              String password = (resultSet.getString("password"));
              String role = (resultSet.getString("role"));
              String email = (resultSet.getString("email"));

              user = new User(userId,name,password,email,role);

            }
            preparedStatement.close();
            resultSet.close();
        }
        return user;
    }

}
