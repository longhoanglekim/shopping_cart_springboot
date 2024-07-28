<html>
<head>
    <title>Logout Confirmation</title>
</head>
<body>
<h1>Are you sure you want to logout?</h1>
<form th:action="@{/perform-logout}" method="post">
    <button type="submit">Yes, Logout</button>
</form>
<%--<a href="${pageContext.request.contextPath}/">No, take me back</a>--%>
</body>
</html>