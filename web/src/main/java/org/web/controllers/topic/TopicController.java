package org.web.controllers.topic;

import jakarta.jws.WebParam;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.dao.inter.PostDAO;
import org.example.dao.inter.TopicDAO;
import org.example.dao.inter.UserDAO;
import org.example.model.Post;
import org.example.model.Topic;
import org.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.web.forms.TopicForm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping(value = {"/topic"})
public class TopicController {

    @Autowired
    TopicDAO topicDAO;
    @Autowired
    UserDAO userDAO;
    @Autowired
    PostDAO postDAO;

    @GetMapping(value = {"create"})
    public ModelAndView preCreate(){
        return new ModelAndView("createTopic").addObject("topicForm", new TopicForm());
    }
    @PostMapping(value ={"/create"})
    public ModelAndView create(@ModelAttribute("topicForm") TopicForm createTopicForm, HttpServletResponse response, HttpServletRequest request) throws IOException {
        ModelAndView modelAndView;
        Topic byTopicName = topicDAO.getTopicByTopicName(createTopicForm.getTopicName());
        if (byTopicName == null) {
            Topic topic = new Topic();
            topic.setName(createTopicForm.getTopicName());
            topicDAO.add(topic);
            response.sendRedirect(request.getContextPath() + "/welcome");
        }
            modelAndView = new ModelAndView("welcome")
                    .addObject("createTopicError", "<p style =\"color: red\"> Sorry, there is a topic with this topic name</p>");
        return modelAndView;
    }
    @GetMapping(value = {"/add"})
    public ModelAndView preAddTopic(HttpServletRequest request){
        ModelAndView modelAndView;
        HttpSession session = request.getSession(false);
        int userId = (int) session.getAttribute("id");

        User userByIdWithTopic = userDAO.getUserByIdWithTopic(userId);
        List<Topic> allTopicsUser = userByIdWithTopic.getTopics();
        List<Topic> allTopics = topicDAO.allTopic();
        if (allTopicsUser.size()!=0) {
            for (Topic topic : allTopicsUser) {
                for (int k = 0; k < allTopics.size(); k++) {
                    boolean isFreTopic = topic.equals(allTopics.get(k));
                    if (isFreTopic) {
                        allTopics.remove(k);
                    }
                }
            }
        }
        List<Topic> freeTopics = new ArrayList<>(allTopics);

        return new ModelAndView("addTopic").addObject("freeTopics", freeTopics);
    }
    @PostMapping(value = {"/add"})
    public ModelAndView addTopic(@ModelAttribute("id") int topicId, HttpServletRequest request, HttpServletResponse response) throws IOException {

        ModelAndView modelAndView;
        HttpSession session = request.getSession(false);
        int userId = (int) session.getAttribute("id");
        User userByUserName = userDAO.getUserByIdWithTopic(userId);
        Topic topicById = topicDAO.getById(topicId);

        List<Topic> topics = userByUserName.getTopics();
        int sizeUpTo = topics.size();
        topics.add(topicById);
        userByUserName.setTopics(topics);
        int sizeAfter = topics.size();

        topicById.setUsers(Collections.singletonList(userByUserName));
        if (sizeUpTo!=sizeAfter){
            modelAndView = new ModelAndView("welcome")
                    .addObject("addTopic","<p style = \"color: blue\"> Topic add successful.</p>");
        }else {
            modelAndView = new ModelAndView("welcome")
                    .addObject("addTopic","<p style = \"color: red\"> Topic not added.</p>");
        }
        userDAO.update(userByUserName);
        return modelAndView;
    }

    @GetMapping(value = {"/delete"})
    public ModelAndView deleteTopic(@ModelAttribute("id") int topicId, HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        int userId = (int) session.getAttribute("id");
        if (topicId!=0) {
            Topic topicById = topicDAO.getById(topicId);
            User userById = userDAO.getUserByIdWithTopic(userId);
            List<Post> postByUserTopic = postDAO.getPostByUserTopic(userId, topicId);
            int indexTopicInList = -1;
            List<Topic> userTopics = userById.getTopics();
            for (Topic topic : userTopics) {
                if (topic.getName().equals(topicById.getName())) {
                    indexTopicInList = userTopics.indexOf(topic);
                    if (postByUserTopic.size()!=0) {
                        postByUserTopic.removeAll(postByUserTopic);
                    }
                }
            }
            userTopics.remove(indexTopicInList);
            userDAO.update(userById);
        }
        return new ModelAndView("welcome")
                .addObject("deleteTopic","<p style = \"color: blue\"> Topic deleted successfully.</p>");
    }

}
