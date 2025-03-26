<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List, model.Reservation, java.text.NumberFormat" %>
<jsp:useBean id="reservations" scope="request" type="java.util.List<model.Reservation>" />

<!DOCTYPE html>
<html>
    <head>
      
        <title>Medical Reservation List</title>
        <style>
            body {
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                margin: 0;
                padding: 20px;
                background-color: #f5f7fa;
            }
            .container {
                max-width: 1200px;
                margin: 0 auto;
                background: white;
                padding: 25px;
                border-radius: 8px;
                box-shadow: 0 2px 15px rgba(0,0,0,0.1);
            }
            h1 {
                color: #2c3e50;
                margin-bottom: 25px;
                padding-bottom: 10px;
                border-bottom: 2px solid #3498db;
            }
            .filter-container {
                background: #f8f9fa;
                padding: 20px;
                border-radius: 8px;
                margin-bottom: 25px;
                box-shadow: 0 1px 3px rgba(0,0,0,0.1);
            }
            .filter-form {
                display: flex;
                flex-wrap: wrap;
                align-items: flex-end;
                gap: 20px;
            }
            .filter-group {
                flex: 1;
                min-width: 220px;
            }
            .filter-group label {
                display: block;
                margin-bottom: 8px;
                font-weight: 600;
                color: #2c3e50;
            }
            .filter-control {
                width: 100%;
                padding: 10px;
                border: 1px solid #ddd;
                border-radius: 4px;
                font-size: 14px;
                height: 40px;
                box-sizing: border-box;
            }
            .filter-actions {
                display: flex;
                align-items: center;
                height: 40px;
                margin-bottom: 5px;
            }
            .btn {
                padding: 10px 20px;
                border-radius: 4px;
                cursor: pointer;
                font-weight: 600;
                font-size: 14px;
                height: 40px;
                display: inline-flex;
                align-items: center;
                justify-content: center;
            }
            .btn-primary {
                background-color: #3498db;
                color: white;
                border: none;
                transition: background-color 0.3s;
            }
            .btn-primary:hover {
                background-color: #2980b9;
            }
            .btn-edit {
                background-color: #f39c12;
                color: white;
                border: none;
                transition: background-color 0.3s;
            }
            .btn-edit:hover {
                background-color: #e67e22;
            }
            .btn-save {
                background-color: #2ecc71;
                color: white;
                border: none;
                transition: background-color 0.3s;
                display: none;
            }
            .btn-save:hover {
                background-color: #27ae60;
            }
            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 20px;
                box-shadow: 0 1px 3px rgba(0,0,0,0.1);
            }
            th, td {
                padding: 15px;
                text-align: left;
                border-bottom: 1px solid #ecf0f1;
            }
            th {
                background-color: #2c3e50;
                color: white;
                font-weight: 600;
            }
            tr:nth-child(even) {
                background-color: #f8f9fa;
            }
            tr:hover {
                background-color: #f1f7fd;
            }
            .action-link {
                color: #3498db;
                text-decoration: none;
                font-weight: 500;
                transition: color 0.3s;
                margin-right: 10px;
            }
            .action-link:hover {
                color: #21618c;
                text-decoration: underline;
            }
            .no-data {
                text-align: center;
                padding: 20px;
                color: #7f8c8d;
                font-style: italic;
            }
            .prescription-text {
                display: inline-block;
                max-width: 300px;
                white-space: nowrap;
                overflow: hidden;
                text-overflow: ellipsis;
            }
            .prescription-edit {
                width: 100%;
                padding: 8px;
                border: 1px solid #ddd;
                border-radius: 4px;
                resize: vertical;
                min-height: 60px;
                display: none;
            }
            .action-buttons {
                display: flex;
                gap: 10px;
            }
            @media (max-width: 768px) {
                .filter-form {
                    flex-direction: column;
                }
                .filter-group {
                    width: 100%;
                }
                .filter-actions {
                    width: 100%;
                }
                .btn {
                    width: 100%;
                }
                .action-buttons {
                    flex-direction: column;
                }
            }
        </style>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    </head>
    <body >
          <jsp:include page="./staffHeader.jsp" />
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