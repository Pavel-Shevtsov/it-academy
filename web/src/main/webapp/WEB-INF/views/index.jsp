<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language = "Java" contentType = "text/html charset = ISO-8859-1" pageEncoding = "ISO-8859-1" %>

    <html>
    <head>
    <meta http-equiv = "Context-Type" context = "text/html charset = ISO-8859-1" >
    <style>
        <%@include file="/WEB-INF/style/styleLoginPage.css"%>
        <%@include file="/WEB-INF/style/styleBody.css"%>
    </style>

    <title>Login to application</title>
    </head>
            <body>
                    <div class = "message">
                        ${loginError}
                        ${errorPasswordUsername}
                        ${userUpdate}
                        ${createNewUser}
                    </div>
                    <div class= "loginPage">
                                <div class = "legendLoginPage">
                                        <h1> Login to App</h1>
                                        <h1>"Topic and Post"</h1>
                                </div>
                                <div class= "loginText">
                                    <form action="${pageContext.request.contextPath}/login" method = "post" >
                                         <div class="text-field">
                                                  <p>UserName</p>
                                                  <h2><input type="text" name = "username" required = "required"></h2>

                                                  <p>Password</p>
                                                  <input type="password" name = "password" required = "required">
                                </div>
                                            <input type = "submit", value = "Login">
                                    </form>
                    </div>
                    <button onclick = "location.href = '${pageContext.request.contextPath}/register' " >Registration</button>
            </body>
    </html>
