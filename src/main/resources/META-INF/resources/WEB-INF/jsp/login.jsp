<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <title>Login page</title>
    <link rel="icon" href="data:,">
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

    </c:if>
    <div style="margin-top: 200px;">
        <form id="loginForm">
            <table>
                <tr>
                    <td>Enter the account:</td>
                    <td><input type="text" id="username" name="username" required="required"></td>
                </tr>
                <tr>
                    <td>Enter the password:</td>
                    <td><input type="password" id="password" name="password" required="required"></td>
                </tr>
            </table>
            <input type="hidden" id="token" value="">
            <input type="submit" value="Login" class="btn btn-success">
        </form>
    </div>
    <p class="icon-link">Haven't got an account?</p>
    <a href="${pageContext.request.contextPath}/register" class="btn btn-primary">Register</a>
</div>
<script src="webjars/bootstrap/5.3.3/js/bootstrap.min.js"></script>
<script src="webjars/jquery/3.6.0/jquery.min.js"></script>
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
<script>
    document.addEventListener('DOMContentLoaded', function () {
        console.log("Login page loaded");
        if (localStorage.getItem('token')) {
            localStorage.removeItem('token');
        }
        document.getElementById('loginForm').addEventListener('submit', function (event) {
            event.preventDefault(); // Ngăn không cho form submit mặc định

            const username = document.getElementById('username').value;
            const password = document.getElementById('password').value;

            // Gửi yêu cầu đăng nhập qua fetch API
            fetch('/auth/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    username: username,
                    password: password
                })
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Login failed');
                    }
                    return response.json();
                })
                .then(data => {
                    if (data.token) {
                        // Lưu token vào localStorage sau khi đăng nhập thành công
                        <%--console.log('Token:', data.token);--%>
                        <%--localStorage.setItem('token', data.token);--%>
                        <%--const token = data.token;--%>
                        <%--document.cookie = `jwtToken=${token}; path=/; secure; HttpOnly;`;--%>

                        // Chuyển hướng đến trang welcome hoặc trang khác sau khi đăng nhập thành công
                        window.location.href = '/welcome';
                    } else {
                        // Hiển thị thông báo lỗi nếu không nhận được token
                        document.getElementById('errorMessage').style.display = 'block';
                        setTimeout(() => {
                            document.getElementById('errorMessage').style.display = 'none';
                        }, 2000);
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                    document.getElementById('errorMessage').style.display = 'block';
                    setTimeout(() => {
                        document.getElementById('errorMessage').style.display = 'none';
                    }, 2000);
                });
        });
    });



</script>
<<script>
    document.addEventListener('DOMContentLoaded', function () {
        console.log("Login page loaded");

        // Xóa token từ localStorage nếu tồn tại
        if (localStorage.getItem('token')) {
            console.log("Token found in localStorage, removing it.");
            localStorage.removeItem('token');
        }

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

                // Xóa cookie
                deleteCookie(name.trim());
            }
        }

        // Xóa tất cả các cookie khi vào trang
        deleteAllCookies();

        // Thêm sự kiện submit cho form đăng nhập
        document.getElementById('loginForm').addEventListener('submit', function (event) {
            event.preventDefault(); // Ngăn không cho form submit mặc định

            const username = document.getElementById('username').value;
            const password = document.getElementById('password').value;

            // Gửi yêu cầu đăng nhập qua fetch API
            fetch('/auth/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    username: username,
                    password: password
                })
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Login failed');
                    }
                    return response.json();
                })
                .then(data => {
                    if (data.token) {
                        // Lưu token vào localStorage sau khi đăng nhập thành công
                        console.log('Token:', data.token);
                        localStorage.setItem('token', data.token);

                        // Chuyển hướng đến trang welcome hoặc trang khác sau khi đăng nhập thành công
                        window.location.href = '/welcome';
                    } else {
                        // Hiển thị thông báo lỗi nếu không nhận được token
                        document.getElementById('errorMessage').style.display = 'block';
                        setTimeout(() => {
                            document.getElementById('errorMessage').style.display = 'none';
                        }, 2000);
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                    document.getElementById('errorMessage').style.display = 'block';
                    setTimeout(() => {
                        document.getElementById('errorMessage').style.display = 'none';
                    }, 2000);
                });
        });
    });
</script>

</body>
</html>