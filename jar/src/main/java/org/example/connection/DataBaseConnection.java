package org.example.connection;

import org.example.constant.Constant;

import java.sql.*;

import javax.sql.*;

public class DataBaseConnection implements Constant {
    public Connection getConnection() throws SQLException {
        String url = URL_DB_MYSQL;
        String user = USER_BD_MYSQL;
        String password = PASSWORD_BD_MYSQL;
        Driver driver = new com.mysql.cj.jdbc.Driver();
        DriverManager.registerDriver(driver);

        return DriverManager.getConnection(url,user,password);

    }

}
