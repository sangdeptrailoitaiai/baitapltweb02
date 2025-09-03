package controllers;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Date;

import Model.UserModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.IUserService;
import services.impl.UserService;

// Nếu đã thêm BCrypt (khuyến nghị):
// import org.mindrot.jbcrypt.BCrypt;

@WebServlet(urlPatterns = { "/register" })
public class RegisterController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final IUserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String fullname = trim(req.getParameter("fullname"));
        String email    = trim(req.getParameter("email"));
        String username = trim(req.getParameter("username"));
        String password = trim(req.getParameter("password"));
        String confirm  = trim(req.getParameter("confirm"));
        String phone    = trim(req.getParameter("phone"));
        String avatar   = trim(req.getParameter("avatar"));

        // 1) validate cơ bản
        if (isBlank(fullname) || isBlank(email) || isBlank(username) || isBlank(password) || isBlank(confirm)) {
            req.setAttribute("alert", "Vui lòng điền đầy đủ các trường bắt buộc.");
            req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
            return;
        }
        if (!password.equals(confirm)) {
            req.setAttribute("alert", "Mật khẩu nhập lại chưa khớp.");
            req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
            return;
        }
        if (username.length() < 3) {
            req.setAttribute("alert", "Tên đăng nhập phải từ 3 ký tự trở lên.");
            req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
            return;
        }
        if (password.length() < 6) {
            req.setAttribute("alert", "Mật khẩu phải từ 6 ký tự trở lên.");
            req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
            return;
        }

        // 2) kiểm tra trùng username/email
        if (userService.FindByUsername(username) != null) {
            req.setAttribute("alert", "Tên đăng nhập đã tồn tại.");
            req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
            return;
        }
        if (userService.findByEmail(email) != null) {
            req.setAttribute("alert", "Email đã được sử dụng.");
            req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
            return;
        }
        

        // 3) hash mật khẩu (khuyến nghị). Nếu bạn CHƯA thêm BCrypt, tạm dùng plaintext (không khuyến nghị)
        // String hashed = BCrypt.hashpw(password, BCrypt.gensalt(10));
        String hashed = password; // TODO: thay bằng dòng BCrypt ở trên sau khi thêm dependency
        String roleStr = trim(req.getParameter("roleid"));
        int roleid;

        try {
            roleid = Integer.parseInt(roleStr);
        } catch (Exception e) {
            req.setAttribute("alert", "Role ID không hợp lệ. Vui lòng chọn từ 1 đến 5.");
            req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
            return;
        }

        if (roleid < 1 || roleid > 5) {
            req.setAttribute("alert", "Role ID phải trong khoảng 1–5.");
            req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
            return;
        }


        // 4) build model & insert
        UserModel user = new UserModel();
        user.setEmail(email);
        user.setUsername(username);
        user.setFullname(fullname);
        user.setPassword(hashed);
        user.setAvatar(isBlank(avatar) ? null : avatar);
        user.setRoleid(roleid); // default role user
        user.setPhone(isBlank(phone) ? null : phone);
        user.setCreatedDate(new Date(System.currentTimeMillis())); // nếu DB tự set theo default GETDATE() thì có thể để null

        boolean ok = userService.register(user);

        if (ok) {
            // chuyển về login với thông báo
            String msg = URLEncoder.encode("Đăng ký thành công, hãy đăng nhập.", StandardCharsets.UTF_8);
            resp.sendRedirect(req.getContextPath() + "/login?success=" + msg);
        } else {
            req.setAttribute("alert", "Không thể tạo tài khoản (lỗi hệ thống).");
            req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
        }
    }

    private static String trim(String s) { return s == null ? null : s.trim(); }
    private static boolean isBlank(String s) { return s == null || s.trim().isEmpty(); }
}
