/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

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
import model.User;

/**
 *
 * @author admin
 */
public class ServiceDBContext {
    public List<Service> getServices() {
        List<Service> services = new ArrayList<>();
        String sql = "SELECT s.serviceID, s.serviceName, s.serviceDetail, s.servicePrice, s.imageURL, s.status, sc.categoryName, u.userID, u.name " +
                     "FROM Services s " +
                     "JOIN ServiceCategory sc ON s.categoryID = sc.categoryID " +
                     "JOIN Users u ON s.authorID = u.userID";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement stm = conn.prepareStatement(sql);
             ResultSet rs = stm.executeQuery()) {

            while (rs.next()) {
                Service service = new Service();
                service.setServiceID(rs.getInt("serviceID"));
                service.setServiceName(rs.getString("serviceName"));
                service.setServiceDetail(rs.getString("serviceDetail"));
                service.setServicePrice(rs.getFloat("servicePrice"));
                service.setImageURL(rs.getString("imageURL"));
                service.setStatus(rs.getBoolean("status"));

                ServiceCategory category = new ServiceCategory();
                category.setCategoryName(rs.getString("categoryName"));
                service.setCategory(category);

                User author = new User();
                author.setUserID(rs.getInt("userID"));
                author.setName(rs.getString("name"));
                service.setAuthorID(author);

                services.add(service);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServiceDBContext.class.getName()).log(Level.SEVERE, "Error fetching services", ex);
        }
        return services;
    }
    public static void main(String[] args) {
        ServiceDBContext serviceDB = new ServiceDBContext();

        // Fetch services
        List<Service> services = serviceDB.getServices();

        // Print services for testing
        if (services == null || services.isEmpty()) {
            System.out.println("No services available.");
        } else {
            for (Service service : services) {
                System.out.println(service);
            }
        }
}
}
