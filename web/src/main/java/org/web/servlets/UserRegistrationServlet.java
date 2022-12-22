package org.web.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.dao.impl.UserDAOImpl;
import org.example.model.User;
import org.example.validation.UserValidation;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(name = "UserRegistrationServlet", urlPatterns = "/register")
public class UserRegistrationServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/register.jsp");
        rd.forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = new User();
        PrintWriter pw = resp.getWriter();
        UserDAOImpl userDAO = new UserDAOImpl();
        UserValidation userValidation = new UserValidation();

        resp.setContentType("text/html");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String repeatPassword = req.getParameter("repeatPassword");
        String email = req.getParameter("email");
        String role = "User";

        HttpSession httpSession = req.getSession(false);

        User userTem = new User();
        userTem.setUserName(username);
        userTem.setEmail(email);
        userTem.setRole(role);

        User comparisonUser = userDAO.getUserByUserName(userTem.getUserName());
        if (comparisonUser == null && password.equals(repeatPassword)) {

            boolean passwordValidate = userValidation.isPasswordValidate(password);

            if(passwordValidate){
                userTem.setPassword(password);


                user.setUserName(userTem.getUserName());
                user.setPassword(userTem.getPassword());
                user.setEmail(userTem.getEmail());
                user.setRole(userTem.getRole());

                userDAO.add(user);
                req.setAttribute("createNewUser", "<p style = \"color: blue\"> User successfully registered." +
                        "Please login to account </p>");

                if (httpSession!=null){
                    httpSession.setAttribute("name",user.getUserName());
                    httpSession.setAttribute("role", user.getRole());
                }

                RequestDispatcher requestDispatcher = req.getRequestDispatcher("index.jsp");
                requestDispatcher.forward(req,resp);
                pw.close();

            }else {
                req.setAttribute("errorPassword","<p style = \"color: red\"> The password must contain at least 8 characters, at least 1" +
                        "\n uppercase character and at least one digit.\n" +
                        "Enter again </p>");
                RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/register.jsp");
                rd.include(req,resp);
            }

        }else if(comparisonUser!=null&&comparisonUser.getUserName().equals(userTem.getUserName())){
            req.setAttribute("errorUserName","<p style =\"color: red\">A user with the same name already exists." +
                    " Please enter a different username");
            RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/register.jsp");
            rd.include(req,resp);
        }else if(!password.equals(repeatPassword)){
            req.setAttribute("errorRepeatPassword","<p style =\"color: red\"> Password strings are not identical." +
                    "Please enter them again</p>");
            RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/register.jsp");
            rd.include(req,resp);
        }else {
            if (userValidation.isHaveUserWithUserEmail(userTem.getEmail())){
                req.setAttribute("errorEmailRegister","<p style =\"color: red\"> User with this email is already register</p>");
                RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/register.jsp");
                rd.include(req,resp);
            }
        }
    }
}
