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
            String sql = "select *"
                    + "from servicesCategories ;";
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {
                int categoryID = rs.getInt("CategoryID");
                String categoryName = rs.getString("CategoryName");
                String categoryDetail = rs.getString("CategoryDetail");

                ServiceCategory serviceCategory = new ServiceCategory(categoryID, categoryName, categoryDetail);
                serviceCategories.add(serviceCategory);
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

        return serviceCategories;
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
            stm = connection.prepareStatement(sql);
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
            stm = connection.prepareStatement(sql);
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
        ResultSet rs = null;
        try {
            String sql = "UPDATE services"
                    + "SET ServiceName = ?, ServiceDetail=? ,CategoryID=?, ServicePrice=?,SalePrice=?,imageURL=?"
                    + "WHERE ServiceID=?;  ";
            stm = connection.prepareStatement(sql);
            stm.setString(1, serviceName);
            stm.setString(2, serviceDetail);
            stm.setInt(3, categoryID);
            stm.setFloat(4, servicePrice);
            stm.setFloat(5, salePrice);
            stm.setString(6, imageURL);
            stm.setInt(7, serviceID);
            rs = stm.executeQuery();
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
    }

    /**
     *
     * @param ID
     */
    public void deleteService(int ID) {
        PreparedStatement stm = null;

        try {
            String sql = "DELETE FROM services WHERE ServiceID = ?;";
            stm = connection.prepareStatement(sql);
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
            + "JOIN users u ON s.authorID = u.userID "
            + "WHERE (? IS NULL OR s.serviceName LIKE ?) "
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

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

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

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        ServiceDBContext s = new ServiceDBContext();
        System.out.println(s.getServiceByID(1).getAuthor().getName());
    }
}
