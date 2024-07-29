<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <link href="webjars/bootstrap/5.3.3/css/bootstrap.min.css" rel="stylesheet">
    <title>Add product</title>
</head>
<header>
    <jsp:include page="header.jsp" flush="true"/>
</header>
<body>
<div>
    <%--@elvariable id="product" type="com.trainings.shoppingcartdemo.models.Product"--%>
    <form:form method="post" modelAttribute="product">
        <table class="table">
            <tr>
                <td>Name</td>
                <td><form:input type="text" required="required" path="name"/> </td>
                <td><form:errors path="name" cssClass="text-warning"/></td>
            </tr>
            <tr>
                <td>Description</td>
                <td><form:input type="text" path="description"/></td>
            </tr>
            <tr>
                <td>Category</td>
                <td><form:input type="text" path="category" required="required"/></td>
            </tr>
            <tr>
                <td>Price</td>
                <td><form:input type="text" path="price" required="required"/></td>
                <td><form:errors path="price" cssClass="text-warning"/></td>
            </tr>

        </table>
        <input type="submit" value="Submit" class="btn btn-success">
    </form:form>
</div>
<script src="webjars/bootstrap/5.3.3/js/bootstrap.min.js"></script>
<script src="webjars/jquery/3.6.0/jquery.min.js"></script>
</body>
</html>