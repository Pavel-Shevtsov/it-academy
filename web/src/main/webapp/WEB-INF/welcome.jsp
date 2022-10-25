<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language = "Java" contentType = "text/html charset = ISO-8859-1" pageEncoding = "ISO-8859-1" %>

    <html>
    <head>
    <meta http-equiv = "Context-Type" context = "text/html charset = ISO-8859-1" >

    <title>Welcome</title>

    </head>
            <body>
                ${updatePassword}
                ${deleteUser}
                <fieldset style = "width : 250px">
                        <legend>
                           Welcome
                        </legend>
                    <table>
                        <form action="logout" method = "get">
                            <tr>
                            <td>Welcome    ${name}</td>
                            </tr>
                            <tr>
                                <td><button onclick = "location.href = '${pageContext.request.contextPath}/logout' " >logout
                                </button></td>
                        </form>
                                <td><button onclick = "location.href = '${pageContext.request.contextPath}/users' " >All Users
                                </button></td>
                                <td><button onclick = "location.href = '${pageContext.request.contextPath}/update' " >Update
                                </button></td>
                            </tr>
                    </table>
                </fieldset>
            </body>
    </html>