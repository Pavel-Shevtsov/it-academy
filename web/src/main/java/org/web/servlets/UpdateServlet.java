package org.web.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.dao.impl.UserDAOImpl;
import org.example.dao.impl.UserModifyDAOImpl;
import org.example.model.User;
import org.example.validation.UserValidation;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "UpdateServlet", urlPatterns = "/update")
public class UpdateServlet extends HttpServlet {
    UserModifyDAOImpl userModifyDAO = new UserModifyDAOImpl();
    UserValidation userValidation = new UserValidation();



    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html");
        String userId = req.getParameter("id");
        if (userId == null){
            HttpSession session = req.getSession();
            req.setAttribute("name",session.getAttribute("name"));
            req.setAttribute("oldPassword",session.getAttribute("password"));
            req.setAttribute("email",session.getAttribute("email"));
            RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/update.jsp");
            rd.forward(req,resp);
        }else{
            User userTem = userModifyDAO.checkUserById(Integer.parseInt(userId));
            req.setAttribute("id",userTem.getId());
            req.setAttribute("name" , userTem.getUserName());
            req.setAttribute("oldPassword",userTem.getPassword());
            req.setAttribute("email",userTem.getEmail());
            RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/update.jsp");
            rd.forward(req,resp);
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

        User userTem = userModifyDAO.checkUserRole(oldName);
        boolean haveUserWithUserEmail = userValidation.isHaveUserWithUserEmail(newEmail);
        String role = (String) session.getAttribute("role");
        UserDAOImpl userDAO = new UserDAOImpl();
        User  user = null;



        if (!newName.equals("")){
            User userByUserName = userDAO.getUserByUserName(newName);
            if (userByUserName==null){
                user = userModifyDAO.updateUser(userTem.getId());
                user.setUserName(newName);
                userModifyDAO.addUser(user);
            }else{
                req.setAttribute("userNameAlreadyRegistered","<p style = \"color: red\">A user with this username is already registered</p>");
                RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/welcome.jsp");
                rd.forward(req,resp);
            }

        }if(!newPassword.equals("")){
            if (userValidation.isPasswordValidate(newPassword)) {
                user = userModifyDAO.updateUser(userTem.getId());
                user.setPassword(newPassword);
                userModifyDAO.addUser(user);
            }else{
                if(role.equalsIgnoreCase("Admin")){
                    req.setAttribute("updatePassword","<p style = \"color: red\"> The password must contain at least 8 characters, at least 1" +
                            "\n uppercase character and at least one digit </p>");
                    RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/welcome.jsp");
                    rd.forward(req,resp);
                }else  {
                    req.setAttribute("updateUserPassword","<p style = \"color: red\"> The password must contain at least 8 characters, at least 1" +
                            "\n uppercase character and at least one digit </p>");
                    RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/welcome.jsp");
                    rd.forward(req,resp);
                }
            }
        }if(!newEmail.equals("")){
            if (!haveUserWithUserEmail) {
                user = userModifyDAO.updateUser(userTem.getId());
                user.setEmail(newEmail);
                userModifyDAO.addUser(user);
            }else{
                req.setAttribute("userEmailAlreadyRegistered","<p style = \"color: red\">A user with this email is already registered</p>");
                RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/welcome.jsp");
                rd.forward(req,resp);
            }
        }

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

