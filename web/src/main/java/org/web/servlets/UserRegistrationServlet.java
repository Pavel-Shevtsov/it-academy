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

import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(name = "UserRegistrationServlet", urlPatterns = "/register")
public class UserRegistrationServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/register.jsp");
        rd.forward(req,resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = new User();
        PrintWriter pw = resp.getWriter();
        UserDAOImpl userDAO = new UserDAOImpl();
        UserModifyDAOImpl userModifyDAO = new UserModifyDAOImpl();

        resp.setContentType("text/html");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String repeatPassword = req.getParameter("repeatpassword");
        String email = req.getParameter("email");
        String role = "User";

        HttpSession httpSession = req.getSession(false);

        User userTem = new User();
        userTem.setUserName(username);
        userTem.setEmail(email);
        userTem.setRole(role);

        User comparisonUser = userDAO.getUserByUserName(userTem.getUserName());

        if (comparisonUser!=null &&!comparisonUser.getUserName().equals(userTem.getUserName())&&password.equals(repeatPassword) ){
            userTem.setPassword(password);

            user.setId(userModifyDAO.checkUserId());
            user.setUserName(userTem.getUserName());
            user.setPassword(userTem.getPassword());
            user.setEmail(userTem.getEmail());
            user.setRole(userTem.getRole());

            userModifyDAO.addUser(user);
            req.setAttribute("createNewUser","<p style = \"color: blue\"> User successfully registered." +
                    "Please login to account </p>");

                if (httpSession!=null){
                    httpSession.setAttribute("name",user.getUserName());
                    httpSession.setAttribute("role", user.getRole());

                }

                RequestDispatcher requestDispatcher = req.getRequestDispatcher("index.jsp");
                requestDispatcher.forward(req,resp);
                pw.close();


        }else if(comparisonUser.getUserName().equals(userTem.getUserName())){
            pw.write("<p style =\"color: red\">A user with the same name already exists." +
                    " Please enter a different username");
            RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/register.jsp");
            rd.include(req,resp);
            pw.close();
        }else if(!password.equals(repeatPassword)){
            pw.write("<p style =\"color: red\"> Password strings are not identical." +
                    "Please enter them again</p>");
            RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/register.jsp");
            rd.include(req,resp);
            pw.close();
        }

    }
}
