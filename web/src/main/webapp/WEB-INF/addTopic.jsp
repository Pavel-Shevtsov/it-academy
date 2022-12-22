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
        <%@include file="/WEB-INF/style/styleTopicCreateBoard.css"%>
        <%@include file="/WEB-INF/style/styleTable.css"%>
    </style>
    <title>Add Topic</title>
    </head>
            <body>
                    <div class="panel">
                        <a class="nameApplication" href = '${pageContext.request.contextPath}/welcome'>T&P</a>
                          <div class="dropDownUser">
                              <button class="dropBtn">${name}</button>
                                  <div class="dropDownUser-content" style="right:20;">
                                      <p><a href = '${pageContext.request.contextPath}/welcome'>Welcome</a></p>
                                      <p><a href = '${pageContext.request.contextPath}/update'>Update</a></p>
                                      <c:if test="${role.equals('Admin')}">
                                      <p><a href ='${pageContext.request.contextPath}/users'>All Users</a></p>
                                      <p><a href ='${pageContext.request.contextPath}/createTopic'>Create Topic</a></p>
                                      </c:if>
                                      <c:if test="${role.equals('User')}">
                                      <p><a href ='${pageContext.request.contextPath}/allFreeTopics'>Add Topic</a></p>
                                      </c:if>
                                      <p><a href = '${pageContext.request.contextPath}/logout' >Logout</a></p>
                                  </div>
                          </div>
                    </div>


                        <table>
                            <thead>
                                <tr>
                                  <th scope="col">Name</th>
                                  <th scope="col">Action</th>
                                </tr>
                            </thead>
                                <tbody>
                                    <c:forEach var="topic" items="${freeTopics}">
                                        <tr>
                                            <td><c:out value ="${topic.name}"/></td>
                                            <td><a class="action" href = "${pageContext.request.contextPath}/addTopic?id=${topic.id}" > Add</td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                        </table>


            </body>
    </html>