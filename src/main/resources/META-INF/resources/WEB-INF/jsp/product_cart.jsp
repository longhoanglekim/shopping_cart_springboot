<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html>
<head>
    <link href="${pageContext.request.contextPath}/webjars/bootstrap/5.3.3/css/bootstrap.min.css" rel="stylesheet">
    <title>Show Product</title>
    <style>

    </style>
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
            <tr>
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
<script src="${pageContext.request.contextPath}/webjars/bootstrap/5.3.3/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/webjars/jquery/3.6.0/jquery.min.js"></script>
<script>
    $(document).ready(function() {
        // Handle the plus button click
        $('.plus-btn').click(function() {
            var $input = $(this).closest('.quantity-selector').find('#quantity');
            var value = parseInt($input.val());
            $input.val(value + 1);

            // Lấy tên sản phẩm từ cột đầu tiên trong cùng hàng
            var productName = $(this).closest('tr').find('td:first').text();

            // Gọi hàm cập nhật số lượng
            updateQuantity(productName, value + 1);
        });

        // Handle the minus button click
        $('.minus-btn').click(function() {
            var $input = $(this).closest('.quantity-selector').find('#quantity');
            var value = parseInt($input.val());

            // Lấy tên sản phẩm từ cột đầu tiên trong cùng hàng
            var productName = $(this).closest('tr').find('td:first').text();

            if (value > 1) {
                $input.val(value - 1);
                updateQuantity(productName, value - 1); // Cập nhật số lượng mới
            } else if (value === 1) {
                var confirmation = confirm("Are you sure you want to remove this product from your cart?");
                if (confirmation) {
                    $input.val(0);
                    $(this).closest('tr').remove(); // Xóa dòng sản phẩm
                    updateQuantity(productName, 0); // Cập nhật số lượng mới là 0
                }
            }
        });

        // Hàm cập nhật số lượng sản phẩm lên server
        function updateQuantity(productName, quantity) {
            $.ajax({
                url: '/update-quantity',  // URL đến API xử lý yêu cầu cập nhật
                method: 'POST',           // Phương thức POST
                data: {
                    productName: productName,  // Gửi tên sản phẩm
                    quantity: quantity         // Gửi số lượng mới
                },
                success: function(response) {
                    console.log('Cập nhật thành công');
                },
                error: function(xhr, status, error) {
                    console.error('Cập nhật thất bại');
                }
            });
        }
    });
</script>

</body>
</html>
