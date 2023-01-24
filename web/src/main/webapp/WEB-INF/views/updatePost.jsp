<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language = "Java" contentType = "text/html; charset = ISO-8859-1" pageEncoding = "ISO-8859-1" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
    <html>
    <head>
    <meta http-equiv = "Context-Type" context = "text/html charset = ISO-8859-1" >
    <style>
        <%@include file="/WEB-INF/style/styleBody.css"%>
        <%@include file="/WEB-INF/style/stylePanel.css"%>
        <%@include file="/WEB-INF/style/styleDropDownUser.css"%>
        <%@include file="/WEB-INF/style/postPage.css"%>
        <%@include file="/WEB-INF/style/photoViews.css"%>
    </style>
    <title>Add Topic</title>
    </head>
            <body>
            <div class= "message">
            ${updatePost}
            </div>
                    <div class="panel">
                        <a class="nameApplication" href = '${pageContext.request.contextPath}/welcome'>T&P</a>
                          <div class="dropDownUser">
                              <button class="dropBtn"> <img src="${pageContext.request.contextPath}/add/imageOnPage"  class = "photoViews"/> ${userName}</button>
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
                                      <p><a href = '${pageContext.request.contextPath}/user/logout' >Logout</a></p>
                                  </div>
                          </div>
                    </div>

                     <form action="${pageContext.request.contextPath}/post/update" method = "post" >
                        <div class="addPostPage">
                            <h1>Update post </h1>
                            	<h3> New name post </h2>
                            	    <input type="text" class ="namePost" name="newName" value = "${postForm.name}" >
                            	<h3>New text </h2>

                            	    <textarea name = "newText" >${postForm.text} </textarea>
                            	<input type = "submit", value = "Update">
                        </div>
                     </form>



            </body>
    </html>