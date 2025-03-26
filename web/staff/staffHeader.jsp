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
        body {
            margin: 0;
            padding: 0;
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
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
        <span class="navbar-toggler-icon"></span>
    </button>
    
    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav me-auto">
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/staff/reservationlist">
                    <i class="fas fa-list"></i> Reservations List
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/staff/examination/list">
                    <i class="fas fa-users"></i> Medical Examination
                </a>
            </li>
        </ul>

        <!-- Hiển thị nút Logout nhỏ hơn -->
        <%
            User user = (User) session.getAttribute("user");
            if (user != null) {
        %>
        <div class="d-flex align-items-center">
            <span class="text-white me-2"><%= user.getName() %></span>
            <a href="${pageContext.request.contextPath}/logout" class="btn btn-danger btn-sm">
                <i class="fas fa-sign-out-alt"></i>
            </a>
        </div>
        <% } %>
    </div>
</nav>

<!-- Bootstrap Script -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
