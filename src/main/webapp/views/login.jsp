<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Đăng nhập hệ thống</title>
<style>
body {
	font-family: Arial, sans-serif;
	background: #f1f1f1;
}

.login-box {
	width: 350px;
	margin: 100px auto;
	background: #fff;
	padding: 30px;
	border-radius: 10px;
	box-shadow: 0px 0px 10px #ccc;
}

.login-box h2 {
	text-align: center;
	margin-bottom: 20px;
}

.login-box input {
	width: 100%;
	padding: 10px;
	margin: 8px 0;
	border: 1px solid #ddd;
	border-radius: 5px;
}

.login-box button {
	width: 100%;
	padding: 10px;
	background: #007BFF;
	color: #fff;
	border: none;
	border-radius: 5px;
	cursor: pointer;
}

.login-box button:hover {
	background: #0056b3;
}

.register-btn {
	width: 100%;
	padding: 10px;
	margin-top: 10px;
	background: #28a745;
	color: #fff;
	border: none;
	border-radius: 5px;
	cursor: pointer;
	text-align: center;
	display: block;
	text-decoration: none;
}

.register-btn:hover {
	background: #218838;
}

.alert {
	color: red;
	text-align: center;
}
</style>
</head>
<body>
	<div class="login-box">
		<h2>Đăng nhập</h2>

		<form method="post" action="${pageContext.request.contextPath}/login">
			<input type="text" name="username" placeholder="Tên đăng nhập"
				required> <input type="password" name="password"
				placeholder="Mật khẩu" required> <label><input
				type="checkbox" name="remember"> Ghi nhớ đăng nhập</label>
			<button type="submit">Đăng nhập</button>
		</form>

		<!-- Nút sang trang đăng ký -->
		<a href="${pageContext.request.contextPath}/Register.jsp"
			class="register-btn">Đăng ký tài khoản</a>

		<p class="alert">${alert}</p>
	</div>
</body>
</html>