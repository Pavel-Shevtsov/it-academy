/*
package org.web;

import org.example.exception.UserValidationException;
import org.example.dao.impl.UserDAOImpl;
import org.example.dao.impl.UserModifyDAOImpl;
import org.example.model.User;
import org.example.validation.UserValidation;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;


public class Main {

    static UserDAOImpl userDao = new UserDAOImpl();
    static UserValidation userValidation = new UserValidation();
    static UserModifyDAOImpl userModifyDAO = new UserModifyDAOImpl();
    static Scanner scanner = new Scanner(System.in);
    static User mainUser = new User();


    public static void main(String[] args) throws IOException, UserValidationException {

        welcome();

    }


    private static void welcome() throws UserValidationException, IOException {

       while (true) {

           System.out.println("---------Welcome--------");
           System.out.println("Sign up--------------[1]");
           System.out.println("Sign in--------------[2]");
           System.out.println("Exit-----------------[3]");
           int welcomePosition = scanner.nextInt();

           if (welcomePosition == 1) {
               signUp();
           }

           if (welcomePosition == 2) {
                signIn();
           }
           if (welcomePosition == 3) {
                break;
           }
           if (welcomePosition<1||welcomePosition>3){
               System.err.println("There is no such position in the menu \nPlease select again" );
               welcome();
           }
       }
    }




    private static void signUp() throws UserValidationException, IOException {

        User createUser = new User();

        System.out.println("---------Sign up--------");
        System.out.println("Back to welcome------[1]");

        System.out.println("Enter the user name");
        String userName = scanner.next();
        if (userName.equals("1")){
            welcome();
        }
            createUser.setUserName(userName);

            createUser.setId(userModifyDAO.checkUserId());

        System.out.println("Enter the password");
        String userPassword = scanner.next();
        if (userPassword.equals("1")){
            welcome();
        }
            createUser.setPassword(userPassword);

        System.out.println("Enter the eMail");
        String userEMail = scanner.next();
        if (userName.equals("1")){
            welcome();
        }
            createUser.setEmail(userEMail);

        System.out.println("Enter the user role. \nEither \"User\" or \"Admin\"");
        String userRole = scanner.next();
        if (userName.equals("1")){
            welcome();
        }
            createUser.setRole(userRole);

        boolean verification = userVerificationWhenAdding(createUser);

        if(verification){
            welcome();
        }


    }

    private static void signIn() throws UserValidationException, IOException {
        while(true) {
            System.out.println("---------Sign in--------");
            System.out.println("Back to welcome------[1]");

            System.out.println("Enter your userName ");
            String userName = scanner.next();

            System.out.println("Enter your password ");
            String userPassword = scanner.next();

            boolean userVerification = userVerificationAtLogin(userName, userPassword);
            mainUser = userModifyDAO.checkUserRole(userName);

            String userRole = mainUser.getRole().toUpperCase(Locale.ROOT);
            if (userVerification) {
                if (userRole.equals("USER")) {
                    userMenu();
                } else if (userRole.equals("ADMIN")) {
                    adminMenu();
                }
            }

        }
    }

    private static void adminMenu() throws IOException, UserValidationException {

        while (true) {

            System.out.println("----------Menu----------");
            System.out.println("View all users-------[1]");
            System.out.println("Change your password-[2]");
            System.out.println("Change user password-[3]");
            System.out.println("Delete user----------[4]");
            System.out.println("exit-----------------[5]");
            String adminMenuPosition = scanner.next();

            if (adminMenuPosition.equals("1")) {
                viewAllUsers();
            }
            if (adminMenuPosition.equals("2")) {
                updateMainUser(mainUser.getId());
            }
            if (adminMenuPosition.equals("3")) {
                updateUser();
            }
            if (adminMenuPosition.equals("4")) {
                removeUser();
            }
        }

    }

    private static void userMenu() throws IOException, UserValidationException {

        while (true) {

            System.out.println("----------Menu----------");
            System.out.println("Change password------[1]");
            System.out.println("exit-----------------[2]");
            String userMenuPosition = scanner.next();

            if (userMenuPosition.equals("1")) {
                updateMainUser(mainUser.getId());
            }
            if (userMenuPosition.equals("2")) {
                welcome();
            }

        }
    }


    private static boolean userVerificationWhenAdding(User user) throws IOException, UserValidationException {
        boolean passwordValidate = userValidation.isPasswordValidate(user.getPassword());

        boolean emailValidation = userValidation.isHaveUserWithUserEmail(user.getEmail());

        boolean userNameValidation = userValidation.isHaveUserWithUserName(user.getUserName());

        if (passwordValidate) {
            if (!emailValidation) {
                if (!userNameValidation) {
                    userModifyDAO.addUser(user);
                    return true;
                } else {
                    throw new UserValidationException("A user with this name is already registered");
                }
            } else {
                throw new UserValidationException("A user with such an email has already been registered");
            }
        } else {
            throw new UserValidationException("Unreliable password");
        }

    }

    private static boolean userVerificationAtLogin(String userName, String userPassword) throws IOException, UserValidationException {
        boolean userPasswordValidate = userValidation.isHaveUserWithUserPassword(userPassword);
        boolean userNameValidation = userValidation.isHaveUserWithUserName(userName);
        if (userPasswordValidate){
            return userNameValidation;
            }else {
            throw new UserValidationException("Invalid username or password");
        }
    }

    private static void viewAllUsers() throws IOException, UserValidationException {

        while(true) {

            System.out.println("--------All users-------");
            System.out.println("Back-----------------[1]");
            ArrayList<User> users = userModifyDAO.allUsers();
            for (User user : users) {
                if (!user.equals(mainUser)) {
                    System.out.println(user);
                }
            }

            String allUsersPosition = scanner.next();

            if (allUsersPosition.equals("1")) {
                if (mainUser.getRole().equalsIgnoreCase("ADMIN")) {
                    adminMenu();
                }
            }
        }
    }

    private static void removeUser() throws IOException, UserValidationException {

        ArrayList<User> users = userModifyDAO.allUsers();
        for (User user : users) {
            if (!user.equals(mainUser)){
                System.out.println(user);
            }
        }

        System.out.println("\nSelect the ID of the user you want to change");

        int id = scanner.nextInt();
        userModifyDAO.deleteUser(id);
    }


    private static void updateUser() throws IOException, UserValidationException {

        ArrayList<User> users = userModifyDAO.allUsers();
        for (User user : users) {
            if (!user.equals(mainUser)) {
                System.out.println(user);
            }
        }

        System.out.println("\nSelect the ID of the user you want to change");
        System.out.println("Back-----------------[0]");
        String updateUserId = scanner.next();

        if (updateUserId.equals("0")){
            adminMenu();
        }



       User changeableUser = userModifyDAO.updateUser(Integer.parseInt(updateUserId));

       if (changeableUser!=null){
           System.out.println("Enter a new password");
           String setNewPassword = scanner.next();
           changeableUser.setPassword(setNewPassword);
           userModifyDAO.addUser(changeableUser);
       }

    }

    private static void updateMainUser(int id) throws IOException, UserValidationException {

        User changeableUser = userModifyDAO.updateUser(id);

        if (changeableUser!=null){
            System.out.println("Enter a new password");
            String setNewPassword = scanner.next();
            changeableUser.setPassword(setNewPassword);
            userModifyDAO.addUser(changeableUser);
            if (mainUser.getRole().toUpperCase().equals("ADMIN")){
                adminMenu();
            }else if (mainUser.getRole().toUpperCase().equals("USER")){
                userMenu();
            }
        }

    }

    }
*/





