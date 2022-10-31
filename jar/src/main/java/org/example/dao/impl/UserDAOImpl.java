package org.example.dao.impl;

import org.example.connection.DataBaseConnection;
import org.example.connection.FileConnection;
import org.example.constant.Constant;
import org.example.dao.UserDAO;
import org.example.model.User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UserDAOImpl implements UserDAO, Constant {

    FileConnection connection = new FileConnection();
    DataBaseConnection dataBaseConnection = new DataBaseConnection();
    User user = new User();


    @Override
    public User getUserByUserName(String userName) throws IOException, SQLException {

        PreparedStatement preparedStatement;

        try (Connection connection = dataBaseConnection.getConnection()) {

            preparedStatement = connection.prepareStatement(SQL_BY_NAME);
            preparedStatement.setString(2,userName);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                user.setId(resultSet.getInt("id"));
                user.setUserName(resultSet.getString("userName"));
                user.setPassword(resultSet.getString("password"));
                user.setRole(resultSet.getString("role"));
                user.setEmail(resultSet.getString("email"));

            }
            preparedStatement.close();
            resultSet.close();
        }
        return user;
    }

    @Override
    public User getUserByEmail(String email) throws SQLException {
        PreparedStatement preparedStatement;

        try (Connection connection = dataBaseConnection.getConnection()) {

            preparedStatement = connection.prepareStatement(SQL_BY_EMAIL);
            preparedStatement.setString(5,email);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                user.setId(resultSet.getInt("id"));
                user.setUserName(resultSet.getString("userName"));
                user.setPassword(resultSet.getString("password"));
                user.setRole(resultSet.getString("role"));
                user.setEmail(resultSet.getString("email"));

            }
            preparedStatement.close();
            resultSet.close();
        }
        return user;
    }





    @Override
    public User getUserByUserPassword(String password) throws SQLException {
        PreparedStatement preparedStatement;

        try (Connection connection = dataBaseConnection.getConnection()) {

            preparedStatement = connection.prepareStatement(SQL_BY_PASSWORD);
            preparedStatement.setString(3,password);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                user.setId(resultSet.getInt("id"));
                user.setUserName(resultSet.getString("userName"));
                user.setPassword(resultSet.getString("password"));
                user.setRole(resultSet.getString("role"));
                user.setEmail(resultSet.getString("email"));

            }
            preparedStatement.close();
            resultSet.close();
        }
        return user;
    }


}
