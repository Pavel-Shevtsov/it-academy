package org.web.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.example.dao.inter.UserDAO;
import org.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

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
        HttpServletRequest req = (HttpServletRequest)request;
        String userName = (String) req.getSession().getAttribute("name");
        User userByUserName = userDAO.getUserByUserName(userName);
        if(userByUserName!=null&&!userByUserName.getRole().equalsIgnoreCase("admin")){
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/welcome.jsp");
            rd.forward(request,response);
        }

    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
