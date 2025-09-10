<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Trang quản trị</title>
<style>
body {
	font-family: Arial, sans-serif;
	background: #f4f6f8;
	margin: 0;
	padding: 0;
}

.container {
	max-width: 800px;
	margin: 60px auto;
	background: #fff;
	padding: 40px;
	border-radius: 12px;
	box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
	text-align: center;
}

h1 {
	color: #111827;
	margin-bottom: 24px;
}

.btn {
	display: inline-block;
	padding: 10px 20px;
	margin-top: 20px;
	border-radius: 6px;
	text-decoration: none;
	background: #111827;
	color: #fff;
	font-weight: bold;
	transition: background 0.2s;
}

.btn:hover {
	background: #1f2937;
}
</style>
</head>
<body>
	<div class="container">
		<h1>Chào mừng Quản trị viên 👑</h1>
		<p>Bạn đã đăng nhập thành công vào trang quản trị.</p>

		<a href="<c:url value='/login'/>" class="btn">Đăng xuất</a>
	</div>
</body>
</html>
