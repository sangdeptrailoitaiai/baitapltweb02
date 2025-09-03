<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8" />
<title>Đăng ký tài khoản</title>
<style>
body {
	font-family: Arial, sans-serif;
	background: #f1f1f1;
}

.box {
	width: 420px;
	margin: 60px auto;
	background: #fff;
	padding: 28px;
	border-radius: 10px;
	box-shadow: 0 0 10px #ccc;
}

.box h2 {
	text-align: center;
	margin: 0 0 18px;
}

.box .row {
	margin: 10px 0;
}

.box label {
	display: block;
	margin-bottom: 6px;
	font-weight: bold;
}

.box input {
	width: 100%;
	padding: 10px;
	border: 1px solid #ddd;
	border-radius: 6px;
}

.box .hint {
	font-size: 12px;
	color: #777;
	margin-top: 4px;
}

.box .flex {
	display: flex;
	gap: 10px;
}

.box button {
	width: 100%;
	padding: 12px;
	border: none;
	border-radius: 6px;
	background: #28a745;
	color: #fff;
	cursor: pointer;
	font-weight: bold;
}

.box button:hover {
	background: #218838;
}

.link-btn {
	display: block;
	text-align: center;
	margin-top: 10px;
	padding: 10px;
	border-radius: 6px;
	background: #007BFF;
	color: #fff;
	text-decoration: none;
}

.link-btn:hover {
	background: #0056b3;
}

.alert {
	color: #c0392b;
	text-align: center;
	margin-top: 8px;
}

.success {
	color: #2ecc71;
	text-align: center;
	margin-top: 8px;
}

.pw-mismatch {
	color: #e67e22;
	font-size: 13px;
	margin-top: 6px;
	display: none;
}
</style>
</head>
<body>
	<div class="box">
		<h2>Đăng ký</h2>

		<!-- Thông báo từ server -->
		<p class="alert">${alert}</p>
		<p class="success">${success}</p>

		<!-- LƯU Ý: đổi action cho khớp servlet của bạn (ví dụ /register) -->
		<form method="post"
			action="${pageContext.request.contextPath}/register"
			accept-charset="UTF-8" novalidate>
			<div class="row">
				<label for="fullname">Họ và tên</label> <input id="fullname"
					name="fullname" type="text" required value="${param.fullname}"
					placeholder="Ví dụ: Trương Tấn Sang" />
			</div>

			<div class="row">
				<label for="email">Email</label> <input id="email" name="email"
					type="email" required value="${param.email}"
					placeholder="you@example.com" />
				<div class="hint">Dùng email hợp lệ để khôi phục mật khẩu khi
					cần.</div>
			</div>

			<div class="row">
				<label for="username">Tên đăng nhập</label> <input id="username"
					name="username" type="text" required minlength="3" maxlength="30"
					value="${param.username}"
					placeholder="Tên đăng nhập (không dấu, không khoảng trắng)" />
			</div>

			<div class="flex">
				<div class="row" style="flex: 1">
					<label for="password">Mật khẩu</label> <input id="password"
						name="password" type="password" required minlength="6"
						placeholder="Tối thiểu 6 ký tự" />
				</div>
				<div class="row" style="flex: 1">
					<label for="confirm">Nhập lại mật khẩu</label> <input id="confirm"
						name="confirm" type="password" required minlength="6"
						placeholder="Nhập lại giống mật khẩu" />
				</div>
			</div>
			<div id="pwMsg" class="pw-mismatch">Mật khẩu nhập lại chưa
				khớp.</div>
			<!-- ROLE (1–USER, 2–ADMIN, 3–MANAGER, 4–SELLER, 5–SHIPPER) -->
			<div class="row">
				<label for="roleid">Quyền </label> <select id="roleid" name="roleid"
					required>
					<option value="">-- Chọn role --</option>
					<option value="1">1 – USER</option>
					<option value="2">2 – ADMIN</option>
					<option value="3">3 – MANAGER</option>
					<option value="4">4 – SELLER</option>
					<option value="5">5 – SHIPPER</option>
				</select>
				<div class="hint">Chỉ chọn các giá trị 1–5 theo bảng role của
					bạn.</div>
			</div>

			<div class="row">
				<label for="phone">Số điện thoại (tuỳ chọn)</label> <input
					id="phone" name="phone" type="text" pattern="\\+?\\d{8,15}"
					value="${param.phone}" placeholder="+84xxxxxxxxx" />
			</div>

			<div class="row">
				<label for="avatar">Ảnh đại diện (URL – tuỳ chọn)</label> <input
					id="avatar" name="avatar" type="url" value="${param.avatar}"
					placeholder="https://link-to-your-avatar.jpg" />
			</div>

			<button id="btnSubmit" type="submit">Tạo tài khoản</button>
		</form>

		<a class="link-btn" href="${pageContext.request.contextPath}/login">Đã
			có tài khoản? Đăng nhập</a>
	</div>

	<script>
    (function () {
      const pw = document.getElementById('password');
      const cf = document.getElementById('confirm');
      const msg = document.getElementById('pwMsg');
      const btn = document.getElementById('btnSubmit');

      function check() {
        if (!pw.value || !cf.value) { msg.style.display = 'none'; btn.disabled = false; return; }
        if (pw.value !== cf.value) { msg.style.display = 'block'; btn.disabled = true; }
        else { msg.style.display = 'none'; btn.disabled = false; }
      }
      pw.addEventListener('input', check);
      cf.addEventListener('input', check);
    })();
  </script>
</body>
</html>
