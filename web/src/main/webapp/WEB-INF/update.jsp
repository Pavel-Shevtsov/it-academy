<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language = "Java" contentType = "text/html charset = ISO-8859-1" pageEncoding = "ISO-8859-1" %>
    <html>
    <head>
    <meta http-equiv = "Context-Type" context = "text/html charset = ISO-8859-1" >
    <style><%@include file="/WEB-INF/style/styleBody.css"%></style>
    <style><%@include file="/WEB-INF/style/styleUpdatePage.css"%></style>
    <title>Update </title>
    </head>
            <body>
                    <div class= "updatePage">
                        <div class = "legendUpdatePage">
                            <h1>Changing user data <h1>

                        </div>
                            <div class= "updateText">
                                    <form action="${pageContext.request.contextPath}/update" method = "post" >
                                        <div class="text-field">
                                            <p>Old Name</p>
                                            <input class="text-readonly" type = "text" name="oldName" value="${name}" readonly>
                                            <p>New Name</p>
                                            <input type="text" name = "newName" >
                                            <p>Old Password</p>
                                            <input class="text-readonly" type = "text" name="oldPassword" value="${oldPassword}" readonly>
                                            <p>New Password</p>
                                            <input type="password" name = "newPassword" >
                                            <p> Old email </p>
                                            <input class="text-readonly" type = "text" name="oldEmail" value="${email}"readonly>
                                            <p> New Email </p>
                                            <input type="email" name = "newEmail" >
                                        </div>
                                        <input type = "submit", value = "Update">
                                    </form>

                            </div>
                            <button onclick = "location.href = '${pageContext.request.contextPath}/welcome' ">Back </button>
                    </div>
            </body>
    </html>
