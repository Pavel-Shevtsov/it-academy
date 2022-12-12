package org.web.servlets.post;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


import java.io.IOException;

@WebServlet(urlPatterns = "/addPost")
public class AddPostServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        int userId = (int)session.getAttribute("id");
        int topicId = Integer.parseInt(req.getParameter("id"));




        RequestDispatcher rd = req.getRequestDispatcher("myPost.jsp");
        rd.forward(req,resp);

    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        String name = String.valueOf(session.getAttribute("name"));
        int id = Integer.parseInt(req.getParameter("id"));


    }
}
