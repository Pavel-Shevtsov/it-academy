package org.web.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.example.model.User;
import org.example.repository.UserJpaRepository;
import org.example.validation.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.web.forms.UserForm;
//import org.web.service.EmailService;

@Controller
@RequestMapping(value = "/add")
public class RegistrationController {

   /* @Autowired
    EmailService emailService;*/

    @Autowired
    UserJpaRepository userJpaRepository;

    @GetMapping
    public ModelAndView preRegistration (){
        ModelAndView modelAndView = new ModelAndView("register")
                .addObject("registrationForm",new UserForm())
                .addObject("imageForm", new UserForm());
        return modelAndView;
    }

    @PostMapping
    public ModelAndView postRegistration(@ModelAttribute("registrationForm") UserForm registrationUser, HttpServletRequest request){
        User user = new User();
        ModelAndView modelAndView = null;
        UserValidation userValidation = new UserValidation();
        UserForm imageForm = (UserForm) request.getSession().getAttribute("imageForm");
        User comparisonUser = userJpaRepository.findByUserName(registrationUser.getUsername());

        if (comparisonUser==null){
            user.setUserName(registrationUser.getUsername());
            if (registrationUser.getPassword().equalsIgnoreCase(registrationUser.getRepeatPassword())){
                if (userValidation.isPasswordValidate(registrationUser.getPassword())){
                    user.setPassword(BCrypt.hashpw(registrationUser.getPassword(), BCrypt.gensalt(10)));
                    if (userJpaRepository.findByEmail(registrationUser.getEmail())==null){
                        user.setEmail(registrationUser.getEmail());
                        if (imageForm!=null){
                            user.setImage(imageForm.getImage());
                        }
                        user.setRole("User");
                        userJpaRepository.save(user);
                        //emailService.sendEmail("pahaschewzow@gmail.com", user.getEmail(),"Registration","Name - "  + user.getUserName() + "; ID - " + user.getId() + "; Email - " + user.getEmail() + " registered in the app" );

                        return  new ModelAndView("register")
                                .addObject("errorPassword","<p style = \"color: red\"> The password must contain at least 8 characters, at least 1" +
                                        "\n uppercase character and at least one digit.\n" +
                                        "Enter again </p>")
                                .addObject("registrationForm", registrationUser);
                    }else {
                        return new ModelAndView("register")
                                .addObject("errorEmailRegister", "<p style =\"color: red\"> User with this email is already register</p>")
                                .addObject("registrationForm", registrationUser);
                    }
                }else{
                    return new ModelAndView("register")
                            .addObject("errorPassword","<p style = \"color: red\"> The password must contain at least 8 characters, at least 1" +
                                    "\n uppercase character and at least one digit.\n" +
                                    "Enter again </p>")
                            .addObject( "registrationUser",registrationUser);
                }
            }else{
                return new ModelAndView("register")
                        .addObject("errorRepeatPassword","<p style =\"color: red\"> Password strings are not identical." +
                                "Please enter them again</p>")
                        .addObject("registrationForm",registrationUser);
            }
        }else{
            return new ModelAndView("register")
                    .addObject("errorUserName", "<p style =\"color: red\">A user with the same name already exists." +
                            " Please enter a different username")
                    .addObject("registrationForm", registrationUser);
        }
    }
}
