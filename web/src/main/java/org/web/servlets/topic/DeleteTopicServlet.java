package org.web.servlets.topic;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.dao.UserModifyDAO;
import org.example.dao.impl.TopicDAOImpl;
import org.example.dao.impl.UserDAOImpl;
import org.example.dao.impl.UserModifyDAOImpl;
import org.example.model.Topic;
import org.example.model.User;


import java.io.IOException;
import java.util.List;

@WebServlet(name = "deleteTopic", urlPatterns = "/deleteTopic")
public class DeleteTopicServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        int userId = (int) session.getAttribute("id");
        TopicDAOImpl topicDAO = new TopicDAOImpl();
        UserDAOImpl userDAO = new UserDAOImpl();
        UserModifyDAO userModifyDAO = new UserModifyDAOImpl();

        String topicId = req.getParameter("id");
        if (topicId!=null) {
            Topic topicById = topicDAO.getTopicById(Integer.parseInt(topicId));
            User userById = userDAO.getUserByIdWithTopic(userId);
            int indexTopicInList = -1;
            List<Topic> userTopics = userById.getTopics();
            for (Topic topic : userTopics) {
                if (topic.getName().equals(topicById.getName())) {
                    indexTopicInList = userTopics.indexOf(topic);

                }

            }
            userTopics.remove(indexTopicInList);
            userModifyDAO.updateUser(userById);
        }
            req.setAttribute("deleteTopic","<p style = \"color: blue\"> Topic deleted successfully.</p>");
            RequestDispatcher rd = req.getRequestDispatcher("/welcome");
            rd.forward(req,resp);

    }
}
