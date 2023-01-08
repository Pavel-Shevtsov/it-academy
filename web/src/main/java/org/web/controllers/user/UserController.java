package org.web.controllers.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.SneakyThrows;
import org.example.dao.inter.UserDAO;
import org.example.model.User;

import org.example.validation.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @GetMapping(value = {"/users"})
    public ModelAndView allUsers(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        String name = (String) session.getAttribute("name");
        List<User> users = new ArrayList<>();
        List<User> allUsers ;
        allUsers = userDAO.allUsers();
        allUsers.forEach(u ->{
            if (!u.getUserName().equals(name)){
                users.add(u);
            }
        });
        return  new ModelAndView("/users").addObject("otherUsers", users);
    }

    @GetMapping(value = {"/update"})
    public ModelAndView preUpdate(@ModelAttribute("id") int userID) {
        UserForm updateUserForm = new UserForm(userDAO.getById(userID)) ;
        return new ModelAndView("/update").addObject("updateUserForm", updateUserForm);
    }

    @PostMapping(value = {"/update"})
    public ModelAndView postUpdate(@ModelAttribute("updateUserForm") UserForm updateUserForm, HttpServletRequest request) {

        UserValidation userValidation = new UserValidation();
        boolean validationName = false;
        boolean validationEmail = false;
        HttpSession session = request.getSession(false);
        ModelAndView modelAndView;
        String role = (String) session.getAttribute("role");

        User updatedUser = userDAO.getUserByUserName(updateUserForm.getUsername());
        UserForm imageForm = (UserForm) request.getSession().getAttribute("imageForm");

        if (!updateUserForm.getNewUsername().equals("")) {
            validationName = userValidation.isHaveUserWithUserName(updateUserForm.getNewUsername());
            if (validationName) {
                modelAndView = new ModelAndView("welcome")
                        .addObject("userNameAlreadyRegistered", "<p style = \"color: red\">A user with this username is already registered</p>");
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
                if (role.equalsIgnoreCase("Admin")) {
                    modelAndView = new ModelAndView("welcome")
                            .addObject("updatePassword", "<p style = \"color: red\"> The password must contain at least 8 characters, at least 1" +
                                    "\n uppercase character and at least one digit </p>");
                } else {
                    modelAndView = new ModelAndView("welcome")
                            .addObject("updateUserPassword", "<p style = \"color: red\"> The password must contain at least 8 characters, at least 1" +
                                    "\n uppercase character and at least one digit </p>");
                }
            }
        }

        if (!updateUserForm.getNewEmail().equals("")) {
            validationEmail = userValidation.isHaveUserWithUserEmail(updateUserForm.getNewEmail());
            if (validationEmail) {
                modelAndView = new ModelAndView("welcome")
                        .addObject("userEmailAlreadyRegistered", "<p style = \"color: red\">A user with this email is already registered</p>");
            } else {
                updatedUser.setEmail(updateUserForm.getNewEmail());
            }
        } else

            userDAO.update(updatedUser);

        if (updateUserForm.getUsername().equals(session.getAttribute("name"))) {
            modelAndView = new ModelAndView("login")
                    .addObject("userUpdate", "<p style = \"color: blue\"> User named " + updateUserForm.getUsername() + " " +
                            updateUserForm.getPassword() + " " + updateUserForm.getEmail() +
                            " updated to " + updateUserForm.getNewUsername() + " " +
                            updateUserForm.getNewPassword() + " " +
                            updateUserForm.getNewEmail() + " . Please login the app again</p>");
        } else {
            modelAndView = new ModelAndView("welcome")
                    .addObject("userUpdate", "<p style = \"color: blue\"> User named " + updateUserForm.getUsername() + " " +
                            updateUserForm.getPassword() + " " + updateUserForm.getEmail() +
                            " updated to " + updateUserForm.getNewUsername() + " " +
                            updateUserForm.getNewPassword() + " " +
                            updateUserForm.getNewEmail() + ".");
        }
        return modelAndView;
    }

    @GetMapping(value = {"/delete"})
    public void postUserDelete(@ModelAttribute("id") Integer userId,HttpServletRequest request, HttpServletResponse response) throws IOException {
            userDAO.delete(userId);
        response.sendRedirect(request.getContextPath()+"/welcome");
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

        ModelAndView modelAndView;
        String userName = (String)request.getSession().getAttribute("name");
        imageForm.setImage(imageForm.getFileData().getBytes());
        request.getSession().setAttribute("imageForm",imageForm);
        if(userName!=null){
            UserForm userForm = new UserForm( userDAO.getUserByUserName(userName));
            userForm.setImage(imageForm.getImage());
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
        response.getOutputStream().close();
    }

    @GetMapping(value = {"/imageOnWelcomePage"})
    public void imageOnWelcomePage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userName = (String) request.getSession().getAttribute("name");
        UserForm userForm = new UserForm(userDAO.getUserByUserName(userName));
        if (userForm.getImage() != null) {
            response.setContentType("img/jpg");
            response.getOutputStream().write(userForm.getImage());
        }
        response.getOutputStream().close();
    }
}

