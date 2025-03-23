
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Service" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="model.Reservation, model.ReservationDetail, java.util.List" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Reservation</title>
        <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Montserrat">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css">
        <script src="https://kit.fontawesome.com/bf043842f3.js" crossorigin="anonymous"></script>
    </head>
    <style>
        * {
            box-sizing: border-box;
        }
        body {
            font-family: Verdana, sans-serif;
            margin:0;
        }
        img {
            vertical-align: middle;
        }
        .nav-link{
            color: white;
            text-decoration: none;
            padding: 15px;
            display: block;
            text-align: center;
        }
        .navbar h3 {
            font-family: 'Montserrat', sans-serif;
            margin: 0;
        }
        .navbar a {
            color: #fff;
            text-decoration: none;
        }
        .navbar a:hover {
            color: #d1d1d1;
        }
    </style>
    <body>
        <div class="container">
            <jsp:include page="./headerCustomer.jsp" />
            <div class="reservation-detail">
                <h2 class="text-center mb-4">Reservation Information</h2>

                <!-- Hiển thị thông báo lỗi nếu có -->
                <% if (request.getAttribute("error") != null) { %>
                <p class="error text-center">${error}</p>
                <% } %>

                <!-- Hiển thị chi tiết đặt chỗ nếu có -->
                <% Reservation reservation = (Reservation) request.getAttribute("reservation"); %>
                <% if (reservation != null) {%>
                <p><strong>Reservation ID:</strong> <%= reservation.getReservationID()%></p>
                <p><strong>Customer Name:</strong> <%= reservation.getCustomerName()%></p>
                <p><strong>Email:</strong> <%= reservation.getEmail()%></p>
                <p><strong>Phone:</strong> <%= reservation.getPhone()%></p>
                <p><strong>Creation Date:</strong> <%= reservation.getCreationDate()%></p>
                <p><strong>Total Price:</strong> $<%= reservation.getTotalPrice()%></p>
                <p><strong>Status:</strong> 
                    <span class="badge <%= reservation.getStatus() == 1 ? "bg-success" : "bg-secondary"%>">
                        <%= reservation.getStatus() == 1 ? "Active" : "Inactive"%>
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
                        </tr>
                    </thead>
                    <tbody>
                        <% for (ReservationDetail detail : details) {%>
                        <tr>
                            <td><%= detail.getService().getServiceName()%></td>
                            <td><%= detail.getAmount()%></td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
                <% } else { %>
                <p class="text-center">No details found for this reservation.</p>
                <% } %>
                <% } else { %>
                <p class="text-center">No reservation found.</p>
                <% }%>

                <!-- Nút quay lại danh sách đặt chỗ -->
                <div class="text-center mt-4">
                    <a href="${pageContext.request.contextPath}/customer/myReservation" class="btn btn-primary">Back to Reservation History</a>
                </div>
            </div>

            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
            <jsp:include page="./footerCustomer.jsp" />
        </div>
    </body>
</html>