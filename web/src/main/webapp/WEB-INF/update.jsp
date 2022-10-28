<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language = "Java" contentType = "text/html charset = ISO-8859-1" pageEncoding = "ISO-8859-1" %>

    <html>
    <head>
    <meta http-equiv = "Context-Type" context = "text/html charset = ISO-8859-1" >

    <title>Update </title>

    </head>
            <body>

                <fieldset style = "width : 350px">
                    <legend>
                       Update
                    </legend>
                    <table>
                        <form action="${pageContext.request.contextPath}/update" method = "post" >
                            <tr>
                                 <td>Old UserName </td>
                                 <td><input type = "text" name="oldName" value="${name}" readonly><td>
                            </tr>
                            <tr>
                                 <td>New UserName </td>
                                 <td><input type="text" name = "newName"  ><td>
                            </tr>

                            <tr>
                                 <td> Old Password </td>
                                 <td><input type = "text" name="oldPassword" value="${oldPassword}" readonly><td>
                            </tr>
                            <tr>
                                 <td> New Password </td>
                                 <td><input type="password" name = "newPassword"><td>
                            </tr>
                            <tr>
                                <td> Old Email </td>
                                <td><input type = "text" name="oldEmail" value="${email}"readonly><td>
                            </tr>
                            <tr>
                                <td> New Email </td>
                                <td><input type="email" name = "newEmail" ><td>

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
