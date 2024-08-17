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
            <th>Description</th>
            <th>Category</th>
            <th>Price</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <th>${product.name}</th>
            <th>${product.description}</th>
            <th>${product.category}</th>
            <th>${product.getFormattedPrice()} VND</th>
            <th>
                <a href="${pageContext.request.contextPath}/updateProduct?id=${product.id}" class="btn btn-success">Update</a>
                <form action="${pageContext.request.contextPath}/addProductToCart" method="post">
                    <input type="hidden" name="id" value="${product.id}">
                    <button type="submit" class="btn btn-success">Add to cart</button>
                </form>

            </th>
        </tr>
        </tbody>
    </table>

</div>
<script src="${pageContext.request.contextPath}/webjars/bootstrap/5.3.3/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/webjars/jquery/3.6.0/jquery.min.js"></script>
</body>
</html>
