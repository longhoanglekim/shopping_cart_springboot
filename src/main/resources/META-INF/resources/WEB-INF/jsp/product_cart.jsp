<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html>
<head>
    <link href="${pageContext.request.contextPath}/webjars/bootstrap/5.3.3/css/bootstrap.min.css" rel="stylesheet">
    <title>Show Product</title>
    <link type="text/css" rel="stylesheet" href="css/custom.css" />
</head>
<header>
    <jsp:include page="header.jsp" flush="true"/>
</header>
<body>
<div class="container">
    <table class="table table-bordered">
        <tr>
            <td>Name</td>
            <td>Number of products</td>
        </tr>
        <c:forEach items="${productMap}" var="product">
            <tr data-price="${product.key.price}">
                <td>${product.key.name}</td>
                <td>
                    <div class="quantity-selector">
                        <button type="button" class="minus-btn">-</button>
                        <label>
                            <input type="text" id="quantity" value="${product.value}" class="textNumber" readonly width="5">
                        </label>
                        <button type="button" class="plus-btn">+</button>
                    </div>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>

</body>
<footer>
    <div class="topnav">
        <div class="topnav-right">
            <p>Total value : <span id="totalValue">${totalValue}</span></p>
            <a href="${pageContext.request.contextPath}/confirmOrder">Confirm order</a>
        </div>
    </div>
</footer>
<script src="${pageContext.request.contextPath}/webjars/bootstrap/5.3.3/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/webjars/jquery/3.6.0/jquery.min.js"></script>
<script>
    document.addEventListener("DOMContentLoaded", function () {
        const token = localStorage.getItem('token');  // Lấy token từ localStorage
        const navLinks = document.getElementById('nav-links');  // Lấy phần tử nav chứa các liên kết
        const contextPath = window.location.pathname.substring(0, window.location.pathname.indexOf("/", 2)); // Lấy context path

        if (token) {
            fetch('/api/jwt/getName', {
                method: 'GET',
                headers: {
                    'Authorization': 'Bearer ' + token,
                    'Content-Type': 'application/json'
                }
            })
                .then(response => response.json())
                .then(data => {
                    if (data && data.username) {
                <%--        const name = data.username; // Gán giá trị username từ API--%>
                <%--        console.log(name);--%>
                <%--        // Xóa liên kết login và register cũ--%>
                <%--        navLinks.innerHTML = '';--%>

                <%--        // Sử dụng template literals để chèn giá trị `name` từ JavaScript--%>
                <%--        const profileLinkValue = `<a class="nav-link" href="${contextPath}/profile">${name}</a>`;--%>

                <%--        // Tạo phần tử `li` chứa liên kết profile--%>
                <%--        const profileLink = document.createElement('li');--%>
                <%--        profileLink.classList.add('nav-item');--%>
                <%--        profileLink.innerHTML = profileLinkValue;--%>
                <%--        navLinks.appendChild(profileLink);--%>

                <%--        // Tạo phần tử giỏ hàng và logout--%>
                <%--        const cartLink = document.createElement('li');--%>
                <%--        cartLink.classList.add('nav-item');--%>
                <%--        cartLink.innerHTML = `--%>
                <%--    <a class="nav-link" href="${contextPath}/shopping_cart">--%>
                <%--        <img src="/image/shopping-cart.png" alt="Shopping cart" width="35" height="30">--%>
                <%--    </a>--%>
                <%--`;--%>
                <%--        navLinks.appendChild(cartLink);--%>

                <%--        const logoutLink = document.createElement('li');--%>
                <%--        logoutLink.classList.add('nav-item');--%>
                <%--        logoutLink.innerHTML = `<a class="nav-link" href="${contextPath}/logout">Logout</a>`;--%>
                <%--        navLinks.appendChild(logoutLink);--%>
                    }
                })
                .catch(error => {
                    console.error('Error fetching user info:', error);
                });
        }
    });

</script>

</html>