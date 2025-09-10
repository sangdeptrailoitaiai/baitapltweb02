//package controllers;
//
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
//import services.ICategoryService;
//import services.impl.CategoryServiceImpl;
//
//@WebServlet(urlPatterns = { "/admin/category/list" })
//public class CategoryController extends HttpServlet {
//
//	private static final long serialVersionUID = 1L;
//	ICategoryService cateService = new CategoryServiceImpl();
//
//	@Override
//	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		List<Category> cateList = cateService.getAll();
//		req.setAttribute("cateList", cateList);
//		RequestDispatcher dispatcher = req.getRequestDispatcher("/views/admin/listcategory.jsp");
//		dispatcher.forward(req, resp);
//	}
//
//}
