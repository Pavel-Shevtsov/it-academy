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
    </style>
    <title>Create Topic</title>
    </head>
            <body>
                <div class= "message">
                    ${createTopicError}
                </div>


                    <div class="panel">
                        <a class="nameApplication" href = '${pageContext.request.contextPath}/welcome'>T&P</a>
                                         <div class="dropDownUser">
                                             <button class="dropBtn">${userName}</button>
                                                 <div class="dropDownUser-content" style="right:20;">
                                                     <p><a href = '${pageContext.request.contextPath}/welcome'>Welcome</a></p>
                                                     <p><a href = '${pageContext.request.contextPath}/user/update?id=${userId}'>Update</a></p>
                                                     <c:if test="${role.equals('Admin')}">
                                                     <p><a href ='${pageContext.request.contextPath}/user/users'>All Users</a></p>
                                                     <p><a href ='${pageContext.request.contextPath}/topic/create'>Create Topic</a></p>
                                                     </c:if>
                                                     <c:if test="${role.equals('User')}">
                                                     <p><a href ='${pageContext.request.contextPath}/topic/allFree'>Add Topic</a></p>
                                                     </c:if>
                                                     <p><a href = '${pageContext.request.contextPath}/user/logout' >Logout</a></p></div>
                                                 </div>
                                         </div>
                    </div>
                                        <form action="${pageContext.request.contextPath}/topic/create" method = "post" >

                                            <div class = "topicBoard">
                                             <h2>Create Topic</h2>
                                            <p><input type = "text" name ="topicName" required = "required" placeholder = "Enter topic name..."></p>
                                                <div class = "submit">
                                                <p><input type = "submit", value = "Create"></p>
                                                </div>
                                            </div>
                                        </form>


            </body>
    </html>