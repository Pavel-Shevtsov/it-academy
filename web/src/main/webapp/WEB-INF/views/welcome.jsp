<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language = "Java" contentType = "text/html; charset = ISO-8859-1" pageEncoding = "ISO-8859-1" %>

    <html>
    <head>
    <meta http-equiv = "Context-Type" context = "text/html charset = ISO-8859-1" >
    <style>
        <%@include file="/WEB-INF/style/styleBody.css"%>
        <%@include file="/WEB-INF/style/styleWelcomePage.css"%>
        <%@include file="/WEB-INF/style/stylePanel.css"%>
        <%@include file="/WEB-INF/style/styleDropDownUser.css"%>
        <%@include file="/WEB-INF/style/styleTable.css"%>
    </style>
    <title>Welcome</title>
    </head>
            <body>

                    <div class="panel">
                        <a class="nameApplication" href = '${pageContext.request.contextPath}/welcome'>T&P</a>
                           <div class="dropDownUser">
                               <button class="dropBtn">${name}</button>
                                   <div class="dropDownUser-content" style="right:20;">
                                       <p><a href = '${pageContext.request.contextPath}/welcome'>Welcome</a></p>
                                       <p><a href = '${pageContext.request.contextPath}/user/update?id=${id}'>Update</a></p>
                                       <c:if test="${role.equals('Admin')}">
                                       <p><a href ='${pageContext.request.contextPath}/user/users'>All Users</a></p>
                                       <p><a href ='${pageContext.request.contextPath}/topic/create'>Create Topic</a></p>
                                       </c:if>
                                       <c:if test="${role.equals('User')}">
                                       <p><a href ='${pageContext.request.contextPath}/topic/add'>Add Topic</a></p>                                       </c:if>
                                       <p><a href = '${pageContext.request.contextPath}/user/logout' >Logout</a></p>
                                   </div>
                           </div>
                    </div>
                         <div class= "message">
                              ${userOtherUpdate}
                              ${userUpdate}
                              ${updatePassword}
                              ${updateUserPassword}
                              ${userEmailAlreadyRegistered}
                              ${userNameAlreadyRegistered}
                              ${deleteUser}
                              ${deleteTopic}
                              ${createTopicError}
                              ${createTopicSuccessfully}
                              ${addTopic}
                              ${deletePost}
                              ${updatePost}
                          </div>
                                 <div class ="welcomeMessage">
                                      <h1>Welcome ${name}</h1>
                                      <p><img src="${pageContext.request.contextPath}/user/imageOnWelcomePage" width="100"/></p>
                                 </div>
                <table>
                    <c:if test="${role.equals('Admin')}">
                        <thead>
                            <th >All topics</th>
                        </thead>
                            <tbody>
                                <c:forEach var="topic" items="${allTopics}">
                                    <tr>
                                        <td><c:out value ="${topic.name}"/></td>
                                    </tr>
                            </tbody>
                                </c:forEach>
                    </c:if>
                                <c:if test="${role.equals('User')}">
                                      <form action="${pageContext.request.contextPath}/welcome" method = "get" >
                                          <thead>
                                              <th >All topics</th>
                                              <th >Action</th>
                                          </thead>
                                              <tbody>
                                                  <c:forEach var="topic" items="${userTopics}">
                                                      <tr>
                                                          <td><c:out value ="${topic.name}"/></td>
                                                          <td><p><a class="action" href ="${pageContext.request.contextPath}/topic/delete?id=${topic.id}">Delete</a></p>
                                                          <p><a class="action" href ="${pageContext.request.contextPath}/post/posts?idTopic=${topic.id}">Go to</a></p></td>
                                                      </tr>
                                              </tbody>
                                                  </c:forEach>
                                      </form>
                                </c:if>
                </table>
            </body>
    </html>