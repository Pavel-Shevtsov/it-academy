x<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language = "Java" contentType = "text/html; charset = ISO-8859-1" pageEncoding = "ISO-8859-1" %>

    <html>
    <head>
    <meta http-equiv = "Context-Type" context = "text/html charset = ISO-8859-1" >
    <style>
        <%@include file="/WEB-INF/style/styleBody.css"%>
        <%@include file="/WEB-INF/style/stylePanel.css"%>
        <%@include file="/WEB-INF/style/styleDropDownUser.css"%>
        <%@include file="/WEB-INF/style/myPostsPage.css"%>
    </style>
    <title>My posts</title>
    </head>
            <body>
                    <div class="panel">
                        <a class="nameApplication" href = '${pageContext.request.contextPath}/welcome'>T&P</a>
                        <a class="addPost" href = "${pageContext.request.contextPath}/post/add?idTopic=${topicId}">Add Post</a>
                           <div class="dropDownUser">
                               <button class="dropBtn">${name}</button>
                                   <div class="dropDownUser-content" style="right:20;">
                                       <p><a href = '${pageContext.request.contextPath}/welcome'>Welcome</a></p>
                                       <p><a href = '${pageContext.request.contextPath}/user/update'>Update</a></p>
                                       <c:if test="${role.equals('Admin')}">
                                       <p><a href ='${pageContext.request.contextPath}/user/users'>All Users</a></p>
                                       <p><a href ='${pageContext.request.contextPath}/topic/create'>Create Topic</a></p>
                                       </c:if>
                                       <c:if test="${role.equals('User')}">
                                       <p><a href ='${pageContext.request.contextPath}/topic/add'>Add Topic</a></p>
                                       <p><a href = '${pageContext.request.contextPath}/user/logout' >Logout</a></p>
                                        </c:if>
                                   </div>
                           </div>
                    </div>
                           <div class= "message">
                               ${addPost}
                           </div>
                                            <h2>My posts ${topicName}</h2>
                                            <c:forEach var="post" items="${posts}">
                                                <div class="myPostPage">
                                                    <p class="namePost">Name post: <c:out value ="${post.name}"/></p>
                                                    <a class="modifyLink" href = "${pageContext.request.contextPath}/post/update?idPost=${post.id}"> Update </a>
                                                    <a class="modifyLink" href = "${pageContext.request.contextPath}/post/delete?idPost=${post.id}" > Delete </a>
                                                    <p class="textPost">Text post: <c:out value ="${post.text}"/></p>
                                                </div>

                                            </c:forEach>
            </body>
    </html>