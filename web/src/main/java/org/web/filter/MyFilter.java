package org.web.filter;
import jakarta.servlet.*;
import jakarta.servlet.Filter;
import jakarta.servlet.annotation.WebFilter;
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


@WebFilter(urlPatterns = {"/welcome","/users","/update","/createTopic","/allFreTopics","/addTopic"})
public class MyFilter implements Filter {

    UserDAOImpl userDAO = new UserDAOImpl();
    TopicDAOImpl topicDAO = new TopicDAOImpl();
    private FilterConfig config = null;
    private boolean active = false;

    @Override
    public void init(FilterConfig config){
        {
            this.config = config;
            String act = config.getInitParameter("active");
            if (act != null)
                active = (act.toUpperCase().equals("TRUE"));
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest)  servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        List<User> users = new ArrayList<>();
        List<Topic> topics = new ArrayList<>();
        HttpSession session = req.getSession();

        String role = (String) session.getAttribute("role");
        String name  = (String) session.getAttribute("name");

        if (session == null || session.getAttribute("name")==null){
            RequestDispatcher rd = req.getRequestDispatcher("index.jsp");
            rd.forward(req,resp);
        }else {

            if (role.equalsIgnoreCase("Admin")){
                List <User> allUsers ;
                allUsers = userDAO.allUsers();
                allUsers.forEach(u ->{
                    if (!u.getUserName().equals(name)){
                        users.add(u);
                    }
                });
                List <Topic> allTopics;
                allTopics = topicDAO.allTopic();
                topics.addAll(allTopics);
            }
        }
        servletRequest.setAttribute("otherUsers", users);
        servletRequest.setAttribute("AllTopic",topics);
        servletRequest.setAttribute("role",role);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        {
            config = null;
        }
    }
}
