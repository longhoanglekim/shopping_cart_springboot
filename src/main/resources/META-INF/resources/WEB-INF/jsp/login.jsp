<%@ taglib prefix="th" uri="http://www.springframework.org/tags/form" %>
<%--<!DOCTYPE html>--%>
<%--<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="https://www.thymeleaf.org">--%>
<%--<head>--%>
<%--    <title>Please Log In</title>--%>
<%--</head>--%>
<%--<body>--%>
<%--<h1>Please Log In</h1>--%>
<%--<div th:if="${param.error}">--%>
<%--    Invalid username and password.</div>--%>
<%--<div th:if="${param.logout}">--%>
<%--    You have been logged out.</div>--%>
<%--<form th:action="@{/login}" method="post">--%>
<%--    <div>--%>
<%--        <input type="text" name="username" placeholder="Username"/>--%>
<%--    </div>--%>
<%--    <div>--%>
<%--        <input type="password" name="password" placeholder="Password"/>--%>
<%--    </div>--%>
<%--    <input type="submit" value="Log in" />--%>
<%--</form>--%>
<%--</body>--%>
<%--</html>--%>
<html>
<head>
    <link href="webjars/bootstrap/5.3.3/css/bootstrap.min.css" rel="stylesheet">
    <title>Login page</title>
</head>
<body>
<div>
    <h1>Please Login</h1>
    <form th:th:action="@{/login}" method="post">
        <table>
            <tr>
                <td>Enter the account:</td>
                <td><input type="text" name="username" required="required" placeholder="Username"></td>
            </tr>
            <tr>
                <td>Enter the password:</td>
                <td><input type="password" name="password" required="required" placeholder="Password"></td>
            </tr>
        </table>
        <input type="submit" value="Login" class="btn btn-success">
    </form>
    ${messageLogin}
</div>
<script src="webjars/bootstrap/5.3.3/js/bootstrap.min.js"></script>
<script src="webjars/jquery/3.6.0/jquery.min.js"></script>
</body>
</html>