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

@WebServlet( name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        RequestDispatcher rd = req.getRequestDispatcher("index.jsp");
        rd.forward(req,resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        HttpSession session = req.getSession(false);

        User userTem = new User();
        userTem.setPassword(password);
        userTem.setUserName(username);

        UserDAOImpl userDAO = new UserDAOImpl();
        UserValidation userValidation = new UserValidation();
        User user = userDAO.getUserByUserName(userTem.getUserName());

        if (userTem.getPassword().equals(user.getPassword())){

            if (session!= null){
                session.setAttribute("id",user.getId());
                session.setAttribute("name", user.getUserName());
                session.setAttribute("password",user.getPassword());
                session.setAttribute("role", user.getRole());
                session.setAttribute("email", user.getEmail());
            }

            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/welcome");
            requestDispatcher.forward(req,resp);
        }else {
            PrintWriter pw = resp.getWriter();
            pw.write("<p style =\"color: red\"> Sorry username or password error</p>");
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("index.jsp");
            requestDispatcher.include(req,resp);
            pw.close();
        }

    }
}
