package org.web.controllers.user;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.SneakyThrows;
import org.example.model.Post;
import org.example.model.Topic;
import org.example.model.User;

import org.example.repository.*;
import org.example.repository.UserJpaRepository;
import org.example.validation.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.web.forms.UserForm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping(value = "/user")
public class UserController {


    @Autowired
    UserJpaRepository userJpaRepository;

    @Autowired
    TopicJpaRepository topicJpaRepository;

    @Autowired
    PostJpaRepository postJpaRepository;

    @GetMapping(value = {"/update"})
    public ModelAndView preUpdate(@RequestParam("id") int userId,HttpServletRequest request) {
        UserForm updateUserForm = new UserForm(userJpaRepository.findById(userId)) ;
        request.getSession().setAttribute("updatedUserId",userId);
        return new ModelAndView("update").addObject("updateUserForm", updateUserForm);
    }

    @SneakyThrows
    @PostMapping(value = {"/update"})
    public ModelAndView postUpdate(@ModelAttribute("updateUserForm") UserForm updateUserForm, HttpServletRequest request, HttpServletResponse response) {

        UserValidation userValidation = new UserValidation();
        HttpSession session = request.getSession(false);
        ModelAndView modelAndView = null;
        User sessionUser = (User) session.getAttribute("user");

        User updatedUser = userJpaRepository.findById(updateUserForm.getId());
        UserForm imageForm = (UserForm) request.getSession().getAttribute("imageForm");

        if (!updateUserForm.getNewUsername().equals("")) {
            if (userJpaRepository.findByUserName(updateUserForm.getNewUsername())!= null) {
                return new ModelAndView("update")
                        .addObject("userNameAlreadyRegistered", "<p style = \"color: red\">A user with this username is already registered</p>")
                        .addObject("updateUserForm", updateUserForm);
            } else {
                updatedUser.setUserName(updateUserForm.getNewUsername());
            }
        }
        if(imageForm!=null){
           updatedUser.setImage(imageForm.getImage());
        }

        if (!updateUserForm.getNewPassword().equals("")) {
            if (userValidation.isPasswordValidate(updateUserForm.getNewPassword())) {
                updatedUser.setPassword(updateUserForm.getNewPassword());
            } else {
                if (sessionUser.getRole().equalsIgnoreCase("Admin")) {
                    return  new ModelAndView("update")
                            .addObject("updatePassword", "<p style = \"color: red\"> The password must contain at least 8 characters, at least 1" +
                                    "\n uppercase character and at least one digit </p>")
                            .addObject("updateUserForm", updateUserForm);
                } else {
                    return new ModelAndView("update")
                            .addObject("updateUserPassword", "<p style = \"color: red\"> The password must contain at least 8 characters, at least 1" +
                                    "\n uppercase character and at least one digit </p>")
                            .addObject("updateUserForm", updateUserForm);
                }
            }
        }

        if (!updateUserForm.getNewEmail().equals("")) {
            if (userJpaRepository.findByEmail(updateUserForm.getNewEmail())!=null) {
                return new ModelAndView("update")
                        .addObject("userEmailAlreadyRegistered", "<p style = \"color: red\">A user with this email is already registered</p>")
                        .addObject("updateUserForm", updateUserForm);
            } else {
                updatedUser.setEmail(updateUserForm.getNewEmail());
            }
        }
            userJpaRepository.save(updatedUser);

        if (updateUserForm.getUsername().equals(session.getAttribute("userName"))) {
            response.sendRedirect(request.getContextPath());
        } else {
            response.sendRedirect(request.getContextPath()+ "/user/users");
        }
        return modelAndView;
    }

    @GetMapping(value = {"/users"})
    public ModelAndView preUsers (HttpServletRequest request){
        User sessionUser = (User) request.getSession().getAttribute("user");
        List<User> users = new ArrayList<>();
        List<User> allUsers ;
        allUsers = userJpaRepository.findAll();
        allUsers.forEach(u ->{
            if (!u.getUserName().equals(sessionUser.getUserName())){
                users.add(u);
            }
        });
        return new ModelAndView("users")
                .addObject("otherUsers", users);
    }

    @GetMapping(value = {"/delete"})
    public void postUserDelete(@ModelAttribute("id") int userId,HttpServletRequest request, HttpServletResponse response) throws IOException {
        User sessionUser = (User) request.getSession().getAttribute("user");
            if (sessionUser.getRole().equalsIgnoreCase("Admin")){
                User userByIdWithTopic = userJpaRepository.getUserByIdWithTopic(userId);
                List<Topic> topics = userByIdWithTopic.getTopics();
                if (topics.size()!=0) {
                    for (int i = 0; i < topics.size(); i++) {
                        int topicId = topics.get(i).getId();
                        List<Post> postByUserTopic = postJpaRepository.findPostByUserTopic(userId, topicId);
                        for (Post post : postByUserTopic) {
                            postJpaRepository.delete(post);
                        }
                    }
                    userByIdWithTopic.setTopics(new ArrayList<>());
                    userJpaRepository.save(userByIdWithTopic);
                }
                userJpaRepository.delete(userByIdWithTopic);
                response.sendRedirect(request.getContextPath()+"/user/users");
            }
        }

    @SneakyThrows
    @GetMapping(value = {"/logout"})
    public void logout(HttpServletRequest request, HttpServletResponse response){
        HttpSession session=request.getSession();
        if (session!=null) {
            session.invalidate();
        }
        response.sendRedirect(request.getContextPath());
    }

}

