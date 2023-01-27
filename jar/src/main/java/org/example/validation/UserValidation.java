package org.example.validation;

import org.example.model.User;
import org.example.repository.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class  UserValidation {

    @Autowired
    UserJpaRepository userJpaRepository;

    public boolean isPasswordValidate(String password){
        String pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,20}$";
        return password.matches(pattern);
    }

   public boolean isHaveUserWithUserName(String userName){
       Optional<User> user;
       user = Optional.ofNullable(userJpaRepository.findByUserName(userName));
       return  user.isPresent();
   }
}
