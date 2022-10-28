<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language = "Java" contentType = "text/html charset = ISO-8859-1" pageEncoding = "ISO-8859-1" %>

    <html>
    <head>
    <meta http-equiv = "Context-Type" context = "text/html charset = ISO-8859-1" >

    <title>Account registration</title>

    </head>
            <body>
                <fieldset style = "width : 250px">

                 <legend>
                    Account registration
                 </legend>
                    <table>
                        <form action="${pageContext.request.contextPath}/register" method = "post" >
                            <tr>
                                    <td>UserName </td>
                                    <td><input type="text" name = "username" required = "required"><td>
                            </tr>
                            <tr>
                                    <td>  Password </td>
                                    <td><input type="password" name = "password" required = "required"><td>
                            </tr>

                            <tr>
                                     <td>  Repeat password </td>
                                     <td><input type="password" name = "repeatPassword" required = "required"><td>
                            </tr>
                            <tr>
                                      <td>  email </td>
                                      <td><input type="email" name = "email" required = "required"><td>
                            </tr>
                            <tr>
                                        <td><input type = "submit", value = "Register"></td>
                        </form>
                            <td><button onclick = "location.href = '${pageContext.request.contextPath}/login' " >Back
                            </button></td>
                            </tr>
                    </table>
                </fieldset>
            </body>
    </html>