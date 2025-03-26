<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List, model.Reservation, java.text.NumberFormat" %>
<jsp:useBean id="reservations" scope="request" type="java.util.List<model.Reservation>" />

<!DOCTYPE html>
<html>
    <head>
           <jsp:include page="./staffHeader.jsp" />
        <title>Reservation List</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
        <style>
            .action-btns {
                display: flex;
                gap: 5px;
                justify-content: center;
            }
            .btn-change-status {
                background-color: #6f42c1;
                color: white;
                border: none;
            }
            .btn-change-status:hover {
                background-color: #5a32a3;
                color: white;
            }
            .btn-accept-payment {
                background-color: #28a745;
                color: white;
                border: none;
            }
            .btn-accept-payment:hover {
                background-color: #218838;
                color: white;
            }
            .btn-cancel-payment {
                background-color: #dc3545;
                color: white;
                border: none;
            }
            .btn-cancel-payment:hover {
                background-color: #c82333;
                color: white;
            }
            .badge-pending {
                background-color: #ffc107;
                color: #212529;
            }
            .badge-paid {
                background-color: #28a745;
                color: white;
            }
        </style>
    </head>
    <body class="container py-4">
        <h1 class="mb-4 text-center">Reservation List</h1>

        <!-- Filter and Search Form -->
        <form method="GET" action="${pageContext.request.contextPath}/staff/reservationlist" class="row g-3 mb-4">
            <!-- Search by Customer Name or Email -->
            <div class="col-md-3">
                <label for="search" class="form-label">Search:</label>
                <input type="text" class="form-control" name="search" id="search" 
                       value="<%= request.getParameter("search") != null ? request.getParameter("search") : "" %>">
            </div>

            <!-- Filter by Status -->
            <div class="col-md-2">
                <label for="status" class="form-label">Status:</label>
                <select class="form-select" name="status" id="status">
                    <option value="">All</option>
                    <option value="1" <%= "1".equals(request.getParameter("status")) ? "selected" : "" %>>Active</option>
                    <option value="0" <%= "0".equals(request.getParameter("status")) ? "selected" : "" %>>Inactive</option>
                </select>
            </div>

            <!-- Filter by Accept Status -->
            <div class="col-md-2">
                <label for="acceptStatus" class="form-label">Payment Status:</label>
                <select class="form-select" name="acceptStatus" id="acceptStatus">
                    <option value="">All</option>
                    <option value="1" <%= "1".equals(request.getParameter("acceptStatus")) ? "selected" : "" %>>Paid</option>
                    <option value="0" <%= "0".equals(request.getParameter("acceptStatus")) ? "selected" : "" %>>Unpaid</option>
                </select>
            </div>

            <!-- Filter by Date Range -->
            <div class="col-md-3">
                <label for="fromDate" class="form-label">From Date:</label>
                <input type="date" class="form-control" name="fromDate" id="fromDate" 
                       value="<%= request.getParameter("fromDate") != null ? request.getParameter("fromDate") : "" %>">
            </div>
            <div class="col-md-3">
                <label for="toDate" class="form-label">To Date:</label>
                <input type="date" class="form-control" name="toDate" id="toDate" 
                       value="<%= request.getParameter("toDate") != null ? request.getParameter("toDate") : "" %>">
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
                    <th>Date</th>
                    <th>Total</th>
                    <th>Status</th>
                    <th>Payment Status</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <% if (reservations != null && !reservations.isEmpty()) { 
                    NumberFormat nf = NumberFormat.getInstance();
                    nf.setMinimumFractionDigits(2);
                    nf.setMaximumFractionDigits(2);
                %>
                    <% for (Reservation reservation : reservations) { %>
                    <tr>
                        <td><%= reservation.getReservationID() %></td>
                        <td><%= reservation.getCustomerName() %></td>
                        <td><%= reservation.getEmail() %></td>
                        <td><%= reservation.getPhone() %></td>
                        <td><%= reservation.getCreationDate() %></td>
                        <td><%= nf.format(reservation.getTotalPrice()) %></td>
                        <td>
                            <span class="badge <%= reservation.getStatus() == 1 ? "bg-success" : "bg-secondary" %>">
                                <%= reservation.getStatus() == 1 ? "Active" : "Inactive" %>
                            </span>
                        </td>
                        <td>
                            <span class="badge <%= reservation.getAcceptStatus() == 1 ? "badge-paid" : "badge-pending" %>">
                                <%= reservation.getAcceptStatus() == 1 ? "Paid" : "Unpaid" %>
                            </span>
                        </td>
                        <td>
                            <div class="action-btns">
                                <!-- View Details Button -->
                                <a href="${pageContext.request.contextPath}/staff/reservationdetail?reservationID=<%= reservation.getReservationID() %>" 
                                   class="btn btn-info btn-sm">Details</a>
                                
                                <!-- Change Status Button -->
                                <form method="POST" action="${pageContext.request.contextPath}/staff/reservationlist" style="display:inline;">
                                    <input type="hidden" name="reservationId" value="<%= reservation.getReservationID() %>">
                                    <input type="hidden" name="newStatus" value="<%= reservation.getStatus() == 1 ? "0" : "1" %>">
                                    <input type="hidden" name="page" value="<%= request.getParameter("page") != null ? request.getParameter("page") : 1 %>">
                                    <input type="hidden" name="search" value="<%= request.getParameter("search") != null ? request.getParameter("search") : "" %>">
                                    <input type="hidden" name="status" value="<%= request.getParameter("status") != null ? request.getParameter("status") : "" %>">
                                    <input type="hidden" name="acceptStatus" value="<%= request.getParameter("acceptStatus") != null ? request.getParameter("acceptStatus") : "" %>">
                                    <input type="hidden" name="fromDate" value="<%= request.getParameter("fromDate") != null ? request.getParameter("fromDate") : "" %>">
                                    <input type="hidden" name="toDate" value="<%= request.getParameter("toDate") != null ? request.getParameter("toDate") : "" %>">
                                    
                                   
                                </form>
                                
                                <!-- Payment Action Buttons -->
                                <form method="POST" action="${pageContext.request.contextPath}/staff/reservationlist" style="display:inline;">
                                    <input type="hidden" name="reservationId" value="<%= reservation.getReservationID() %>">
                                    <input type="hidden" name="newAcceptStatus" value="<%= reservation.getAcceptStatus() == 1 ? "0" : "1" %>">
                                    <input type="hidden" name="page" value="<%= request.getParameter("page") != null ? request.getParameter("page") : 1 %>">
                                    <input type="hidden" name="search" value="<%= request.getParameter("search") != null ? request.getParameter("search") : "" %>">
                                    <input type="hidden" name="status" value="<%= request.getParameter("status") != null ? request.getParameter("status") : "" %>">
                                    <input type="hidden" name="acceptStatus" value="<%= request.getParameter("acceptStatus") != null ? request.getParameter("acceptStatus") : "" %>">
                                    <input type="hidden" name="fromDate" value="<%= request.getParameter("fromDate") != null ? request.getParameter("fromDate") : "" %>">
                                    <input type="hidden" name="toDate" value="<%= request.getParameter("toDate") != null ? request.getParameter("toDate") : "" %>">
                                    
                                    <button type="submit" class="btn btn-sm <%= reservation.getAcceptStatus() == 1 ? "btn-cancel-payment" : "btn-accept-payment" %>">
                                        <%= reservation.getAcceptStatus() == 1 ? "Cancel Payment" : "Confirm Payment" %>
                                    </button>
                                </form>
                            </div>
                        </td>
                    </tr>
                    <% } %>
                <% } else { %>
                    <tr>
                        <td colspan="9" class="text-center">No reservations found</td>
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
                        if (request.getParameter("search") != null) {
                            queryString += "&search=" + request.getParameter("search");
                        }
                        if (request.getParameter("status") != null) {
                            queryString += "&status=" + request.getParameter("status");
                        }
                        if (request.getParameter("acceptStatus") != null) {
                            queryString += "&acceptStatus=" + request.getParameter("acceptStatus");
                        }
                        if (request.getParameter("fromDate") != null) {
                            queryString += "&fromDate=" + request.getParameter("fromDate");
                        }
                        if (request.getParameter("toDate") != null) {
                            queryString += "&toDate=" + request.getParameter("toDate");
                        }
                    %>
                    <% if (currentPage > 1) { %>
                    <li class="page-item">
                        <a class="page-link" href="reservationlist?page=<%= currentPage - 1 %><%= queryString %>">&laquo;</a>
                    </li>
                    <% } else { %>
                    <li class="page-item disabled">
                        <a class="page-link" href="#">&laquo;</a>
                    </li>
                    <% } %>

                    <% for (int i = 1; i <= totalPages; i++) { %>
                    <li class="page-item <%= (i == currentPage) ? "active" : "" %>">
                        <a class="page-link" href="reservationlist?page=<%= i %><%= queryString %>"><%= i %></a>
                    </li>
                    <% } %>

                    <% if (currentPage < totalPages) { %>
                    <li class="page-item">
                        <a class="page-link" href="reservationlist?page=<%= currentPage + 1 %><%= queryString %>">&raquo;</a>
                    </li>
                    <% } else { %>
                    <li class="page-item disabled">
                        <a class="page-link" href="#">&raquo;</a>
                    </li>
                    <% } %>
                </ul>
            </nav>
        </div>

        <div class="d-flex justify-content-center mt-3">
            <a href="${pageContext.request.contextPath}/homepage" class="btn btn-outline-primary">
                &larr; Back to Home
            </a>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>