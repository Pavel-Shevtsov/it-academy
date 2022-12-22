package org.example.validation;

import org.example.dao.impl.UserDAOImpl;
import org.example.model.User;
import java.util.Optional;

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

  public boolean isHaveUserWithUserEmail(String email){
      Optional<User> user = Optional.ofNullable(userDAO.getUserByEmail(email));
      return  user.isPresent();
   }
}
