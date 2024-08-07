<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Logout Confirmation</title>
</head>
<body>
<h1>Are you sure you want to logout?</h1>
<form:form method="post" >
    <button type="submit">Yes, Logout</button>
    <a href="${pageContext.request.contextPath}/">No, take me back</a>
</form:form>
</body>
</html>