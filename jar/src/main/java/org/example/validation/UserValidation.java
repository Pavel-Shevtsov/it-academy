package org.example.validation;

import org.example.dao.impl.UserDAOImpl;
import org.example.model.User;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class  UserValidation {
    UserDAOImpl userDAO = new UserDAOImpl();

    public boolean isPasswordValidate(String password){
        String pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,20}$";
        return password.matches(pattern);
    }

   public boolean isHaveUserWithUserName(String userName){
       Optional<User> user;
       user = Optional.ofNullable(userDAO.getUserByUserName(userName));
       return  user.isPresent();
   }
}
