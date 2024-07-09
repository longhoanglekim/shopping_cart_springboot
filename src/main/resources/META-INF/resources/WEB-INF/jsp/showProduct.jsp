<<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html>
<head>
    <title>Hello bro</title>
</head>
<body>
<div>
    <h1>Hello world, ${username}</h1>
    <table border="1">
        <tr>
            <td>Name</td>
            <td>Description</td>
            <td>Price</td>
        </tr>
        <c:forEach items="${productList}" var="product">
            <tr>
                <td>${product.name}</td>
                <td>${product.description}</td>
                <td>${product.price}</td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>