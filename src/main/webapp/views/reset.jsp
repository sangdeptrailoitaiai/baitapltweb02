<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8" />
<title>Đặt mật khẩu mới</title>
<style>
body {
	font-family: Arial, sans-serif;
	background: #f1f1f1
}

.box {
	width: 420px;
	margin: 60px auto;
	background: #fff;
	padding: 28px;
	border-radius: 10px;
	box-shadow: 0 0 10px #ccc
}

h2 {
	text-align: center;
	margin: 0 0 18px
}

.row {
	margin: 10px 0
}

label {
	display: block;
	margin-bottom: 6px;
	font-weight: bold
}

input {
	width: 100%;
	padding: 10px;
	border: 1px solid #ddd;
	border-radius: 6px
}

button {
	width: 100%;
	padding: 12px;
	border: none;
	border-radius: 6px;
	background: #28a745;
	color: #fff;
	cursor: pointer;
	font-weight: bold
}

button:hover {
	background: #218838
}

.alert {
	color: #c0392b;
	text-align: center;
	margin-top: 8px
}
</style>
</head>
<body>
	<div class="box">
		<h2>Đặt mật khẩu mới</h2>
		<p class="alert">${alert}</p>

		<form method="post"
			action="${pageContext.request.contextPath}/reset-password">
			<input type="hidden" name="token" value="${param.token}" />
			<div class="row">
				<label for="password">Mật khẩu mới</label> <input id="password"
					name="password" type="password" minlength="6" required />
			</div>
			<div class="row">
				<label for="confirm">Nhập lại mật khẩu</label> <input id="confirm"
					name="confirm" type="password" minlength="6" required />
			</div>
			<button type="submit">Cập nhật mật khẩu</button>
		</form>
	</div>
	<script>
const p=document.getElementById('password'), c=document.getElementById('confirm');
[p,c].forEach(el=>el.addEventListener('input',()=>c.setCustomValidity(p.value===c.value?'':'Mật khẩu nhập lại chưa khớp')));
</script>
</body>
</html>
