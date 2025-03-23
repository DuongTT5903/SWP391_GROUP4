package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Feedback;
import model.Service;
import model.User;

/**
 *
 * @author yugio
 */
public class FeedbackDBContext {

    /**
     *
     * @return
     */
   public List<Feedback> getAllFeedbacks() {
    List<Feedback> feedbackList = new ArrayList<>();
    String sql = "SELECT f.FeedbackID, f.FeedbackDetail, f.CustomerID, f.Rated, f.imglink, "
               + "f.serviceID, s.ServiceName, f.CreationDate, f.Status, u.UserID, u.Name AS UserName, u.Email, u.Phone "
               + "FROM Feedbacks f "
               + "INNER JOIN Users u ON f.CustomerID = u.UserID "
               + "INNER JOIN Services s ON f.serviceID = s.ServiceID";
    
    try (Connection connection = DBContext.getConnection(); PreparedStatement stm = connection.prepareStatement(sql); ResultSet rs = stm.executeQuery()) {

        while (rs.next()) {
            Feedback feedback = new Feedback(
                    rs.getInt("FeedbackID"),
                    rs.getString("FeedbackDetail"),
                    rs.getInt("CustomerID"),
                    rs.getInt("Rated"),
                    rs.getString("imglink"),
                    new Service(rs.getInt("serviceID"), rs.getString("ServiceName"), null, null, 0.0f, 0.0f, null, false, null),
                    rs.getDate("CreationDate"),
                    rs.getBoolean("Status"),
                    new User(rs.getInt("UserID"), rs.getString("UserName"), false, rs.getString("Email"), null, null, rs.getString("Phone"), null, null)
            );
            feedbackList.add(feedback);
        }
    } catch (SQLException ex) {
        Logger.getLogger(FeedbackDBContext.class.getName()).log(Level.SEVERE, "Error fetching all feedbacks", ex);
    }
    return feedbackList;
}

    

        public void UpdateFeedback(int fid, String detail, int rate, String crDate, int status) {
    try {
        Connection connection = new DBContext().getConnection();
        String sql = "UPDATE Feedbacks SET FeedbackDetail = ?, Rated = ?, CreationDate = ?, Status = ? WHERE FeedbackID = ?";
        PreparedStatement stm = connection.prepareStatement(sql);
        stm.setString(1, detail);
        stm.setInt(2, rate);
        stm.setString(3, crDate);
        stm.setInt(4, status);
        stm.setInt(5, fid);
        stm.executeUpdate();
    } catch (Exception e) {
        e.printStackTrace();
    }
    }
    public void UpdateFeedbackStatus(int fid, int status) {
    try {
        Connection connection = new DBContext().getConnection();
        String sql = "UPDATE Feedbacks SET Status = ? WHERE FeedbackID = ?";
        PreparedStatement stm = connection.prepareStatement(sql);
        stm.setInt(1, status);
        stm.setInt(2, fid);
        stm.executeUpdate();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
public void deleteFeedback(int fid) {
    try {
        Connection connection = new DBContext().getConnection();
        String sql = "DELETE FROM Feedbacks WHERE FeedbackID = ?";
        PreparedStatement stm = connection.prepareStatement(sql);
        stm.setInt(1, fid);
        stm.executeUpdate();
    } catch (Exception e) {
        e.printStackTrace();
    }
}


    /**
     *
     * @param userID
     * @return
     */
    public List<Feedback> getFeedbacksByUser(int userID) {
        List<Feedback> feedbackList = new ArrayList<>();
        String sql = "SELECT f.FeedbackID, f.FeedbackDetail, f.CustomerID, f.Rated, f.imglink, "
                + "f.serviceID, f.CreationDate, f.Status, u.UserID, u.Name AS UserName "
                + "FROM Feedbacks f INNER JOIN Users u ON f.CustomerID = u.UserID "
                + "WHERE u.UserID = ?";

        try (Connection connection = DBContext.getConnection(); PreparedStatement stm = connection.prepareStatement(sql)) {

            stm.setInt(1, userID);
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Feedback feedback = new Feedback(
                            rs.getInt("FeedbackID"),
                            rs.getString("FeedbackDetail"),
                            rs.getInt("CustomerID"),
                            rs.getInt("Rated"),
                            rs.getString("imglink"),
                            new Service(rs.getInt("serviceID"), null, null, null, 0.0f, 0.0f, null, false, null),
                            rs.getDate("CreationDate"),
                            rs.getBoolean("Status"),
                            new User(rs.getInt("UserID"), rs.getString("UserName"), false, null, null, null, null, null, null)
                    );
                    feedbackList.add(feedback);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(FeedbackDBContext.class.getName()).log(Level.SEVERE, "Error fetching feedbacks by user", ex);
        }
        return feedbackList;
    }
    public Feedback getFeedbackByID(int feedbackID) {
    Feedback feedback = null;
    String sql = "SELECT f.FeedbackID, f.FeedbackDetail, f.CustomerID, f.Rated, f.imglink, "
               + "f.serviceID, s.Name AS ServiceName, f.CreationDate, f.Status, u.UserID, u.Name AS UserName "
               + "FROM Feedbacks f "
               + "INNER JOIN Users u ON f.CustomerID = u.UserID "
               + "INNER JOIN Services s ON f.serviceID = s.ServiceID "
               + "WHERE f.FeedbackID = ?";

    try (Connection connection = DBContext.getConnection();
         PreparedStatement stm = connection.prepareStatement(sql)) {

        stm.setInt(1, feedbackID);
        try (ResultSet rs = stm.executeQuery()) {
            if (rs.next()) {
                feedback = new Feedback(
                        rs.getInt("FeedbackID"),
                        rs.getString("FeedbackDetail"),
                        rs.getInt("CustomerID"),
                        rs.getInt("Rated"),
                        rs.getString("imglink"),
                        new Service(rs.getInt("serviceID"), rs.getString("ServiceName"), null, null, 0.0f, 0.0f, null, false, null),
                        rs.getDate("CreationDate"),
                        rs.getBoolean("Status"),
                        new User(rs.getInt("UserID"), rs.getString("UserName"), false, null, null, null, null, null, null)
                );
            }
        }
    } catch (SQLException ex) {
        Logger.getLogger(FeedbackDBContext.class.getName()).log(Level.SEVERE, "Error fetching feedback by ID", ex);
    }
    return feedback;
    }
    public void addFeedback(Feedback feedback) {
        String sql = "INSERT INTO Feedbacks (FeedbackDetail, CustomerID, Rated, imglink, ServiceID, CreationDate, Status) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, feedback.getFeedbackDetail());
            ps.setInt(2, feedback.getCustomerID());   // Hoặc feedback.getUser().getUserID()
            ps.setInt(3, feedback.getRated());
            ps.setString(4, feedback.getImgLink());
            // Giả sử feedback.getServices() != null
            ps.setInt(5, feedback.getServices().getServiceID());
            // Lấy ngày hiện tại hoặc feedback.getCreationDate()
            ps.setDate(6, new java.sql.Date(new Date().getTime()));
            ps.setBoolean(7, true); // Status = true => feedback hiển thị, tuỳ logic

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Lấy danh sách feedback cho 1 service
    public List<Feedback> getFeedbackByServiceID(int serviceID) {
        List<Feedback> list = new ArrayList<>();
        String sql = "SELECT * FROM Feedbacks WHERE ServiceID = ? AND Status = 1";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, serviceID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Feedback f = new Feedback();
                f.setId(rs.getInt("FeedbackID"));
                f.setFeedbackDetail(rs.getString("FeedbackDetail"));
                f.setCustomerID(rs.getInt("CustomerID"));
                f.setRated(rs.getInt("Rated"));
                f.setImgLink(rs.getString("imglink"));
                // ...
                // Tuỳ bạn có join sang Services, Users để lấy thêm thông tin
                list.add(f);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}

