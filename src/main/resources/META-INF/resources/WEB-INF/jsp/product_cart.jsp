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

</body>
<footer>
    <div class="topnav">
        <div class="topnav-right">
            <p>Total value : <span>${totalValue}</span></p>
            <a href="${pageContext.request.contextPath}/confirmOrder">Checkout</a>
        </div>
    </div>
</footer>
<script src="${pageContext.request.contextPath}/webjars/bootstrap/5.3.3/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/webjars/jquery/3.6.0/jquery.min.js"></script>
<script>
    $(document).ready(function() {

        // Handle the plus button click
        $('.plus-btn').click(function() {
            var $input = $(this).closest('.quantity-selector').find('#quantity');
            var value = parseInt($input.val());
            $input.val(value + 1);  // Increment the quantity

            var productName = $(this).closest('tr').find('td:first').text();

            updateQuantity(productName, value + 1);  // Update the quantity on the server

            // Set #totalValue to 0.00 after clicking the plus button
            $('#totalValue').text('0.00');
        });

        // Handle the minus button click
        $('.minus-btn').click(function() {
            var $input = $(this).closest('.quantity-selector').find('#quantity');
            var value = parseInt($input.val());

            var productName = $(this).closest('tr').find('td:first').text();

            if (value > 1) {
                $input.val(value - 1);
                updateQuantity(productName, value - 1);  // Update the quantity on the server
            } else if (value === 1) {
                var confirmation = confirm("Are you sure you want to remove this product from your cart?");
                if (confirmation) {
                    $input.val(0);
                    $(this).closest('tr').remove();  // Remove the product row
                    updateQuantity(productName, 0);  // Update the quantity to 0 on the server
                }
            }
        });

        // Function to update quantity on the server
        function updateQuantity(productName, quantity) {
            $.ajax({
                url: '/update-quantity',
                method: 'POST',
                data: {
                    productName: productName,
                    quantity: quantity
                },
                success: function(response) {
                    console.log('Cập nhật thành công');
                    // Avoid updating #totalValue from the server response to prevent overwriting the '0.00'
                    // $('#totalValue').text(response.totalValue.toFixed(2));
                },
                error: function(xhr, status, error) {
                    console.error('Cập nhật thất bại');
                }
            });
        }
    });

</script>

</html>