<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language = "Java" contentType = "text/html charset = ISO-8859-1" pageEncoding = "ISO-8859-1" %>

    <html>
    <head>
    <meta http-equiv = "Context-Type" context = "text/html charset = ISO-8859-1" >

    <title>Login to application</title>

    </head>
            <body>
                ${createNewUser}
                <fieldset style = "width : 250px">
                        <legend>
                           Login to app
                        </legend>
                    <table>
                        <form action="${pageContext.request.contextPath}/login" method = "post" >
                            <tr>
                                 <td>UserName </td>
                                 <td><input type="text" name = "username" required = "required"><td>
                            </tr>
                            <tr>
                                 <td>  Password </td>
                                 <td><input type="password" name = "password" required = "required"><td>
                            </tr>
                            <tr>
                                <td><input type = "submit", value = "Login"></td>
                        </form>
                                <td><button onclick = "location.href = '${pageContext.request.contextPath}/register' " >Register
                                </button></td>
                            </tr>
                    </table>
                </fieldset>
            </body>
    </html>
