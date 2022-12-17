<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language = "Java" contentType = "text/html charset = ISO-8859-1" pageEncoding = "ISO-8859-1" %>

    <html>
    <head>
    <meta http-equiv = "Context-Type" context = "text/html charset = ISO-8859-1" >
    <style><%@include file="/WEB-INF/style/styleBody.css"%></style>
    <style><%@include file="/WEB-INF/style/styleRegistrationPage.css"%></style>
    <title>Account registration</title>

    </head>
            <body>
                <div class = "errors">
                    ${errorPassword}
                    ${errorUserName}
                    ${errorRepeatPassword}
                    ${errorEmailRegister}
                </div>
                   <div class= "registrationPage">
                               <div class = "legendRegistrationPage">
                                       <h1> Registration <h1>
                               </div>
                               <div class= "registrationText">
                                   <form action="${pageContext.request.contextPath}/register" method = "post" >
                                        <div class="text-field">
                                                 <p>UserName</p>
                                                 <h2><input type="text" name = "username" required = "required"></h2>
                                                 <p>Password</p>
                                                 <input type="password" name = "password" required = "required">
                                                 <p> Confirm Password </p>
                                                 <input type="password" name = "repeatPassword" required = "required">
                                                 <p>  email </p>
                                                 <p><input type="email" name = "email" required = "required"><p>
                                        </div>
                                                  <input type = "submit", value = "Registration">
                                   </form>
                               </div>
                            <button onclick = "location.href = '${pageContext.request.contextPath}/login' " >Back</button>
                   </div>
            </body>
    </html>