package org.web.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.example.dao.inter.UserDAO;
import org.example.model.User;
import org.example.validation.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.web.forms.UserForm;
import org.web.service.EmailService;

@Controller
@RequestMapping(value = "/addUser")
public class RegistrationController {

    @Autowired
    UserDAO userDAO;
    @Autowired
    EmailService emailService;

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
        User comparisonUser = userDAO.getUserByUserName(registrationUser.getUsername());
        if (comparisonUser == null && registrationUser.getPassword().equals(registrationUser.getRepeatPassword())) {

            boolean passwordValidate = userValidation.isPasswordValidate(registrationUser.getPassword());

            if(imageForm!=null){
                registrationUser.setImage(imageForm.getImage());
            }
            if(passwordValidate){
                registrationUser.setRole("User");
                user.setUserName(registrationUser.getUsername());
                user.setPassword(registrationUser.getPassword());
                user.setEmail(registrationUser.getEmail());
                user.setRole(registrationUser.getRole());
                user.setImage(registrationUser.getImage());
                userDAO.add(user);
                emailService.sendEmail("pahaschewzow@gmail.com", user.getEmail(),"Registration","\nUser : " + user.getUserName() + " " + user.getEmail() + " registered in the app" );

                modelAndView = new ModelAndView("login")
                        .addObject("errorPassword","<p style = \"color: red\"> The password must contain at least 8 characters, at least 1" +
                        "\n uppercase character and at least one digit.\n" +
                        "Enter again </p>");

            }else {
                request.setAttribute("errorPassword","<p style = \"color: red\"> The password must contain at least 8 characters, at least 1" +
                        "\n uppercase character and at least one digit.\n" +
                        "Enter again </p>");
                modelAndView = new ModelAndView("register")
                        .addObject("errorPassword","<p style = \"color: red\"> The password must contain at least 8 characters, at least 1" +
                        "\n uppercase character and at least one digit.\n" +
                        "Enter again </p>")
                        .addObject("registrationForm",new UserForm());
            }

        }else if(comparisonUser!=null&&comparisonUser.getUserName().equals(registrationUser.getUsername())){

            modelAndView = new ModelAndView("register")
                    .addObject("errorUserName","<p style =\"color: red\">A user with the same name already exists." +
                    " Please enter a different username")
                            .addObject("registrationForm",new UserForm());
        }else if(!registrationUser.getPassword().equals(registrationUser.getRepeatPassword())){
            request.setAttribute("errorRepeatPassword","<p style =\"color: red\"> Password strings are not identical." +
                    "Please enter them again</p>");
            modelAndView = new ModelAndView("register")
                    .addObject("errorRepeatPassword","<p style =\"color: red\"> Password strings are not identical." +
                            "Please enter them again</p>")
                    .addObject("registrationForm",new UserForm());
        }else {
            if (userDAO.getUserByEmail(registrationUser.getEmail())!=null){
                modelAndView = new ModelAndView("register")
                        .addObject("errorEmailRegister","<p style =\"color: red\"> User with this email is already register</p>")
                        .addObject("registrationForm",new UserForm());
            }
        }
        return modelAndView;
    }
}
