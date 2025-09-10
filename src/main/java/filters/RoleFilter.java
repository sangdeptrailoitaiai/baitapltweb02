package filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import java.io.IOException;
import Model.UserModel;

@WebFilter({"/user/*","/admin/*","/manager/*"})
public class RoleFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        UserModel acc = (UserModel) req.getSession().getAttribute("account");
        if (acc == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String uri = req.getRequestURI();
        int role = acc.getRoleid();
        String ctx = req.getContextPath();

        boolean ok =
            (uri.startsWith(ctx+"/user/")    && role == 1) ||
            (uri.startsWith(ctx+"/admin/")   && role == 2) ||
            (uri.startsWith(ctx+"/manager/") && role == 3);

        if (!ok) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Không đủ quyền truy cập");
            return;
        }

        chain.doFilter(request, response);
    }
}
