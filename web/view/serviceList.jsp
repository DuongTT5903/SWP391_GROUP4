<%-- 
    Document   : service_staff
    Created on : Feb 4, 2025, 10:50:48 PM
    Author     : trung
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Service" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Danh sách Dịch vụ</title>
        <link rel="icon" type="image/png" href="image/favicon-16x16.png">
        <link rel="stylesheet" href="style.css">
        <script src="https://kit.fontawesome.com/bf043842f3.js" crossorigin="anonymous"></script>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        <style>
            .service-list {
                display: grid;
                grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
                gap: 20px;
            }

            .service-item {
                border: 1px solid #ddd;
                padding: 15px;
                border-radius: 10px;
                text-align: center;
                transition: transform 0.3s ease-in-out;
            }

            .service-item:hover {
                transform: scale(1.05);
                box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
            }

            .service-item img {
                width: 100%;
                height: 180px;
                object-fit: cover;
                border-radius: 5px;
            }

            .price {
                text-decoration: line-through;
                color: gray;
            }

            .sale-price {
                font-size: 18px;
                color: red;
                font-weight: bold;
            }

            .buttons button {
                margin: 5px;
            }

            .nav-link{
                color: white;
                text-decoration: none;
                padding: 15px;
                display: block;
                text-align: center;
            }
        </style>
        <script>
            function addToCart(serviceName) {
                let cart = JSON.parse(localStorage.getItem('cart')) || [];
                cart.push(serviceID);
                localStorage.setItem('cart', JSON.stringify(cart));
                alert("Dịch vụ " + serviceName + " đã được thêm vào giỏ hàng!");
            }

            function giveFeedback(serviceID) {
                let feedback = prompt("Nhập phản hồi của bạn:");
                if (feedback) {
                    alert("Cảm ơn bạn đã gửi phản hồi!");
                }
            }
        </script>
    </head>

    <body>

        <div class="container">
            <nav class="navbar navbar-expand-lg bg-secondary" style="height: 55px;">
                <div class="container-fluid">
                    <c:choose>
                        <c:when test="${sessionScope.userRole == 'customer'}">
                            <!-- Content for customer role -->
                            <h3 class="w3-wide public"> 
                                <b><a href="/customer" style="text-decoration: none;color: white;">Customer</a></b>
                            </h3>
                        </c:when>
                        <c:when test="${sessionScope.userRole == 'manager'}">
                            <h3 class="w3-wide public"> 
                                <b><a href="/manager" style="text-decoration: none;color: white;">Manager</a></b>
                            </h3>
                        </c:when>

                        <c:when test="${sessionScope.userRole == 'staff'}">
                            <!-- Content for staff role -->
                            <h3 class="w3-wide public"> 
                                <b><a href="/staff" style="text-decoration: none;color: white;">Staff</a></b>
                            </h3>
                        </c:when>

                        <c:when test="${sessionScope.userRole == 'admin'}">
                            <!-- Content for admin role -->
                            <h3 class="w3-wide public"> 
                                <b><a href="/admin" style="text-decoration: none;color: white;">Admin</a></b>
                            </h3>
                        </c:when>

                        <c:otherwise>
                            <!-- Default content if userRole is not recognized -->
                            <h3 class="w3-wide public"> 
                                <b><a href="${pageContext.request.contextPath}/login" style="text-decoration: none;color: white;">LOGIN</a></b>
                            </h3>
                        </c:otherwise>
                    </c:choose>
                    <div class="collapse navbar-collapse" id="navbarNav">
                        <ul class="navbar-nav ms-auto">
                            <li class="nav-item"><a class="nav-link " href="${pageContext.request.contextPath}\homepage">Homepage</a></li>
                            <li class="nav-item"><a class="nav-link " href="#">Blog</a></li>
                            <li class="nav-item"><a class="nav-link " href="#">Reservations</a></li>
                        </ul>
                    </div>
                </div>
            </nav>

            <h2 class="text-center">Service List</h2>
            <!-- Hộp tìm kiếm -->
            <form action="serviceList" method="GET" class="mb-4 d-flex gap-2">

                <select name="category" class="form-select " style="width: 500px;">
                    <option value="0">Tất cả loại</option>
                    <option value="1">Nha Khoa</option>
                    <option value="2">Y tế tổng quát</option>
                    <option value="3">Tiêm chủng</option>
                </select>
                <div class="input-group ">
                    <input type="text" name="search" class="form-control" placeholder="Tìm kiếm..." value="">
                    <button class="btn btn-primary" type="submit">
                        <i class="fa-solid fa-magnifying-glass"></i>
                    </button>
                </div>

            </form>

            <!-- Danh sách dịch vụ -->
            <center>  <div class="service-list" style="width: 750px; ">
                    <c:forEach var="service" items="${services}">
                        <div class="service-item">
                            <a href="serviceDetail.jsp?id=${service.serviceID}" style="text-decoration: none;">
                                <h3>${service.serviceName}</h3>
                            </a>
                            <p>${service.serviceDetail}</p>
                            <c:choose> <c:when test="${service.servicePrice != service.servicePrice*(100-service.salePrice)/100}">
                                    <p><span class="price">${service.servicePrice}00VNĐ</span>
                                    </c:when>
                                    <c:otherwise>
                                    </c:otherwise>
                                </c:choose>
                                <span class="sale-price">${service.servicePrice*(100-service.salePrice)/100}00VNĐ</span></p>
                            <div class="buttons">
                                <button class="btn btn-success" name="action" value="buy" onclick="addToCart(${service.serviceName})">Mua</button>
                                <button class="btn btn-outline-secondary" name="action" value="feedback" onclick="giveFeedback(${service.serviceID})">Phản
                                    hồi</button>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </center>


            <!-- Phân trang -->
            <nav class="mt-4">
                <ul class="pagination justify-content-center">
                    <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                        <a class="page-link"
                           href="${pageContext.request.contextPath}\serviceList?page=0&search=&category=0">« Trang
                            trước</a>
                    </li>
                    <li class="page-item">
                        <a class="page-link"
                           href="${pageContext.request.contextPath}\serviceList?page=2&search=&category=0">Trang sau
                            »</a>
                    </li>
                </ul>
            </nav>

            <!-- Liên hệ -->
            <footer class="text-center mt-4">
                <p>Liên hệ: <a href="mailto:support@example.com">support@example.com</a></p>
            </footer>
        </div>
    </body>

</html>