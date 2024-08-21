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
    <a class="navbar-brand m-1" href="http://localhost:8080/welcome">JusEnuf</a>
    <div class="collapse navbar-collapse">
        <ul class="navbar-nav">
            <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/welcome">Home</a></li>
            <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/showListProduct/all">All products</a></li>
        </ul>
    </div>
    <div class="collapse navbar-collapse">
        <c:choose>
            <c:when test="${not empty username}">
                <ul class="navbar-nav">
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/profile">${username}</a></li>
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/shopping_cart">
                        <img src="${pageContext.request.contextPath}/image/shopping-cart.png" alt="Shopping cart" width="35" height="30">
                    </a></li>
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/logout">Logout</a></li>
                </ul>
            </c:when>
            <c:otherwise>
                <ul class="navbar-nav">
                    <li><a href="login" class="nav-link">Login</a></li>
                    <li><a href="register" class="nav-link">Register</a></li>
                </ul>
            </c:otherwise>
        </c:choose>
    </div>

</nav>
<script src="webjars/bootstrap/5.3.3/js/bootstrap.min.js"></script>
<script src="webjars/jquery/3.6.0/jquery.min.js"></script>
</body>
</html>
