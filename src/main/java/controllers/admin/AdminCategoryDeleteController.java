package controllers.admin;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import services.ICategoryService;
import services.impl.CategoryServiceImpl;

@WebServlet("/admin/category/delete")
public class AdminCategoryDeleteController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final ICategoryService service = new CategoryServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));

        // Admin xoá tự do: ownerId = 0 (DAO đã hỗ trợ)
        service.delete(id, 0);

        resp.sendRedirect(req.getContextPath() + "/admin/home");
    }
}
