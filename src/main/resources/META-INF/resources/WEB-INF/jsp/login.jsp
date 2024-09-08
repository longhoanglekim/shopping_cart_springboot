<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <title>Login page</title>
    <style>
        #errorMessage {
            color: red;
            display: none;
            position: absolute;
            top: 5px;
            left: 100px;
            background-color: #f8d7da;
            padding: 10px;
            border-radius: 5px;
            box-shadow: 0px 0px 5px rgba(0, 0, 0, 0.2);
            margin-bottom: 10px;
        }

        .container {
            margin-top: 50px;
        }
    </style>
</head>
<header>
    <jsp:include page="header.jsp" flush="true"/>
</header>
    <body>
    <div class="container">
        <c:if test="${param.error != null}">
            <div id="errorMessage">Invalid username or password</div>
            <script>
                const errorMessage = document.getElementById("errorMessage");
                errorMessage.style.display = "block";
                setTimeout(() => {
                    errorMessage.style.display = "none";
                }, 2000);
            </script>
        </c:if>
        <div style="margin-top: 200px;">
            <form method="post" action="/login">
                <table>
                    <tr>
                        <td>Enter the account:</td>
                        <td><input type="text" name="username" required="required"></td>
                    </tr>
                    <tr>
                        <td>Enter the password:</td>
                        <td><input type="password" name="password" required="required"></td>
                    </tr>
                </table>
                <input type="submit" value="Login" class="btn btn-success">
            </form>
        </div>
        <p class="icon-link">Haven't got an account?</p>
        <a href="${pageContext.request.contextPath}/register" class="btn btn-primary">Register</a>
    </div>
    <script>
        // Hàm để xóa cookie theo tên
        function deleteCookie(name) {
            document.cookie = name + '=;expires=Thu, 01 Jan 1970 00:00:00 GMT;path=/';
        }

        // Lấy tất cả các cookie và xóa chúng
        function deleteAllCookies() {
            const cookies = document.cookie.split(";");

            for (let i = 0; i < cookies.length; i++) {
                const cookie = cookies[i];
                const eqPos = cookie.indexOf("=");
                const name = eqPos > -1 ? cookie.substr(0, eqPos) : cookie;

                // Kiểm tra nếu cookie là 'error'
                if (name.trim() === 'error') {
                    console.log("Found 'error' cookie, deleting it.");
                }

                deleteCookie(name.trim());
            }
        }

        // Gọi hàm xóa tất cả cookie ngay khi vào trang
        deleteAllCookies();
    </script>
    </body>
</html>