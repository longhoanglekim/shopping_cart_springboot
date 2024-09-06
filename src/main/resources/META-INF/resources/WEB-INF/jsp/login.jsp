<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
</head>
<body>
<h2>Login</h2>
<form id="loginForm">
    <label for="username">Username:</label>
    <input type="text" id="username" name="username" required>
    <br>
    <label for="password">Password:</label>
    <input type="password" id="password" name="password" required>
    <br>
    <button type="submit">Login</button>
</form>

<script>
    document.getElementById('loginForm').addEventListener('submit', async function(event) {
        event.preventDefault();

        // Lấy dữ liệu từ form
        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;

        try {
            const response = await fetch('http://localhost:8080/auth/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ username, password })
            });

            if (response.ok) {
                const data = await response.json();
                alert('Login successful! Token: ' + data.token);

                // Lưu JWT vào localStorage để sử dụng sau này
                localStorage.setItem('token', data.token);

                // Redirect tới trang khác sau khi đăng nhập thành công
                window.location.href = '/welcome';
            } else {
                const errorData = await response.json();
                alert('Login failed! ' + errorData.message);
            }
        } catch (error) {
            console.error('Error:', error);
            alert('Login failed! Please check the console for more information.');
        }
    });
</script>