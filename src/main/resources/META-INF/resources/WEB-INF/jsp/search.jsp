<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link href="webjars/bootstrap/5.3.3/css/bootstrap.min.css" rel="stylesheet">
    <title>Get Search Items</title>
    <style>
        .left-div {
            float: left;
            margin-right : 20px;
            border-right: 2px solid lightskyblue;
            height: 100%;
            width : 200px;
        }
        .right-div {
        }
        #keyword {
            color : red;
        }
    </style>
</head>
<header>
    <jsp:include page="header.jsp" flush="true"/>
</header>
<body>
<div>
    <div class="left-div">
        <p>Search filter</p>
    </div>
    <div class="right-div">
        <p>Search result for '<span id="keyword">${keyword}</span>'</p>
        <div>
            ${bestShop.username}
        </div>
    </div>
</div>
</body>
</html>