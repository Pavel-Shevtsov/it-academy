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

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

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
        User user = null;
        try {
            user = userDAO.getUserByUserName(userTem.getUserName());
        } catch (SQLException e) {
            req.setAttribute("error","<p style = \"color: red\">  Don't worry, an error has occurred.\n" +
                    "Return to the login page and login again </p>");
            RequestDispatcher rd = req.getRequestDispatcher("error.jsp");
            rd.forward(req,resp);
        }
        if (user==null){
            PrintWriter pw = resp.getWriter();
            pw.write("<p style =\"color: red\"> Sorry user with this username is not registered </p>");
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("index.jsp");
            requestDispatcher.include(req,resp);
            pw.close();
        }

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
