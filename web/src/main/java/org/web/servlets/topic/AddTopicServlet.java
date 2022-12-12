package org.web.servlets.topic;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.dao.impl.TopicDAOImpl;
import org.example.dao.impl.UserDAOImpl;
import org.example.dao.impl.UserModifyDAOImpl;
import org.example.model.Topic;
import org.example.model.User;

import java.io.IOException;

import java.util.Collections;
import java.util.List;

@WebServlet(name = "AddTopic", urlPatterns = "/addTopic")
public class AddTopicServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        TopicDAOImpl topicDAO = new TopicDAOImpl();
        UserDAOImpl userDAO = new UserDAOImpl();
        UserModifyDAOImpl userModifyDAO = new UserModifyDAOImpl();
        int userId = (int) session.getAttribute("id");
        int topicId = Integer.parseInt(req.getParameter("id"));

        User userByUserName = userDAO.getUserByIdWithTopic(userId);
        Topic topicById = topicDAO.getTopicById(topicId);

        List<Topic> topics = userByUserName.getTopics();
        int sizeUpTo = topics.size();
        topics.add(topicById);
        userByUserName.setTopics(topics);
        int sizeAfter = topics.size();

        topicById.setUsers(Collections.singletonList(userByUserName));
        if (sizeUpTo!=sizeAfter){
            req.setAttribute("addTopic","<p style = \"color: blue\"> topic add successful.</p>");
        }else {
            req.setAttribute("addTopic","<p style = \"color: red\"> topic not added.</p>");
        }

        userModifyDAO.updateUser(userByUserName);
        RequestDispatcher rd = req.getRequestDispatcher("/welcome");
        rd.forward(req,resp);

    }


}
