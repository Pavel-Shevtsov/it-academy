package org.web.controllers;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.dao.inter.UserDAO;
import org.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.web.forms.UserForm;
import org.web.service.EmailService;
import jakarta.activation.spi.MimeTypeRegistryProvider;

import java.io.IOException;

@Controller
@RequestMapping(value = "/")
public class LoginController {

    @Autowired
    UserDAO userDAO;
    @Autowired
    EmailService emailService;


    @GetMapping(value = {"/login"})
    public ModelAndView preLogin(){
        return new ModelAndView("login").addObject("registrationForm",new UserForm());
    }

    @PostMapping(value = {"/login"})
    public ModelAndView submitLogin(@ModelAttribute("registrationForm") UserForm loginUser, HttpServletRequest request, HttpServletResponse response) throws IOException {

        ModelAndView model ;
        HttpSession session = request.getSession(false);

        User user;
        user = userDAO.getUserByUserName(loginUser.getUsername());
        if (user == null) {
            model = new ModelAndView("index")
                    .addObject("loginError", "<p style =\"color: red\"> Sorry user with this username is not registered </p>");
        }
        if (loginUser.getPassword().equals(user.getPassword())) {
            if (session != null) {
                session.setAttribute("id", user.getId());
                session.setAttribute("name", user.getUserName());
                session.setAttribute("password", user.getPassword());
                session.setAttribute("role", user.getRole());
                session.setAttribute("email", user.getEmail());
            }
            emailService.sendEmail("pahaschewzow@gmail.com", user.getEmail(),"Login","User " + user + "Login in App" );
            response.sendRedirect(request.getContextPath()+"/welcome");


        }
            model = new ModelAndView("login")
                    .addObject("errorPasswordUsername", "<p style =\"color: red\"> Sorry username or password error</p>");

         return model;
    }
}
