<%@ taglib prefix="th" uri="http://www.springframework.org/tags/form" %>
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
</div>
<script src="webjars/bootstrap/5.3.3/js/bootstrap.min.js"></script>
<script src="webjars/jquery/3.6.0/jquery.min.js"></script>
</body>
</html>