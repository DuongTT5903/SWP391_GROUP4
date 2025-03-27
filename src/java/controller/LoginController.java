package controller;

import dal.UserDBContext;
import model.User;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.math.BigInteger;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

/**
 * @author yugio
 */
@WebServlet(name = "LoginController", urlPatterns = {"/login"})
public class LoginController extends HttpServlet {

    private UserDBContext userDBContext = new UserDBContext();

    // Thông tin Google OAuth (lấy từ Google Console)
    private static final String CLIENT_ID = "117147386228-fj6fr5a89rgrv1bqaabfmjsglehdlvpe.apps.googleusercontent.com";
    private static final String CLIENT_SECRET = ""; // Thay bằng Client Secret từ Google Console
    private static final String REDIRECT_URI = "http://localhost:8080/SWP391_GROUP4/login";
    private static final String TOKEN_ENDPOINT = "https://oauth2.googleapis.com/token";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Kiểm tra nếu có mã code từ Google
        String code = request.getParameter("code");
        if (code != null) {
            try {
                // Gửi yêu cầu đến Google Token Endpoint để lấy access_token và id_token
                CloseableHttpClient httpClient = HttpClients.createDefault();
                HttpPost httpPost = new HttpPost(TOKEN_ENDPOINT);

                // Thiết lập các tham số cho yêu cầu
                List<BasicNameValuePair> params = new ArrayList<>();
                params.add(new BasicNameValuePair("code", code));
                params.add(new BasicNameValuePair("client_id", CLIENT_ID));
                params.add(new BasicNameValuePair("client_secret", CLIENT_SECRET));
                params.add(new BasicNameValuePair("redirect_uri", REDIRECT_URI));
                params.add(new BasicNameValuePair("grant_type", "authorization_code"));
                httpPost.setEntity(new UrlEncodedFormEntity(params));

                // Thực hiện yêu cầu và lấy phản hồi
                try (CloseableHttpResponse httpResponse = httpClient.execute(httpPost)) {
                    String jsonResponse = EntityUtils.toString(httpResponse.getEntity());
                    Gson gson = new Gson();
                    Map<String, Object> tokenResponse = gson.fromJson(jsonResponse, Map.class);

                    // Kiểm tra nếu có lỗi trong phản hồi
                    if (tokenResponse.containsKey("error")) {
                        request.setAttribute("e", "Lỗi từ Google: " + tokenResponse.get("error"));
                        request.getRequestDispatcher("views/login.jsp").forward(request, response);
                        return;
                    }

                    // Lấy id_token từ phản hồi
                    String idTokenString = (String) tokenResponse.get("id_token");
                    if (idTokenString == null) {
                        request.setAttribute("e", "Không tìm thấy id_token trong phản hồi từ Google");
                        request.getRequestDispatcher("views/login.jsp").forward(request, response);
                        return;
                    }

                    // Giải mã id_token (JWT) để lấy thông tin người dùng
                    String[] jwtParts = idTokenString.split("\\.");
                    if (jwtParts.length != 3) {
                        request.setAttribute("e", "id_token không hợp lệ");
                        request.getRequestDispatcher("views/login.jsp").forward(request, response);
                        return;
                    }

                    // Giải mã phần payload của JWT
                    String payloadJson = new String(Base64.getUrlDecoder().decode(jwtParts[1]));
                    Map<String, Object> payload = gson.fromJson(payloadJson, Map.class);

                    // Lấy thông tin người dùng từ payload
                    String email = (String) payload.get("email");
                    String name = (String) payload.get("name");
                    String pictureUrl = (String) payload.get("picture");

                    // Kiểm tra xem user đã tồn tại trong database chưa
                    User user = userDBContext.getUserByEmail(email);
//                    if (user == null) {
//                        // Nếu user chưa tồn tại, tạo mới
//                        user = new User();
//                        user.setName(name);
//                        user.setEmail(email);
//                        user.setUsername(email); // Dùng email làm username
//                        user.setPassword(""); // Không cần password cho Google login
//                        user.setGender(false); // Mặc định giới tính (có thể để người dùng cập nhật sau)
//                        user.setPhone(""); // Để trống, người dùng có thể cập nhật sau
//                        user.setRole("4"); // RoleID mặc định là 4 (user)
//                        user.setImageURL(pictureUrl); // Lưu URL ảnh đại diện từ Google
////                        userDBContext.insertUser(user); // Thêm user vào database
//                    }

                    // Lưu user vào session
                    HttpSession session = request.getSession();
                    session.setAttribute("user", user);
                    session.setAttribute("roleID", user.getRole());

                    // Chuyển hướng đến trang chủ
                    response.sendRedirect(request.getContextPath() + "/homepage");
                }
            } catch (Exception e) {
                request.setAttribute("e", "Lỗi khi đăng nhập bằng Google: " + e.getMessage());
                request.getRequestDispatcher("views/login.jsp").forward(request, response);
            }
        } else {
            // Nếu không có code, hiển thị trang đăng nhập
            request.getRequestDispatcher("views/login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // Lấy username và password từ form
    String username = request.getParameter("username");
    String password = request.getParameter("password");

    // Mã hóa mật khẩu trước khi kiểm tra trong database
    String hashedPassword = hashPassword(password);

    User user = userDBContext.getUserByUsername(username, hashedPassword);

    if (user != null) {
        // Đăng nhập thành công, lưu user vào session
        HttpSession session = request.getSession();
        session.setAttribute("user", user);

        String roleID = userDBContext.getRoleIDByUsernameAndPassword(username, hashedPassword);
        session.setAttribute("roleID", roleID);

        // Điều hướng dựa vào roleID
        switch (roleID) {
            case "1":
                response.sendRedirect(request.getContextPath() + "/admin/dashboard");
                break;
            case "2":
                response.sendRedirect(request.getContextPath() + "/manager/customerList");
                break;
            case "3":
                response.sendRedirect(request.getContextPath() + "/staff/reservationlist");
                break;
            case "4":
                response.sendRedirect(request.getContextPath() + "/homepage");
                break;
            default:
                // Nếu role không hợp lệ, quay lại trang login với thông báo lỗi
                request.setAttribute("e", "Tài khoản không có quyền truy cập");
                request.getRequestDispatcher("views/login.jsp").forward(request, response);
                break;
        }
    } else {
        // Đăng nhập thất bại, báo lỗi
        request.setAttribute("e", "Sai tài khoản hoặc mật khẩu");
        request.getRequestDispatcher("views/login.jsp").forward(request, response);
    }
}

    private String hashPassword(String password) {
        String salt = "RANDOM_SALT"; // Nên lưu salt riêng cho từng user
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest((salt + password).getBytes(StandardCharsets.UTF_8));
            BigInteger number = new BigInteger(1, hashedBytes);
            StringBuilder hexString = new StringBuilder(number.toString(16));

            while (hexString.length() < 64) {
                hexString.insert(0, '0');
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Lỗi khi mã hóa mật khẩu", e);
        }
    }
}