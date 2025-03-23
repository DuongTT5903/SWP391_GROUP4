
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
    <body >
        <div class="container">
            <jsp:include page="./headerCustomer.jsp" />
            <form action="reservation" method="GET" class="row g-3">
                <div class="row" style="margin-top: 50px">
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
                </div>

            </form>               
            <c:if test="${not empty cartMessage}">
                <p class="text-danger fw-bold">${cartMessage}</p>
            </c:if>

          <c:if test="${not empty cartItems}">
                <table class="table table-bordered">
                    <thead class="bg-secondary">
                        <tr>
                            <th>#</th>
                            <th>Tên dịch vụ</th>
                            <th>Giá</th>
                            <th>Số lượng</th>
                            <th>Tổng giá</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="s" items="${checkItem}" varStatus="status">                      
                            <tr>
                                <td>${status.index + 1}</td>
                                <td>${s.service.serviceName}</td>
                                <td>${(s.service.servicePrice * (100 - s.service.salePrice)) / 100}00 VNĐ</td>
                                <td>
                                    ${s.amount}
                                </td>
                                <td>${s.amount * (s.service.servicePrice * (100 - s.service.salePrice)) / 100}00 VNĐ</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
            <p>Tổng thanh toán:${totalPrice}00 VNĐ</p>

            <nav class="mt-4">
                <ul class="pagination justify-content-center">
                    <!-- Nút Trang Trước -->
                    <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                        <a class="page-link" href="${pageContext.request.contextPath}/customer/reservation?page=${currentPage - 1}&search=${param.search}&category=${param.category}"><<</a>
                    </li>

                    <!-- Hiển thị các số trang -->
                    <c:forEach var="i" begin="1" end="${totalPages}">
                        <li class="page-item ${i == currentPage ? 'active' : ''}">
                            <a class="page-link" href="${pageContext.request.contextPath}/customer/reservation?page=${i}&search=${param.search}&category=${param.category}">${i}</a>
                        </li>
                    </c:forEach>

                    <!-- Nút Trang Sau -->
                    <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                        <a class="page-link" href="${pageContext.request.contextPath}/customer/reservation?page=${currentPage + 1}&search=${param.search}&category=${param.category}">>></a>
                    </li>
                </ul>
            </nav>
            <form action="reservation" method="POST" class="mt-4">
                <div class="row g-3">
                    <div class="col-md-6">
                        <label for="email" class="form-label">Name:</label>
                        <input type="name" name="name" class="form-control ${not empty errors.name ? 'is-invalid' : ''}" value="${sessionScope.user.name}" >
                        <div class="invalid-feedback">
                            ${errors.email}
                        </div>
                    </div>
                    <div class="col-md-6">
                        <label for="email" class="form-label">Email:</label>
                        <input type="email" name="email" class="form-control ${not empty errors.email ? 'is-invalid' : ''}" value="${sessionScope.user.email}" >
                        <div class="invalid-feedback">
                            ${errors.email}
                        </div>
                    </div>
                    <div class="col-md-6">
                        <label for="phone" class="form-label">Số điện thoại:</label>
                        <input type="tel" name="phone" class="form-control ${not empty errors.phone ? 'is-invalid' : ''}" value="${sessionScope.user.phone}" >
                        <div class="invalid-feedback">
                            ${errors.phone}
                        </div>
                    </div>
                    <div class="col-md-6">
                        <label for="address" class="form-label">Địa chỉ:</label>
                        <input type="text" name="address" class="form-control ${not empty errors.address ? 'is-invalid' : ''}" value="${customer.address}">
                        <div class="invalid-feedback">
                            ${errors.address}
                        </div>
                    </div>               
                    <div class="col-md-12">
                        <label class="form-label">Phương thức thanh toán:</label>
                        <div class="btn-group w-100">
                            <input type="radio" class="btn-check" id="creditCard" name="payment" value="0" onclick="toggleCardDetails(true)" >
                            <label class="btn btn-outline-primary" for="creditCard">Thẻ tín dụng</label>

                            <input type="radio" class="btn-check" id="bankApp" name="payment" value="1" onclick="toggleCardDetails(false)" >
                            <label class="btn btn-outline-secondary" for="bankApp">VP Payment</label>
                        </div>
                        <div class="text-danger">${errors.payment}</div>
                    </div>
                </div>
                <div class="col-md-12 mt-3" id="cardDetails" style="display: none;">
                    <div class="row g-3">
                        <div class="col-md-6">
                            <label for="cardName" class="form-label">Tên chủ thẻ:</label>
                            <input type="text" name="cardName" class="form-control ${not empty errors.cardName ? 'is-invalid' : ''}" >
                            <div class="invalid-feedback">${errors.cardName}</div>
                        </div>

                        <div class="col-md-6">
                            <label for="cardNumber" class="form-label">Số thẻ:</label>
                            <input type="text" name="cardNumber" class="form-control ${not empty errors.cardNumber ? 'is-invalid' : ''}" >
                            <div class="invalid-feedback">${errors.cardNumber}</div>
                        </div>

                        <div class="col-md-6">
                            <label for="CVV" class="form-label">CVV:</label>
                            <input type="text" name="CVV" class="form-control ${not empty errors.CVV ? 'is-invalid' : ''}">
                            <div class="invalid-feedback">${errors.CVV}</div>
                        </div>

                        <div class="col-md-6">
                            <label for="expirationDate" class="form-label">Ngày hết hạn:</label>
                            <input type="date" name="expirationDate" class="form-control ${not empty errors.expirationDate ? 'is-invalid' : ''}">
                            <div class="invalid-feedback">${errors.expirationDate}</div>
                        </div>
                    </div>
                </div>

                <div class="col-md-12 text-center mt-4">
                    <button type="submit" class="btn btn-success">Đặt lịch</button>
                </div>                   
            </form>        
            <jsp:include page="./footerCustomer.jsp" />
        </div>
        <script>
            function toggleCardDetails(show) {
                document.getElementById("cardDetails").style.display = show ? "block" : "none";
            }
        </script>
    </body>
</html>
