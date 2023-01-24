<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language = "Java" contentType = "text/html charset = ISO-8859-1" pageEncoding = "ISO-8859-1" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
    <html>
    <head>
    <meta http-equiv = "Context-Type" context = "text/html charset = ISO-8859-1" >
    <style><%@include file="/WEB-INF/style/styleBody.css"%></style>
    <style><%@include file="/WEB-INF/style/styleWelcomePage.css"%></style>
    <style><%@include file="/WEB-INF/style/stylePanel.css"%></style>
    <style><%@include file="/WEB-INF/style/styleDropDownUser.css"%></style>
    <style><%@include file="/WEB-INF/style/styleTable.css"%></style>
    <style><%@include file="/WEB-INF/style/photoViews.css"%></style>
    <title>All Users</title>

    </head>
            <body>
            <div class="panel">
                 <a class="nameApplication" href = '${pageContext.request.contextPath}/welcome'>T&P</a>
                    <div class="dropdownUser">
                    <button class="dropBtn"><img src="${pageContext.request.contextPath}/add/imageOnPage"  class = "photoViews"/> ${userName}</button>
                        <div class="dropdownUser-content" style="right:20;" >
                            <p><a href = '${pageContext.request.contextPath}/welcome'>Welcome</a></p>
                            <p><a href = '${pageContext.request.contextPath}/user/update?id=${userId}'>Update</a></p>
                            <p><a href ='${pageContext.request.contextPath}/user/users'>All Users</a></p>
                            <p><a href ='${pageContext.request.contextPath}/topic/create'>Create Topic</a></p>
                            <p><a href = '${pageContext.request.contextPath}/user/logout' >Logout</a></p>
                        </div>
                    </div>
            </div>

                <div class= "message">
                    ${updateUserPassword}
                </div>

                    <form action="${pageContext.request.contextPath}/user/update" method = "get" >
                        <table>
                            <thead>
                                <tr>
                                  <th scope="col">Name</th>
                                  <th scope="col">Password</th>
                                  <th scope="col">Email</th>
                                  <th scope="col">Role</th>
                                  <th scope="col">Action</th>
                                </tr>
                            </thead>
                                    <tbody>
                                      <c:forEach var="user" items="${otherUsers}">
                                          <tr>
                                              <td><c:out value ="${user.userName}"/></td>
                                              <td><c:out value ="${user.password}"/></td>
                                              <td><c:out value ="${user.email}"/></td>
                                              <td><c:out value ="${user.role}"/></td>
                                          <td><a class="action" href = "${pageContext.request.contextPath}/user/update?id=${user.id}" > Update
                                          <a class="action" href = "${pageContext.request.contextPath}/user/delete?id=${user.id}" >   Delete</td>
                                           </tr>
                                      </c:forEach>
                                    </tbody>
                        </table>
                    </form>
            </body>
    </html>