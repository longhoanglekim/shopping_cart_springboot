<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <link href="webjars/bootstrap/5.3.3/css/bootstrap.min.css" rel="stylesheet">
    <title>Welcome to our page</title>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/custom.css"/>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/product.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/product.js"></script>
</head>
<header>
    <jsp:include page="header.jsp" flush="true"/>
</header>
<body style="background-color: lightgrey;">
<div class="container">
        <c:if test="${not empty username}">
            <h1>Welcome ${username}</h1>
        </c:if>
        <div>
            <c:forEach items="${categories}" var="category">
                <div>
                    <div>
                        <h3>${category}</h3>
                        <div id="productContainer-${category}" class="product-container">
                            <!-- Products will be loaded here -->
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
</div>
<script src="webjars/bootstrap/5.3.3/js/bootstrap.min.js"></script>
<script src="webjars/jquery/3.6.0/jquery.min.js"></script>

<script>
    document.addEventListener("DOMContentLoaded", function () {
        const categories = [
            <c:forEach items="${categories}" var="category">
            '${category}'<c:if test="${!status.last}">, </c:if>
            </c:forEach>
        ];
        categories.forEach(category => {
            getProductListByCategory(category);
        });
    });
</script>
</body>
</html>
