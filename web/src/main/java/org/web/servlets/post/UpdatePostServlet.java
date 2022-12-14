package org.web.servlets.post;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.dao.impl.PostDAOImpl;
import org.example.model.Post;

import java.io.IOException;

@WebServlet(urlPatterns = "/updatePost")
public class UpdatePostServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        PostDAOImpl postDAO = new PostDAOImpl();
        int postId = Integer.parseInt(req.getParameter("idPost"));
        session.setAttribute("postId", postId);
        Post postById = postDAO.getPostById(postId);
        String postName = postById.getName();
        String postText = postById.getText();
        req.setAttribute("postName", postName);
        req.setAttribute("postText", postText);

        RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/updatePost.jsp");
        rd.forward(req,resp);
    }
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html");
        HttpSession session = req.getSession(false);
        int postId = (int)session.getAttribute("postId");

        PostDAOImpl postDAO = new PostDAOImpl();

        String newPostName = req.getParameter("newPostName");
        String newPostText = req.getParameter("newPostText");

        Post postById = postDAO.getPostById(postId);
        postById.setName(newPostName);
        postById.setText(newPostText);

        postDAO.updatePost(postById);

        if (postDAO.getPostById(postId).getName().equalsIgnoreCase(newPostName)){
            req.setAttribute("updatePost","<p style =\"color: blue\"> Post successfully updated</p>");
        }else{
            req.setAttribute("updatePost","<p style =\"color: red\"> Post not updated</p>");
        }
        RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/welcome.jsp");
        rd.forward(req,resp);

    }
}


