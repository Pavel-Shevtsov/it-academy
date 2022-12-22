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

@WebServlet(name = "UpdateServlet", urlPatterns = "/update")
public class UpdateServlet extends HttpServlet {
    UserValidation userValidation = new UserValidation();
    UserDAOImpl userDAO = new UserDAOImpl();


    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html");
        String userId = req.getParameter("id");
        if (userId == null) {
            HttpSession session = req.getSession();
            req.setAttribute("name", session.getAttribute("name"));
            req.setAttribute("oldPassword", session.getAttribute("password"));
            req.setAttribute("email", session.getAttribute("email"));
            RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/update.jsp");
            rd.forward(req, resp);
        } else {
            User userTem;
            userTem = userDAO.getById(Integer.parseInt(userId));
            req.setAttribute("id", userTem.getId());
            req.setAttribute("name", userTem.getUserName());
            req.setAttribute("oldPassword", userTem.getPassword());
            req.setAttribute("email", userTem.getEmail());
            RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/update.jsp");
            rd.forward(req, resp);
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html");
        HttpSession session = req.getSession();
        String newName = req.getParameter("newName");
        String newPassword = req.getParameter("newPassword");
        String newEmail = req.getParameter("newEmail");
        String oldName = req.getParameter("oldName");
        String oldPassword = req.getParameter("oldPassword");
        String oldEmail = req.getParameter("oldEmail");
        String role = (String) session.getAttribute("role");



        boolean validationName = false;
        boolean validationEmail = false;

        User updatedUser = userDAO.getUserByUserName(oldName);


        if (!newName.equals("")) {
            validationName = userValidation.isHaveUserWithUserName(newName);
            if (validationName) {
                req.setAttribute("userNameAlreadyRegistered", "<p style = \"color: red\">A user with this username is already registered</p>");
                RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/welcome.jsp");
                rd.forward(req, resp);
            } else {
                updatedUser.setUserName(newName);
            }
        }

        if (!newPassword.equals("")) {
            boolean validationPassword = userValidation.isPasswordValidate(newPassword);
            if (validationPassword) {
                updatedUser.setPassword(newPassword);
            } else {
                if (role.equalsIgnoreCase("Admin")) {
                    req.setAttribute("updatePassword", "<p style = \"color: red\"> The password must contain at least 8 characters, at least 1" +
                            "\n uppercase character and at least one digit </p>");
                    RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/welcome.jsp");
                    rd.forward(req, resp);
                } else {
                    req.setAttribute("updateUserPassword", "<p style = \"color: red\"> The password must contain at least 8 characters, at least 1" +
                            "\n uppercase character and at least one digit </p>");
                    RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/welcome.jsp");
                    rd.forward(req, resp);
                }
            }
        }

        if (!newEmail.equals("")) {
            validationEmail = userValidation.isHaveUserWithUserEmail(newEmail);
            if (validationEmail) {
                req.setAttribute("userEmailAlreadyRegistered", "<p style = \"color: red\">A user with this email is already registered</p>");
                RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/welcome.jsp");
                rd.forward(req, resp);
            } else {
                updatedUser.setEmail(newEmail);
            }
        } else

            userDAO.update(updatedUser);

        if (oldName.equals(session.getAttribute("name"))){
            req.setAttribute("userUpdate","<p style = \"color: blue\"> User named " + oldName + " " + oldPassword + " " + oldEmail + " updated to " + newName + " " +
                    newPassword + " " + newEmail + " . Please login the app again</p>");
            RequestDispatcher rd = req.getRequestDispatcher("index.jsp");
            rd.forward(req, resp);
        }else {
            req.setAttribute("userOtherUpdate","<p style = \"color: blue\"> User named "+ oldName + " " + oldPassword + " " + oldEmail + " updated to " + newName + " " +
                    newPassword + " " + newEmail + "</p>");
            RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/welcome.jsp");
            rd.forward(req, resp);

        }
    }
}

