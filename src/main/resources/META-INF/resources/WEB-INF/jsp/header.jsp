<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/getAttribute.js"></script>
    <title>header</title>
    <link rel="icon" href="data:,">
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/custom.css"/>
    <style>
        #findList {
            position: absolute;
            background-color: white;
            border: 1px solid #ddd;
            z-index: 1000;
            width: calc(50% - 20px);
            max-height: 200px;
            overflow-y: auto;
            box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.2);
        }

        #findList li {
            list-style-type: none;
        }

        #findList li:hover {
            background: #aaaaaa;
        }

        button {
            border: none;
            outline: none;
            background: none; /* Remove any background color from the button */
            padding: 0; /* Ensure there's no padding around the button */
            margin: 0; /* Ensure no extra margin */
        }

        #cartButton {
            border: none; /* Remove border from the image inside the button */
            outline: none; /* Remove any outline when focused */
            background-color: transparent; /* Ensure background is transparent */
        }

        button img {
            display: block; /* Remove inline spacing around the image */
        }

        /* Container cho phần Account */
        .account-dropdown {
            position: relative;
            display: inline-block;
            margin-left : 1px;
        }

        /* Nút bấm cho account (giống như tên tài khoản) */
        .account-btn {
            background-color: #f60;
            color: white;
            padding: 10px;
            font-size: 16px;
            border: none;
            cursor: pointer;
        }

        /* CSS cho menu thả xuống */
        .dropdown-menu {
            display: none;
            position: absolute;
            background-color: white;
            min-width: 130px;
            box-shadow: 0px 8px 16px rgba(0, 0, 0, 0.2);
            z-index: 1;
            margin-left: 10px;
            left : -20px;
        }

        /* CSS cho các item trong menu */
        .dropdown-menu a {
            color: black;
            padding: 12px 16px;
            text-decoration: none;
            display: block;
            font-size: 14px;
        }

        /* Hiệu ứng khi hover vào menu item */
        .dropdown-menu a:hover {
            background-color: #f1f1f1;
        }

        /* Hiển thị menu khi hover */
        .dropdown-menu:hover {
            display: block;
        }

        .account-menu {
            margin-left: 3px;
        }


        .account-menu:hover {
            background-color: lightgrey;
        }
    </style>

</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
        <!-- Logo và các liên kết bên trái -->
        <a class="navbar-brand" href="${pageContext.request.contextPath}/welcome">JusEnuf</a>
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
            <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/welcome">Home</a></li>
            <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/showListProduct/all">All
                products</a></li>
        </ul>

        <div class="mx-auto" style="width: 50%;">
            <form class="d-flex" action="${pageContext.request.contextPath}/search" method="GET">
                <input class="form-control me-2" type="text" placeholder="Search" aria-label="Search" id="searchInput"
                       style="flex-grow: 1;">
                <input type="hidden" id="selectedItems" name="selectedItems" value="">
                <input type="hidden" id='keyword' name='keyword' value="">
                <button class="btn btn-outline-success" type="submit">Search</button>
            </form>
            <ul id="findList">
            </ul>
        </div>

        <!-- Các liên kết bên phải -->
        <ul class="navbar-nav ms-auto mb-2 mb-lg-0" id="nav-links">
            <!-- Ban đầu sẽ hiển thị login và register, JavaScript sẽ thay đổi sau -->
            <li class="nav-item"><a href="${pageContext.request.contextPath}/login" class="nav-link" id="login-link">Login</a>
            </li>
            <li class="nav-item"><a href="${pageContext.request.contextPath}/register" class="nav-link"
                                    id="register-link">Register</a></li>
        </ul>
    </div>
</nav>

<script>
    document.addEventListener('DOMContentLoaded', async function () {
        // Ví dụ: Lấy giá trị của cookie có tên là 'jwtToken'
        // get token from asyn function
        const jwtToken = await getToken();
        console.log("Token :" + jwtToken);
        if (jwtToken) {
            console.log("Set name");
            fetch('api/jwt/getName', {
                method: 'GET',
                headers: {
                    'Authorization': 'Bearer ' + jwtToken,
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

<script src="/js/bootstrap.min.js"></script>
<script src="/js/jquery.min.js"></script>

<script>
    let selectedStrings = [];
    let suggestedItems = [];
    let itemClicked = false;
    let debounceTimeout;

    document.getElementById('searchInput').addEventListener('input', function () {
        clearTimeout(debounceTimeout);  // Xóa timeout trước đó nếu người dùng đang tiếp tục nhập
        const query = this.value;

        debounceTimeout = setTimeout(() => {
            document.getElementById('keyword').value = query;
            itemClicked = false;  // Reset trạng thái khi người dùng nhập mới
            console.log("Keyword is : " + query);

            if (query.length > 0) {
                fetch(`/api/search/searchSuggestions?keyword=` + query)
                    .then(response => {
                        const contentType = response.headers.get("content-type");
                        if (contentType && contentType.includes("application/json")) {
                            return response.json();  // Parse JSON nếu đúng định dạng
                        } else {
                            throw new Error("Invalid response format");
                        }
                    })
                    .then(data => {
                        const findList = document.getElementById('findList');
                        findList.innerHTML = '';  // Reset danh sách gợi ý
                        suggestedItems = [];  // Reset danh sách các mục đã gợi ý

                        if (data.length > 0) {
                            findList.style.display = 'block';
                            data.forEach(item => {
                                suggestedItems.push(item);  // Thêm tất cả các mục đã gợi ý
                                const li = document.createElement('li');
                                li.textContent = item;
                                li.addEventListener('click', function () {
                                    document.getElementById('keyword').value = item;
                                    // Khi mục được chọn, chỉ thêm mục đó vào selectedStrings
                                    selectedStrings = [item];
                                    document.getElementById('searchInput').value = item;
                                    itemClicked = true;  // Đánh dấu rằng mục đã được chọn
                                    findList.style.display = 'none';  // Ẩn danh sách
                                });
                                findList.appendChild(li);
                            });
                        } else {
                            // Hiển thị thông báo khi không tìm thấy kết quả
                            findList.innerHTML = '<li>No results found</li>';
                            findList.style.display = 'block';
                        }
                    })
                    .catch(error => {
                        console.error("Error fetching search suggestions:", error);
                        const findList = document.getElementById('findList');
                        findList.innerHTML = '<li>Error loading suggestions</li>';
                        findList.style.display = 'block';  // Hiển thị thông báo lỗi trong hộp gợi ý
                    });
            } else {
                document.getElementById('findList').style.display = 'none';
                suggestedItems = [];  // Reset danh sách các mục đã gợi ý khi không có từ khóa
            }
        }, 300);  // Đợi 300ms sau khi người dùng ngừng nhập
    });

    // Xử lý sự kiện khi form được submit
    document.querySelector('form.d-flex').addEventListener('submit', function () {
        if (!itemClicked) {  // Chỉ khi không có mục cụ thể nào được chọn, sử dụng toàn bộ danh sách gợi ý
            selectedStrings = [...new Set([...suggestedItems])];  // Loại bỏ trùng lặp nếu có
        }
        document.getElementById('selectedItems').value = selectedStrings.join(',');  // Thêm các mục đã chọn vào hidden input
    });
</script>
<script>
    document.addEventListener("DOMContentLoaded", async function () {
        console.log('Header script loaded');
        const token = await getToken("jwtToken");

        const navLinks = document.getElementById('nav-links');  // Lấy phần tử nav chứa các liên kết
        console.log(token);
        // Nếu có token, gọi API để lấy thông tin người dùng và cập nhật header
        if (token) {

            navLinks.innerHTML = '';

            // Thêm các liên kết mới cho người dùng đã đăng nhập
            navLinks.innerHTML = `
                        <div id="accmenu-div">
                             <li class="nav-item account-dropdown">
                                <button class="nav-link btn" id="profileMenu">Example Name</button>
                                <ul class="dropdown-menu" id="dropdown-menu">
                                    <li><a class="account-menu" id="profileButton" href="${pageContext.request.contextPath}/profile">Profile</a></li>
                                    <li><a class="account-menu" id="orderButton" href="${pageContext.request.contextPath}/orders">My purchases</a></li>
                                </ul>
                            </li>
                         </div>
                        <li class="nav-item">
                            <button class="nav-link btn" id="cartButton">
                                <img src="/image/shopping-cart.png" alt="Shopping cart" width="35" height="30">
                            </button>
                        </li>
                        <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/logout" id="logout">Logout</a></li>
                    `;
            const username = await getName(token);  // Chờ hàm getName(token) trả về giá trị
            if (username) {
                console.log('Username:', username);
                document.getElementById("profileMenu").textContent = username;  // Đặt tên người dùng
            }
            /// Thêm event listener sau khi HTML đã được chèn
            document.getElementById('accmenu-div').addEventListener('mouseenter', function () {
                console.log('Mouse entered');  // Kiểm tra xem event mouse enter có hoạt động không
                document.querySelector('.dropdown-menu').style.display = 'block';  // Hiển thị menu
            });
            document.getElementById('accmenu-div').addEventListener('mouseleave', function () {
                document.querySelector('.dropdown-menu').style.display = 'none';  // Ẩn menu
            });
            document.getElementById('cartButton').addEventListener('click', function () {
                console.log('Button clicked'); // Kiểm tra xem event click có hoạt động không
                fetch('${pageContext.request.contextPath}/shopping_cart', {
                    method: 'GET'
                })
                    .then(response => {
                        if (response.ok) {
                            return response.text(); // Lấy dữ liệu dưới dạng HTML (text)
                        } else {
                            throw new Error('Network response was not ok.');
                        }
                    })
                    .then(html => {
                        console.log('Success:', html);
                        // Hiển thị HTML hoặc chuyển hướng trang
                        document.open(); // Mở tài liệu mới
                        document.write(html); // Ghi nội dung HTML vào
                        document.close(); // Đóng tài liệu để hoàn tất việc render
                    })
                    .catch(error => {
                        console.error('Fetch error:', error);
                    });
            });

        }
        if (!token) {
            // Nếu không có token, hiển thị liên kết login và register
            navLinks.innerHTML = `
                        <li class="nav-item"><a href="${pageContext.request.contextPath}/login" class="nav-link" id="login-link">Login</a></li>
                        <li class="nav-item"><a href="${pageContext.request.contextPath}/register" class="nav-link" id="register-link">Register</a></li>
                    `;
        }
        document.getElementById('logout').addEventListener('click', function (event) {
            event.preventDefault();  // Ngăn việc chuyển trang ngay lập tức
            localStorage.removeItem('token');  // Xóa token khỏi localStorage

            // Sau khi xóa token, chuyển hướng đến trang logout trên server để xử lý
            window.location.href = '${pageContext.request.contextPath}/login';
        });
    });

</script>
</body>
</html>
