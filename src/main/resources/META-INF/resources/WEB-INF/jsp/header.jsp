<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <link href="webjars/bootstrap/5.3.3/css/bootstrap.min.css" rel="stylesheet">
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
            <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/showListProduct/all">All products</a></li>
        </ul>

        <div class="mx-auto" style="width: 50%;">
            <form class="d-flex" action="${pageContext.request.contextPath}/search" method="GET">
                <input class="form-control me-2" type="text" placeholder="Search" aria-label="Search" id="searchInput" style="flex-grow: 1;">
                <input type="hidden" id="selectedItems" name="selectedItems" value="">
                <input type="hidden" id='keyword' name ='keyword' value="">
                <button class="btn btn-outline-success" type="submit">Search</button>
            </form>
            <ul id="findList">
            </ul>
        </div>

        <!-- Các liên kết bên phải -->
        <ul class="navbar-nav ms-auto mb-2 mb-lg-0">
            <c:choose>
                <c:when test="${not empty username}">
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/profile">${username}</a></li>
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/shopping_cart">
                        <img src="${pageContext.request.contextPath}/image/shopping-cart.png" alt="Shopping cart" width="35" height="30">
                    </a></li>
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/logout">Logout</a></li>
                </c:when>
                <c:otherwise>
                    <li class="nav-item"><a href="${pageContext.request.contextPath}/login" class="nav-link">Login</a></li>
                    <li class="nav-item"><a href="${pageContext.request.contextPath}/register" class="nav-link">Register</a></li>
                </c:otherwise>
            </c:choose>
        </ul>
    </div>
</nav>

<script src="webjars/bootstrap/5.3.3/js/bootstrap.min.js"></script>
<script src="webjars/jquery/3.6.0/jquery.min.js"></script>
<script>
    let selectedStrings = [];
    let suggestedItems = [];

    document.getElementById('searchInput').addEventListener('input', function () {
        const query = this.value;
        document.getElementById('keyword').value = query;
        if (query.length > 0) {
            fetch(`/api/search/searchSuggestions?keyword=` + query)
                .then(response => response.json())
                .then(data => {
                    const findList = document.getElementById('findList');
                    findList.innerHTML = '';
                    suggestedItems = [];  // Reset suggested items

                    if (data.length > 0) {
                        findList.style.display = 'block';
                        data.forEach(item => {
                            suggestedItems.push(item);  // Add all suggested items
                            const li = document.createElement('li');
                            li.textContent = item;
                            li.addEventListener('click', function() {
                                selectedStrings.push(item);
                                document.getElementById('searchInput').value = item;
                                findList.style.display = 'none';
                                document.getElementById('selectedItems').value = selectedStrings.join(',');
                            });
                            findList.appendChild(li);
                        });
                    } else {
                        findList.style.display = 'none';
                    }
                });
        } else {
            document.getElementById('findList').style.display = 'none';
            suggestedItems = [];  // Reset suggested items
        }
    });

    document.querySelector('form.d-flex').addEventListener('submit', function () {
        // Add all suggested items to selectedStrings before submitting the form
        selectedStrings = [...new Set([...selectedStrings, ...suggestedItems])];
        document.getElementById('selectedItems').value = selectedStrings.join(',');
    });

</script>
</body>
</html>
