package org.web.filter;
import jakarta.servlet.*;
import jakarta.servlet.Filter;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.dao.impl.UserModifyDAOImpl;
import org.example.model.User;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@WebFilter(urlPatterns = {"/users", "/update", "/welcome"})
public class MyFilter implements Filter {

    UserModifyDAOImpl userModifyDAO = new UserModifyDAOImpl();
    private FilterConfig config = null;
    private boolean active = false;

    @Override
    public void init(FilterConfig config) throws ServletException {
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

        HttpSession session = req.getSession();

        if (session == null || session.getAttribute("name")==null){
            RequestDispatcher rd = req.getRequestDispatcher("index.jsp");
            rd.forward(req,resp);
        }else {
            String role = (String) session.getAttribute("role");
            String name  = (String) session.getAttribute("name");
            if (role.equalsIgnoreCase("Admin")){
                List <User> allusers = userModifyDAO.allUsers();
                allusers.forEach(u ->{
                    if (!u.getUserName().equals(name)){
                        users.add(u);
                    }
                });
            }
        }
        servletRequest.setAttribute("otherUsers", users);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        {
            config = null;
        }
    }
}
