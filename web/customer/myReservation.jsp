
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Service" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="java.util.List, model.Reservation" %>
<jsp:useBean id="reservations" scope="request" type="java.util.List<model.Reservation>" />
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
            <h1 class="mb-4 text-center">Reservation History</h1>

            <!-- Filter and Search Form -->
            <form action="reservationlist" method="GET" class="row g-3 mb-4">
                <!-- Search by Customer Name or Email -->
                <div class="col-md-3">
                    <label for="search" class="form-label">Search by Customer Name or Email:</label>
                    <input type="text" class="form-control" name="search" id="search" 
                           value="<%= request.getParameter("search") != null ? request.getParameter("search") : ""%>">
                </div>

                <!-- Filter by Status -->
                <div class="col-md-2">
                    <label for="status" class="form-label">Status:</label>
                    <select class="form-select" name="status" id="status">
                        <option value="">All</option>
                        <option value="1" <%= "1".equals(request.getParameter("status")) ? "selected" : ""%>>Active</option>
                        <option value="0" <%= "0".equals(request.getParameter("status")) ? "selected" : ""%>>Inactive</option>
                    </select>
                </div>

                <!-- Filter by Date Range -->
                <div class="col-md-3">
                    <label for="fromDate" class="form-label">From Date:</label>
                    <input type="date" class="form-control" name="fromDate" id="fromDate" 
                           value="<%= request.getParameter("fromDate") != null ? request.getParameter("fromDate") : ""%>">
                </div>
                <div class="col-md-3">
                    <label for="toDate" class="form-label">To Date:</label>
                    <input type="date" class="form-control" name="toDate" id="toDate" 
                           value="<%= request.getParameter("toDate") != null ? request.getParameter("toDate") : ""%>">
                </div>

                <!-- Filter Button -->
                <div class="col-md-1 d-flex align-items-end">
                    <button type="submit" class="btn btn-primary w-100">Filter</button>
                </div>
            </form>

            <!-- Reservation Table -->
            <table class="table table-bordered table-hover text-center">
                <thead class="table-dark">
                    <tr>
                        <th>ID</th>
                        <th>Customer Name</th>
                        <th>Email</th>
                        <th>Phone</th>
                        <th>Creation Date</th>
                        <th>Total Price</th>
                        <th>Status</th>
                        <th>Action</th> <!-- Thêm cột Action -->
                    </tr>
                </thead>
                <tbody>
                    <% if (reservations != null && !reservations.isEmpty()) { %>
                    <% for (Reservation reservation : reservations) {%>
                    <tr>
                        <td><%= reservation.getReservationID()%></td>
                        <td><%= reservation.getCustomerName()%></td>
                        <td><%= reservation.getEmail()%></td>
                        <td><%= reservation.getPhone()%></td>
                        <td><%= reservation.getCreationDate()%></td>
                        <td>$<%= reservation.getTotalPrice()%></td>
                        <td>
                            <span class="badge <%= reservation.getStatus() == 1 ? "bg-success" : "bg-secondary"%>">
                                <%= reservation.getStatus() == 1 ? "Active" : "Inactive"%>
                            </span>
                        </td>
                        <!-- Thêm liên kết đến trang chi tiết -->
                        <td>
                            <a href="${pageContext.request.contextPath}/customer/reservationInfo?reservationID=<%= reservation.getReservationID()%>" 
                               class="btn btn-info btn-sm">View Details</a>
                        </td>
                    </tr>
                    <% } %>
                    <% } else { %>
                    <tr>
                        <td colspan="8">No reservations found.</td>
                    </tr>
                    <% } %>
                </tbody>
            </table>

            <!-- Pagination -->
            <div class="d-flex justify-content-center mt-4">
                <nav>
                    <ul class="pagination">
                        <%
                            int currentPage = (request.getAttribute("currentPage") != null)
                                    ? (int) request.getAttribute("currentPage") : 1;
                            int totalPages = (request.getAttribute("totalPages") != null)
                                    ? (int) request.getAttribute("totalPages") : 1;
                            String queryString = "";
                            // Giả sử bạn cần nối thêm các tham số filter vào liên kết, có thể xử lý thêm nếu cần.
                        %>
                        <% if (currentPage > 1) {%>
                        <li class="page-item">
                            <a class="page-link" href="myReservation?page=<%= currentPage - 1%><%= queryString%>" aria-label="Previous">
                                <span aria-hidden="true">&laquo;</span>
                            </a>
                        </li>
                        <% } else { %>
                        <li class="page-item disabled">
                            <a class="page-link" href="#" aria-label="Previous">
                                <span aria-hidden="true">&laquo;</span>
                            </a>
                        </li>
                        <% } %>

                        <% for (int i = 1; i <= totalPages; i++) {%>
                        <li class="page-item <%= (i == currentPage) ? "active" : ""%>">
                            <a class="page-link" href="myReservation?page=<%= i%><%= queryString%>"><%= i%></a>
                        </li>
                        <% } %>

                        <% if (currentPage < totalPages) {%>
                        <li class="page-item">
                            <a class="page-link" href="myReservation?page=<%= currentPage + 1%><%= queryString%>" aria-label="Next">
                                <span aria-hidden="true">&raquo;</span>
                            </a>
                        </li>
                        <% } else { %>
                        <li class="page-item disabled">
                            <a class="page-link" href="#" aria-label="Next">
                                <span aria-hidden="true">&raquo;</span>
                            </a>
                        </li>
                        <% }%>
                    </ul>
                </nav>
            </div>
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
            <jsp:include page="./footerCustomer.jsp" />
        </div>
    </body>
</html>
