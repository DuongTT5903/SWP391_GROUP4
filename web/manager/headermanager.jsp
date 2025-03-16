
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.User"%>
<%@page session="true"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard</title>
    <!-- Bootstrap 5 -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <!-- Font Awesome (Icons) -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        .navbar {
            background-color: #343a40;
            padding: 10px 20px;
        }
        .navbar-brand {
            color: white !important;
            font-size: 20px;
            font-weight: bold;
        }
        .nav-link {
            color: white !important;
        }
        .nav-link:hover {
            color: #f8f9fa !important;
        }
        .dropdown-menu {
            right: 0;
            left: auto;
        }
        .avatar {
            width: 40px;
            height: 40px;
            border-radius: 50%;
            object-fit: cover;
        }
    </style>
</head>
<body>

<!-- Navbar -->
<nav class="navbar navbar-expand-lg navbar-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="<%= request.getContextPath() %>/homepage">
            <i class="fas fa-cogs"></i> Homepage
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>
        
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav me-auto">
                <li class="nav-item">
                    <a class="nav-link" href="postList"><i class="fas fa-list"></i> Post List</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="customerList"><i class="fas fa-users"></i> User List</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="listservice"><i class="fas fa-tags"></i> Service List</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="feedbackdetail"><i class="fas fa-list"></i>Feedbacklist</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="reservationList"><i class="fas fa-list"></i>Reservation List</a>
                </li>
            </ul>

            <!-- User Dropdown -->
            <%
                User user = (User) session.getAttribute("user");
                if (user != null) {
            %>
            <div class="dropdown">
                <a class="nav-link dropdown-toggle d-flex align-items-center text-white" href="#" role="button" data-bs-toggle="dropdown">
                    <img src="<%= user.getImageURL() %>" alt="Avatar" class="avatar">
                    <span class="ms-2"><%= user.getName() %></span>
                </a>
                <ul class="dropdown-menu dropdown-menu-end">
                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/userProfile"><i class="fas fa-user"></i> Hồ sơ</a></li>
                    
                    <li><hr class="dropdown-divider"></li>
                    <li><a class="dropdown-item text-danger" href="${pageContext.request.contextPath}/logout"><i class="fas fa-sign-out-alt"></i> Đăng xuất</a></li>
                </ul>
            </div>
            <% } %>
        </div>
    </div>
</nav>

<!-- Bootstrap Script -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
