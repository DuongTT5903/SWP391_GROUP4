package controller;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Feedback;
import dal.FeedbackDBContext;

@WebServlet(name = "FeedbackDetailController", urlPatterns = {"/manager/feedbackdetail"})
public class FeedbackDetailController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("action");
        FeedbackDBContext fdao = new FeedbackDBContext();

        if (action == null) {
            // --- Bắt đầu phân trang ---
            int page = 1;  // Mặc định trang đầu tiên
            int pageSize = 5; // Số feedback mỗi trang

            // Lấy số trang từ request (nếu có)
            if (request.getParameter("page") != null) {
                try {
                    page = Integer.parseInt(request.getParameter("page"));
                } catch (NumberFormatException e) {
                    page = 1; // Nếu lỗi, quay về trang đầu
                }
            }

            // Lấy danh sách feedback từ DB
            List<Feedback> allFeedbacks = fdao.getAllFeedbacks();
            int totalFeedbacks = allFeedbacks.size();
            int totalPages = (int) Math.ceil((double) totalFeedbacks / pageSize);

            // Đảm bảo `page` hợp lệ
            if (page < 1) page = 1;
            if (page > totalPages) page = totalPages;

            // Xác định vị trí bắt đầu và kết thúc trong danh sách
            int start = (page - 1) * pageSize;
            int end = Math.min(start + pageSize, totalFeedbacks);

            // Lấy danh sách feedback theo trang
            List<Feedback> flist = allFeedbacks.subList(start, end);

            // Đưa dữ liệu vào request để hiển thị trong JSP
            request.setAttribute("flist", flist);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);
            
            request.getRequestDispatcher("/manager/feedbackdetail.jsp").forward(request, response);
        } else if ("edit".equals(action)) {
            String fid = request.getParameter("fid");
            String fcreate = request.getParameter("fcreate");
            String frate = request.getParameter("frate");
            String content = request.getParameter("content");
            String fstatus = request.getParameter("fstatus");
            fdao.UpdateFeedback(Integer.parseInt(fid), content, Integer.parseInt(frate), fcreate, Integer.parseInt(fstatus));
            response.sendRedirect("feedbackdetail");
        } else if ("switch".equals(action)) {
            String fid = request.getParameter("fid");
            String fstatus = request.getParameter("fstatus");
            fdao.UpdateFeedbackStatus(Integer.parseInt(fid), Integer.parseInt(fstatus));
            response.sendRedirect("feedbackdetail");
        } else if ("delete".equals(action)) {
            String fid = request.getParameter("fid");
            fdao.deleteFeedback(Integer.parseInt(fid));
            response.sendRedirect("feedbackdetail");
        }
    }

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

    @Override
    public String getServletInfo() {
        return "Feedback Detail Controller with Pagination";
    }
}
