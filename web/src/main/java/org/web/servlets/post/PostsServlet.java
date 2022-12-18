package org.web.servlets.post;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.dao.impl.PostDAOImpl;
import org.example.dao.impl.TopicDAOImpl;
import org.example.model.Post;
import org.example.model.Topic;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/posts")
public class PostsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        PostDAOImpl postDAO = new PostDAOImpl();
        int userId = (int)session.getAttribute("id");
        int topicId = Integer.parseInt(req.getParameter("idTopic"));
        session.setAttribute("topicId", topicId);
        TopicDAOImpl topicDAO = new TopicDAOImpl();
        Topic topicById = topicDAO.getById(topicId);
        List<Post> userPost = postDAO.getByUserTopic(userId,topicId);

        req.setAttribute("topicName", topicById.getName());
        req.setAttribute("posts",userPost);
        req.setAttribute("topicId", session.getAttribute("topicId"));
        RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/myPost.jsp");
        rd.forward(req,resp);
    }
}
