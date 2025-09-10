package controllers;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import utils.Constant;

@WebServlet(urlPatterns = { "/logout" })
public class LogoutController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// Hủy session
		HttpSession session = req.getSession(false);
		if (session != null) {
			session.invalidate();
		}

		// Xóa cookie "remember me" nếu có
		Cookie[] cookies = req.getCookies();
		if (cookies != null) {
			for (Cookie c : cookies) {
				if (Constant.COOKIE_REMEMBER.equals(c.getName())) {
					c.setValue("");
					c.setMaxAge(0);
					c.setPath(req.getContextPath().isEmpty() ? "/" : req.getContextPath());
					resp.addCookie(c);
				}
			}
		}

		// Chuyển về trang login
		resp.sendRedirect(req.getContextPath() + "/login");
	}
}
