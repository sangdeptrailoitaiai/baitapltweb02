<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="Model.Category" %>
<%
    Category category = (Category) request.getAttribute("category");
    String ctx = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Sửa Category</title>
<style>
  body{font-family:Arial,Helvetica,sans-serif;background:#f5f6f8}
  .wrap{max-width:500px;margin:40px auto;background:#fff;padding:20px;border-radius:10px;box-shadow:0 2px 10px rgba(0,0,0,.06)}
  h2{margin:0 0 20px}
  label{display:block;margin-top:10px;font-weight:bold}
  input[type=text],input[type=file]{width:100%;padding:10px;border:1px solid #ddd;border-radius:8px}
  .btn{margin-top:14px;padding:10px 14px;border:none;border-radius:8px;background:#007bff;color:#fff;cursor:pointer}
  .btn:hover{background:#0056b3}
  img{max-height:60px;margin-top:6px}
  a.back{display:inline-block;margin-top:12px;text-decoration:none;color:#007bff}
</style>
</head>
<body>
<div class="wrap">
  <h2>Sửa Category</h2>
  <form method="post" action="<%=ctx%>/admin/category/edit" enctype="multipart/form-data">
    <input type="hidden" name="id" value="<%=category.getCate_id()%>">

    <label for="name">Tên Category</label>
    <input id="name" type="text" name="name" value="<%=category.getCate_name()%>" required>

    <label for="icon">Icon (ảnh mới nếu muốn đổi)</label>
    <input id="icon" type="file" name="icon" accept="image/*">

    <% if (category.getIcons() != null && !category.getIcons().isBlank()) { %>
      <p>Icon hiện tại:</p>
      <img src="<%=ctx%>/<%=category.getIcons()%>" alt="icon">
    <% } %>

    <button class="btn" type="submit">Cập nhật</button>
  </form>

  <a class="back" href="<%=ctx%>/admin/home">← Quay lại danh sách</a>
</div>
</body>
</html>
