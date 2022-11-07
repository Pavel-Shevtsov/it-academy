package org.example.model;

import java.util.Objects;

public class User {

    private int id;
    private String userName;
    private String password;
    private String email;
    private String role;


    public User() {
    }

    public User(int id) {
        this.id = id;
    }

    public User(int id, String name, String password, String email, String role) {
        this.id = id;
        this.userName = name;
        this.password = password;
        this.email = email;
        this.role = role;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String name) {
        this.userName = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return id + "," + userName + "," + password  + "," + email + "," + role  ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, email, role);
    }
}
