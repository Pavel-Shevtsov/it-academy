package org.web.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.dao.impl.UserModifyDAOImpl;

import java.io.IOException;


@WebServlet(name = "DeleteServlet", urlPatterns = "/delete")
public class DeleteServlet extends HttpServlet {
    UserModifyDAOImpl userModifyDAO = new UserModifyDAOImpl();


    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String userId = req.getParameter("id");
        if (userId!=null){
            userModifyDAO.deleteUser(Integer.parseInt(userId));
            req.setAttribute("deleteUser","<p style = \"color: blue\"> User deleted successfully.</p>");
            RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/welcome.jsp");
            rd.forward(req,resp);
        }

    }
}
