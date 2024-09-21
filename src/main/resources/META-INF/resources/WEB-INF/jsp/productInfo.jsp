<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html>
<head>
    <link href="${pageContext.request.contextPath}/webjars/bootstrap/5.3.3/css/bootstrap.min.css" rel="stylesheet">
    <title>Show Product</title>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/product.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/verifyToken.js"></script>
</head>
<header>
    <jsp:include page="header.jsp" flush="true"/>
</header>
<body>
<div class="container">
    <h1>Hello world</h1>
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
        <tr id="product-${product.id}">
            <th>${product.name}</th>
            <th>${product.description}</th>
            <th>${product.category}</th>
            <th>${product.getFormattedPrice()}</th>
            <th>
                <div style="margin-bottom: 10px">
                    <button class="button-product" id="updateProduct">Update</button>
                </div>
                <div style="margin-bottom: 10px">
                    <form action="${pageContext.request.contextPath}/addProductToCart" method="post">
                        <input type="hidden" name="id" value="${product.id}">
                        <button type="submit" class="btn btn-success">Add to cart</button>
                    </form>
                </div>
                <div>
                    <button type="submit" class="btn btn-success" onclick="deleteProduct(${product.id})">Delete</button>
                </div>
            </th>
        </tr>
        </tbody>
    </table>

</div>
<script src="${pageContext.request.contextPath}/webjars/bootstrap/5.3.3/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/webjars/jquery/3.6.0/jquery.min.js"></script>
<script>
    function deleteProduct(id) {
        if (confirm("Are you sure to delete this product?")) {
            $.ajax({
                url : '${pageContext.request.contextPath}/deleteProduct?id=' + id,
                type : 'POST',
                success :function (response) {
                    window.location.href = '${pageContext.request.contextPath}/showListProduct/' + '${sessionScope.category}';
                },
                error: function(xhr, status, error) {
                    alert("An error occurred while trying to delete the product: " + error);
                }
            })
        }
    }
    document.addEventListener('DOMContentLoaded', function () {
        console.log('DOM loaded');
        const updateButton = document.getElementById('updateProduct');
        console.log("Exist button: " + updateButton);
        updateButton.addEventListener('click', function () {
            if (checkToken()) {
                const token = localStorage.getItem('token');
                const id = ${product.id};
                console.log("Update product");
                //Todo: Fetch product update gets
                fetch('${pageContext.request.contextPath}/updateProduct?id=' + id, {
                    method: 'GET',  // Dùng GET vì bạn đang lấy HTML
                    headers: {
                        'Authorization': 'Bearer ' + token // Thêm token vào header
                    }
                })
                    .then(response => {
                        if (response.ok) {
                            return response.text(); // Lấy dữ liệu dưới dạng HTML (text)
                        } else {
                            throw new Error('Network response was not ok.');
                        }
                    })
                    .then(html => {
                        console.log('Success:', html);
                        // Hiển thị HTML hoặc chuyển hướng trang
                        document.open(); // Mở tài liệu mới
                        document.write(html); // Ghi nội dung HTML vào
                        document.close(); // Đóng tài liệu để hoàn tất việc render
                    })
                    .catch(error => {
                        console.error('Fetch error:', error);
                    });
            } else {
                console.log("Please login first");
            }
        });
    });

</script>
</body>
</html>
