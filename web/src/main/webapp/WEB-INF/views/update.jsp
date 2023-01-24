<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language = "Java" contentType = "text/html charset = ISO-8859-1" pageEncoding = "ISO-8859-1" %>
    <html>
    <head>
    <meta http-equiv = "Context-Type" context = "text/html charset = ISO-8859-1" >
    <style><%@include file="/WEB-INF/style/styleBody.css"%></style>
    <style><%@include file="/WEB-INF/style/styleUpdatePage.css"%></style>
    <style><%@include file="/WEB-INF/style/viewsPhotoOnUpdateAndRegistrationPage.css"%></style>
    <title>Update </title>
    </head>
            <body>
             <div class= "message">
                ${userEmailAlreadyRegistered}
                ${userNameAlreadyRegistered}
                ${updatePassword}
                ${updateUserPassword}
             </div>
                    <div class= "updatePage">
                        <div class = "legendUpdatePage">
                            <h1>Changing user data <h1>

                        </div>
                            <div class= "updateText">
                            <form action="${pageContext.request.contextPath}/add/uploadPhoto" enctype="multipart/form-data" method="post">

                                  <p>Choose your photo</p>
                                  <img src="${pageContext.request.contextPath}/add/viewImage" class ="viewsOnUpdateAndRegistrationPages"/>
                                  <p><input type="file" name="fileData" >
                                  <input type="submit" value="Download"></p>
                            </form>
                                    <form action="${pageContext.request.contextPath}/user/update" method = "post" >
                                        <div class="text-field">
                                            <p>ID</p>
                                            <input class="text-readonly"type = "text" name = "id" value ="${updateUserForm.id}"readonly>
                                            <p>Old Name</p>
                                            <input class="text-readonly" type = "text" name="username" value="${updateUserForm.username}" readonly>
                                            <p>New Name</p>
                                            <input type="text" name = "newUsername" >
                                            <p>New Password</p>
                                            <input type="password" name = "newPassword" >
                                            <p> Old email </p>
                                            <input class="text-readonly" type = "text" name="email" value="${updateUserForm.email}"readonly>
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
