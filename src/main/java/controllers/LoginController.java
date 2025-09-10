package controllers;

import java.io.IOException;

import Model.UserModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import services.IUserService;
import services.impl.UserService;
import utils.Constant;

@WebServlet(urlPatterns = { "/login" })
public class LoginController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	IUserService service = new UserService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/views/login.jsp").forward(req, resp);

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	        throws ServletException, IOException {

	    req.setCharacterEncoding("UTF-8");

	    String username = req.getParameter("username");
	    String password = req.getParameter("password");

	    if (username == null || password == null || username.isBlank() || password.isBlank()) {
	        req.setAttribute("alert", "Tài khoản hoặc mật khẩu không được rỗng");
	        req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
	        return; // <-- bắt buộc
	    }

	    var user = service.login(username.trim(), password.trim());
	    if (user == null) {
	        req.setAttribute("alert", "Tài khoản hoặc mật khẩu không đúng");
	        req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
	        return; // <-- bắt buộc
	    }

	    // Đăng nhập OK
	    HttpSession session = req.getSession(true);
	    session.setAttribute("account", user);

	    // (tùy chọn) remember me...
	    if ("on".equals(req.getParameter("remember"))) {
	        Cookie ck = new Cookie(Constant.COOKIE_REMEMBER, username.trim());
	        ck.setMaxAge(30 * 24 * 60 * 60);
	        ck.setPath(req.getContextPath().isEmpty() ? "/" : req.getContextPath());
	        resp.addCookie(ck);
	    }

	    String target;
	    switch (user.getRoleid()) {
	        case 2:  target = "/admin/home";   break; // admin
	        case 3:  target = "/manager/home"; break; // manager
	        default: target = "/user/home";    break; // user
	    }

	    resp.sendRedirect(req.getContextPath() + target);
	    return; // <-- kết thúc hẳn doPost
	}



}
