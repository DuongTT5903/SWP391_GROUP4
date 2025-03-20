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
import model.Slider;
import model.User;

/**
 *
 * @author admin
 */
public class SliderDBContext {

    /**
     * Lấy danh sách slider theo các điều kiện tìm kiếm, trạng thái, và phân
     * trang.
     *
     * @param search Từ khoá tìm kiếm (theo title/backlink)
     * @param status Trạng thái (true/false) hoặc null nếu lấy tất cả
     * @param pageIndex Trang hiện tại (bắt đầu từ 1)
     * @param pageSize Số slider trên mỗi trang
     * @return Danh sách slider
     */
    Connection conn = new DBContext().getConnection();

    public List<Slider> getActiveSliders() {

        List<Slider> sliders = new ArrayList<>();
        String sql = "SELECT SlideID, title, backLink, img, status, authorID FROM slider WHERE Status = 1";

        try (Connection conn = DBContext.getConnection(); PreparedStatement stm = conn.prepareStatement(sql)) {
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Slider slider = new Slider(
                            rs.getInt("SlideID"),
                            rs.getString("Title"),
                            rs.getString("BackLink"),
                            rs.getString("Img"),
                            rs.getBoolean("Status"),
                            new User(rs.getInt("AuthorID")) // Giả sử User có constructor nhận ID
                    );
                    sliders.add(slider);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, "Error fetching users with pagination", ex);
        }
        return sliders;
    }

    // Phương thức lấy danh sách slider theo tìm kiếm, trạng thái và phân trang
    public List<Slider> getSliders(String search, Boolean status, int pageIndex, int pageSize) {
        List<Slider> sliders = new ArrayList<>();
        int offset = (pageIndex - 1) * pageSize;

        StringBuilder sql = new StringBuilder(
                "SELECT SlideID, title, backLink, img, status, authorID "
                + "FROM slider "
                + "WHERE 1=1 "
        );

        // Thêm điều kiện tìm kiếm nếu có
        if (search != null && !search.trim().isEmpty()) {
            sql.append(" AND (title LIKE ? OR backLink LIKE ?) ");
        }
        // Thêm điều kiện lọc trạng thái nếu có
        if (status != null) {
            sql.append(" AND status = ? ");
        }
        // Sắp xếp theo SlideID và phân trang
        sql.append(" ORDER BY SlideID ASC LIMIT ?, ?");

        try (Connection conn = DBContext.getConnection(); PreparedStatement stm = conn.prepareStatement(sql.toString())) {

            int paramIndex = 1;
            if (search != null && !search.trim().isEmpty()) {
                stm.setString(paramIndex++, "%" + search + "%");
                stm.setString(paramIndex++, "%" + search + "%");
            }
            if (status != null) {
                stm.setBoolean(paramIndex++, status);
            }
            stm.setInt(paramIndex++, offset);
            stm.setInt(paramIndex++, pageSize);

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Slider slider = new Slider();
                    slider.setSlideID(rs.getInt("SlideID"));
                    slider.setTitle(rs.getString("title"));
                    slider.setBackLink(rs.getString("backLink"));
                    slider.setImg(rs.getString("img"));
                    slider.setStatus(rs.getBoolean("status"));
                    // Nếu cần lấy thông tin User tác giả:
                    // slider.setAuthor(new User(rs.getInt("authorID")));
                    sliders.add(slider);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(SliderDBContext.class.getName()).log(Level.SEVERE, "Lỗi khi lấy danh sách slider", ex);
        }
        return sliders;
    }

    /**
     * Đếm tổng số slider thoả mãn điều kiện tìm kiếm và trạng thái (nếu có).
     *
     * @param search Từ khoá tìm kiếm
     * @param status Trạng thái (true/false) hoặc null
     * @return Tổng số slider
     */
    public int countSliders(String search, Boolean status) {
        int total = 0;
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) AS Total FROM slider WHERE 1=1 ");

        if (search != null && !search.trim().isEmpty()) {
            sql.append(" AND (title LIKE ? OR backLink LIKE ?) ");
        }
        if (status != null) {
            sql.append(" AND status = ? ");
        }

        try (Connection conn = DBContext.getConnection(); PreparedStatement stm = conn.prepareStatement(sql.toString())) {

            int paramIndex = 1;
            if (search != null && !search.trim().isEmpty()) {
                stm.setString(paramIndex++, "%" + search + "%");
                stm.setString(paramIndex++, "%" + search + "%");
            }
            if (status != null) {
                stm.setBoolean(paramIndex++, status);
            }

            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    total = rs.getInt("Total");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(SliderDBContext.class.getName()).log(Level.SEVERE, "Lỗi khi đếm số slider", ex);
        }
        return total;
    }

    /**
     * Cập nhật trạng thái (ẩn/hiển thị) cho slider theo slideID.
     *
     * @param slideID ID của slider
     * @param newStatus true = hiển thị, false = ẩn
     */
    public void updateSliderStatus(int slideID, boolean newStatus) {
        String sql = "UPDATE slider SET status = ? WHERE SlideID = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement stm = conn.prepareStatement(sql)) {

            stm.setBoolean(1, newStatus);
            stm.setInt(2, slideID);
            stm.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(SliderDBContext.class.getName()).log(Level.SEVERE, "Lỗi khi cập nhật trạng thái slider", ex);
        }
    }

    /**
     * (Tuỳ chọn) Lấy slider theo ID nếu bạn cần cho chức năng edit chi tiết.
     *
     * @param slideID ID của slider
     * @return Đối tượng Slider hoặc null nếu không tìm thấy
     */
//    public Slider getSliderById(int slideID) {
//        String sql = "SELECT SlideID, title, backLink, img, status, authorID FROM slider WHERE SlideID = ?";
//        try (Connection conn = DBContext.getConnection();
//             PreparedStatement stm = conn.prepareStatement(sql)) {
//
//            stm.setInt(1, slideID);
//            try (ResultSet rs = stm.executeQuery()) {
//                if (rs.next()) {
//                    Slider slider = new Slider();
//                    slider.setSlideID(rs.getInt("SlideID"));
//                    slider.setTitle(rs.getString("title"));
//                    slider.setBackLink(rs.getString("backLink"));
//                    slider.setImg(rs.getString("img"));
//                    slider.setStatus(rs.getBoolean("status"));
//                    return slider;
//                }
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(SliderDBContext.class.getName()).log(Level.SEVERE, "Lỗi khi lấy slider theo ID", ex);
//        }
//        return null;
//    }


}
