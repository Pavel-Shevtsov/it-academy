package org.web.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.dao.impl.UserModifyDAOImpl;
import org.example.model.User;
import org.example.validation.UserValidation;

import java.io.IOException;

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
            req.setAttribute("name" , userTem.getUserName());
            req.setAttribute("oldPassword",userTem.getPassword());
            req.setAttribute("email",userTem.getEmail());
            RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/update.jsp");
            rd.forward(req,resp);
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html");
        String newUserPassword = req.getParameter("newPassword");
        String userName = req.getParameter("name");
        User user = userModifyDAO.checkUserRole(userName);
        String role = user.getRole();
        User userTem ;

        if (userValidation.isPasswordValidate(newUserPassword)) {
            userTem = userModifyDAO.updateUser(user.getId());
            userTem.setPassword(newUserPassword);
            userModifyDAO.addUser(userTem);
            req.setAttribute("updatePassword", "<p style = \"color: blue\"> User updated successfully</p>");
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/welcome.jsp");
            requestDispatcher.forward(req, resp);
        }
        if(role.equals("Admin")){
            req.setAttribute("updatePassword","<p style = \"color: red\"> The password must contain at least 8 characters, at least 1" +
                    "\n uppercase character and at least one digit </p>");
            RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/update.jsp");
            rd.include(req,resp);
        }else  {
            req.setAttribute("updateUserPassword","<p style = \"color: red\"> The password must contain at least 8 characters, at least 1" +
                    "\n uppercase character and at least one digit </p>");
            RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/users.jsp");
            rd.include(req,resp);
        }

    }
}

