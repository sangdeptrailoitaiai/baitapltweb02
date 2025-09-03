<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8"/>
<title>Quên mật khẩu</title>
<style>
body{font-family:Arial,sans-serif;background:#f1f1f1}
.box{width:420px;margin:60px auto;background:#fff;padding:28px;border-radius:10px;box-shadow:0 0 10px #ccc}
h2{text-align:center;margin:0 0 18px}
.row{margin:10px 0}
label{display:block;margin-bottom:6px;font-weight:bold}
input{width:100%;padding:10px;border:1px solid #ddd;border-radius:6px}
button{width:100%;padding:12px;border:none;border-radius:6px;background:#007BFF;color:#fff;cursor:pointer;font-weight:bold}
button:hover{background:#0056b3}
.alert{color:#c0392b;text-align:center;margin-top:8px}
.success{color:#2ecc71;text-align:center;margin-top:8px}
.link{display:block;text-align:center;margin-top:10px}
.token{margin-top:10px;padding:10px;background:#f6f8fa;border:1px dashed #bbb;border-radius:6px;font-family:monospace}
</style>
</head>
<body>
<div class="box">
  <h2>Quên mật khẩu</h2>
  <p class="alert">${alert}</p>
  <p class="success">${success}</p>

  <form method="post" action="${pageContext.request.contextPath}/forgot">
    <div class="row">
      <label for="identifier">Email hoặc Username</label>
      <input id="identifier" name="identifier" type="text" required value="${param.identifier}" placeholder="Nhập email hoặc tên đăng nhập"/>
    </div>
    <button type="submit">Gửi liên kết đặt lại</button>
  </form>

  <!-- Hiển thị token/link để TEST nếu chưa cấu hình SMTP -->
  <c:if test="${not empty testLink}">
    <div class="token">
      Link đặt lại (test): <a href="${testLink}">${testLink}</a>
    </div>
  </c:if>

  <a class="link" href="${pageContext.request.contextPath}/login">Quay lại đăng nhập</a>
</div>
</body>
</html>
