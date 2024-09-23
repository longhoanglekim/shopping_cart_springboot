<%@ taglib prefix="th" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <link href="webjars/bootstrap/5.3.3/css/bootstrap.min.css" rel="stylesheet">
    <title>Register page</title>
</head>
<body>
<div class="container">
    <h1>Please Register</h1>
    <form method="post" id="registerForm">
        <table>
            <tr>
                <td>Enter the account:</td>
                <td><input type="text" name="username" required="required"></td>
            </tr>
            <tr>
                <td>Enter the password:</td>
                <td><input type="password" name="password" required="required"></td>
            </tr>
            <tr>
                <td>Confirm the password</td>
                <td><input type="password" name="confirmPassword" required="required"></td>
            </tr>
        </table>
        <input type="submit" value="Register" class="btn btn-success">
    </form>
</div>
<script src="webjars/bootstrap/5.3.3/js/bootstrap.min.js"></script>
<script src="webjars/jquery/3.6.0/jquery.min.js"></script>
</body>
<script>
    document.getElementById('registerForm').addEventListener('submit', function (event) {
        event.preventDefault();
        const username = document.querySelector('input[name="username"]').value;
        const password = document.querySelector('input[name="password"]').value;
        const confirmPassword = document.querySelector('input[name="confirmPassword"]').value;
        if (password !== confirmPassword) {
            alert('Password and confirm password are not the same');
            return;
        }
        fetch('/auth/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                username: username,
                password: password
            })
        }).then(response => {
            if (response.status === 200) {
                alert('Register successfully');
                window.location.href = '/login';
            } else {
                alert('Register failed');
            }
        });
    });
</script>
</html>