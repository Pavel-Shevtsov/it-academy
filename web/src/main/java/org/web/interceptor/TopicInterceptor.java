package org.web.interceptor;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.example.model.User;
import org.springframework.web.servlet.mvc.WebContentInterceptor;


public class TopicInterceptor extends WebContentInterceptor {
    @SneakyThrows
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException {
        User sessionUser = (User) request.getSession().getAttribute("user");
        if(sessionUser.getRole().equalsIgnoreCase("user")||request.getSession()==null){
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/views/login.jsp");
            requestDispatcher.forward(request,response);
            return false;
        }
        return true;
    }
}
