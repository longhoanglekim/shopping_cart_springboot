<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Order Status Selector</title>
    <style>
        /* Thanh trạng thái đơn hàng */
        .order-status-bar {
            display: flex;
            justify-content: space-around;
            margin: 20px 0;
            background-color: #f1f1f1;
            padding: 10px;
            border-radius: 5px;
        }

        /* Các nút trạng thái đơn hàng */
        .order-status {
            padding: 10px 20px;
            border-radius: 5px;
            color: white;
            font-weight: bold;
            cursor: pointer;
            text-align: center;
            text-decoration: none;
            background-color: #aaa;
        }

        /* Hiệu ứng hover */
        .order-status:hover {
            opacity: 0.8;
        }

        /* Trạng thái đang được chọn */
        .order-status.active {
            background-color: #007bff;
        }

        /* Danh sách đơn hàng */
        .order-list {
            display: none;
        }

        /* Các đơn hàng */
        .order-item {
            padding: 5px;
            border-bottom: 1px solid #eee;
        }

        /* Hiển thị khi được chọn */
        .order-list.active {
            display: block;
        }
    </style>
</head>
<header>
    <jsp:include page="header.jsp" flush="true" />
 </header>
<body>

<!-- Thanh trạng thái đơn hàng -->
<div class="order-status-bar">
    <div class="order-status active" onclick="showOrders('canceled')">Canceled</div>
    <div class="order-status" onclick="showOrders('in-transit')">In Transit</div>
    <div class="order-status" onclick="showOrders('processing')">Processing</div>
    <div class="order-status" onclick="showOrders('pending-confirmation')">Pending Confirmation</div>
    <div class="order-status" onclick="showOrders('completed')">Completed</div>
    <div class="order-status" onclick="showOrders('preparing')">Preparing</div>
</div>

<!-- Order lists -->
<div id="canceled" class="order-list active">
    <div class="order-item">Order #1234</div>
    <div class="order-item">Order #5678</div>
</div>

<div id="in-transit" class="order-list">
    <div class="order-item">Order #9101</div>
    <div class="order-item">Order #1121</div>
</div>


<div id="processing" class="order-list">
    <div class="order-item">Order #7181</div>
    <div class="order-item">Order #9202</div>
</div>

<div id="pending-confirmation" class="order-list">
<%--    <div class="order-item">Order #1123</div>--%>
<%--    <div class="order-item">Order #1456</div>--%>
    <c:forEach var="productMap" items="${sessionScope.pendingList}">
        <table class="table table-bordered">
            <tr>
                <td>Product Info</td>
                <td>Price</td>
                <td>Quantity</td>
            </tr>
            <c:forEach items="${productMap }" var="productPair" >
                <c:if test="${productPair.key != null}">
                    <tr>
                        <td>
                            <p>
                                    ${productPair.key.name}
                                <span id="productDescription">${productPair.key.description}</span>
                            </p>
                        </td>
                        <td>${productPair.key.getFormattedPrice()}</td>
                        <td>${productPair.value}</td>
                    </tr>
                </c:if>
            </c:forEach>
        </table>
    </c:forEach>

</div>

<div id="completed" class="order-list">
    <div class="order-item">Order #7890</div>
    <div class="order-item">Order #1233</div>
</div>

<div id="preparing" class="order-list">
    <div class="order-item">Order #2334</div>
    <div class="order-item">Order #3455</div>
</div>

<script>
    function showOrders(status) {
        // Bỏ active khỏi tất cả các trạng thái và ẩn tất cả danh sách đơn hàng
        var statuses = document.querySelectorAll('.order-status');
        statuses.forEach(function(el) {
            el.classList.remove('active');
        });

        var orderLists = document.querySelectorAll('.order-list');
        orderLists.forEach(function(list) {
            list.classList.remove('active');
        });

        // Thêm active vào trạng thái được chọn và hiển thị danh sách tương ứng
        document.querySelector('.order-status[onclick="showOrders(\'' + status + '\')"]').classList.add('active');
        document.getElementById(status).classList.add('active');
    }
</script>

</body>
</html>
