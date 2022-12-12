package org.web.servlets;


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
import java.util.List;

@WebServlet(name = "WelcomeServlet", urlPatterns = "/welcome")
public class WelcomeServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        UserDAOImpl userDAO = new UserDAOImpl();
        TopicDAOImpl topicDAO = new TopicDAOImpl();
        HttpSession session = req.getSession(false);
        String role = (String) session.getAttribute("role");
        if (role.equals("Admin")){
            List<Topic> allTopicsList = topicDAO.allTopic();
            req.setAttribute("allTopics", allTopicsList);
        }else{
            User user = userDAO.getUserByUserName((String) session.getAttribute("name"));
            User userTopicsList = userDAO.getUserByIdWithTopic(user.getId());
            List<Topic> topics = userTopicsList.getTopics();
            req.setAttribute("userTopics", topics);
        }
        req.setAttribute("role", role);
        RequestDispatcher rd = req.getRequestDispatcher("/welcome");
        rd.forward(req,resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        req.setAttribute("name",req.getParameter("username"));

        RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/welcome.jsp");
        rd.forward(req,resp);
    }




}
