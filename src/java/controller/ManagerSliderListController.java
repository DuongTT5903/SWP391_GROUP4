package controller;

import dal.SliderDBContext;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Slider;


public class ManagerSliderListController extends HttpServlet {

    /**
     * Phương thức xử lý chung cho cả GET và POST.
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null || action.isEmpty()) {
            action = "list"; // Mặc định hiển thị danh sách
        }

        switch (action) {
            case "hide":
                capNhatTrangThaiSlider(request, response, false);
                break;
            case "show":
                capNhatTrangThaiSlider(request, response, true);
                break;
            // Nếu cần chức năng chỉnh sửa (edit) thì thêm case "edit":
            // case "edit":
            //     chinhSuaSlider(request, response);
            //     break;
            case "list":
            default:
                hienThiDanhSachSlider(request, response);
                break;
        }
    }

    /**
     * Hiển thị danh sách slider (có phân trang, tìm kiếm, lọc trạng thái).
     */
    protected void hienThiDanhSachSlider(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy tham số tìm kiếm, trạng thái, trang
        String rawSearch = request.getParameter("search");
        String rawStatus = request.getParameter("status");
        String rawPage = request.getParameter("page");

        String search = (rawSearch == null) ? "" : rawSearch.trim();

        // Xử lý trạng thái: "1" => true, "0" => false, null => lấy tất cả
        Boolean status = null;
        if (rawStatus != null && !rawStatus.isEmpty()) {
            if (rawStatus.equals("1")) {
                status = true;
            } else if (rawStatus.equals("0")) {
                status = false;
            }
        }

        // Xử lý số trang
        int pageIndex = 1;
        if (rawPage != null && !rawPage.isEmpty()) {
            try {
                pageIndex = Integer.parseInt(rawPage);
            } catch (NumberFormatException e) {
                // Nếu không parse được, giữ nguyên là trang 1
            }
        }
        int pageSize = 5; // Số slider trên mỗi trang

        // Gọi DBContext để lấy dữ liệu
        SliderDBContext db = new SliderDBContext();
        List<Slider> sliders = db.getSliders(search, status, pageIndex, pageSize);
        int totalRecords = db.countSliders(search, status);
        int totalPages = (totalRecords % pageSize == 0)
                ? (totalRecords / pageSize)
                : (totalRecords / pageSize + 1);

        // Gửi dữ liệu sang JSP
        request.setAttribute("sliders", sliders);
        request.setAttribute("pageIndex", pageIndex);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("search", search);
        request.setAttribute("status", rawStatus);

        // Forward đến trang JSP (sliderList.jsp)
        request.getRequestDispatcher("sliderList.jsp").forward(request, response);
    }

    /**
     * Cập nhật trạng thái slider (ẩn/hiển thị).
     */
    protected void capNhatTrangThaiSlider(HttpServletRequest request, HttpServletResponse response, boolean newStatus)
            throws ServletException, IOException {
        String rawId = request.getParameter("slideID");
        if (rawId != null && !rawId.isEmpty()) {
            try {
                int slideID = Integer.parseInt(rawId);
                SliderDBContext db = new SliderDBContext();
                db.updateSliderStatus(slideID, newStatus);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        // Sau khi cập nhật xong, quay về danh sách
        response.sendRedirect(request.getContextPath() + "/manager/sliderList?action=list");
    }

    /**
     * (Tuỳ chọn) Xử lý chỉnh sửa slider nếu bạn có trang edit riêng.
     */
//    protected void chinhSuaSlider(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        String rawId = request.getParameter("slideID");
//        if (rawId != null && !rawId.isEmpty()) {
//            try {
//                int slideID = Integer.parseInt(rawId);
//                SliderDBContext db = new SliderDBContext();
//                Slider slider = db.getSliderById(slideID);
//                if (slider != null) {
//                    request.setAttribute("slider", slider);
//                    // Forward sang trang JSP edit, ví dụ: sliderEdit.jsp
//                    request.getRequestDispatcher("/sliderEdit.jsp").forward(request, response);
//                    return;
//                }
//            } catch (NumberFormatException e) {
//                e.printStackTrace();
//            }
//        }
//        // Nếu không tìm thấy hoặc lỗi, quay về danh sách
//        response.sendRedirect(request.getContextPath() + "/manager/sliderList?action=list");
//    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
