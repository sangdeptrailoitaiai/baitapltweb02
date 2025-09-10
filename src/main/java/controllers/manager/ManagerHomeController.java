package controllers.manager;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import Model.UserModel;
import services.ICategoryService;
import services.impl.CategoryServiceImpl;

@WebServlet(urlPatterns = {"/manager/home"})
public class ManagerHomeController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final ICategoryService categoryService = new CategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Lấy user đăng nhập
        UserModel acc = (UserModel) req.getSession().getAttribute("account");
        if (acc == null) { resp.sendRedirect(req.getContextPath()+"/login"); return; }

        // manager chỉ xem category của CHÍNH MÌNH
        req.setAttribute("categories", categoryService.findByUser(acc.getId()));

        // dùng chung list.jsp
        req.getRequestDispatcher("/views/category/list.jsp").forward(req, resp);
        // Hoặc forward /views/manager/home.jsp nếu bạn có file riêng
    }
}
