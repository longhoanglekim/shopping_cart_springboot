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
    <h1>Hello world, ${sessionScope.username}</h1>
    <table class="table table-bordered">
        <thead>
        <tr>
            <th>Name</th>
            <th>Category</th>
            <th>Price</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${productList}" var="product">
            <tr>
                <td>
                    <a href="${pageContext.request.contextPath}/productInfo?id=${product.id}" class="btn btn-success">${product.name}</a>
                </td>
<%--                <td>${product.description}</td>--%>
                <td>${product.category}</td>
                <td>${product.getFormattedPrice()}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <a href="${pageContext.request.contextPath}/addProduct" class="btn btn-success">Add product</a>
</div>
<script src="${pageContext.request.contextPath}/webjars/bootstrap/5.3.3/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/webjars/jquery/3.6.0/jquery.min.js"></script>
</body>
</html>
