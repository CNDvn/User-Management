<%-- 
    Document   : register
    Created on : May 30, 2021, 5:42:44 AM
    Author     : CND
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Register Page</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <h1>Register</h1>
    <c:set var="accountErr" value="${requestScope.AccountErr}"/>
    <form action="RegisterServlet" enctype='multipart/form-data' method="POST" >
        <table>
            <tbody>
                <tr>
                    <td>Username:</td>
                    <td>
                        <input type="text" name="txtUsername" value="<c:if test='${empty accountErr.usernameErr}'>${param.txtUsername}</c:if>" /><br/>
                        <font color="green">* 6 - 30 character</font>
                    </td>
                    <td>
            <c:if test="${not empty accountErr.usernameErr}">
                <font color="red">${accountErr.usernameErr}</font>
            </c:if>
            </td>

            </tr>
            <tr>
                <td>Password:</td>
                <td>
                    <input type="password" name="txtPassword" value="" /><br/>
                    <font color="green">* At least 6 character</font>
                </td>
                <td>
            <c:if test="${not empty accountErr.passwordErr}">
                <font color="red">${accountErr.passwordErr}</font>
            </c:if>
            </td>
            </tr>
            <tr>
                <td>Re-Password:</td>
                <td>
                    <input type="password" name="txtRe-password" value="" /><br/>
                </td>
                <td>
            <c:if test="${not empty accountErr.rePasswordErr}">
                <font color="red">${accountErr.rePasswordErr}</font>
            </c:if>
            </td>
            </tr>
            <tr>
                <td>Fullname:</td>
                <td>
                    <input type="text" name="txtFullname" value="<c:if test='${empty accountErr.fullnameErr}'>${param.txtFullname}</c:if>" /><br/>
                    <font color="green">* limit 50 character</font>
                </td>
                <td>
            <c:if test="${not empty accountErr.fullnameErr}">
                <font color="red">${accountErr.fullnameErr}</font>
            </c:if>
            </td>
            </tr>
            <tr>
                <td>Email:</td>
                <td>
                    <input type="text" name="txtEmail" value="<c:if test='${empty accountErr.emailErr}'>${param.txtEmail}</c:if>" /><br/>
                    <font color="green">* 6 - 40 character</font>
                </td>
                <td>
            <c:if test="${not empty accountErr.emailErr}">
                <font color="red">${accountErr.emailErr}</font>
            </c:if>
            </td>
            </tr>
            <tr>
                <td>Phone:</td>
                <td>
                    <input type="tel" name="txtPhone" value="<c:if test='${empty accountErr.phoneErr}'>${param.txtPhone}</c:if>" /><br/>
                    <font color="green">* At least 10 character</font>
                </td>
                <td>
            <c:if test="${not empty accountErr.phoneErr}">
                <font color="red">${accountErr.phoneErr}</font>
            </c:if>
            </td>
            </tr>
            <tr>
                <td>Photo</td>
                <td>
                    <input type="file" name="photo"><br/>
                    <font color="green">* just file jpg or png extension</font>
                </td>
                <td>
            <c:if test="${not empty accountErr.photoErr}">
                <font color="red">${accountErr.photoErr}</font>
            </c:if> 
            </td>
            </tr>
            <tr>
                <td><input type="submit" value="register" name="btnAction" /> </td>
                <td><input type="reset" value="Reset" /> </td>
            </tr>
            </tbody>
        </table>

    </form>
</body>
</html>