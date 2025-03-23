/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import static dal.DBContext.connection;
import static dal.DBContext.getConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Cart;
import model.Customer;
import model.Reservation;
import model.ReservationDetail;
import model.Service;
import model.ServiceCategory;
import model.User;

/*
 *
 * @author admin
 */
public class ReservationDBContext {
    public int getCartCount(int userID) {
    int count = 0;
    String sql = "SELECT COUNT(*) FROM Carts WHERE userID = ?";
    try (Connection conn = getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, userID);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            count = rs.getInt(1);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return count;
}

    public Reservation getReservationById(int reservationID) {
    Reservation reservation = null;
    String sql = "SELECT * FROM Reservations WHERE reservationID = ?";

    try (Connection conn = getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setInt(1, reservationID);

        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                reservation = new Reservation();
                reservation.setReservationID(rs.getInt("reservationID"));
                reservation.setCustomerName(rs.getString("customerName"));
                reservation.setEmail(rs.getString("email"));
                reservation.setPhone(rs.getString("phone"));
                reservation.setAddress(rs.getString("address"));
                reservation.setCreationDate(rs.getDate("creationDate"));
                reservation.setStatus(rs.getInt("status"));
                reservation.setTotalPrice(rs.getFloat("totalPrice"));
              

                // Lấy danh sách chi tiết dịch vụ đã đặt
                List<ReservationDetail> details = getReservationDetails(reservationID);
                reservation.setDetails(details);
            }
        }

    } catch (SQLException e) {
        System.err.println("Error fetching reservation by ID: " + e.getMessage());
    }

    return reservation;
}
    public List<ReservationDetail> getReservationDetails(int reservationID) {
    List<ReservationDetail> details = new ArrayList<>();
    String sql = "SELECT rd.DetailID, rd.ServiceID, s.ServiceName, s.Category, s.Thumbnail, rd.Amount, rd.NumberOfPerson "
               + "FROM ReservationDetails rd "
               + "JOIN Services s ON rd.ServiceID = s.ServiceID "
               + "WHERE rd.ReservationID = ?";

    try (Connection conn = getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setInt(1, reservationID);

        try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                ReservationDetail detail = new ReservationDetail();
                detail.setDetailID(rs.getInt("DetailID"));
                detail.setRevationID(reservationID);

                // Tạo đối tượng Service
                Service service = new Service();
                service.setServiceID(rs.getInt("ServiceID"));
                service.setServiceName(rs.getString("ServiceName"));
               
                detail.setService(service);

                detail.setAmount(rs.getInt("Amount"));
               
                details.add(detail);
            }
        }

    } catch (SQLException e) {
        System.err.println("Error fetching reservation details: " + e.getMessage());
    }

    return details;
}
    public List<Reservation> getReservations(Integer status, String fromDate, String toDate, Integer staffId,
                                         String searchQuery, String sortBy, int page, int pageSize) {
    List<Reservation> reservations = new ArrayList<>();
    String sql = "SELECT * FROM Reservations WHERE 1=1";

    // Thêm điều kiện lọc vào câu lệnh SQL
    if (status != null) {
        sql += " AND status = ?";
    }
    if (fromDate != null && !fromDate.isEmpty()) {
        sql += " AND creationDate >= ?";
    }
    if (toDate != null && !toDate.isEmpty()) {
        sql += " AND creationDate <= ?";
    }
    if (staffId != null) {
        sql += " AND staffID = ?";
    }
    if (searchQuery != null && !searchQuery.isEmpty()) {
        sql += " AND (customerName LIKE ? OR email LIKE ? OR phone LIKE ?)";
    }

    // Thêm sắp xếp (nếu có)
    if (sortBy != null && !sortBy.isEmpty()) {
        sql += " ORDER BY " + sortBy;
    }

    // Thêm phân trang
    sql += " LIMIT ? OFFSET ?";

    try (Connection conn = getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        int paramIndex = 1;

        // Thiết lập các tham số cho câu lệnh SQL
        if (status != null) {
            pstmt.setInt(paramIndex++, status);
        }
        if (fromDate != null && !fromDate.isEmpty()) {
            pstmt.setString(paramIndex++, fromDate);
        }
        if (toDate != null && !toDate.isEmpty()) {
            pstmt.setString(paramIndex++, toDate);
        }
        if (staffId != null) {
            pstmt.setInt(paramIndex++, staffId);
        }
        if (searchQuery != null && !searchQuery.isEmpty()) {
            String searchPattern = "%" + searchQuery + "%";
            pstmt.setString(paramIndex++, searchPattern);
            pstmt.setString(paramIndex++, searchPattern);
            pstmt.setString(paramIndex++, searchPattern);
        }

        // Thiết lập phân trang
        pstmt.setInt(paramIndex++, pageSize);
        pstmt.setInt(paramIndex++, (page - 1) * pageSize);

        // Thực thi câu lệnh SQL và lấy kết quả
        try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Reservation reservation = new Reservation();
                reservation.setReservationID(rs.getInt("reservationID"));
                reservation.setCustomerName(rs.getString("customerName"));
                reservation.setEmail(rs.getString("email"));
                reservation.setPhone(rs.getString("phone"));
                reservation.setAddress(rs.getString("address"));
                reservation.setCreationDate(rs.getDate("creationDate"));
                reservation.setStatus(rs.getInt("status"));
                reservation.setTotalPrice(rs.getFloat("totalPrice"));

                // Bỏ phần chi tiết (ReservationDetail)
                // List<ReservationDetail> details = getReservationDetails(reservation.getReservationID());
                // reservation.setDetails(details);

                reservations.add(reservation);
            }
        }

    } catch (SQLException e) {
        System.err.println("Error fetching reservations: " + e.getMessage());
    }

    // Debug: In ra số lượng bản ghi được lấy
    System.out.println("Total reservations fetched: " + reservations.size());

    return reservations;
}
    
public int countReservations(Integer status, String fromDate, String toDate, Integer staffId, String searchQuery) {
    String sql = "SELECT COUNT(*) AS total FROM Reservations WHERE 1=1";

    // Thêm điều kiện lọc vào câu lệnh SQL
    if (status != null) {
        sql += " AND status = ?";
    }
    if (fromDate != null && !fromDate.isEmpty()) {
        sql += " AND creationDate >= ?";
    }
    if (toDate != null && !toDate.isEmpty()) {
        sql += " AND creationDate <= ?";
    }
    if (staffId != null) {
        sql += " AND staffID = ?";
    }
    if (searchQuery != null && !searchQuery.isEmpty()) {
        sql += " AND (customerName LIKE ? OR email LIKE ? OR phone LIKE ?)";
    }

    try (Connection conn = getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        int paramIndex = 1;

        // Thiết lập các tham số cho câu lệnh SQL
        if (status != null) {
            pstmt.setInt(paramIndex++, status);
        }
        if (fromDate != null && !fromDate.isEmpty()) {
            pstmt.setString(paramIndex++, fromDate);
        }
        if (toDate != null && !toDate.isEmpty()) {
            pstmt.setString(paramIndex++, toDate);
        }
        if (staffId != null) {
            pstmt.setInt(paramIndex++, staffId);
        }
        if (searchQuery != null && !searchQuery.isEmpty()) {
            String searchPattern = "%" + searchQuery + "%";
            pstmt.setString(paramIndex++, searchPattern);
            pstmt.setString(paramIndex++, searchPattern);
            pstmt.setString(paramIndex++, searchPattern);
        }

        // Thực thi câu lệnh SQL và lấy kết quả
        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        }

    } catch (SQLException e) {
        System.err.println("Error counting reservations: " + e.getMessage());
    }

    return 0; // Trả về 0 nếu có lỗi
}

    public int ReservationID() {
        String sql = "SELECT COUNT(*)"
                + "FROM Reservations;";
        int count = 0;
        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                count = rs.getInt("COUNT(*)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;

    }

    public void addReservation(String customerName, String email, String address, String phone, LocalDate creationDate, int userID, int paymentMethod, float totalPrice, int gender, String Description, int status) {

        String sql = "INSERT INTO reservations ( CustomerName,Email,Address,Phone ,CreationDate,UserID   ,PaymentMethod ,TotalPrice, Gender,Description ,Status)"
                + " VALUES (?, ?, ?, ?, ?,?, ?, ?,?,?,?);";

        // ✅ Use executeUpdate() instead
        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, customerName);
            stmt.setString(2, email);
            stmt.setString(3, address);
            stmt.setString(4, phone);
            stmt.setDate(5, java.sql.Date.valueOf(creationDate));
            stmt.setInt(6, userID);
            stmt.setInt(7, paymentMethod);
            stmt.setFloat(8, totalPrice);
            stmt.setInt(9, gender);
            stmt.setString(10, Description);
            stmt.setInt(11, status);
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Service added successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addReservationDetail(int revationID, int serviceID, int amount, int numberOfPerson) {
        String sql = "INSERT INTO reservationDetails ( reservationID,serviceID,amount,numberOfPerson)"
                + " VALUES (?, ?, ?,?);";

        // ✅ Use executeUpdate() instead
        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, revationID);
            stmt.setInt(2, serviceID);
            stmt.setInt(3, amount);
            stmt.setInt(4, numberOfPerson);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Service added successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addCardInfo(int revationID, String cardName, String cardNumber, String CVV, LocalDate expirationDate) {
        String sql = "INSERT INTO reservations ( revationID,cardName,cardNumber,CVV,expirationDate)"
                + " VALUES (?,?, ?, ?,?);";

        // ✅ Use executeUpdate() instead
        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, revationID);
            stmt.setString(2, cardName);
            stmt.setString(3, cardNumber);
            stmt.setString(4, CVV);
            stmt.setDate(5, java.sql.Date.valueOf(expirationDate));

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Service added successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

public Customer getCustomerByID(int ID) {
    Customer customer = null;
    String query = """
            SELECT u.userID, u.name, u.gender, u.email, u.username, u.phone, 
                   c.CustomerID, c.Address 
            FROM customer c 
            INNER JOIN users u ON c.userID = u.userID 
            WHERE c.userID = ?""";

    try (Connection conn = DBContext.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {

        if (conn == null) {
            Logger.getLogger(ReservationDBContext.class.getName()).log(Level.SEVERE, "Database connection is null");
            return null;
        }

        stmt.setInt(1, ID);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            Boolean gender = (rs.getObject("gender") != null) ? rs.getBoolean("gender") : null;

            User user = new User(
                    rs.getInt("userID"),
                    rs.getString("name"),
                    gender,
                    rs.getString("email"),
                    rs.getString("username"),
                    null, // Không lưu mật khẩu
                    rs.getString("phone"),
                    "", "" // Giá trị rỗng nếu cần
            );

            customer = new Customer(
                    rs.getInt("CustomerID"),
                    user,
                    rs.getString("Address")
            );
        } else {
            Logger.getLogger(ReservationDBContext.class.getName()).log(Level.INFO, "No customer found for userID: " + ID);
        }
    } catch (SQLException e) {
        Logger.getLogger(ReservationDBContext.class.getName()).log(Level.SEVERE, "Error fetching customer", e);
    }

    return customer;
}



    public void addCart(int serviceID, int userID, int amount) {
        PreparedStatement stm = null;
        try {
            String sql = "INSERT INTO carts (CheckService, ServiceID, UserID, Amount)"
                    + " VALUES (false, ?, ?, ?);";
            stm = DBContext.getConnection().prepareStatement(sql);
            stm.setInt(1, serviceID);
            stm.setInt(2, userID);
            stm.setInt(3, amount);

            // ✅ Use executeUpdate() instead
            stm.executeUpdate();
            Logger.getLogger(ServiceDBContext.class.getName()).log(Level.INFO, "Dịch vụ đã được thêm thành công: {0}", serviceID);

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

    public void updateCart(int amount, int serviceID, int userID) {
        PreparedStatement stm = null;
        try {
            String sql = "update carts  set amount = ?"
                    + " WHERE serviceID = ? and userID = ?";
            stm = DBContext.getConnection().prepareStatement(sql);
            stm.setInt(1, amount);
            stm.setInt(2, serviceID);
            stm.setInt(3, userID);
            // ✅ Use executeUpdate() instead
            stm.executeUpdate();
            Logger.getLogger(ServiceDBContext.class.getName()).log(Level.INFO, "Dịch vụ đã được thêm thành công: {0}", serviceID);

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

    public void deleteCart(int serviceID, int userID) {
        PreparedStatement stm = null;
        try {
            String sql = "DELETE FROM carts "
                    + " WHERE serviceID = ? and userID = ?";
            stm = DBContext.getConnection().prepareStatement(sql);
            stm.setInt(1, serviceID);
            stm.setInt(2, userID);
            // ✅ Use executeUpdate() instead
            stm.executeUpdate();
            Logger.getLogger(ServiceDBContext.class.getName()).log(Level.INFO, "Dịch vụ đã được thêm thành công: {0}", serviceID);

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

    public List<Cart> getAllCart() {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        Cart cart = null;
        List<Cart> carts = new ArrayList<>();
        try {
            String sql = "SELECT  c.* "
                    + "FROM carts c ";
            conn = DBContext.getConnection();
            stm = conn.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {// ✅ Chỉ lấy 1 kết quả duy nhất      
                User user = null;
                Service service = null;
                cart = new Cart(rs.getInt("ID"), rs.getBoolean("checkService"), rs.getInt("amount"), service, user);
                carts.add(cart);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServiceDBContext.class.getName()).log(Level.SEVERE, "Lỗi khi lấy giỏ hàng", ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stm != null) {
                    stm.close();
                }
                if (conn != null) {
                    conn.close(); // ✅ Đóng kết nối CSDL
                }
            } catch (SQLException ex) {
                Logger.getLogger(ServiceDBContext.class.getName()).log(Level.SEVERE, "Lỗi khi đóng tài nguyên", ex);
            }
        }
        return carts;
    }

    public List<Cart> getCart(String search, int userID, int categoryID, int page, int pageSize) {
        List<Cart> carts = new ArrayList<>();
        String sql = "SELECT c.*, s.categoryID, u.name AS authorName, s.serviceName,s.servicePrice,s.salePrice "
                + "FROM carts c "
                + "JOIN services s ON c.serviceID = s.serviceID "
                + "JOIN users u ON c.userID = u.userID "
                + "WHERE (COALESCE(?, '') = '' OR s.serviceName LIKE ?) AND (c.userID = ?) "
                + "AND (? = 0 OR s.categoryID = ?) "
                + "LIMIT ?, ?";

        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, search.isEmpty() ? "" : search);
            stmt.setString(2, search.isEmpty() ? "%" : "%" + search + "%");
            stmt.setInt(3, userID);
            stmt.setInt(4, categoryID);
            stmt.setInt(5, categoryID);
            stmt.setInt(6, (page - 1) * pageSize);
            stmt.setInt(7, pageSize);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int categoryID1 = rs.getObject("categoryID") != null ? rs.getInt("categoryID") : -1;
                ServiceCategory category = new ServiceCategory(categoryID1, "", "");
                User user = new User(rs.getInt("userID"), rs.getString("authorName"), true, "", "", "", "", "", "");
                Service service = new Service(rs.getInt("serviceID"), rs.getString("serviceName"), "", category, rs.getInt("servicePrice"), rs.getInt("salePrice"), "", true, user);
                Cart cart = new Cart(rs.getInt("ID"), rs.getBoolean("checkService"), rs.getInt("amount"), service, user);
                carts.add(cart);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return carts;

    }
public List<Cart> getCart1(String search, int userID, int categoryID, int page, int pageSize) {
    List<Cart> carts = new ArrayList<>();
    String sql = "SELECT c.*, s.categoryID, u.name AS authorName, s.serviceName, s.ServiceID, s.servicePrice, s.salePrice "
               + "FROM carts c "
               + "JOIN services s ON c.serviceID = s.serviceID "
               + "JOIN users u ON c.userID = u.userID "
               + "WHERE (COALESCE(?, '') = '' OR s.serviceName LIKE ?) AND (c.userID = ?) "
               + "AND (? = 0 OR s.categoryID = ?) AND c.CheckService='1' "
               + "LIMIT ?, ?";

    try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
        // Set the parameters for the PreparedStatement
        stmt.setString(1, search == null || search.isEmpty() ? "" : search); // search parameter for service name
        stmt.setString(2, search == null || search.isEmpty() ? "%" : "%" + search + "%"); // LIKE condition for search
        stmt.setInt(3, userID); // userID filter
        stmt.setInt(4, categoryID); // category filter (for filtering by category)
        stmt.setInt(5, categoryID); // category filter
        stmt.setInt(6, (page - 1) * pageSize); // offset for pagination
        stmt.setInt(7, pageSize); // limit for pagination

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            // Extract data from ResultSet
            int categoryID1 = rs.getObject("categoryID") != null ? rs.getInt("categoryID") : -1;
            ServiceCategory category = new ServiceCategory(categoryID1, "", ""); // Assuming category data is simplified
            
            // Create a User object
            User user = new User(rs.getInt("UserID"), rs.getString("authorName"), true, "", "", "", "", "", "");

            // Create a Service object
            Service service = new Service(
                rs.getInt("ServiceID"),
                rs.getString("serviceName"),
                "", // Assuming some field is empty
                category,
                rs.getInt("servicePrice"),
                rs.getInt("salePrice"),
                "", // Assuming some field is empty
                true, // Assuming the service is active
                user // Link to the user who is associated with the service
            );

            // Create a Cart object and add it to the list
            Cart cart = new Cart(rs.getInt("ID"), rs.getBoolean("checkService"), rs.getInt("amount"), service, user);
            carts.add(cart);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return carts;
}


    public List<Cart> getCart2(String search, int userID, int categoryID, int page, int pageSize) {
        List<Cart> carts = new ArrayList<>();
        String sql = "SELECT c.*, s.categoryID, u.name AS authorName, s.serviceName,s.servicePrice,s.salePrice "
                + "FROM carts c "
                + "JOIN services s ON c.serviceID = s.serviceID "
                + "JOIN users u ON c.userID = u.userID "
                + "WHERE (COALESCE(?, '') = '' OR s.serviceName LIKE ?) AND (c.userID = ?) "
                + "AND (? = 0 OR s.categoryID = ?) "
                + "LIMIT ?, ?";

        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, search.isEmpty() ? "" : search);
            stmt.setString(2, search.isEmpty() ? "%" : "%" + search + "%");
            stmt.setInt(3, userID);
            stmt.setInt(4, categoryID);
            stmt.setInt(5, categoryID);
            stmt.setInt(6, (page - 1) * pageSize);
            stmt.setInt(7, pageSize);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int categoryID1 = rs.getObject("categoryID") != null ? rs.getInt("categoryID") : -1;
                ServiceCategory category = new ServiceCategory(categoryID1, "", "");
                User user = new User(rs.getInt("userID"), rs.getString("authorName"), true, "", "", "", "", "", "");
                Service service = new Service(
                        rs.getInt("serviceID"),
                        rs.getString("serviceName"),
                        "",
                        category,
                        rs.getInt("servicePrice"),
                        rs.getInt("salePrice"),
                        "",
                        true,
                        user
                );
                Cart cart = new Cart(
                        rs.getInt("ID"),
                        rs.getBoolean("checkService"),
                        rs.getInt("amount"),
                        service,
                        user
                );
                carts.add(cart);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return carts;
    }

    public Cart getCartByID(int serviceID, int userID) {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        Cart cart = null;

        try {
            String sql = "SELECT s.serviceName, u.name as authorName, c.* "
                    + "FROM carts c "
                    + "JOIN services s ON c.serviceID = s.serviceID "
                    + "JOIN users u ON c.userID = u.userID "
                    + "WHERE c.serviceID = ? AND c.userID = ?";

            conn = DBContext.getConnection();
            stm = conn.prepareStatement(sql);
            stm.setInt(1, serviceID);
            stm.setInt(2, userID);
            rs = stm.executeQuery();

            if (rs.next()) { // ✅ Chỉ lấy 1 kết quả duy nhất
                ServiceCategory category = null;
                User user = new User(
                        rs.getInt("userID"), rs.getString("authorName"), true, "", "", "", "", "", ""
                );
                Service service = new Service(
                        rs.getInt("serviceID"), rs.getString("serviceName"), "", category, 0, 0, "", true, user
                );
                cart = new Cart(rs.getInt("ID"), rs.getBoolean("checkService"), rs.getInt("amount"), service, user);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServiceDBContext.class.getName()).log(Level.SEVERE, "Lỗi khi lấy giỏ hàng", ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stm != null) {
                    stm.close();
                }
                if (conn != null) {
                    conn.close(); // ✅ Đóng kết nối CSDL
                }
            } catch (SQLException ex) {
                Logger.getLogger(ServiceDBContext.class.getName()).log(Level.SEVERE, "Lỗi khi đóng tài nguyên", ex);
            }
        }
        return cart;
    }

    public void updateStatus(int userID, boolean checkService, int serviceID) {
        String sql = "UPDATE carts SET checkService = ? WHERE userID = ? and serviceID = ? ";
        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBoolean(1, checkService);
            stmt.setInt(2, userID);
            stmt.setInt(3, serviceID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Map<Integer, Integer> getReservationStatusCount() {
        Map<Integer, Integer> statusCount = new HashMap<>();
        String sql = "SELECT Status, COUNT(*) AS count FROM Reservations GROUP BY Status";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int status = rs.getInt("Status");
                int count = rs.getInt("count");
                statusCount.put(status, count);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusCount;
    }

    public Map<String, Double> getServiceRatings() {
        Map<String, Double> serviceRatings = new HashMap<>();
        String sql = "SELECT s.ServiceName, AVG(f.Rated) AS avgRating "
                + "FROM Feedbacks f "
                + "JOIN Services s ON f.ServiceID = s.ServiceID "
                + "WHERE f.Status = 1 "
                + "GROUP BY s.ServiceName";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String serviceName = rs.getString("ServiceName");
                double avgRating = rs.getDouble("avgRating");
                serviceRatings.put(serviceName, avgRating);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return serviceRatings;
    }

  public Map<String, Integer> getReservationsLast7Days() {
    Map<String, Integer> reservationTrends = new LinkedHashMap<>();

    String sql = "SELECT DATE(CreationDate) AS date, COUNT(*) AS count \n"
               + "FROM Reservations \n"
               + "WHERE Status = 1 \n"
               + "AND DATE(CreationDate) BETWEEN CURDATE() - INTERVAL 6 DAY AND CURDATE() \n"
               + "GROUP BY DATE(CreationDate) \n"
               + "ORDER BY DATE(CreationDate) ASC;";

    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            String date = rs.getString("date"); // Lấy ngày dưới dạng String
            int count = rs.getInt("count"); // Lấy số lượt đặt chỗ
            reservationTrends.put(date, count);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return reservationTrends;
}


    public int getNewReservationsCount() {
        int newReservations = 0;
        String sql = "SELECT COUNT(DISTINCT UserID) AS newReservations FROM Reservations "
                + "WHERE DATE(CreationDate) >= CURDATE() - INTERVAL 7 DAY";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                newReservations = rs.getInt("newReservations");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newReservations;
    }

    public Map<String, Double> getRevenueByCategory() {
        Map<String, Double> revenueMap = new HashMap<>();
        String sql = "SELECT sc.CategoryName, SUM(rd.Amount * s.ServicePrice) AS Revenue "
                + "FROM ReservationDetails rd "
                + "JOIN Services s ON rd.ServiceID = s.ServiceID "
                + "JOIN ServiceCategories sc ON s.CategoryID = sc.CategoryID "
                + "JOIN Reservations r ON rd.ReservationID = r.ReservationID "
                + "WHERE r.Status = 1 "
                + "AND r.CreationDate >= CURDATE() - INTERVAL 6 DAY "
                + "GROUP BY sc.CategoryName "
                + "ORDER BY Revenue DESC";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String categoryName = rs.getString("CategoryName");
                double revenue = rs.getDouble("Revenue");
                revenueMap.put(categoryName, revenue);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return revenueMap;
    }

 public static void main(String[] args) {
        ReservationDBContext db = new ReservationDBContext();
        
        int testID = 6; // Thay bằng một userID có trong database
        
        Customer customer = db.getCustomerByID(testID);

        if (customer != null) {
            System.out.println("Customer ID: " + customer.getCustomerID());
            System.out.println("Name: " + customer.getUser().getName());
            System.out.println("Email: " + customer.getUser().getEmail());
            System.out.println("Address: " + customer.getAddress());
        } else {
            System.out.println("Không tìm thấy khách hàng với ID: " + testID);
        }
    }

}
