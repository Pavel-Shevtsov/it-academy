package org.web.controllers;


import org.example.dao.inter.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller(value = "/login")
public class LoginController {

    @Autowired
    UserDAO userDAO;

    @GetMapping
    public ModelAndView login(){
        ModelAndView model = new ModelAndView("index");
        return model;

    }

    @PostMapping
    public ModelAndView submitLogin(){
        ModelAndView model = null;

        return model;
    }
}
