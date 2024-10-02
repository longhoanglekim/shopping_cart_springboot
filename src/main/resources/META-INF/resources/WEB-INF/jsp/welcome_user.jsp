<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <title>Welcome to our page</title>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/custom.css"/>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/product.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/product.js"></script>

    <style>
        .category-box {
            margin-left: 20px;
            background-color: #04AA6D;
            border: none;
            color: white;
            padding: 15px 32px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 16px;
        }
    </style>
    <link rel="icon" href="data:,">
</head>
<header>
    <jsp:include page="header.jsp" flush="true"/>
</header>
<body style="background-color: grey;">
<div>
        <h1 id="welcome-text">Welcome to our page</h1>
        <div>
            <c:forEach items="${categories}" var="category">
            <a href="/showListProduct/${category}" class="category-box">${category}</a>
<%--                        <div id="productContainer-${category}" class="product-container">--%>
<%--                            <!-- Products will be loaded here -->--%>
<%--                        </div>--%>
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
<%--<script>--%>
<%--    document.addEventListener("DOMContentLoaded", function () {--%>
<%--        const categories = [--%>
<%--            <c:forEach items="${categories}" var="category">--%>
<%--            '${category}'<c:if test="${!status.last}">, </c:if>--%>
<%--            </c:forEach>--%>
<%--        ];--%>
<%--        categories.forEach(category => {--%>
<%--            getProductListByCategory(category);--%>
<%--        });--%>
<%--    });--%>

<%--</script>--%>
<script>
    document.addEventListener('DOMContentLoaded', function () {
        const token = localStorage.getItem('token');  // Lấy JWT từ localStorage

        if (token) {

            // Gửi request kèm JWT token trong Authorization header
            fetch('/welcome', {
                method: 'GET',
                headers: {
                    'Authorization': 'Bearer ' + token,  // Gửi JWT trong header
                    'Content-Type': 'application/json'
                }
            })
                .then(response => {
                    console.log("OK");
                    if (response.ok) {
                        return response.text();  // Nếu request thành công, lấy nội dung của profile
                    } else {
                        throw new Error('Unauthorized');
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                    document.getElementById('content').innerHTML = '<h3>Unauthorized access</h3>';  // Nếu thất bại
                });
        } else {
            document.getElementById('content').innerHTML = '<h3>Please login first.</h3>';  // Nếu không có JWT
        }
    });

</script>
</body>
</html>
