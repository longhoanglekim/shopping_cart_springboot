<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html>
<head>
    <link href="${pageContext.request.contextPath}/webjars/bootstrap/5.3.3/css/bootstrap.min.css" rel="stylesheet">
    <title>Show Product</title>
</head>
<header>
    <jsp:include page="header.jsp" flush="true"/>
</header>
<body>
<div class="container">
    <table class="table able-bordered">
        <tr>
            <td>Name</td>
            <td>Price</td>
        </tr>
        <c:forEach items="${productList}" var="product">
            <tr>
                <td>${product.name}</td>
                <td>${product.price}</td>
            </tr>
        </c:forEach>

    </table>
</div>
<script src="${pageContext.request.contextPath}/webjars/bootstrap/5.3.3/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/webjars/jquery/3.6.0/jquery.min.js"></script>
</body>
</html>
