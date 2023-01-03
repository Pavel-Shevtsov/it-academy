package org.web.controllers.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller(value = "/user")
public class UserController {
    @GetMapping(name = "/add")
    public void addUser(){

    }


}
