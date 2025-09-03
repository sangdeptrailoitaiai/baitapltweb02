package controllers;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import Model.UserModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.IUserService;
import services.impl.UserService;

@WebServlet(urlPatterns = {"/forgot"})
public class ForgotPasswordController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final IUserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/views/forgot.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8"); resp.setCharacterEncoding("UTF-8");

        String identifier = trim(req.getParameter("identifier"));
        if (isBlank(identifier)) {
            req.setAttribute("alert", "Vui lòng nhập email hoặc username.");
            req.getRequestDispatcher("/views/forgot.jsp").forward(req, resp);
            return;
        }

        // tìm theo email trước, không có thì theo username
        UserModel user = userService.findByEmail(identifier);
        if (user == null) user = userService.FindByUsername(identifier);
        if (user == null) {
            req.setAttribute("alert", "Không tìm thấy tài khoản.");
            req.getRequestDispatcher("/views/forgot.jsp").forward(req, resp);
            return;
        }

        String token = UUID.randomUUID().toString().replace("-", "");
        // hết hạn sau 30 phút
        boolean ok = userService.setResetToken(user.getId(), token, 30);
        if (!ok) {
            req.setAttribute("alert", "Không thể tạo yêu cầu đặt lại mật khẩu. Thử lại sau.");
            req.getRequestDispatcher("/views/forgot.jsp").forward(req, resp);
            return;
        }

        String base = req.getScheme()+"://"+req.getServerName()+":"+req.getServerPort()+req.getContextPath();
        String link = base + "/reset-password?token=" + URLEncoder.encode(token, StandardCharsets.UTF_8);

        // TODO: nếu đã cấu hình SMTP, gửi email ở đây (utils.MailUtil.send(...))
        // MailUtil.send(user.getEmail(), "Đặt lại mật khẩu", "Nhấp vào liên kết: " + link);

        req.setAttribute("success", "Đã tạo liên kết đặt lại. Vui lòng kiểm tra email.");
        // để tiện TEST khi chưa có SMTP, show link trên trang:
        req.setAttribute("testLink", link);
        req.getRequestDispatcher("/views/forgot.jsp").forward(req, resp);
    }

    private static String trim(String s){ return s==null?null:s.trim(); }
    private static boolean isBlank(String s){ return s==null || s.trim().isEmpty(); }
}
