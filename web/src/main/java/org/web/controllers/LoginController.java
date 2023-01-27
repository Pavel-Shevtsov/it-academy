package org.web.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.model.User;
import org.example.repository.UserJpaRepository;
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
    UserJpaRepository userJpaRepository;

    @GetMapping(value = {"/login"})
    public ModelAndView preLogin(){
        return new ModelAndView("login").addObject("registrationForm",new UserForm());
    }

    @PostMapping(value = {"/loginS"})
    public void submitLogin(@ModelAttribute("registrationForm") UserForm loginUser, HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(true);
        User user = userJpaRepository.findByUserName(loginUser.getUsername());

            if (session != null) {
                session.setAttribute("user", user);
                session.setAttribute("userName", user.getUserName());
                session.setAttribute("userId", user.getId());
                session.setAttribute("visitCounter",0);
            }
            response.sendRedirect(request.getContextPath()+"/welcome");
    }
}
