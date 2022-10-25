<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language = "Java" contentType = "text/html charset = ISO-8859-1" pageEncoding = "ISO-8859-1" %>

    <html>
    <head>
    <meta http-equiv = "Context-Type" context = "text/html charset = ISO-8859-1" >

    <title>Update </title>

    </head>
            <body>
                ${updatePassword}
                <fieldset style = "width : 250px">
                    <legend>
                       Update
                    </legend>
                <form action="${pageContext.request.contextPath}/update" method = "post" >
                    <table>
                        <tr>
                             <td>UserName </td>
                             <td><input type="text" name = "name" required = "required"value = ${name}><td>
                        </tr>

                        <tr>
                             <td> Old Password </td>
                             <td><input type="password" name = "OldPassword" required = "required" value = ${oldPassword}><td>
                        </tr>
                        <tr>
                             <td> New Password </td>
                             <td><input type="password" name = "newPassword" required = "required"><td>
                        </tr>
                        <tr>
                            <td>  email </td>
                            <td><input type="email" name = "email"  required = "required" value = ${email}><td>
                        </tr>

                        <tr>
                            <td> <input type = "submit", value = "Update"></td>

                </form>
                            <td><button onclick = "location.href = '${pageContext.request.contextPath}/welcome' " >Back
                            </button></td>
                        </tr>
                    </table>
                </fieldset>
            </body>
    </html>
