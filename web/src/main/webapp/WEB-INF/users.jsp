<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language = "Java" contentType = "text/html charset = ISO-8859-1" pageEncoding = "ISO-8859-1" %>

    <html>
    <head>
    <meta http-equiv = "Context-Type" context = "text/html charset = ISO-8859-1" >

    <title>All Users</title>

    </head>
    <style>
    td {
      padding: 5px;
    }
    </style>
            <body>
                ${updateUserPassword}
                <fieldset style =" width : 250px ">
                    <legend>
                       All Users
                    </legend>
                    <form action="${pageContext.request.contextPath}/update" method = "get" >
                        <table >
                            <c:forEach var="user" items="${otherUsers}">
                                <tr>
                                    <td><c:out value ="${user}"/><td>


                                <td><a href = "${pageContext.request.contextPath}/update?id=${user.id}" > Update</td>
                                <td><a href = "${pageContext.request.contextPath}/delete?id=${user.id}" > Delete</td>
                                 </tr>
                            </c:forEach>
                        </table>
                    </form>
                        <td><button onclick = "location.href = '${pageContext.request.contextPath}/welcome' " >Back
                        </button></td>
                </fieldset>
            </body>
    </html>