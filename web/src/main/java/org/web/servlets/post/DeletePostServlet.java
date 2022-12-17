package org.web.servlets.post;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.dao.impl.PostDAOImpl;


import java.io.IOException;

@WebServlet(urlPatterns = "/deletePost")
public class DeletePostServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PostDAOImpl postDAO = new PostDAOImpl();
        int postId = Integer.parseInt(req.getParameter("idPost"));
        postDAO.deletePost(postId);

        if (postDAO.getPostById(postId)==null){
            req.setAttribute("deletePost","<p style = \"color: blue\"> Post successfully deleted.</p>");
        }else{
            req.setAttribute("deletePost","<p style = \"color: red\"> Post not deleted.</p>");
        }
        RequestDispatcher rd = req.getRequestDispatcher("/welcome");
        rd.forward(req,resp);

    }
}
