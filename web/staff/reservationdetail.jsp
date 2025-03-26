<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Reservation,model.ReservationDetail, model.Service, model.ServiceCategory, model.User, java.util.List" %>
<%
    Reservation reservation = (Reservation) request.getAttribute("reservation");
    List<ReservationDetail> reservationDetails = (List<ReservationDetail>) request.getAttribute("reservationDetails");
    String userRole = (String) request.getAttribute("userRole"); // Lấy vai trò của người dùng
%>

<!DOCTYPE html>
<html>
    <head>
        
        <title>Reservation Detail</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    </head>
    <body class="container py-4">
        <h1 class="mb-4 text-center">Reservation Detail</h1>

        <!-- Thông tin cơ bản của đặt chỗ -->
        <div class="card mb-4">
            <div class="card-header">Basic Reservation Information</div>
            <div class="card-body">
                <p><strong>Reservation ID:</strong> <%= reservation.getReservationID() %></p>
                <p><strong>Customer Name:</strong> <%= reservation.getCustomerName() %></p>
                <p><strong>Email:</strong> <%= reservation.getEmail() %></p>
                <p><strong>Phone:</strong> <%= reservation.getPhone() %></p>
                <p><strong>Address:</strong> <%= reservation.getAddress() %></p>
                <p><strong>Creation Date:</strong> <%= reservation.getCreationDate() %></p>
                <p><strong>Status:</strong> 
                    <span class="badge <%= reservation.getStatus() == 1 ? "bg-success" : "bg-secondary" %>">
                        <%= reservation.getStatus() == 1 ? "Payment done" : "Not payment" %>
                    </span>
                </p>
            </div>
        </div>

        <!-- Danh sách dịch vụ đã đặt -->
        <div class="card mb-4">
            <div class="card-header">Reserved Services</div>
            <div class="card-body">
                <table class="table table-bordered">
                    <thead>
                        <tr>
                            <th>Thumbnail</th>
                            <th>Name</th>
                            <th>Category</th>
                            <th>Unit Price</th>
                            <th>Sale Price</th>
                            <th>Number of Persons</th>
                            <th>Total Cost</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% 
                            double totalAmount = 0; // Khởi tạo biến tổng
                            for (ReservationDetail detail : reservationDetails) { 
                                double servicePrice = detail.getService().getServicePrice();
                                double salePrice = detail.getService().getSalePrice();
                                int amount = detail.getAmount();
                                double totalCost = servicePrice * amount - (servicePrice * amount * salePrice / 100);
                                totalAmount += totalCost; // Cộng dồn vào tổng
                        %>
                        <tr>
                            <td><img src="<%= detail.getService().getImageURL() %>" alt="Thumbnail" width="50"></td>
                            <td><%= detail.getService().getServiceName() %></td>
                            <td><%= detail.getService().getCategory().getCategoryName() %></td>
                            <td><%= servicePrice*1000 %></td>
                            <td>%<%= salePrice %></td>
                            <td><%= amount %></td>
                            <td><%= totalCost*1000 %></td>
                        </tr>
                        <% } %>
                    </tbody>
                    <tfoot>
                        <tr>
                            <td colspan="6" class="text-end"><strong>Total:</strong></td>
                            <td><%= totalAmount*1000 %></td>
                        </tr>
                    </tfoot>
                </table>
            </div>
        </div>

        <!-- Chức năng thay đổi trạng thái và giao cho nhân viên khác (chỉ hiển thị cho Manager) -->
        <% if ("manager".equals(userRole)) { %>
        <div class="card mb-4">
            <div class="card-header">Manager Actions</div>
            <div class="card-body">
                <form action="updateReservation" method="POST">
                    <input type="hidden" name="reservationID" value="<%= reservation.getReservationID() %>">
                    <div class="mb-3">
                        <label for="status" class="form-label">Change Status:</label>
                        <select class="form-select" name="status" id="status">
                            <option value="1" <%= reservation.getStatus() == 1 ? "selected" : "" %>>Active</option>
                            <option value="0" <%= reservation.getStatus() == 0 ? "selected" : "" %>>Inactive</option>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label for="assignedStaff" class="form-label">Assign to Staff:</label>
                        <select class="form-select" name="assignedStaff" id="assignedStaff">
                            <!-- Danh sách nhân viên từ database -->
                            <option value="1">Staff 1</option>
                            <option value="2">Staff 2</option>
                        </select>
                    </div>
                    <button type="submit" class="btn btn-primary">Update</button>
                </form>
            </div>
        </div>
        <% } %>

        <!-- Nút quay lại -->
        <div class="text-center mt-4">
            <a href="${pageContext.request.contextPath}/staff/reservationlist" class="btn btn-secondary">Back to Reservation List</a>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>