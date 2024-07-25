<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
    <head>
        <link href="webjars/bootstrap/5.3.3/css/bootstrap.min.css" rel="stylesheet">
        <title>Show bro</title>
    </head>
    <header>
        <c:choose>
            <c:when test="${not empty username}">
                <span>Welcome, ${username}</span>
            </c:when>
            <c:otherwise>
                <a href="login">Login</a>
            </c:otherwise>
        </c:choose>
    </header>
</html>