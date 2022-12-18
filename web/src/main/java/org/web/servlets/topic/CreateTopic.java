package org.web.servlets.topic;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.example.dao.impl.TopicDAOImpl;
import org.example.model.Topic;

import java.io.IOException;

@WebServlet(name = "CreateTopic", urlPatterns = "/createTopic")
public class CreateTopic extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/createTopic.jsp");
        rd.forward(req,resp);

    }
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        TopicDAOImpl topicDAO = new TopicDAOImpl();
        Topic topic = new Topic();

        resp.setContentType("text/html");
        String topicName = req.getParameter("topicName");
        Topic topicByTopicName = topicDAO.getTopicByTopicName(topicName);

        if (topicByTopicName == null) {
            topic.setName(topicName);
            topicDAO.add(topic);
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/welcome");
            req.setAttribute("createTopicSuccessfully", "<p style =\"color: blue\"> Topic created successfully</p>");
            requestDispatcher.include(req, resp);
        } else {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/welcome");
            req.setAttribute("createTopicError", "<p style =\"color: red\"> Sorry, there is a topic with this topic name</p>");
            requestDispatcher.forward(req, resp);
        }


    }
}

