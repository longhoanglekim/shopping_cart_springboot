<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/getAttribute.js"></script>
    <title>header</title>
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

<script src="/js/bootstrap.min.js"></script>
<script src="/js/jquery.min.js"></script>
<script>
    let selectedStrings = [];
    let suggestedItems = [];
    let itemClicked = false;  // Thêm biến để theo dõi nếu item đã được chọn
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
        const token = localStorage.getItem('token');  // Kiểm tra token từ localStorage
        const navLinks = document.getElementById('nav-links');  // Lấy phần tử nav chứa các liên kết
        console.log(token)
        // Nếu có token, gọi API để lấy thông tin người dùng và cập nhật header
        if (token) {

            navLinks.innerHTML = '';

            // Thêm các liên kết mới cho người dùng đã đăng nhập
            navLinks.innerHTML = `
                        <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/profile" id="profileLink">Example Name</a></li>
                        <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/shopping_cart">
                            <img src="/image/shopping-cart.png" alt="Shopping cart" width="35" height="30">
                        </a></li>
                        <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/logout">Logout</a></li>
                    `;
            const username = await getName(token);  // Chờ hàm getName(token) trả về giá trị
            if (username) {
                document.getElementById("profileLink").textContent = username;  // Đặt tên người dùng
            }

        }
    });
</script>

</body>
</html>
