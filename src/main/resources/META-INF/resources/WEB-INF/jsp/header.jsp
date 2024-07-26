<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <link href="webjars/bootstrap/5.3.3/css/bootstrap.min.css" rel="stylesheet">
    <title>header</title>
    <style>
    </style>
</head>
<body>
<nav class="navbar navbar-expand-md navbar-light bg-light mb-3 p-1">
    <a class="navbar-brand m-1" href="https://courses.in28minutes.com">in28Minutes</a>
    <div class="collapse navbar-collapse">
        <ul class="navbar-nav">
            <li class="nav-item"><a class="nav-link" href="welcome">Home</a></li>
            <li class="nav-item"><a class="nav-link" href="showProduct?category=all">All products</a></li>
        </ul>
    </div>
    <div class="collapse navbar-collapse">
        <c:choose>
            <c:when test="${not empty username}">
                <ul class="navbar-nav">
                    <li class="nav-item"><a class="nav-link" href="welcome">${username}</a></li>
                    <li class="nav-item"><a class="nav-link" href="logout">Logout</a></li>
                </ul>
            </c:when>
            <c:otherwise>
                <ul class="navbar-nav">
                    <a href="login" class="nav-link">Login</a>
                </ul>
            </c:otherwise>
        </c:choose>
    </div>

</nav>
<script src="webjars/bootstrap/5.3.3/js/bootstrap.min.js"></script>
<script src="webjars/jquery/3.6.0/jquery.min.js"></script>
</body>
</html>
