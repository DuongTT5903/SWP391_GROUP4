/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import static dal.DBContext.getConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Service;
import model.ServiceCategory;
import dal.UserDBContext;
import java.util.Arrays;
import model.User;

/**
 *
 * @author admin
 */
public class ServiceDBContext {

    private static Connection connection = DBContext.getConnection();

    /**
     *
     * @return
     */
    public List<ServiceCategory> getServiceCategories() {
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<ServiceCategory> serviceCategories = new ArrayList<>();

        try {
            String sql = "SELECT * FROM servicecategories;";
            stm = DBContext.getConnection().prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {
                int categoryID = rs.getInt("CategoryID");
                String categoryName = rs.getString("CategoryName");
                String categoryDetail = rs.getString("CategoryDetail");

                ServiceCategory serviceCategory = new ServiceCategory(categoryID, categoryName, categoryDetail);
                serviceCategories.add(serviceCategory);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServiceDBContext.class.getName()).log(Level.SEVERE, "Lỗi khi lấy danh mục dịch vụ", ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stm != null) {
                    stm.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ServiceDBContext.class.getName()).log(Level.SEVERE, "Lỗi khi đóng tài nguyên", ex);
            }
        }

        return serviceCategories;
    }
    public List<Service> searchServices(String search, int categoryID, int page, int pageSize) {
    List<Service> services = new ArrayList<>();
    UserDBContext userDB = new UserDBContext();

    String sql = """
        SELECT s.ServiceID, s.ServiceName, s.ServiceDetail, s.CategoryID, s.ServicePrice, s.SalePrice, 
               s.ImageURL, s.status, s.authorID, sc.CategoryName, sc.CategoryDetail 
        FROM services s 
        INNER JOIN servicecategories sc ON s.CategoryID = sc.CategoryID
        WHERE s.status = 1 
        AND (? IS NULL OR s.ServiceName LIKE ? OR s.ServiceDetail LIKE ?)
        AND (? = 0 OR s.CategoryID = ?)
        ORDER BY s.ServiceID DESC
        LIMIT ? OFFSET ?""";

    try (Connection conn = DBContext.getConnection(); 
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        // Thiết lập tham số tìm kiếm
        if (search == null || search.trim().isEmpty()) {
            stmt.setNull(1, java.sql.Types.VARCHAR);
            stmt.setNull(2, java.sql.Types.VARCHAR);
            stmt.setNull(3, java.sql.Types.VARCHAR);
        } else {
            String searchPattern = "%" + search.trim() + "%";
            stmt.setString(1, search);
            stmt.setString(2, searchPattern);
            stmt.setString(3, searchPattern);
        }

        // Thiết lập tham số cho categoryID
        if (categoryID == 0) {
            stmt.setInt(4, 0);
            stmt.setInt(5, 0);
        } else {
            stmt.setInt(4, categoryID);
            stmt.setInt(5, categoryID);
        }

        // Thiết lập phân trang
        stmt.setInt(6, pageSize); // LIMIT
        stmt.setInt(7, (page - 1) * pageSize); // OFFSET

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            ServiceCategory category = new ServiceCategory(
                rs.getInt("CategoryID"),
                rs.getString("CategoryName"),
                rs.getString("CategoryDetail")
            );

            Service service = new Service(
                rs.getInt("ServiceID"),
                rs.getString("ServiceName"),
                rs.getString("ServiceDetail"),
                category,
                rs.getFloat("ServicePrice"),
                rs.getFloat("SalePrice"),
                rs.getString("ImageURL"),
                rs.getBoolean("status"),
                userDB.getUserByID(rs.getInt("authorID"))
            );
            services.add(service);
        }
    } catch (SQLException e) {
        Logger.getLogger(ServiceDBContext.class.getName()).log(Level.SEVERE, "Error searching services", e);
    }
    return services;
}
   public List<Service> searchServices1(String search, int categoryID, int page, int pageSize) {
    List<Service> services = new ArrayList<>();
    UserDBContext userDB = new UserDBContext();

    String sql = """
        SELECT s.ServiceID, s.ServiceName, s.ServiceDetail, s.CategoryID, s.ServicePrice, s.SalePrice, 
               s.ImageURL, s.status, s.authorID, sc.CategoryName, sc.CategoryDetail 
        FROM services s 
        INNER JOIN servicecategories sc ON s.CategoryID = sc.CategoryID
        WHERE 
         (? IS NULL OR s.ServiceName LIKE ? OR s.ServiceDetail LIKE ?)
        AND (? = 0 OR s.CategoryID = ?)
        ORDER BY s.ServiceID DESC
        LIMIT ? OFFSET ?""";

    try (Connection conn = DBContext.getConnection(); 
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        // Thiết lập tham số tìm kiếm
        if (search == null || search.trim().isEmpty()) {
            stmt.setNull(1, java.sql.Types.VARCHAR);
            stmt.setNull(2, java.sql.Types.VARCHAR);
            stmt.setNull(3, java.sql.Types.VARCHAR);
        } else {
            String searchPattern = "%" + search.trim() + "%";
            stmt.setString(1, search);
            stmt.setString(2, searchPattern);
            stmt.setString(3, searchPattern);
        }

        // Thiết lập tham số cho categoryID
        if (categoryID == 0) {
            stmt.setInt(4, 0);
            stmt.setInt(5, 0);
        } else {
            stmt.setInt(4, categoryID);
            stmt.setInt(5, categoryID);
        }

        // Thiết lập phân trang
        stmt.setInt(6, pageSize); // LIMIT
        stmt.setInt(7, (page - 1) * pageSize); // OFFSET

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            ServiceCategory category = new ServiceCategory(
                rs.getInt("CategoryID"),
                rs.getString("CategoryName"),
                rs.getString("CategoryDetail")
            );

            Service service = new Service(
                rs.getInt("ServiceID"),
                rs.getString("ServiceName"),
                rs.getString("ServiceDetail"),
                category,
                rs.getFloat("ServicePrice"),
                rs.getFloat("SalePrice"),
                rs.getString("ImageURL"),
                rs.getBoolean("status"),
                userDB.getUserByID(rs.getInt("authorID"))
            );
            services.add(service);
        }
    } catch (SQLException e) {
        Logger.getLogger(ServiceDBContext.class.getName()).log(Level.SEVERE, "Error searching services", e);
    }
    return services;
}
// Hàm đếm tổng số dịch vụ để hỗ trợ phân trang
public int getTotalServicesForSearch(String search, int categoryID) {
    int total = 0;
    String sql = """
        SELECT COUNT(*) as total 
        FROM services s 
        INNER JOIN servicecategories sc ON s.CategoryID = sc.CategoryID
        WHERE s.status = 1 
        AND (? IS NULL OR s.ServiceName LIKE ? OR s.ServiceDetail LIKE ?)
        AND (? = 0 OR s.CategoryID = ?)""";

    try (Connection conn = DBContext.getConnection(); 
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        if (search == null || search.trim().isEmpty()) {
            stmt.setNull(1, java.sql.Types.VARCHAR);
            stmt.setNull(2, java.sql.Types.VARCHAR);
            stmt.setNull(3, java.sql.Types.VARCHAR);
        } else {
            String searchPattern = "%" + search.trim() + "%";
            stmt.setString(1, search);
            stmt.setString(2, searchPattern);
            stmt.setString(3, searchPattern);
        }

        if (categoryID == 0) {
            stmt.setInt(4, 0);
            stmt.setInt(5, 0);
        } else {
            stmt.setInt(4, categoryID);
            stmt.setInt(5, categoryID);
        }

        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            total = rs.getInt("total");
        }
    } catch (SQLException e) {
        Logger.getLogger(ServiceDBContext.class.getName()).log(Level.SEVERE, "Error counting services for search", e);
    }
    return total;
}

    /**
     *
     * @param ID
     * @return
     */
    public ServiceCategory getServiceCategoryByID(int ID) {
        PreparedStatement stm = null;
        ResultSet rs = null;
        ServiceCategory serviceCategory = null;
        try {
            String sql = "SELECT * FROM servicecategories WHERE CategoryID = ?;";
            stm = DBContext.getConnection().prepareStatement(sql);
            stm.setInt(1, ID);
            rs = stm.executeQuery();

            while (rs.next()) {
                int categoryID = rs.getInt("CategoryID");
                String categoryName = rs.getString("CategoryName");
                String categoryDetail = rs.getString("CategoryDetail");

                serviceCategory = new ServiceCategory(categoryID, categoryName, categoryDetail);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServiceDBContext.class.getName()).log(Level.SEVERE, "Error fetching user by ID", ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stm != null) {
                    stm.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ServiceDBContext.class.getName()).log(Level.SEVERE, "Error closing resources", ex);
            }
        }
        return serviceCategory;
    }

    /**
     *
     * @return
     */
    public List<Service> getServices() {
        UserDBContext u = new UserDBContext();
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<Service> services = new ArrayList<>();

        try {
            String sql = "select *"
                    + "from services ;";
            stm = DBContext.getConnection().prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {
                int serviceID = rs.getInt("ServiceID");
                String serviceName = rs.getString("ServiceName");
                String serviceDetail = rs.getString("ServiceDetail");
                int categoryID = rs.getInt("CategoryID");
                float servicePrice = rs.getFloat("ServicePrice");
                float salePrice = rs.getFloat("SalePrice");
                String imageURL = rs.getString("ImageURL");
                boolean status = rs.getBoolean("status");
                int authorID = rs.getInt("AuthorID");
                Service service = new Service(serviceID, serviceName, serviceDetail, getServiceCategoryByID(categoryID), servicePrice, salePrice, imageURL, status, u.getUserByID(authorID));
                services.add(service);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServiceDBContext.class.getName()).log(Level.SEVERE, "Error fetching users", ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stm != null) {
                    stm.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ServiceDBContext.class.getName()).log(Level.SEVERE, "Error closing resources", ex);
            }
        }

        return services;
    }

    /**
     *
     * @param serviceName
     * @param serviceDetail
     * @param categoryID
     * @param servicePrice
     * @param salePrice
     * @param imageURL
     * @param authorID
     */
    public void addService(String serviceName, String serviceDetail, int categoryID, float servicePrice, float salePrice, String imageURL, int authorID) {
        PreparedStatement stm = null;
        try {
            String sql = "INSERT INTO services (ServiceName, ServiceDetail, CategoryID, ServicePrice, SalePrice, imageURL, status, authorID)"
                    + " VALUES (?, ?, ?, ?, ?, ?, 1, ?);";
            stm = connection.prepareStatement(sql);
            stm.setString(1, serviceName);
            stm.setString(2, serviceDetail);
            stm.setInt(3, categoryID);
            stm.setFloat(4, servicePrice);
            stm.setFloat(5, salePrice);
            stm.setString(6, imageURL);
            stm.setInt(7, authorID);

            // ✅ Use executeUpdate() instead
            int rowsInserted = stm.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Service added successfully!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServiceDBContext.class.getName()).log(Level.SEVERE, "Error adding service", ex);
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ServiceDBContext.class.getName()).log(Level.SEVERE, "Error closing resources", ex);
            }
        }
    }

    /**
     *
     * @param serviceName
     * @param serviceDetail
     * @param categoryID
     * @param servicePrice
     * @param salePrice
     * @param imageURL
     * @param serviceID
     */
    public void updateService(String serviceName, String serviceDetail, int categoryID, float servicePrice, float salePrice, String imageURL, int serviceID) {
    PreparedStatement stm = null;
    try {
        String sql = "UPDATE services SET ServiceName = ?, ServiceDetail = ?, CategoryID = ?, ServicePrice = ?, SalePrice = ?, ImageURL = ? WHERE ServiceID = ?";
        stm = DBContext.getConnection().prepareStatement(sql);
        stm.setString(1, serviceName);
        stm.setString(2, serviceDetail);
        stm.setInt(3, categoryID);
        stm.setFloat(4, servicePrice);
        stm.setFloat(5, salePrice);
        stm.setString(6, imageURL);
        stm.setInt(7, serviceID);

        int rowsUpdated = stm.executeUpdate();
        if (rowsUpdated > 0) {
            Logger.getLogger(ServiceDBContext.class.getName()).log(Level.INFO, "Service ID {0} updated successfully.", serviceID);
        } else {
            Logger.getLogger(ServiceDBContext.class.getName()).log(Level.WARNING, "No rows updated for Service ID {0}.", serviceID);
        }
    } catch (SQLException ex) {
        Logger.getLogger(ServiceDBContext.class.getName()).log(Level.SEVERE, "Error updating service", ex);
    } finally {
        try {
            if (stm != null) {
                stm.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServiceDBContext.class.getName()).log(Level.SEVERE, "Error closing resources", ex);
        }
    }
}

    /**
     *
     * @param ID
     */
    public void deleteService(int ID) {
        PreparedStatement stm = null;

        try {
            String sql = "DELETE FROM services WHERE ServiceID = ?;";
            stm = DBContext.getConnection().prepareStatement(sql);
            stm.setInt(1, ID);

            int rowsAffected = stm.executeUpdate();

            if (rowsAffected > 0) {
                Logger.getLogger(ServiceDBContext.class.getName()).log(Level.INFO, "Service ID {0} deleted successfully.", ID);
            } else {
                Logger.getLogger(ServiceDBContext.class.getName()).log(Level.WARNING, "No service found with ID {0}.", ID);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ServiceDBContext.class.getName()).log(Level.SEVERE, "Error deleting service with ID " + ID, ex);
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ServiceDBContext.class.getName()).log(Level.SEVERE, "Error closing statement", ex);
            }
        }
    }

    /**
     *
     * @param search
     * @param categoryID
     * @param page
     * @param pageSize
     * @return
     */
    public List<Service> getServices(String search, int categoryID, int page, int pageSize) {
        List<Service> services = new ArrayList<>();

        String sql = """
             SELECT s.ServiceID, s.ServiceName, s.ServiceDetail, sc.CategoryID, sc.CategoryName, sc.CategoryDetail, 
             s.ServicePrice, s.ImageURL, s.status, s.SalePrice, s.authorID 
             FROM services s 
             INNER JOIN servicecategories sc ON s.CategoryID = sc.CategoryID
             WHERE s.status = 1""";

        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, search.isEmpty() ? null : "%" + search + "%");
            stmt.setString(2, search.isEmpty() ? null : "%" + search + "%");
            stmt.setInt(3, categoryID);
            stmt.setInt(4, categoryID);
            stmt.setInt(5, (page - 1) * pageSize);
            stmt.setInt(6, pageSize);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ServiceCategory category = new ServiceCategory(rs.getInt("categoryID"), rs.getString("categoryName"), "");
                User author = new User(rs.getInt("authorID"), rs.getString("authorName"), true, "", "", "", "", "", "");

                Service service = new Service(
                        rs.getInt("serviceID"),
                        rs.getString("serviceName"),
                        rs.getString("serviceDetail"),
                        category,
                        rs.getFloat("servicePrice"),
                        rs.getFloat("salePrice"),
                        rs.getString("imageURL"),
                        rs.getBoolean("status"),
                        author
                );
                services.add(service);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return services;
    }

    public List<Service> getServices(String search, int categoryID, int page, int pageSize, String type, String sort) {
        List<Service> services = new ArrayList<>();

        // Validate sorting input
        List<String> validSortColumns = Arrays.asList("serviceName", "servicePrice", "salePrice", "serviceid"); // Allowed columns
        List<String> validSortOrders = Arrays.asList("ASC", "DESC"); // Allowed sorting orders

        if (!validSortOrders.contains(sort)) {
            sort = "DESC"; // Default sorting order

        }

        // Dynamic SQL (Avoiding ORDER BY placeholder issue)
        String sql = "SELECT s.*, c.categoryName, u.name AS authorName FROM services s "
                + "JOIN servicecategories c ON s.categoryID = c.categoryID "
                + "JOIN users u ON s.authorID = u.userID where s.status=1 "
                + "AND (? IS NULL OR s.serviceName LIKE ?) "
                + "AND (? = 0 OR s.categoryID = ?) "
                + "ORDER BY " + type + " " + sort + " " // Safe dynamic ORDER BY
                + "LIMIT ?, ?";

        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, search.isEmpty() ? null : "%" + search + "%");
            stmt.setString(2, search.isEmpty() ? null : "%" + search + "%");
            stmt.setInt(3, categoryID);
            stmt.setInt(4, categoryID);
            stmt.setInt(5, (page - 1) * pageSize);
            stmt.setInt(6, pageSize);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ServiceCategory category = new ServiceCategory(rs.getInt("categoryID"), rs.getString("categoryName"), "");
                User author = new User(rs.getInt("authorID"), rs.getString("authorName"), true, "", "", "", "", "", "");

                Service service = new Service(
                        rs.getInt("serviceID"),
                        rs.getString("serviceName"),
                        rs.getString("serviceDetail"),
                        category,
                        rs.getFloat("servicePrice"),
                        rs.getFloat("salePrice"),
                        rs.getString("imageURL"),
                        rs.getBoolean("status"),
                        author
                );
                services.add(service);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return services;
    }

    /**
     *
     * @param ID
     * @return
     */
    public Service getServiceByID(int ID) {
        Service service = new Service();
        String sql = "SELECT s.*, c.categoryName, u.name AS authorName FROM services s "
                + "JOIN servicecategories c ON s.categoryID = c.categoryID "
                + "JOIN users u ON s.authorID = u.userID "
                + "WHERE (s.serviceID LIKE ?) ";

        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, ID);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ServiceCategory category = new ServiceCategory(rs.getInt("categoryID"), rs.getString("categoryName"), "");
                User author = new User(rs.getInt("authorID"), rs.getString("authorName"), true, "", "", "", "", "", "");

                service = new Service(
                        rs.getInt("serviceID"),
                        rs.getString("serviceName"),
                        rs.getString("serviceDetail"),
                        category,
                        rs.getFloat("servicePrice"),
                        rs.getFloat("salePrice"),
                        rs.getString("imageURL"),
                        rs.getBoolean("status"),
                        author
                );

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return service;
    }

    // Lấy tất cả dịch vụ để hiển thị trên trang homepage
    /**
     *
     * @return
     */
    public List<Service> getServicesHomepage() {
        List<Service> services = new ArrayList<>();
        String sql = """
                     SELECT s.ServiceID, s.ServiceName, s.ServiceDetail,sc.CategoryID, sc.CategoryName, sc.CategoryDetail, s.ServicePrice,s.ImageURL, 
                     s.status, s.SalePrice, s.authorID FROM services s Inner join servicecategories sc on s.CategoryID = sc.CategoryID""";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ServiceCategory category = new ServiceCategory(
                        rs.getInt("CategoryID"),
                        rs.getString("CategoryDetail"),
                        rs.getString("CategoryName")
                );

                User author = new User();
                author.setUserID(rs.getInt("authorID"));

                Service service = new Service(
                        rs.getInt("ServiceID"),
                        rs.getString("ServiceName"),
                        rs.getString("ServiceDetail"),
                        category,
                        rs.getFloat("ServicePrice"),
                        rs.getFloat("SalePrice"),
                        rs.getString("ImageURL"),
                        rs.getBoolean("status"),
                        author
                );
                services.add(service);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return services;
    }

    public boolean updateServiceStatus(int serviceID, boolean newStatus) {
        PreparedStatement stm = null;
        Connection conn = null;
        try {
            String sql = "UPDATE services SET status = ? WHERE ServiceID = ?";
            conn = DBContext.getConnection();
            stm = conn.prepareStatement(sql);

            stm.setBoolean(1, newStatus); // Trực tiếp đặt giá trị boolean (0 hoặc 1)
            stm.setInt(2, serviceID);

            int rowsUpdated = stm.executeUpdate(); // Thực thi câu lệnh UPDATE
            return rowsUpdated > 0; // Nếu có ít nhất một dòng bị ảnh hưởng, trả về true
        } catch (SQLException ex) {
            Logger.getLogger(ServiceDBContext.class.getName()).log(Level.SEVERE, "Error updating service status", ex);
            return false;
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ServiceDBContext.class.getName()).log(Level.SEVERE, "Error closing resources", ex);
            }
        }
    }

    public List<Service> getListServiceByID(int ID) {
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<Service> serviceList = new ArrayList<>();
        UserDBContext u = new UserDBContext();

        try {
            String sql = "SELECT s.*, c.categoryName, u.name AS authorName FROM services s "
                    + "JOIN servicecategories c ON s.categoryID = c.categoryID "
                    + "JOIN users u ON s.authorID = u.userID "
                    + "WHERE s.serviceID = ?";
            stm = DBContext.getConnection().prepareStatement(sql);
            stm.setInt(1, ID);
            rs = stm.executeQuery();

            while (rs.next()) {
                int serviceID = rs.getInt("serviceID");
                String serviceName = rs.getString("serviceName");
                String serviceDetail = rs.getString("serviceDetail");
                int categoryID = rs.getInt("categoryID");
                float servicePrice = rs.getFloat("servicePrice");
                float salePrice = rs.getFloat("salePrice");
                String imageURL = rs.getString("imageURL");
                boolean status = rs.getBoolean("status");
                int authorID = rs.getInt("authorID");

                Service service = new Service(
                        serviceID, serviceName, serviceDetail,
                        getServiceCategoryByID(categoryID), // Dùng phương thức này để đồng bộ
                        servicePrice, salePrice, imageURL, status,
                        u.getUserByID(authorID)
                );
                serviceList.add(service);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServiceDBContext.class.getName()).log(Level.SEVERE, "Lỗi khi lấy danh sách dịch vụ", ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stm != null) {
                    stm.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ServiceDBContext.class.getName()).log(Level.SEVERE, "Lỗi khi đóng tài nguyên", ex);
            }
        }
        return serviceList;
    }

    public void addService(Service service) {
        PreparedStatement stm = null;

        try {
            String sql = "INSERT INTO services (ServiceName, ServiceDetail, CategoryID, ServicePrice, SalePrice, ImageURL, status, authorID) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
            stm = DBContext.getConnection().prepareStatement(sql);

            stm.setString(1, service.getServiceName());
            stm.setString(2, service.getServiceDetail());
            stm.setInt(3, service.getCategory().getCategoryID()); // Lấy ID từ đối tượng category
            stm.setFloat(4, service.getServicePrice());
            stm.setFloat(5, service.getSalePrice());
            stm.setString(6, service.getImageURL());
            stm.setBoolean(7, service.isStatus());
            stm.setInt(8, 2); // Lấy ID từ đối tượng author

            stm.executeUpdate();
            Logger.getLogger(ServiceDBContext.class.getName()).log(Level.INFO, "Dịch vụ đã được thêm thành công: {0}", service.getServiceName());

        } catch (SQLException ex) {
            Logger.getLogger(ServiceDBContext.class.getName()).log(Level.SEVERE, "Lỗi khi thêm dịch vụ", ex);
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ServiceDBContext.class.getName()).log(Level.SEVERE, "Lỗi khi đóng tài nguyên", ex);
            }
            
        }
        
    }
public Service getServiceById(int serviceId) {
        Service service = null;
        String sql = "SELECT * FROM Services WHERE ServiceID = ?";

        try (Connection conn = DBContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, serviceId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    service = new Service();
                    service.setServiceID(rs.getInt("ServiceID"));
                    service.setServiceName(rs.getString("ServiceName"));
                    service.setServiceDetail(rs.getString("ServiceDetail"));
                    service.setServicePrice(rs.getFloat("ServicePrice"));
                    // Set other Service properties as needed
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServiceDBContext.class.getName()).log(Level.SEVERE, "Error getting service", ex);
        }
        return service;
    }
    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        ServiceDBContext serviceDB = new ServiceDBContext();

        // Gọi hàm lấy danh mục dịch vụ
        System.out.println(serviceDB.getServiceByID(1));
    }

}
