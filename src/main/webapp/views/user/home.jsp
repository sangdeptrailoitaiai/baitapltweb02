<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, Model.Category, Model.UserModel" %>
<%
  String ctx = request.getContextPath();
  UserModel acc = (UserModel) session.getAttribute("account");
  if (acc == null) { response.sendRedirect(ctx + "/login"); return; }

  // danh sách categories do controller set: "categories"
  List<Category> categories = (List<Category>) request.getAttribute("categories");
  if (categories == null) categories = java.util.Collections.emptyList();
%>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>User • Danh sách Category</title>
<style>
  body{font-family:Arial,sans-serif;background:#f7f7f7}
  .wrap{max-width:960px;margin:30px auto;background:#fff;padding:20px;border-radius:10px;box-shadow:0 2px 8px rgba(0,0,0,.08)}
  .top{display:flex;justify-content:space-between;align-items:center;margin-bottom:14px}
  h2{margin:0}
  .btn{display:inline-block;padding:9px 12px;border-radius:6px;text-decoration:none;background:#007bff;color:#fff}
  .btn:hover{background:#0056b3}
  .btn-danger{background:#dc3545}
  .btn-danger:hover{background:#b02a37}
  table{width:100%;border-collapse:collapse}
  th,td{border:1px solid #e5e5e5;padding:8px}
  th{background:#f2f2f2;text-align:left}
  img{height:36px}
  .actions form{display:inline;margin:0 4px}
</style>
</head>
<body>
<div class="wrap">
  <div class="top">
    <h2>Danh sách Category (User)</h2>
    <div>
      &nbsp; <a class="btn" href="<%=ctx%>/logout">Đăng xuất</a>
    </div>
  </div>

  <table>
    <thead>
      <tr>
        <th>ID</th>
        <th>Tên</th>
        <th>Icon</th>
        <th>Owner</th>
        <th>Thao tác</th>
      </tr>
    </thead>
    <tbody>
      <%
        for (Category c : categories) {
          int ownerId = -1;
          // hỗ trợ cả model có/không có user_id (nếu bạn chưa thêm user_id thì sẽ để trống)
          try { ownerId = (int) c.getClass().getMethod("getUser_id").invoke(c); } catch (Exception ignore) {}
          boolean mine = (ownerId != -1 && ownerId == acc.getId());
      %>
        <tr>
          <td><%= c.getCate_id() %></td>
          <td><%= c.getCate_name() %></td>
          <td>
            <% if (c.getIcons() != null && !c.getIcons().isBlank()) { %>
              <img src="<%=ctx%>/<%=c.getIcons()%>" alt="icon">
            <% } %>
          </td>
          <td><%= ownerId == -1 ? "" : ownerId %></td>
          <td class="actions">
            <% if (mine) { %>
              <a class="btn" href="<%=ctx%>/user/category/edit?id=<%=c.getCate_id()%>">Sửa</a>
              <form method="post" action="<%=ctx%>/user/category/delete" onsubmit="return confirm('Xoá category này?')">
                <input type="hidden" name="id" value="<%=c.getCate_id()%>">
                <button class="btn btn-danger" type="submit">Xoá</button>
              </form>
            <% } %>
          </td>
        </tr>
      <% } %>

      <% if (categories.isEmpty()) { %>
        <tr><td colspan="5">Chưa có category nào.</td></tr>
      <% } %>
    </tbody>
  </table>
</div>
</body>
</html>
