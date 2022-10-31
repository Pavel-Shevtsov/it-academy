package org.example.constant;

public interface Constant  {

     String fileName = "D:/Учёба 2.0/it-academyTask2/web/users.csv";
     String URL_DB_MYSQL = "jdbc:mysql://localhost:3306/it-academy";
     String USER_BD_MYSQL = "root";
     String PASSWORD_BD_MYSQL = "root1";
     String DRIVER = "com.msql.cj.jdbc";


     String SQL_BY_NAME = "SELECT * FROM user WHERE userName = ?";
     String SQL_BY_EMAIL = "SELECT * FROM user WHERE email = ?";
     String SQL_BY_PASSWORD = "SELECT * FROM user WHERE password = ?";

     String SQL_ALL_USERS = "SELECT * FROM user";
     String SQL_ADD_USER = "INSERT INTO user (userName, password,role,email) values (?,?,?,?)";
     String SQL_DELETE_USER = "DELETE FROM user WHERE id=?";

}
