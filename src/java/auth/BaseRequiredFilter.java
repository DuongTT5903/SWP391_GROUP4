package auth;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author yugio
 */
@WebFilter("/*") // Áp dụng cho tất cả request
public class BaseRequiredFilter implements Filter {

   private static final List<String> PUBLIC_PAGES = Arrays.asList(
            "/login", // Trang đăng nhập
            "/register", // Trang đăng ký
            "/homepage",
            "/serviceList",          
            "/AddCart",            
            "/blogDetail",
            "/blogList",
            "/reset",
            "/verify",
            "/request",
            "/viewDetailProduct"
    // Trang chủ
    // Trang chủ
    // Giới thiệu
    // Thư mục hình ảnh
    );
    private static final Map<String, String> ROLE_ALLOWED_PATHS = new HashMap<>();

    static {
        ROLE_ALLOWED_PATHS.put("1", "/admin");
        ROLE_ALLOWED_PATHS.put("2", "/manager");
        ROLE_ALLOWED_PATHS.put("3", "/staff");
        ROLE_ALLOWED_PATHS.put("4", "/customer");
    }

    /**
     *
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false); // Lấy session nếu tồn tại, không tạo mới

        // Lấy thông tin user và roleID từ session
        Object user = (session != null) ? session.getAttribute("user") : null;
        String roleID = (session != null) ? (String) session.getAttribute("roleID") : null;

        // Lấy đường dẫn hiện tại
        String path = req.getRequestURI().replace(req.getContextPath(), ""); // Xóa context path để so sánh chính xác

        // Kiểm tra xem trang có thuộc danh sách public không
        boolean isPublicPage = PUBLIC_PAGES.stream().anyMatch(path::startsWith);

        if (user == null && !isPublicPage) {
            // Nếu chưa đăng nhập, chuyển hướng về trang login
            res.sendRedirect(req.getContextPath() + "/homepage");
            return;
        }
        if (path.endsWith(".jsp")) {
            res.sendError(HttpServletResponse.SC_FORBIDDEN, "Truy cập bị từ chối");
            return;
        }

        // Nếu đã đăng nhập nhưng không phải admin, chặn truy cập trang admin
        if (user != null && (roleID == null || !"1".equals(roleID))) {
            if (path.startsWith("/admin")) { // Kiểm tra nếu truy cập trang admin
                res.sendRedirect(req.getContextPath() + "/homepage"); // Chuyển về trang user
                return;
            }
        }
        if (user != null && (roleID == null || !"2".equals(roleID))) {
            if (path.startsWith("/manager")) { // Kiểm tra nếu truy cập trang admin
                res.sendRedirect(req.getContextPath() + "/homepage"); // Chuyển về trang user
                return;
            }
        }
        if (user != null && (roleID == null || !"3".equals(roleID))) {
            if (path.startsWith("/staff")) { // Kiểm tra nếu truy cập trang admin
                res.sendRedirect(req.getContextPath() + "/homepage"); // Chuyển về trang user
                return;
            }
        }
        if (user != null && (roleID == null || !"4".equals(roleID))) {
            if (path.startsWith("/customer")) { // Kiểm tra nếu truy cập trang admin
                res.sendRedirect(req.getContextPath() + "/homepage"); // Chuyển về trang user
                return;
            }
        }

        // Tiếp tục xử lý request nếu hợp lệ
        chain.doFilter(request, response);
    }

    private boolean hasAccess(String roleID, String path) {
        String allowedPath = ROLE_ALLOWED_PATHS.get(roleID);
        return allowedPath != null && path.startsWith(allowedPath);
    }
}
