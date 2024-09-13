<html>

    <head>
        <link href="/css/bootstrap.min.css" rel="stylesheet">
        <title>Profile</title>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/verifyToken.js"></script>

    </head>
    <header>
        <jsp:include page="header.jsp" flush="true" />
    </header>
    <body>
        Username : ${account.username}<br>
        Price in case : ${account.getFormattedCash()} VND
        <script>
            document.addEventListener("DOMContentLoaded", function () {
                if (checkToken()) {
                    // Thực hiện các hành động khác nếu token hợp lệ
                    console.log("Token is valid, proceed with page.");
                }
            });
        </script>
    </body>
</html>
