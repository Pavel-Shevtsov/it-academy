package org.web.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.example.model.Topic;
import org.example.model.User;
import org.example.repository.TopicJpaRepository;
import org.example.repository.UserJpaRepository;
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
    TopicJpaRepository topicJpaRepository;
    @Autowired
    UserJpaRepository userJpaRepository;
    @GetMapping
    public ModelAndView welcomePage(HttpServletRequest request){
        ModelAndView modelAndView;
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        int visitCounter = (int) session.getAttribute("visitCounter");
        if (user.getRole().equalsIgnoreCase("Admin")){
            List<Topic> allTopicsList = topicJpaRepository.findAll();
            modelAndView = new ModelAndView("welcome").addObject("allTopics", allTopicsList)
                    .addObject("user",user)
                    .addObject("visitCounter", visitCounter);
        }else{
            User userTopicsList = userJpaRepository.getUserByIdWithTopic(user.getId());
            List<Topic> topics = userTopicsList.getTopics();
            modelAndView = new ModelAndView("welcome")
                    .addObject("userTopics", topics)
                    .addObject("user",user)
                    .addObject("visitCounter", visitCounter);
        }
        if (visitCounter<1){
            session.setAttribute("visitCounter", visitCounter + 1);
        }
        return modelAndView;
    }
}
