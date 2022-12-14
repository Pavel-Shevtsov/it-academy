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
import org.example.model.Topic;
import org.example.model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = "/allFreeTopics")
public class AllFreeTopics extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        int userId = (int) session.getAttribute("id");
        UserDAOImpl userDAO = new UserDAOImpl();
        TopicDAOImpl topicDAO = new TopicDAOImpl();
        User userByIdWithTopic = userDAO.getUserByIdWithTopic(userId);
        List<Topic> allTopicsUser = userByIdWithTopic.getTopics();
        List<Topic> allTopics = topicDAO.allTopic();
        List <Topic> freeTopics = new ArrayList<>();
        if (allTopicsUser.size()!=0) {
            for (Topic topic : allTopicsUser) {
                for (int k = 0; k < allTopics.size(); k++) {
                    boolean isFreTopic = topic.equals(allTopics.get(k));
                    if (isFreTopic) {
                        allTopics.remove(k);
                    }
                }
            }
        }
        freeTopics.addAll(allTopics);

        req.setAttribute("freeTopics", freeTopics);
        RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/addTopic.jsp");
        rd.forward(req,resp);
    }



    }
