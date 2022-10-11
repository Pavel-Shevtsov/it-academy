package org.example.dao.impl;

import org.example.connection.FileConnection;
import org.example.constant.Constant;
import org.example.dao.UserModifyDAO;
import org.example.model.User;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


public class UserModifyDAOImpl  implements UserModifyDAO, Constant  {
    FileConnection connection = new FileConnection();



    @Override
    public void addUser(User user) throws IOException {
        ArrayList<User> users = new ArrayList<>();

        try(BufferedReader reader = connection.getReader(fileName)) {

            String resultSearch;
            int count = 0;

            while ((resultSearch = reader.readLine()) != null) {

                User userSearch = new User();

                String[] resultSearchUserParam = resultSearch.split(",");
                userSearch.setId(Integer.parseInt(resultSearchUserParam[0]));
                userSearch.setUserName(resultSearchUserParam[1]);
                userSearch.setPassword(resultSearchUserParam[2]);
                userSearch.setEmail(resultSearchUserParam[3]);
                userSearch.setRole(resultSearchUserParam[4]);

                users.add(count++, userSearch);

            }

            users.add(count++,user);
        }

        try (PrintWriter print = connection.getWriter(fileName)){

            for (int i = 0;i < users.size();i++){
                print.write(users.get(i)+ "\n");
                print.flush();
            }
        }

    }

    @Override
    public void deleteUser(int id)  {

        User user = new User(id);

        ArrayList searchForAUsersById = allUsers();
        if (searchForAUsersById.size()!= 0){
            int usersPositionInTheList = searchForAUsersById.indexOf(user);
            searchForAUsersById.remove(usersPositionInTheList);
        }


        try(PrintWriter print = connection.getWriter(fileName)){
            for (int i = 0;i < searchForAUsersById.size();i++) {
                print.write(searchForAUsersById.get(i) + "\n");
                print.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
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
            }else {
                continue;
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
    public ArrayList<User> allUsers() {

        ArrayList<User> users = new ArrayList<>();

        try(BufferedReader reader = connection.getReader(fileName)) {

            String resultSearch;
            int count = 0;

            while ((resultSearch = reader.readLine()) != null) {

                User userSearch = new User();

                String[] resultSearchUserParam = resultSearch.split(",");
                userSearch.setId(Integer.parseInt(resultSearchUserParam[0]));
                userSearch.setUserName(resultSearchUserParam[1]);
                userSearch.setPassword(resultSearchUserParam[2]);
                userSearch.setEmail(resultSearchUserParam[3]);
                userSearch.setRole(resultSearchUserParam[4]);

                users.add(count++, userSearch);



            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  users;
}

    @Override
    public int checkUserId() {

        try (BufferedReader reader = connection.getReader(fileName)) {

            int counterFreeId = 1;
            String resultSearchId;

            List<Integer> existingUserId = new ArrayList<>();

            while ((resultSearchId = reader.readLine()) != null) {

                int count = 0;

                String[] userId = resultSearchId.split(",");
                existingUserId.add(count, Integer.valueOf(userId[0])); ///44
                count++;

            }

            for (int i = 0; i < existingUserId.size()+1; i++) {
                boolean freeId = existingUserId.contains(counterFreeId);
                if (freeId) {
                    counterFreeId++;
                } else {
                    return counterFreeId;
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1 ;
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
