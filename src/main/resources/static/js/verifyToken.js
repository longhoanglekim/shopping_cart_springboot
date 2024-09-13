function checkToken() {
    const token = localStorage.getItem('token');  // Kiểm tra token từ localStorage

    if (token) {
        // Token tồn tại, cho phép truy cập trang
        console.log("Access granted, token exists.");
        return true;  // Trả về true nếu token tồn tại
    } else {
        // Nếu không có token, chuyển hướng về trang login
        window.location.href = '/login';
        return false;  // Trả về false nếu không có token
    }
}
