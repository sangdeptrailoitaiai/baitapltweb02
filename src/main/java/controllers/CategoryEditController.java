//package controllers;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.List;
//
//import Model.Category;
//import jakarta.servlet.RequestDispatcher;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import services.CategoryService;
//import services.impl.CategoryServiceImpl;
//import org.apache.commons.fileupload.FileItem;
//import org.apache.commons.fileupload.FileUploadException;
//import org.apache.commons.fileupload.disk.DiskFileItemFactory;
//import org.apache.commons.fileupload.servlet.ServletFileUpload;
//
//
//@WebServlet(urlPatterns = { "/admin/category/edit" })
//public class CategoryEditController extends HttpServlet {
//
//	private static final long serialVersionUID = 1L;
//	CategoryService cateService = new CategoryServiceImpl();
//
//	@Override
//	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		String id = req.getParameter("id");
//		Category category = cateService.get(Integer.parseInt(id));
//		req.setAttribute("category", category);
//		RequestDispatcher dispatcher = req.getRequestDispatcher("/views/admin/editcategory.jsp");
//		dispatcher.forward(req, resp);
//	}
//
//	@Override
//	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		Category category = new Category();
//		DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
//		ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
//		servletFileUpload.setHeaderEncoding("UTF-8");
//		try {
//			resp.setContentType("text/html");
//			resp.setCharacterEncoding("UTF-8");
//			req.setCharacterEncoding("UTF-8");
//			List<FileItem> items = servletFileUpload.parseRequest(req);
//			for (FileItem item : items) {
//				if (item.getFieldName().equals("id")) {
//					category.setId(Integer.parseInt(item.getString()));
//				} else if (item.getFieldName().equals("name")) {
//					category.setName(item.getString("UTF-8"));
//				} else if (item.getFieldName().equals("icon")) {
//					if (item.getSize() > 0) {// neu co file d
//						String originalFileName = item.getName();
//						int index = originalFileName.lastIndexOf(".");
//						String ext = originalFileName.substring(index + 1);
//						String fileName = System.currentTimeMillis() + "." + ext;
//						File file = new File(Constant.DIR + "/category/" + fileName);
//						item.write(file);
//						category.setIcons("category/" + fileName);
//					} else {
//						category.setIcons(null);
//					}
//				}
//			}
//			cateService.edit(category);
//			resp.sendRedirect(req.getContextPath() + "/admin/category/list");
//		} catch (FileUploadException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//}