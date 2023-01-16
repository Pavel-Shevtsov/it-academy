package org.web.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.example.dao.inter.UserDAO;
import org.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component("UserFilter")
public class UserFilter implements Filter {

    @Autowired
    UserDAO userDAO;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) request).getSession(false);
        User sessionUser = (User) session.getAttribute("user");
        if(!sessionUser.getRole().equalsIgnoreCase("admin")){
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/welcome.jsp");
            rd.forward(request,response);
        } else {
            List<User> users = new ArrayList<>();
            List<User> allUsers ;
            allUsers = userDAO.allUsers();
            allUsers.forEach(u ->{
                if (!u.getUserName().equals(sessionUser.getUserName())){
                    users.add(u);
                }
            });
            request.setAttribute("otherUsers", users);
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/users.jsp");
            rd.forward(request,response);
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
