package org.web.servlets.post;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.dao.TopicModifyDAO;
import org.example.dao.impl.PostDAOImpl;
import org.example.dao.impl.TopicDAOImpl;
import org.example.dao.impl.UserModifyDAOImpl;
import org.example.model.Post;


import java.io.IOException;

@WebServlet(urlPatterns = "/addPost")
public class AddPostServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/addPost.jsp");
        rd.forward(req,resp);

    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html");
        PostDAOImpl postDAO = new PostDAOImpl();
        UserModifyDAOImpl userModifyDAO = new UserModifyDAOImpl();
        TopicDAOImpl topicDAO = new TopicDAOImpl();
        HttpSession session = req.getSession(false);
        int userId = (int)session.getAttribute("id");
        int topicId = (int) session.getAttribute("topicId");

        postDAO.getByUserTopic(userId,topicId);

        String postName = req.getParameter("postName");
        String postText = req.getParameter("postText");

        if (!postName.equals("")) {
            if (!postText.equals("")) {
                Post post = new Post();
                post.setName(postName);
                post.setText(postText);
                post.setUser(userModifyDAO.getUserById(userId));
                post.setTopic(topicDAO.getTopicById(topicId));
                postDAO.addPost(post);
            }else{
                req.setAttribute("addPost","<p style = \"color: red\"> Post is not added, you can not add a post without text.</p>");
                RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/myPost.jsp");
                rd.forward(req,resp);
            }
        }else{

            req.setAttribute("addPost","<p style = \"color: red\"> Post not added, you can not add a post without a name.</p>");
            RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/myPost.jsp");
            rd.forward(req,resp);
        }
        req.setAttribute("addPost","<p style = \"color: blue\"> Post added successfully.</p>");
        RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/myPost.jsp");
        rd.forward(req,resp);

    }
}
