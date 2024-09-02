<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link href="webjars/bootstrap/5.3.3/css/bootstrap.min.css" rel="stylesheet">
    <title>Get Search Items</title>
    <style>
        .parent-div {
            background-color: lightgrey;
            height: 100vh;
            display: flex;
        }

        .left-div {
            float: left;
            margin-right: 20px;
            border-right: 2px solid lightskyblue;
            height: 100%;
            width: 200px;
        }

        .right-div {
            flex-grow: 1;
            background-color: inherit;
        }

        .keyword {
            color: red;
        }

        .right-div .item-container {
            width: 50%;
            background-color: white;
            padding: 10px;
            margin-top: 10px;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        .product-grid {
            display: flex;
            flex-wrap: wrap;
            gap: 50px;
        }

        .product-item {
            background-color: white;
            padding: 10px;
            flex: 1 1 calc(33.333% - 20px); /* Độ rộng linh hoạt */
            max-width: calc(25% - 50px);
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            text-align: center;
        }

        .shopList {
            display: flex;
            flex-wrap: wrap;
            gap: 300px;
        }
    </style>
</head>
<header>
    <jsp:include page="header.jsp" flush="true"/>
</header>
<body>
<div class="parent-div">
    <div class="left-div">
        <p>Search filter</p>
    </div>
    <div class="right-div">
        <div class="shopList">
            <div>
                <p style="flex-grow: 1; margin: 0;">Search result for '<span class="keyword">${keyword}</span>'</p>
            </div>
            <c:if test="${moreThan1}">
                <div>
                    <a href="/moreShops" class="link-underline" style="margin-left: 20px;">More shops</a>
                </div>
            </c:if>
        </div>
        <c:choose>
            <c:when test="${not empty bestShop}">
                <div class="item-container">
                        ${bestShop.username}
                </div>
            </c:when>
            <c:otherwise>
                <div class="item-container">
                    <p>No result for '<span class="keyword">${keyword}</span>'</p>
                </div>
            </c:otherwise>
        </c:choose>
        <div style="margin-top: 20px">

            <c:choose>
                <c:when test="${not empty productList}">
                    <div class="product-grid">

                        <c:forEach var="product" items="${productList}">
                            <div class="product-item">
                                <a href="${pageContext.request.contextPath}/productInfo?id=${product.id}">
                                    <div>
                                        <h4>${product.name}</h4>
                                        <p>${product.getFormattedPrice()}</p>
                                    </div>
                                </a>
                            </div>

                        </c:forEach>

                    </div>
                </c:when>
                <c:otherwise>
                    <div class="item-container">
                        <p>No product for '<span class="keyword">${keyword}</span>'</p>
                    </div>
                </c:otherwise>
            </c:choose>

        </div>
    </div>
</div>
</body>
</html>