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

import java.io.IOException;

@Controller
@RequestMapping(value = "/")
public class LoginController {

    @Autowired
    UserDAO userDAO;

    @GetMapping(value = {"/login"})
    public ModelAndView preLogin(){
        return new ModelAndView("login").addObject("registrationForm",new UserForm());
    }

    @PostMapping(value = {"/login"})
    public ModelAndView submitLogin(@ModelAttribute("registrationForm") UserForm loginUser, HttpServletRequest request, HttpServletResponse response) throws IOException {

        ModelAndView model = null;
        HttpSession session = request.getSession(false);

        User user = userDAO.getUserByUserName(loginUser.getUsername());
        if (user == null) {
            return new ModelAndView("login")
                    .addObject("loginError", "<p style =\"color: red\"> Sorry user with this username is not registered </p>");
        }
        if (loginUser.getPassword().equals(user.getPassword())) {
            if (session != null) {
                session.setAttribute("user", user);
                session.setAttribute("userName", user.getUserName());
                session.setAttribute("userId", user.getId());
                session.setAttribute("visitCounter",0);
            }

            response.sendRedirect(request.getContextPath()+"/welcome");
        }else{
          model = new ModelAndView("login")
                    .addObject("errorPasswordUsername", "<p style =\"color: red\"> Sorry username or password error</p>");
        }
         return model;
    }
}
