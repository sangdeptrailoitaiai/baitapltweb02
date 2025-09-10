package controllers.admin;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import Model.Category;
import Model.UserModel; // nếu muốn gán owner
import services.ICategoryService;
import services.impl.CategoryServiceImpl;

@WebServlet("/admin/category/create")
@MultipartConfig
public class AdminCategoryCreateController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final ICategoryService service = new CategoryServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String name = req.getParameter("cate_name");
        Part iconPart = req.getPart("icons");

        // Lưu file icon (đổi đường dẫn cho phù hợp máy bạn)
        String baseDir = "D:/uploads";            // <- CHỈNH THỰC TẾ
        Files.createDirectories(Paths.get(baseDir, "category"));

        String iconPath = null;
        if (iconPart != null && iconPart.getSize() > 0) {
            String submitted = Paths.get(iconPart.getSubmittedFileName()).getFileName().toString();
            String ext = "";
            int dot = submitted.lastIndexOf('.');
            if (dot > -1) ext = submitted.substring(dot);
            String fileName = System.currentTimeMillis() + ext;

            try (InputStream is = iconPart.getInputStream()) {
                Files.copy(is, Paths.get(baseDir, "category", fileName),
                           StandardCopyOption.REPLACE_EXISTING);
            }
            iconPath = "category/" + fileName; // để <img src="${ctx}/${icons}">
        }

        Category c = new Category();
        c.setCate_name(name);
        c.setIcons(iconPath);

        // Nếu bảng Category có cột user_id NOT NULL, gán owner:
        UserModel acc = (UserModel) req.getSession().getAttribute("account");
        if (acc != null) c.setUser_id(acc.getId());

        service.add(c); // yêu cầu ICategoryService có hàm add

        resp.sendRedirect(req.getContextPath() + "/admin/home");
    }
}
