<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <title>Welcome to our page</title>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/custom.css"/>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/product.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/product.js"></script>
</head>
<header>
    <jsp:include page="header.jsp" flush="true"/>
</header>
<body style="background-color: grey;">
<div>
        <h1 id="welcome-text">Welcome to our page</h1>
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
        const token = localStorage.getItem('token');
        if (token) {
            fetch('api/jwt/getName', {
                method: 'GET',
                headers: {
                    'Authorization': 'Bearer ' + token,
                    'Content-Type': 'application/json'
                }
            })
                .then(response => response.json())
                .then(data => {
                    // Đặt tên người dùng vào welcome text
                    if (data) {
                        document.getElementById('welcome-text').innerText = 'Welcome ' + data.username;
                    }
                })
                .catch(error => console.error('Error:', error));
        }
    });
</script>
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
