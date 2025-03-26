package VNPAY;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet xử lý thanh toán qua VNPay
 */
public class ajaxServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
    
        HttpSession session = req.getSession();

        // Lấy giá trị từ session và kiểm tra null
        Object reservationObj = session.getAttribute("reservationID");
        Object totalPriceObj = session.getAttribute("totalPrice");

        // Kiểm tra nếu session không có giá trị thì chuyển hướng về trang giỏ hàng
        if (reservationObj == null || totalPriceObj == null) {
            resp.sendRedirect("cart");
            return;
        }

        // Chuyển đổi kiểu dữ liệu
        int reservationID = (int) reservationObj;
        double totalPrice = Double.parseDouble(totalPriceObj.toString());

        // Process total bill and other parameters
        double amountDouble = totalPrice * 10000;
        String bankCode = req.getParameter("bankCode");
        
        // Các tham số cố định
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";
        String vnp_TmnCode = Config.vnp_TmnCode;
        String vnp_IpAddr = Config.getIpAddress(req);
        
        // Tạo mã giao dịch duy nhất
        String vnp_TxnRef = String.valueOf(System.currentTimeMillis());
        
        // Khai báo danh sách tham số theo yêu cầu - sử dụng TreeMap để tự động sắp xếp theo bảng chữ cái
        Map<String, String> vnp_Params = new TreeMap<>();
        vnp_Params.put("vnp_Amount", String.valueOf((long) (amountDouble * 10)));
        
        if (bankCode != null && !bankCode.isEmpty()) {
            vnp_Params.put("vnp_BankCode", bankCode);
        }
        
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_CreateDate", new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7")).getTime()));
        vnp_Params.put("vnp_CurrCode", "VND");
        
        // Thêm thời gian hết hạn
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        cld.add(Calendar.MINUTE, 15);
        vnp_Params.put("vnp_ExpireDate", new SimpleDateFormat("yyyyMMddHHmmss").format(cld.getTime()));
        
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
        
        // Set locale
        String locate = req.getParameter("language");
        if (locate != null && !locate.isEmpty()) {
            vnp_Params.put("vnp_Locale", locate);
        } else {
            vnp_Params.put("vnp_Locale", "vn");
        }
        
        // Định dạng thông tin đơn hàng giống mẫu
     vnp_Params.put("vnp_OrderInfo", String.valueOf(reservationID));
        vnp_Params.put("vnp_OrderType", orderType);
        vnp_Params.put("vnp_ReturnUrl", Config.vnp_ReturnUrl);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_Version", vnp_Version);

        // Tạo chuỗi ký tự để mã hóa
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator<String> itr = vnp_Params.keySet().iterator();
        
        while (itr.hasNext()) {
            String fieldName = itr.next();
            String fieldValue = vnp_Params.get(fieldName);
            if (fieldValue != null && !fieldValue.isEmpty()) {
                // Build hash data
                hashData.append(fieldName).append('=')
                       .append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                
                // Build query
                query.append(fieldName).append('=')
                     .append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                     
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }

        // Tạo chữ ký bảo mật
        String vnp_SecureHash = Config.hmacSHA512(Config.secretKey, hashData.toString());
        query.append("&vnp_SecureHash=").append(vnp_SecureHash);

        // Chuyển hướng đến VNPay với URL định dạng giống mẫu: vnpay_return?param1=value1&param2=value2...
        String paymentUrl = Config.vnp_PayUrl + "?" + query.toString();
        resp.sendRedirect(paymentUrl);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp); // Chuyển hướng GET sang xử lý giống POST
    }
}