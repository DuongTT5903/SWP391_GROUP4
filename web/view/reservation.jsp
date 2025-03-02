<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Service" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Reservation</title>
        <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
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
    </style>
    <body >

        <div class="container">
            <h2 class="text-center mb-4">Đặt Chỗ</h2>

            <form action="reservation" method="GET" class="row g-3">
                <div class="col-md-4">
                    <select name="category" class="form-select">
                        <option value="0">Tất cả loại</option>
                        <option value="1">Nha Khoa</option>
                        <option value="2">Y tế tổng quát</option>
                        <option value="3">Tiêm chủng</option>
                    </select>
                </div>
                <div class="col-md-6">
                    <div class="input-group">
                        <input type="text" name="search" class="form-control" placeholder="Tìm kiếm..." value="">
                        <button class="btn btn-primary" type="submit">
                            <i class="fa-solid fa-magnifying-glass"></i>
                        </button>
                    </div>
                </div>
            </form>
            <p>Total:${total}</p><br>
            <c:if test="${not empty cartMessage}">
                <p class="text-danger fw-bold">${cartMessage}</p>
            </c:if>

            <c:if test="${not empty cartItems}">
                <table class="table table-bordered">
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>Tên dịch vụ</th>
                            <th>Giá</th>
                            <th>Số lượng</th>
                            <th>Số người</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="s" items="${cartItems}" varStatus="status">
                            <tr>
                                <td>${status.index + 1}</td>
                                <td>${s.service.serviceName}</td>
                                <td>${(s.service.servicePrice * (100 - s.service.salePrice))/100}00VNĐ</td>
                                <td>${s.amount}</td>
                                <td>${s.numberOfPerson}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>

            <nav class="mt-4">
                <ul class="pagination justify-content-center">
                    <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                        <a class="page-link" href="${pageContext.request.contextPath}/reservation?page=1&search=&category=0">« Trang trước</a>
                    </li>
                    <li class="page-item">
                        <a class="page-link" href="${pageContext.request.contextPath}/reservation?page=2&search=&category=0">Trang sau »</a>
                    </li>
                </ul>
            </nav>

            <form action="reservation" method="POST" class="mt-4">
                <div class="row g-3">
                    <c:if test="${sessionScope.roleID == '4'}">
                        <div class="col-md-6">
                            <label for="name" class="form-label">Họ và tên:</label>
                            <input type="text"  name="name" class="form-control" value="${sessionScope.user.username}" style="background-color: gray" readonly>
                        </div>

                        <div class="col-md-6">
                            <label for="address" class="form-label">Địa chỉ:</label>
                            <input type="text"  name="address" class="form-control" value="${customer.adress}" style="background-color: gray" readonly>
                        </div>

                        <div class="col-md-6">
                            <label for="email" class="form-label">Email:</label>
                            <input type="email"  name="email" class="form-control" value="${sessionScope.user.email}" style="background-color: gray" readonly>
                        </div>

                        <div class="col-md-6">
                            <label for="phone" class="form-label">Số điện thoại:</label>
                            <input type="tel"  name="phone" class="form-control" value="${sessionScope.user.phone}" style="background-color: gray" readonly>
                        </div>

                        <div class="col-md-6">
                            <label for="gender" class="form-label">Giới tính:</label>
                            <select class="form-select" name="gender" readonly>
                                <c:if test="${sessionScope.user.gender==false}">
                                    <option value="0">Nam</option>
                                </c:if>
                                <c:if test="${sessionScope.user.gender==true}">
                                    <option value="1">Nữ</option>
                                </c:if>
                            </select>
                        </div>
                    </c:if>
                    <c:if test="${sessionScope.roleID != '4'}">
                        <div class="col-md-6">
                            <label for="name" class="form-label">Họ và tên:</label>
                            <input type="text"  name="name" class="form-control" minlength="6" maxlength="50" required>
                        </div>

                        <div class="col-md-6">
                            <label for="address" class="form-label">Địa chỉ:</label>
                            <input type="text"  name="address" class="form-control" minlength="40" maxlength="200" required>
                        </div>

                        <div class="col-md-6">
                            <label for="email" class="form-label">Email:</label>
                            <input type="email"  name="email" class="form-control" required>
                        </div>

                        <div class="col-md-6">
                            <label for="phone" class="form-label">Số điện thoại:</label>
                            <input type="tel"  name="phone" class="form-control" pattern="[0-9]{10}" required>
                        </div>

                        <div class="col-md-6">
                            <label for="gender" class="form-label">Giới tính:</label>
                            <select class="form-select" name="gender" required>
                                <option value="0">Nam</option>
                                <option value="1">Nữ</option>
                            </select>
                        </div>
                    </c:if>
                </div>
                    <div class="col-md-12">
                        <label class="form-label">Phương thức thanh toán:</label>
                        <div class="btn-group w-100">
                            <input type="radio" class="btn-check" id="creditCard" name="payment" value="0" onclick="toggleCardDetails(true)">
                            <label class="btn btn-outline-primary" for="creditCard">Thẻ tín dụng</label>

                            <input type="radio" class="btn-check" id="bankApp" name="payment" value="1" onclick="toggleCardDetails(false)">
                            <label class="btn btn-outline-secondary" for="bankApp">Bank App</label>
                        </div>
                    </div>

                    <div class="col-md-12 mt-3" id="cardDetails" style="display: none;">
                        <div class="row g-3">
                            <div class="col-md-6">
                                <label for="cardName" class="form-label">Tên chủ thẻ:</label>
                                <input type="text" name="cardName" class="form-control" minlength="6" maxlength="50">
                            </div>

                            <div class="col-md-6">
                                <label for="cardNumber" class="form-label">Số thẻ:</label>
                                <input type="text" name="cardNumber" class="form-control" minlength="13" maxlength="19">
                            </div>

                            <div class="col-md-6">
                                <label for="CVV" class="form-label">CVV:</label>
                                <input type="text" name="CVV" class="form-control" pattern="\d{3,4}" title="3-4 digits">
                            </div>

                            <div class="col-md-6">
                                <label for="expirationDate" class="form-label">Ngày hết hạn:</label>
                                <input type="date" name="expirationDate" class="form-control">
                            </div>
                        </div>
                    </div>
                    <div class="col-md-12 text-center mt-4">
                        <button type="submit" class="btn btn-success">Thanh toán</button>
                    </div>
            </form>
            <script>
                function toggleCardDetails(show) {
                    document.getElementById("cardDetails").style.display = show ? "block" : "none";
                }
            </script>  
    </body>
</html>
