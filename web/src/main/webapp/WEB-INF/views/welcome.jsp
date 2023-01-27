<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language = "Java" contentType = "text/html; charset = ISO-8859-1" pageEncoding = "UTF-8" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
    <html>
    <head>
    <meta http-equiv = "Context-Type" context = "text/html charset = ISO-8859-1" >
    <style>
        <%@include file="/WEB-INF/style/styleBody.css"%>
        <%@include file="/WEB-INF/style/styleWelcomePage.css"%>
        <%@include file="/WEB-INF/style/stylePanel.css"%>
        <%@include file="/WEB-INF/style/styleDropDownUser.css"%>
        <%@include file="/WEB-INF/style/styleTable.css"%>
        <%@include file="/WEB-INF/style/photoViews.css"%>
    </style>
    <title>Welcome</title>
    </head>
            <body>

                    <div class="panel">
                        <a class="nameApplication" href = '${pageContext.request.contextPath}/welcome'>T&P</a>
                           <div class="dropDownUser">
                               <button class="dropBtn"> <img src="${pageContext.request.contextPath}/add/imageOnPage"  class = "photoViews"/> ${userName}</button>
                                   <div class="dropDownUser-content" style="right:20;">
                                       <p><a href = '${pageContext.request.contextPath}/welcome'>Welcome</a></p>
                                       <p><a href = '${pageContext.request.contextPath}/user/update?id=${userId}'>Update</a></p>
                                       <security:authorize access = "hasRole('ROLE_Admin')">
                                       <p><a href ='${pageContext.request.contextPath}/user/users'>All Users</a></p>
                                       <p><a href ='${pageContext.request.contextPath}/topic/create'>Create Topic</a></p>
                                       </security:authorize>

                                        <security:authorize access = "hasRole('ROLE_User')">
                                        <p><a href ='${pageContext.request.contextPath}/topic/allFree'>Add Topic</a></p>
                                         </security:authorize>
                                       <p><a href = '${pageContext.request.contextPath}/user/logout' >Logout</a></p>
                                   </div>
                           </div>
                    </div>
                         <div class= "message">
                              ${userUpdate}
                              ${deleteUser}
                              ${deleteTopic}
                              ${createTopicSuccessfully}
                              ${addTopic}
                              ${deletePost}
                          </div>
                                 <div class ="welcomeMessage">
                                      <c:if test = "${visitCounter<1}">
                                       <p><img src="${pageContext.request.contextPath}/add/imageOnPage" width="200"/></p>
                                      <h1>Welcome ${userName}</h1>
                                      </c:if>
                                 </div>
                <table>
                    <security:authorize access = "hasRole('ROLE_Admin')">
                        <thead>
                            <th >All topics</th>
                        </thead>
                            <tbody>
                                <c:forEach var="topic" items="${allTopics}">
                                    <tr>
                                        <td><c:out value ="${topic.getName()}"/></td>
                                    </tr>
                            </tbody>
                                </c:forEach>
                   </security:authorize>
                                <security:authorize access = "hasRole('ROLE_User')">
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
                                                          <p><a class="action" href ="${pageContext.request.contextPath}/post/posts?id=${topic.id}">Go to</a></p></td>
                                                      </tr>
                                              </tbody>
                                                  </c:forEach>
                                      </form>
                                </security:authorize>
                </table>
            </body>
    </html>