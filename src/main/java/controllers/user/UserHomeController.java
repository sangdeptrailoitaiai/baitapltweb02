package controllers.user;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import Model.UserModel;
import services.ICategoryService;
import services.impl.CategoryServiceImpl;

@WebServlet("/user/home")
public class UserHomeController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final ICategoryService svc = new CategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setAttribute("categories", svc.findAll());


        req.getRequestDispatcher("/views/user/home.jsp").forward(req, resp);
    }
}
