package controllers.admin;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import services.ICategoryService;
import services.impl.CategoryServiceImpl;

@WebServlet("/admin/home")
public class AdminHomeController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final ICategoryService svc = new CategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // gọi instance svc thay vì gọi interface
        req.setAttribute("categories", svc.findAll());
        req.getRequestDispatcher("/views/admin/home.jsp").forward(req, resp);
    }
}
