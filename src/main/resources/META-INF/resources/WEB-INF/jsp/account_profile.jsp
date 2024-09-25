<html>

<head>
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <title>Profile</title>
    <link rel="icon" href="data:,">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/verifyToken.js"></script>
</head>
<header>
    <jsp:include page="header.jsp" flush="true"/>
</header>
<body>
Username : <span id="username"></span><br>
Price in case : ${cash} VND
<script>
    document.addEventListener('DOMContentLoaded', function () {
        const token = localStorage.getItem('token');
        console.log(token);
        if (token) {

            fetch('api/jwt/getName', {
                method: 'GET',
                headers: {
                    'Authorization': 'Bearer ' + token,
                    'Content-Type': 'application/json'
                }
            })
                .then(response => response.json())
                .then(data => {
                    // Đặt tên người dùng vào welcome text
                    if (data) {
                        document.getElementById('username').innerText = data.username;
                    }
                })
        } else {
            document.getElementById('profileContainer').innerHTML = '<h3>Please login first.</h3>';
        }
    });
</script>
</body>
</html>
