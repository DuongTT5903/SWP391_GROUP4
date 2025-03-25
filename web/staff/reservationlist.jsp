<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List, model.Reservation" %>
<jsp:useBean id="reservations" scope="request" type="java.util.List<model.Reservation>" />

<!DOCTYPE html>
<html>
    <head>
        <title>Reservation List</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    </head>
    <body class="container py-4">
        <h1 class="mb-4 text-center">Reservation List</h1>

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
                    <option value="1" <%= "1".equals(request.getParameter("status")) ? "selected" : ""%>>Đã thanh toán</option>
                    <option value="0" <%= "0".equals(request.getParameter("status")) ? "selected" : ""%>>Chưa thanh toán</option>
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
                    <% for (Reservation reservation : reservations) { %>
                    <tr>
                        <td><%= reservation.getReservationID() %></td>
                        <td><%= reservation.getCustomerName() %></td>
                        <td><%= reservation.getEmail() %></td>
                        <td><%= reservation.getPhone() %></td>
                        <td><%= reservation.getCreationDate() %></td>
                        <td><%= reservation.getTotalPrice()*1000 %></td>
                        <td>
                            <span class="badge <%= reservation.getStatus() == 1 ? "bg-success" : "bg-secondary" %>">
                                <%= reservation.getStatus() == 1 ? "Đã thanh toán" : "Chưa thanh toán" %>
                            </span>
                        </td>
                        <!-- Thêm liên kết đến trang chi tiết -->
                        <td>
                            <a href="${pageContext.request.contextPath}/staff/reservationdetail?reservationID=<%= reservation.getReservationID() %>" 
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
                    <% if (currentPage > 1) { %>
                    <li class="page-item">
                        <a class="page-link" href="reservationlist?page=<%= currentPage - 1 %><%= queryString %>" aria-label="Previous">
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

                    <% for (int i = 1; i <= totalPages; i++) { %>
                    <li class="page-item <%= (i == currentPage) ? "active" : "" %>">
                        <a class="page-link" href="reservationlist?page=<%= i %><%= queryString %>"><%= i %></a>
                    </li>
                    <% } %>

                    <% if (currentPage < totalPages) { %>
                    <li class="page-item">
                        <a class="page-link" href="reservationlist?page=<%= currentPage + 1 %><%= queryString %>" aria-label="Next">
                            <span aria-hidden="true">&raquo;</span>
                        </a>
                    </li>
                    <% } else { %>
                    <li class="page-item disabled">
                        <a class="page-link" href="#" aria-label="Next">
                            <span aria-hidden="true">&raquo;</span>
                        </a>
                    </li>
                    <% } %>
                </ul>
            </nav>
        </div>
                <div class="d-flex justify-content-center mt-3">
                            <a href="${pageContext.request.contextPath}/homepage" class="btn btn-outline-light px-4 py-2 fw-bold rounded-pill">
                                ⬅ Back to Home
                            </a>
                        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>