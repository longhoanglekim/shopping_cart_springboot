<html>
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <title>Profile</title>
    <header>
        <jsp:include page="header.jsp" flush="true" />
    </header>
    <body>
        Username : ${account.username}<br>
        Price in case : ${account.getFormattedCash()} VND
    </body>
</html>