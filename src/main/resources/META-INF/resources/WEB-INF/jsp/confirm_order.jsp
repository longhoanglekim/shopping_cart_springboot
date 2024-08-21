<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Confirm order</title>
    <link rel="stylesheet" href="css/custom.css" />
</head>
<header>
    <jsp:include page="header.jsp" flush="true" />
</header>
<body>
    <div class="container">
        <div>
            <h2>Receiver Info</h2>
            <table class="table table-bordered">
                <tr>
                    <td>Full name</td>
                    <td>${accountDetails.fullName}</td>
                </tr>
                <tr>
                    <td>Address</td>
                    <td>${accountDetails.address}</td>
                </tr>
                <tr>
                    <td>Phone number</td>
                    <td>${accountDetails.phoneNumber}</td>
                </tr>
            </table>
        </div>
        <div>
            <h3>Product List</h3>
            <table class="table table-bordered">
                <tr>
                    <td></td>
                    <td>Price</td>
                    <td>Quantity</td>
                </tr>
                <c:forEach items="${productMap }" var="productPair" >
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
                </c:forEach>
            </table>
            <%--Todo : add deliver payment and checkout method --%>
            <div class="topnav-right">
                <p id="pDeliverPayment">Deliver payment : <span id="deliverPayment">${orderDetails.deliverPayment}</span></p> <br>
                <a href="/checkout" class="btn btn-success">Checkout</a>
            </div>
        </div>
    </div>
</body>
</html>