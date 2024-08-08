<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <link href="webjars/bootstrap/5.3.3/css/bootstrap.min.css" rel="stylesheet">
    <title>Welcome to our page</title>
</head>
<header>
    <jsp:include page="header.jsp" flush="true"/>
</header>
<body>
<div class="container">
    <c:if test="${not empty username}">
        <h1>Welcome ${username}</h1>
    </c:if>
<%--  Page context to get the actual URL, not the file jsp--%>
    <a href="${pageContext.request.contextPath}/showListProduct/books" class="btn btn-success">Book</a>
</div>
<script src="webjars/bootstrap/5.3.3/js/bootstrap.min.js"></script>
<script src="webjars/jquery/3.6.0/jquery.min.js"></script>
</body>
</html>