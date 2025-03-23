package controller;

import dal.SettingDBContext;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Role;
import model.RoleStatus;

public class AdminSettingList extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SettingDBContext db = new SettingDBContext();
        String path = request.getServletPath();

        if (path.equals("/admin/settingList")) {
            // Kiểm tra xem có tham số roleID và status không (cho cập nhật trạng thái)
            String roleIdParam = request.getParameter("roleID");
            String statusParam = request.getParameter("status");

            if (roleIdParam != null && statusParam != null) {
                try {
                    int roleID = Integer.parseInt(roleIdParam);
                    boolean status = Boolean.parseBoolean(statusParam);
                    db.updateRoleStatus(roleID, status); // Cập nhật trạng thái
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }

            // Lấy danh sách roles và forward về roles.jsp
            List<Role> roles = db.getRolesWithStatus();
            request.setAttribute("roles", roles);
            request.getRequestDispatcher("/admin/roles.jsp").forward(request, response);

        } else if (path.equals("/admin/addRole")) {
            // Hiển thị trang addRole.jsp
            request.getRequestDispatcher("/admin/addRole.jsp").forward(request, response);

        } else if (path.equals("/admin/editRole")) {
            // Xử lý yêu cầu hiển thị form chỉnh sửa role
            String roleIdParam = request.getParameter("id");
            if (roleIdParam != null) {
                try {
                    int roleID = Integer.parseInt(roleIdParam);
                    Role role = db.getRoleById(roleID); // Lấy role theo ID
                    if (role != null) {
                        request.setAttribute("role", role);
                        request.getRequestDispatcher("/admin/editRole.jsp").forward(request, response);
                    } else {
                        response.sendError(HttpServletResponse.SC_NOT_FOUND, "Role not found");
                    }
                } catch (NumberFormatException e) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Role ID");
                }
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Role ID is required");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SettingDBContext db = new SettingDBContext();
        String path = request.getServletPath();

        if (path.equals("/admin/settingList")) {
            // Xử lý cập nhật trạng thái role
            String roleIdParam = request.getParameter("roleID");
            String currentStatusParam = request.getParameter("currentStatus");

            if (roleIdParam != null && currentStatusParam != null) {
                try {
                    int roleID = Integer.parseInt(roleIdParam);
                    boolean currentStatus = Boolean.parseBoolean(currentStatusParam);
                    boolean newStatus = !currentStatus; // Đảo trạng thái
                    db.updateRoleStatus(roleID, newStatus); // Cập nhật trạng thái
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }

            // Tải lại danh sách roles
            List<Role> roles = db.getRolesWithStatus();
            request.setAttribute("roles", roles);
            request.getRequestDispatcher("/admin/roles.jsp").forward(request, response);

        } else if (path.equals("/admin/addRole")) {
            // Xử lý thêm role mới
            String roleName = request.getParameter("roleName");
            List<Role> roles = db.getRolesWithStatus();

            // Kiểm tra roleName rỗng hoặc chỉ chứa dấu cách
            if (roleName == null || roleName.trim().isEmpty()) {
                request.setAttribute("error", "Role name cannot be empty.");
                request.getRequestDispatcher("/admin/addRole.jsp").forward(request, response);
                return;
            }

            // Kiểm tra trùng roleName
            for (Role existingRole : roles) {
                if (existingRole.getRoleName().equalsIgnoreCase(roleName.trim())) {
                    request.setAttribute("error", "Role name already exists.");
                    request.getRequestDispatcher("/admin/addRole.jsp").forward(request, response);
                    return;
                }
            }

            // Thêm role mới
            Role newRole = new Role();
            newRole.setRoleName(roleName.trim());
            newRole.setStatus(new RoleStatus(true)); // Mặc định trạng thái là true
            db.addRole(newRole);

            // Tải lại danh sách roles sau khi thêm
            roles = db.getRolesWithStatus();
            request.setAttribute("roles", roles);
            request.getRequestDispatcher("/admin/roles.jsp").forward(request, response);

        } else if (path.equals("/admin/editRole")) {
            // Xử lý cập nhật role
            String roleIdParam = request.getParameter("roleID");
            String roleName = request.getParameter("roleName");

            if (roleIdParam == null || roleName == null || roleName.trim().isEmpty()) {
                request.setAttribute("error", "Role name cannot be empty.");
                Role role = db.getRoleById(Integer.parseInt(request.getParameter("roleID")));
                request.setAttribute("role", role);
                request.getRequestDispatcher("/admin/editRole.jsp").forward(request, response);
                return;
            }

            try {
                int roleID = Integer.parseInt(roleIdParam);
                List<Role> roles = db.getRolesWithStatus();

                // Kiểm tra trùng roleName (trừ role hiện tại)
                for (Role existingRole : roles) {
                    if (existingRole.getRoleID() != roleID && existingRole.getRoleName().equalsIgnoreCase(roleName.trim())) {
                        request.setAttribute("error", "Role name already exists.");
                        Role role = db.getRoleById(roleID);
                        request.setAttribute("role", role);
                        request.getRequestDispatcher("/admin/editRole.jsp").forward(request, response);
                        return;
                    }
                }

                // Cập nhật role
                Role role = new Role(roleID, roleName.trim());
                db.updateRole(role);

                // Tải lại danh sách roles sau khi sửa
                roles = db.getRolesWithStatus();
                request.setAttribute("roles", roles);
                request.getRequestDispatcher("/admin/roles.jsp").forward(request, response);
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Invalid Role ID.");
                List<Role> roles = db.getRolesWithStatus();
                request.setAttribute("roles", roles);
                request.getRequestDispatcher("/admin/roles.jsp").forward(request, response);
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "Admin Setting List Servlet";
    }
}