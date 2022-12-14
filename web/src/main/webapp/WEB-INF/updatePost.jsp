<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language = "Java" contentType = "text/html; charset = ISO-8859-1" pageEncoding = "ISO-8859-1" %>

    <html>
    <head>
    <meta http-equiv = "Context-Type" context = "text/html charset = ISO-8859-1" >
    <style>
        <%@include file="/WEB-INF/style/styleBody.css"%>
        <%@include file="/WEB-INF/style/stylePanel.css"%>
        <%@include file="/WEB-INF/style/styleDropDownUser.css"%>
        <%@include file="/WEB-INF/style/postPage.css"%>
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


                     <form action="${pageContext.request.contextPath}/updatePost" method = "post" >
                        <div class="addPostPage">
                        <h1>Add Post</h1>
                            	<h3>Name post </h2>
                            	<input type="text" class ="namePost" name="newPostName" value = ${postName} >
                            	<h3>Text </h2>
                            	<textarea name = "newPostText"  >   ${postText}</textarea>
                            	<input type = "submit", value = "Update">
                        </div>
                     </form>



            </body>
    </html>