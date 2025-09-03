package controllers;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.IUserService;
import services.impl.UserService;

@WebServlet(urlPatterns = {"/reset-password"})
public class ResetPasswordController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final IUserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getParameter("token");
        if (token == null || token.isBlank() || userService.isResetTokenExpired(token)) {
            req.setAttribute("alert", "Liên kết không hợp lệ hoặc đã hết hạn.");
            req.getRequestDispatcher("/views/forgot.jsp").forward(req, resp);
            return;
        }
        req.getRequestDispatcher("/views/reset.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8"); resp.setCharacterEncoding("UTF-8");

        String token = req.getParameter("token");
        String pw    = req.getParameter("password");
        String cf    = req.getParameter("confirm");

        if (token==null || token.isBlank() || pw==null || cf==null || pw.isBlank() || cf.isBlank()) {
            req.setAttribute("alert", "Thiếu dữ liệu.");
            req.getRequestDispatcher("/views/reset.jsp").forward(req, resp);
            return;
        }
        if (!pw.equals(cf)) {
            req.setAttribute("alert", "Mật khẩu nhập lại chưa khớp.");
            req.getRequestDispatcher("/views/reset.jsp").forward(req, resp);
            return;
        }
        if (userService.isResetTokenExpired(token)) {
            req.setAttribute("alert", "Liên kết đã hết hạn.");
            req.getRequestDispatcher("/views/forgot.jsp").forward(req, resp);
            return;
        }

        boolean ok = userService.resetPassword(token, pw);
        if (ok) {
            String msg = URLEncoder.encode("Cập nhật mật khẩu thành công. Hãy đăng nhập.", StandardCharsets.UTF_8);
            resp.sendRedirect(req.getContextPath()+"/login?success="+msg);
        } else {
            req.setAttribute("alert", "Không thể cập nhật mật khẩu.");
            req.getRequestDispatcher("/views/reset.jsp").forward(req, resp);
        }
    }
}
