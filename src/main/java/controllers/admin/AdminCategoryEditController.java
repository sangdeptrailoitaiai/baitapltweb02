package controllers.admin;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import Model.Category;
import services.ICategoryService;
import services.impl.CategoryServiceImpl;
import utils.Constant; // nếu bạn có Constant.DIR, nếu không thì đổi baseDir bên dưới

@WebServlet("/admin/category/edit")
@MultipartConfig
public class AdminCategoryEditController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final ICategoryService service = new CategoryServiceImpl();

    // Mở form
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Category c = service.get(id);
        if (c == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        req.setAttribute("category", c);
        req.getRequestDispatcher("/views/admin/editcategory.jsp").forward(req, resp);
    }

    // Cập nhật
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        Part iconPart = req.getPart("icon"); // tên input trong editcategory.jsp

        Category old = service.get(id);
        if (old == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // giữ icon cũ nếu không upload mới
        String iconPath = old.getIcons();

        if (iconPart != null && iconPart.getSize() > 0) {
            String submitted = Paths.get(iconPart.getSubmittedFileName()).getFileName().toString();
            String ext = "";
            int dot = submitted.lastIndexOf('.');
            if (dot > -1) ext = submitted.substring(dot);

            String fileName = System.currentTimeMillis() + ext;

            // ĐƯỜNG DẪN LƯU FILE
            String baseDir = (Constant.DIR != null) ? Constant.DIR : "D:/uploads";
            Files.createDirectories(Paths.get(baseDir, "category"));

            try (InputStream is = iconPart.getInputStream()) {
                Files.copy(is, Paths.get(baseDir, "category", fileName),
                        StandardCopyOption.REPLACE_EXISTING);
            }
            iconPath = "category/" + fileName;
        }

        Category c = new Category();
        c.setCate_id(id);
        c.setCate_name(name);
        c.setIcons(iconPath);
        c.setUser_id(old.getUser_id()); // nếu có ràng buộc owner ở DAO

        service.edit(c);

        resp.sendRedirect(req.getContextPath() + "/admin/home");
    }
}
