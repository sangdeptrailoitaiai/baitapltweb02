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
	    resp.setCharacterEncoding("UTF-8");

	    String username = req.getParameter("username");
	    String password = req.getParameter("password");

	    if (username == null || password == null) {
	        req.setAttribute("alert", "Thiếu trường username/password");
	        req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
	        return;
	    }
	    username = username.trim();
	    password = password.trim();

	    if (username.isEmpty() || password.isEmpty()) {
	        req.setAttribute("alert", "Tài khoản hoặc mật khẩu không được rỗng");
	        req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
	        return;
	    }

	    UserModel user = service.login(username, password); // bên dưới nhớ xử lý hash/trim

	    if (user != null) {
	        HttpSession session = req.getSession(true);
	        session.setAttribute("account", user);

	        if ("on".equals(req.getParameter("remember"))) {
	            Cookie cookie = new Cookie(Constant.COOKIE_REMEMBER, username);
	            cookie.setMaxAge(30 * 24 * 60 * 60); // 30 ngày
	            cookie.setPath(req.getContextPath().isEmpty() ? "/" : req.getContextPath());
	            resp.addCookie(cookie);
	        }
	        resp.sendRedirect(req.getContextPath() + "/waiting");
	    } else {
	        req.setAttribute("alert", "Tài khoản hoặc mật khẩu không đúng");
	        req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
	    }
	}


}
