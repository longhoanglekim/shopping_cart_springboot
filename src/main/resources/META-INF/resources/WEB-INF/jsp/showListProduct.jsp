<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html>
<head>
    <link href="${pageContext.request.contextPath}/webjars/bootstrap/5.3.3/css/bootstrap.min.css" rel="stylesheet">
    <title>Show Product</title>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/product.css"/>
</head>
<header>
    <jsp:include page="header.jsp" flush="true"/>
</header>
<body>
<div class="container">
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
                    <button class="button-product" id="${product.id}" >${product.name}</button>
                </td>
                <td>${product.category}</td>
                <td>${product.getFormattedPrice()} <span>VND</span></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
<%--    <a href="${pageContext.request.contextPath}/addProduct" class="btn btn-success">Add product</a>--%>
</div>
<script src="${pageContext.request.contextPath}/webjars/bootstrap/5.3.3/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/webjars/jquery/3.6.0/jquery.min.js"></script>
<script>
    document.addEventListener('DOMContentLoaded', function () {
        console.log('DOM loaded');
        const buttons = document.querySelectorAll('.button-product');
        buttons.forEach(button => {
            button.addEventListener('click', function () {
                const id = button.id;
                window.location.href = '${pageContext.request.contextPath}/productInfo?id=' + id;
            });
        });
    });
</script>
</body>
</html>
