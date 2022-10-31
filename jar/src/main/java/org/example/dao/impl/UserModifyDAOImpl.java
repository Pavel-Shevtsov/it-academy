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

            preparedStatement.setString(2,user.getUserName());
            preparedStatement.setString(3,user.getPassword());
            preparedStatement.setString(4,user.getRole());
            preparedStatement.setString(5,user.getEmail());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }

    }

    @Override
    public void deleteUser(int id) throws SQLException {

        PreparedStatement preparedStatement;

        try (Connection connection = dataBaseConnection.getConnection()) {
            preparedStatement = connection.prepareStatement(SQL_DELETE_USER);
            preparedStatement.setInt(1,user.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }

    }


    @Override
    public User updateUser(int id) throws IOException {

        ArrayList<User> updateUser = allUsers();
        User changeableUser = null;
        int indexUser = 0;

        for (int i = 0; i < updateUser.size(); i++) {
            int idListUser = updateUser.get(i).getId();

            if (idListUser == id){
                changeableUser = updateUser.get(i);
                indexUser = i;
                break;
            }
        }

        if (changeableUser!=null){
           
            updateUser.remove(indexUser);

            try (PrintWriter print = connection.getWriter(fileName)){

                for (int i = 0;i < updateUser.size();i++){
                    print.write(updateUser.get(i) + "\n" );
                    print.flush();
                }

            return changeableUser;
        }


        }

        return null;
    }

    @Override
    public ArrayList<User> allUsers() throws SQLException {

        ArrayList<User> users = new ArrayList<>();

        PreparedStatement preparedStatement;

        try (Connection connection = dataBaseConnection.getConnection()) {

            preparedStatement = connection.prepareStatement(SQL_ALL_USERS);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                user.setId(resultSet.getInt("id"));
                user.setUserName(resultSet.getString("userName"));
                user.setPassword(resultSet.getString("password"));
                user.setRole(resultSet.getString("role"));
                user.setEmail(resultSet.getString("email"));
                users.add(user);
            }

        }
        return users;
    }


    @Override
    public User checkUserRole(String userName) {
        try (BufferedReader reader = connection.getReader(fileName)) {

            boolean contains;
            String resultSearch;
            User user = new User();

            while ((resultSearch = reader.readLine()) != null) {
                String[] result = resultSearch.split(",");
                user.setId(Integer.parseInt(result[0]));
                user.setUserName(result[1]);
                user.setPassword(result[2]);
                user.setEmail(result[3]);
                user.setRole(result[4]);

                contains = user.getUserName().equals(userName);
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
