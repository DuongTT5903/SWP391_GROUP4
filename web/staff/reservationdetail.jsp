<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Reservation, model.ReservationDetail, java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Reservation Detail</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }
        .reservation-detail {
            border: 1px solid #ccc;
            padding: 20px;
            max-width: 800px;
            margin: 0 auto;
            background-color: #f9f9f9;
        }
        .reservation-detail h2 {
            margin-top: 0;
        }
        .reservation-detail p {
            margin: 10px 0;
        }
        .error {
            color: red;
            font-weight: bold;
        }
        .table {
            margin-top: 20px;
        }
    </style>
</head>
<body class="container py-4">
    <div class="reservation-detail">
        <h2 class="text-center mb-4">Reservation Detail</h2>

        <!-- Hiển thị thông báo lỗi nếu có -->
        <% if (request.getAttribute("error") != null) { %>
            <p class="error text-center">${error}</p>
        <% } %>

        <!-- Hiển thị chi tiết đặt chỗ nếu có -->
        <% Reservation reservation = (Reservation) request.getAttribute("reservation"); %>
        <% if (reservation != null) { %>
            <p><strong>Reservation ID:</strong> <%= reservation.getReservationID() %></p>
            <p><strong>Customer Name:</strong> <%= reservation.getCustomerName() %></p>
            <p><strong>Email:</strong> <%= reservation.getEmail() %></p>
            <p><strong>Phone:</strong> <%= reservation.getPhone() %></p>
            <p><strong>Creation Date:</strong> <%= reservation.getCreationDate() %></p>
            <p><strong>Total Price:</strong> $<%= reservation.getTotalPrice() %></p>
            <p><strong>Status:</strong> 
                <span class="badge <%= reservation.getStatus() == 1 ? "bg-success" : "bg-secondary" %>">
                    <%= reservation.getStatus() == 1 ? "Active" : "Inactive" %>
                </span>
            </p>

            <!-- Hiển thị danh sách chi tiết đặt chỗ (nếu có) -->
            <% List<ReservationDetail> details = reservation.getDetails(); %>
            <% if (details != null && !details.isEmpty()) { %>
                <h4 class="mt-4">Reservation Details</h4>
                <table class="table table-bordered table-hover text-center">
                    <thead class="table-dark">
                        <tr>
                            <th>Service</th>
                            <th>Amount</th>
                            <th>Number of Persons</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (ReservationDetail detail : details) { %>
                            <tr>
                                <td><%= detail.getService().getServiceName() %></td>
                                <td><%= detail.getAmount() %></td>
                                <td><%= detail.getNumberOfPerson() %></td>
                            </tr>
                        <% } %>
                    </tbody>
                </table>
            <% } else { %>
                <p class="text-center">No details found for this reservation.</p>
            <% } %>
        <% } else { %>
            <p class="text-center">No reservation found.</p>
        <% } %>

        <!-- Nút quay lại danh sách đặt chỗ -->
        <div class="text-center mt-4">
            <a href="${pageContext.request.contextPath}/staff/reservationlist" class="btn btn-primary">Back to Reservation List</a>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>