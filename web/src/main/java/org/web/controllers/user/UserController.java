package org.web.controllers.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.SneakyThrows;
import org.example.dao.inter.PostDAO;
import org.example.dao.inter.TopicDAO;
import org.example.dao.inter.UserDAO;
import org.example.model.Topic;
import org.example.model.User;

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
    UserDAO userDAO;
    @Autowired
    TopicDAO topicDAO;
    @Autowired
    PostDAO postDAO;

    @GetMapping(value = {"/update"})
    public ModelAndView preUpdate(@RequestParam("id") int userId,HttpServletRequest request) {
        UserForm updateUserForm = new UserForm(userDAO.getById(userId)) ;
        request.getSession().setAttribute("updatedUserId",userId);
        return new ModelAndView("update").addObject("updateUserForm", updateUserForm);
    }

    @SneakyThrows
    @PostMapping(value = {"/update"})
    public ModelAndView postUpdate(@ModelAttribute("updateUserForm") UserForm updateUserForm, HttpServletRequest request, HttpServletResponse response) {

        UserValidation userValidation = null;
        HttpSession session = request.getSession(false);
        ModelAndView modelAndView = null;
        User sessionUser = (User) session.getAttribute("user");

        User updatedUser = userDAO.getById(updateUserForm.getId());
        UserForm imageForm = (UserForm) request.getSession().getAttribute("imageForm");

        if (!updateUserForm.getNewUsername().equals("")) {

            if (userDAO.getUserByUserName(updateUserForm.getNewUsername())!= null) {
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
            boolean validationPassword = userValidation.isPasswordValidate(updateUserForm.getNewPassword());
            if (validationPassword) {
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
            if (userDAO.getUserByEmail(updateUserForm.getNewEmail())!=null) {
                return new ModelAndView("update")
                        .addObject("userEmailAlreadyRegistered", "<p style = \"color: red\">A user with this email is already registered</p>")
                        .addObject("updateUserForm", updateUserForm);
            } else {
                updatedUser.setEmail(updateUserForm.getNewEmail());
            }
        }
            userDAO.update(updatedUser);

        if (updateUserForm.getUsername().equals(session.getAttribute("userName"))) {
            modelAndView = new ModelAndView("login")
                    .addObject("userUpdate", "<p style = \"color: blue\"> User named " + updateUserForm.getUsername() + " " +
                            updateUserForm.getPassword() + " " + updateUserForm.getEmail() +
                            " updated to " + updateUserForm.getNewUsername() + " " +
                            updateUserForm.getNewPassword() + " " +
                            updateUserForm.getNewEmail() + " . Please login the app again</p>");
        } else {
            response.sendRedirect(request.getContextPath()+ "/user/users");
        }
        return modelAndView;
    }

    @GetMapping(value = {"/delete"})
    public void postUserDelete(@ModelAttribute("id") int userId,HttpServletRequest request, HttpServletResponse response) throws IOException {
        User sessionUser = (User) request.getSession().getAttribute("user");
            if (sessionUser.getRole().equalsIgnoreCase("Admin")){
                User userByIdWithTopic = userDAO.getUserByIdWithTopic(userId);
                List<Topic> topics = userByIdWithTopic.getTopics();
                if (topics.size()!=0) {
                    for (int i = 0; i < topics.size(); i++) {
                        int topicId = topics.get(i).getId();
                        postDAO.deleteAllUserPost(userId, topicId);
                    }
                    userByIdWithTopic.setTopics(new ArrayList<>());
                    userDAO.update(userByIdWithTopic);
                }
                userDAO.delete(userId);
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
    @PostMapping(value = {"/uploadPhoto"})
    public ModelAndView uploadPhoto(@ModelAttribute("imageForm") UserForm imageForm, HttpServletRequest request) throws IOException {

        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        ModelAndView modelAndView;
        imageForm.setImage(imageForm.getFileData().getBytes());
        request.getSession().setAttribute("imageForm",imageForm);

        if(user!=null){
            UserForm userForm = new UserForm(userDAO.getById((Integer) session.getAttribute("updatedUserId")));
            userForm.setImage(imageForm.getImage());
            session.removeAttribute("updatedUserId");
            modelAndView = new ModelAndView("update");
            modelAndView.addObject("updateUserForm",userForm);
        }else{
            UserForm userForm=new UserForm();
            modelAndView = new ModelAndView("register");
            modelAndView.addObject("registrationForm",userForm);
        }
        return modelAndView;
    }

    @GetMapping(value = {"/viewImage"})
    public void viewImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserForm imageForm = (UserForm) request.getSession().getAttribute("imageForm");
        if(imageForm.getImage()!=null) {
            response.setContentType("image/jpg");
            response.getOutputStream().write(imageForm.getImage());
        }
        request.removeAttribute("imageForm");
        response.getOutputStream().close();
    }

    @GetMapping(value = {"/imageOnWelcomePage"})
    public void imageOnWelcomePage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        UserForm userForm = new UserForm((User) session.getAttribute("user"));
        if (userForm.getImage() != null) {
            response.setContentType("img/jpg");
            response.getOutputStream().write(userForm.getImage());
        }
        response.getOutputStream().close();
    }
}

