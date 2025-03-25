
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Service" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="model.Reservation, model.ReservationDetail, java.util.List" %>
<%
    Reservation reservation = (Reservation) request.getAttribute("reservation");
    List<ReservationDetail> reservationDetails = (List<ReservationDetail>) request.getAttribute("reservationDetails");
    String userRole = (String) request.getAttribute("userRole"); // Lấy vai trò của người dùng
%>
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
            <h1 class="mb-4 text-center">Reservation Information</h1>
            <div class="card mb-4">
                <div class="card-header">Basic Reservation Information</div>
                <div class="card-body">                
                    <p><strong>Customer Name:</strong> <%= reservation.getCustomerName()%></p>
                    <p><strong>Email:</strong> <%= reservation.getEmail()%></p>
                    <p><strong>Phone:</strong> <%= reservation.getPhone()%></p>
                    <p><strong>Address:</strong> <%= reservation.getAddress()%></p>
                    <p><strong>Creation Date:</strong> <%= reservation.getCreationDate()%></p>
                    <p><strong>Booking Date:</strong> <%= reservation.getBookingDate()%></p>
                    <p><strong>Status:</strong> 
                        <span class="badge <%= reservation.getStatus() == 1 ? "bg-success" : "bg-secondary"%>">
                            <%= reservation.getStatus() == 1 ? "Payment done" : "Not payment"%>
                        </span>
                    </p>
                    <p><strong>Accept Status:</strong> 
                        <span class="badge <%= reservation.getAcceptStatus() == 1 ? "bg-success" : "bg-secondary"%>">
                            <%= reservation.getAcceptStatus() == 1 ? "Payment done" : "Not payment"%>
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
                                <td><img src="<%= detail.getService().getImageURL()%>" alt="Thumbnail" width="50"></td>
                                <td><%= detail.getService().getServiceName()%></td>
                                <td><%= detail.getService().getCategory().getCategoryName()%></td>
                                <td><%= servicePrice * 1000%> VNĐ</td>
                                <td><%= salePrice%>%</td>
                                <td><%= amount%></td>
                                <td><%= totalCost * 1000%> VNĐ</td>
                            </tr>
                            <% }%>
                        </tbody>
                        <tfoot>
                            <tr>
                                <td colspan="6" class="text-end"><strong>Total:</strong></td>
                                <td><%= totalAmount * 1000%></td>
                            </tr>
                        </tfoot>
                    </table>
                </div>
            </div>

            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
            <jsp:include page="./footerCustomer.jsp" />
        </div>
    </body>
</html>