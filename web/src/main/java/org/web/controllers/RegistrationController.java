package org.web.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.SneakyThrows;
import org.example.model.User;
import org.example.repository.UserJpaRepository;
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
import org.web.service.EmailService;

@Controller
@RequestMapping(value = "/add")
public class RegistrationController {

    @Autowired
    EmailService emailService;

    @Autowired
    UserJpaRepository userJpaRepository;

    @GetMapping
    public ModelAndView preRegistration() {
        ModelAndView modelAndView = new ModelAndView("register")
                .addObject("registrationForm", new UserForm())
                .addObject("imageForm", new UserForm());
        return modelAndView;
    }

    @SneakyThrows
    @PostMapping
    public ModelAndView postRegistration(@ModelAttribute("registrationForm") UserForm registrationUser, HttpServletRequest request, HttpServletResponse response) {
        User user = new User();
        ModelAndView modelAndView = null;
        UserValidation userValidation = new UserValidation();
        UserForm imageForm = (UserForm) request.getSession().getAttribute("imageForm");
        User comparisonUser = userJpaRepository.findByUserName(registrationUser.getUsername());

        if (comparisonUser == null) {
            user.setUserName(registrationUser.getUsername());
            if (registrationUser.getPassword().equalsIgnoreCase(registrationUser.getRepeatPassword())) {
                if (userValidation.isPasswordValidate(registrationUser.getPassword())) {
                    user.setPassword(registrationUser.getPassword());
                    if (userJpaRepository.findByEmail(registrationUser.getEmail()) == null) {
                        user.setEmail(registrationUser.getEmail());
                        if (imageForm != null) {
                            user.setImage(imageForm.getImage());
                        }
                        user.setRole("User");
                        userJpaRepository.save(user);
                        emailService.sendEmail("pahaschewzow@gmail.com", user.getEmail(),"Registration","Name - "  + user.getUserName() + "; ID - " + user.getId() + "; Email - " + user.getEmail() + " registered in the app" );
                        response.sendRedirect(request.getContextPath());
                    } else {
                        return new ModelAndView("register")
                                .addObject("errorEmailRegister", "<p style =\"color: red\"> User with this email is already register</p>")
                                .addObject("registrationForm", registrationUser);
                    }
                } else {
                    return new ModelAndView("register")
                            .addObject("errorPassword", "<p style = \"color: red\"> The password must contain at least 8 characters, at least 1" +
                                    "\n uppercase character and at least one digit.\n" +
                                    "Enter again </p>")
                            .addObject("registrationUser", registrationUser);
                }
            } else {
                return new ModelAndView("register")
                        .addObject("errorRepeatPassword", "<p style =\"color: red\"> Password strings are not identical." +
                                "Please enter them again</p>")
                        .addObject("registrationForm", registrationUser);
            }
        } else {
            return new ModelAndView("register")
                    .addObject("errorUserName", "<p style =\"color: red\">A user with the same name already exists." +
                            " Please enter a different username")
                    .addObject("registrationForm", registrationUser);
        }
        return modelAndView;
    }
    @PostMapping(value = {"/uploadPhoto"})
    public ModelAndView uploadPhoto(@ModelAttribute("imageForm") UserForm imageForm, HttpServletRequest request) throws IOException {

        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        ModelAndView modelAndView;
        imageForm.setImage(imageForm.getFileData().getBytes());
        request.getSession().setAttribute("imageForm",imageForm);

        if(user!=null){
            UserForm userForm = new UserForm(userJpaRepository.findById((int) session.getAttribute("updatedUserId")));
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

    @GetMapping(value = {"/imageOnPage"})
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
