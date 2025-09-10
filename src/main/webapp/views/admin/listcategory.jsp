<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, Model.Category, java.lang.reflect.*" %>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Danh sách Category</title>
<style>
  body{font-family:Arial,sans-serif;background:#f7f7f7}
  .wrap{max-width:900px;margin:30px auto;background:#fff;padding:20px;border-radius:8px;box-shadow:0 2px 8px rgba(0,0,0,.08)}
  h2{margin:0 0 12px}
  table{width:100%;border-collapse:collapse}
  th,td{border:1px solid #e5e5e5;padding:8px}
  th{background:#f2f2f2;text-align:left}
  img{height:36px}
  .top{display:flex;justify-content:space-between;align-items:center;margin-bottom:12px}
  .btn{display:inline-block;padding:8px 12px;border-radius:6px;text-decoration:none;background:#007bff;color:#fff}
  .btn:hover{background:#0056b3}
</style>
</head>
<body>
<div class="wrap">
  <div class="top">
    <h2>Danh sách Category (Admin)</h2>
    <a class="btn" href="<%=request.getContextPath()%>/admin/home">Về trang Admin Home</a>
  </div>

  <%
    // Lấy list từ request: ưu tiên "cateList", nếu null dùng "categories"
    List<?> list = (List<?>) request.getAttribute("cateList");
    if (list == null) list = (List<?>) request.getAttribute("categories");
    if (list == null) list = Collections.emptyList();

    // Helper reflection nhỏ để hỗ trợ 2 loại getter (id/name)
    java.util.function.Function<Object, String> getIdStr = o -> {
      try {
        try { return String.valueOf(o.getClass().getMethod("getCate_id").invoke(o)); }
        catch (NoSuchMethodException ex) { return String.valueOf(o.getClass().getMethod("getId").invoke(o)); }
      } catch (Exception e) { return ""; }
    };
    java.util.function.Function<Object, String> getNameStr = o -> {
      try {
        try { return String.valueOf(o.getClass().getMethod("getCate_name").invoke(o)); }
        catch (NoSuchMethodException ex) { return String.valueOf(o.getClass().getMethod("getName").invoke(o)); }
      } catch (Exception e) { return ""; }
    };
    java.util.function.Function<Object, String> getIconStr = o -> {
      try { return String.valueOf(o.getClass().getMethod("getIcons").invoke(o)); }
      catch (Exception e) { return ""; }
    };
  %>

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
    <%
      for (Object obj : list) {
        String id   = getIdStr.apply(obj);
        String name = getNameStr.apply(obj);
        String icon = getIconStr.apply(obj);
    %>
      <tr>
        <td><%= id %></td>
        <td><%= name %></td>
        <td>
          <%
            if (icon != null && !icon.isBlank()) {
          %>
            <img src="<%=request.getContextPath()%>/<%=icon%>" alt="icon">
          <%
            }
          %>
        </td>
        <td>
          <a class="btn" href="<%=request.getContextPath()%>/admin/category/edit?id=<%=id%>">Sửa</a>
        </td>
      </tr>
    <%
      }
      if (list.isEmpty()) {
    %>
      <tr><td colspan="4">Chưa có category nào.</td></tr>
    <%
      }
    %>
    </tbody>
  </table>
</div>
</body>
</html>
