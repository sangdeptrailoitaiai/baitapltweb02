<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Admin • Quản trị Category</title>
<style>
body {
	font-family: Arial, Helvetica, sans-serif;
	background: #f5f6f8
}

.wrap {
	max-width: 1000px;
	margin: 28px auto;
	background: #fff;
	padding: 20px;
	border-radius: 10px;
	box-shadow: 0 2px 10px rgba(0, 0, 0, .06)
}

h2 {
	margin: 0 0 14px
}

.grid {
	display: grid;
	grid-template-columns: 1fr 1fr;
	gap: 16px
}

.card {
	border: 1px solid #eaeaea;
	border-radius: 10px;
	padding: 16px
}

.card h3 {
	margin: 0 0 12px
}

label {
	display: block;
	margin-bottom: 6px;
	font-weight: 600
}

input[type=text], input[type=file] {
	width: 100%;
	padding: 10px;
	border: 1px solid #ddd;
	border-radius: 8px
}

.btn, button {
	padding: 9px 12px;
	border: none;
	border-radius: 8px;
	background: #007bff;
	color: #fff;
	text-decoration: none;
	cursor: pointer
}

.btn:hover, button:hover {
	background: #0056b3
}

.btn-danger {
	background: #dc3545
}

.btn-danger:hover {
	background: #b02a37
}

table {
	width: 100%;
	border-collapse: collapse
}

th, td {
	border: 1px solid #eee;
	padding: 8px
}

th {
	background: #f9fafb;
	text-align: left
}

img {
	height: 40px
}

.actions form {
	display: inline;
	margin: 0 4px
}

.topbar {
	display: flex;
	justify-content: space-between;
	align-items: center;
	margin-bottom: 12px
}

.muted {
	color: #666
}
</style>
</head>
<body>
	<div class="wrap">
		<div class="topbar">
			<h2>Trang quản trị Category</h2>
			<div class="muted">
				<a class="btn" href="${pageContext.request.contextPath}/logout">Đăng
					xuất</a>
			</div>
		</div>

		<div class="grid">
			<!-- Form thêm mới -->
			<div class="card">
				<h3>Thêm Category</h3>
				<form method="post"
					action="${pageContext.request.contextPath}/admin/category/create"
					enctype="multipart/form-data">
					<input type="text" name="cate_name" required> <input
						type="file" name="icons" accept="image/*">
					<button type="submit">+ Thêm</button>
				</form>


			</div>

			<!-- Bảng danh sách -->
			<div class="card">
				<h3>Danh sách Category</h3>
				<table>
					<thead>
						<tr>
							<th>ID</th>
							<th>Tên</th>
							<th>Icon</th>
							<th>Thao tác</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="c" items="${categories}">
							<tr>
								<td>${c.cate_id}</td>
								<td>${c.cate_name}</td>
								<td><c:if test="${not empty c.icons}">
										<img src="${pageContext.request.contextPath}/${c.icons}"
											alt="icon">
									</c:if></td>
								<td class="actions"><a class="btn"
									href="${pageContext.request.contextPath}/admin/category/edit?id=${c.cate_id}">Sửa</a>
									<form method="post"
										action="${pageContext.request.contextPath}/admin/category/delete"
										onsubmit="return confirm('Xoá mục này?')">
										<input type="hidden" name="id" value="${c.cate_id}">
										<button class="btn-danger" type="submit">Xoá</button>
									</form></td>
							</tr>
						</c:forEach>

						<c:if test="${empty categories}">
							<tr>
								<td colspan="4">Chưa có category nào.</td>
							</tr>
						</c:if>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>
</html>
