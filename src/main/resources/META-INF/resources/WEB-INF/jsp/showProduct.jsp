<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html>
<head>
    <link href="webjars/bootstrap/5.3.3/css/bootstrap.min.css" rel="stylesheet">
    <title>Show product</title>
</head>
<header>
    <jsp:include page="header.jsp" flush="true"/>
</header>
<body>
<div>
    <h1>Hello world, ${username}</h1>
    <table class="table-bordered">
        <tr>
            <td>Name</td>
            <td>Description</td>
            <td>Category</td>
            <td>Price</td>
        </tr>
        <c:forEach items="${productList}" var="product">
            <tr>
                <td>${product.name}</td>
                <td>${product.description}</td>
                <td>${product.category}</td>
                <td>${product.price}</td>
                <td>
                    <a href="updateProduct?id=${product.id}" class="btn btn-success">Update</a>
                </td>
            </tr>
        </c:forEach>
    </table>
    <a href="addProduct" class="btn btn-success">Add product</a>
</div>
<script src="webjars/bootstrap/5.3.3/js/bootstrap.min.js"></script>
<script src="webjars/jquery/3.6.0/jquery.min.js"></script>
</body>
</html>