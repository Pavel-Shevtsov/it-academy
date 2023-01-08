package org.web.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.example.dao.inter.TopicDAO;
import org.example.dao.inter.UserDAO;
import org.example.model.Topic;
import org.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping(value = {"/welcome"})
public class WelcomeController {

    @Autowired
    TopicDAO topicDAO;
    @Autowired
    UserDAO userDAO;

    @GetMapping
    public ModelAndView welcomePage(HttpServletRequest request){
        ModelAndView modelAndView;
        HttpSession session = request.getSession(false);
        String role = (String) session.getAttribute("role");
        if (role.equals("Admin")){
            List<Topic> allTopicsList = topicDAO.allTopic();
            modelAndView = new ModelAndView("welcome").addObject("allTopics", allTopicsList);
        }else{
            User user = userDAO.getUserByUserName((String) session.getAttribute("name"));
            User userTopicsList = userDAO.getUserByIdWithTopic(user.getId());
            List<Topic> topics = userTopicsList.getTopics();
            modelAndView = new ModelAndView("welcome")
                    .addObject("userTopics", topics);
        }
        return modelAndView;
    }
}
