<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Service" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Service Cart</title>
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
            <a href="${pageContext.request.contextPath}/serviceList" class="btn btn-primary mt-3" style="margin-bottom: 16px;">Thêm dịch vụ</a>
            <br>
            <c:if test="${not empty cartMessage}">
                <p class="text-danger fw-bold">${cartMessage}</p>
            </c:if>

            <c:if test="${not empty cartItems}">
                <table class="table table-bordered">
                    <thead class="bg-dark text-white">
                        <tr>
                            <th>#</th>
                            <th>Tên dịch vụ</th>
                            <th>Giá</th>
                            <th>Số lượng</th>
                            <th>Tổng giá</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="s" items="${cartItems}" varStatus="status">                      
                            <tr>
                                <!-- Checkbox cập nhật trạng thái -->
                                <td>
                                    <form action="UpdateCartStatus" method="post">
                                        <input type="hidden" name="serviceID" value="${s.service.serviceID}">
                                        <input type="hidden" name="userID" value="${s.user.userID}">
                                        <input type="checkbox" name="checkService" value="true"
                                               ${s.checkService ? "checked" : ""} 
                                               onchange="this.form.submit()">
                                    </form>            
                                </td>

                                <!-- Hiển thị thông tin dịch vụ -->
                                <td>${s.service.serviceName}</td>
                                <td>${(s.service.servicePrice * (100 - s.service.salePrice)) / 100}00 VNĐ</td>

                                <!-- Cập nhật số lượng -->
                                <td>
                                    <form action="UpdateCart" method="POST">
                                        <input type="hidden" name="serviceID" value="${s.service.serviceID}">
                                        <input type="hidden" name="userID" value="${s.user.userID}">
                                        <div class="input-group d-flex align-items-center">
                                            <button class="btn btn-secondary px-3" type="submit" name="update" value="decrease">-</button>
                                            <span class="mx-3 text-center" style="min-width: 20px;">${s.amount}</span>
                                            <button class="btn btn-secondary px-3" type="submit" name="update" value="increase">+</button>
                                        </div>
                                    </form>
                                </td>

                                <!-- Hiển thị tổng giá -->
                                <td>${s.amount * (s.service.servicePrice * (100 - s.service.salePrice)) / 100}00 VNĐ</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
            <p>Tổng Thanh Toán: ${total}00 VNĐ</p>
            <nav class="mt-4">
                <ul class="pagination justify-content-center">
                    <!-- Nút Trang Trước -->
                    <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                        <a class="page-link" href="${pageContext.request.contextPath}/customer/shoppingCart?page=${currentPage - 1}&search=${param.search}&category=${param.category}"><<</a>
                    </li>

                    <!-- Hiển thị các số trang -->
                    <c:forEach var="i" begin="1" end="${totalPages}">
                        <li class="page-item ${i == currentPage ? 'active' : ''}">
                            <a class="page-link" href="${pageContext.request.contextPath}/customer/shoppingCart?page=${i}&search=${param.search}&category=${param.category}">${i}</a>
                        </li>
                    </c:forEach>

                    <!-- Nút Trang Sau -->
                    <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                        <a class="page-link" href="${pageContext.request.contextPath}/customer/shoppingCart?page=${currentPage + 1}&search=${param.search}&category=${param.category}">>></a>
                    </li>
                </ul>
            </nav>
            <c:if test="${not empty cartItems}">
                <a href="${pageContext.request.contextPath}\customer\reservation" class="btn btn-primary" style="margin-bottom: 5px">Thanh toán</a>
            </c:if>
           <jsp:include page="./footerCustomer.jsp" />
        </div>
    </body>
</html>
