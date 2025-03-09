<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Service" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    // Lấy thời gian bắt đầu từ session
    Long startTime = (Long) session.getAttribute("startTime");

    if (startTime != null) {
        long currentTime = System.currentTimeMillis() / 1000; // Lấy thời gian hiện tại (seconds)
        long elapsedTime = currentTime - startTime; // Tính thời gian đã trôi qua

        // Nếu vượt quá 15 phút (900 giây), hiển thị thông báo và chuyển hướng về trang chủ
        if (elapsedTime > 900) {
%>
<script>
    alert("Phiên làm việc của bạn đã hết hạn. Bạn sẽ được chuyển về trang chủ.");
    window.location.href = "<%= request.getContextPath()%>/homepage"; // Đổi đường dẫn trang chủ nếu cần
</script>
<%
            return; // Dừng xử lý tiếp theo
        }
    }
%>


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
            <div class="container">
                <nav class="navbar navbar-expand-lg bg-secondary" style="height: 55px;">
                    <div class="container-fluid">
                        <c:choose>
                            <c:when test="${sessionScope.roleID == '4'}">
                                <h3 class="w3-wide public"> 
                                    <b><a href="${pageContext.request.contextPath}/userProfile" style="text-decoration: none">${sessionScope.user.username}</a></b>
                                    <br>
                                </h3>       
                            </c:when>
                            <c:when test="${sessionScope.roleID == '2'}">
                                <h3 class="w3-wide public"> 
                                    <b><a href="${pageContext.request.contextPath}/userProfile" style="text-decoration: none">${sessionScope.user.username}</a></b>
                                    <br>
                                </h3>
                            </c:when>
                            <c:when test="${sessionScope.roleID == '3'}">
                                <h3 class="w3-wide public"> 
                                    <b><a href="/staff" style="text-decoration: none">${sessionScope.user.username}</a></b>
                                    <br>
                                </h3>
                            </c:when>
                            <c:when test="${sessionScope.roleID == '1'}">
                                <h3 class="w3-wide public"> 
                                    <b><a href="/admin" style="text-decoration: none">${sessionScope.user.username}</a></b>
                                    <br>
                                </h3>
                            </c:when>
                            <c:otherwise>
                                <h3 class="w3-wide public"> 
                                    <b><a href="${pageContext.request.contextPath}/login" style="text-decoration: none">LOGIN</a></b>
                                </h3>
                            </c:otherwise>
                        </c:choose>
                        <div class="collapse navbar-collapse" id="navbarNav">
                            <ul class="navbar-nav ms-auto">
                                <li class="nav-item"><a class="nav-link " href="${pageContext.request.contextPath}\homepage">Homepage</a></li>
                                <li class="nav-item"><a class="nav-link " href="${pageContext.request.contextPath}\shoppingCart">Cart</a></li>
                                <li class="nav-item"><a class="nav-link " href="#">Blog</a></li>
                                <li class="nav-item"><a class="nav-link " href="#">Reservations</a></li>
                            </ul>
                        </div>
                    </div>
                </nav>

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
                    <table class="table table-bordered" style="margin-top: 15px">
                        <thead>
                            <tr>
                                <th>#</th>
                                <th>Tên dịch vụ</th>
                                <th>Giá</th>
                                <th>Số lượng</th>
                                <!--                                <th>Số người</th>-->
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="s" items="${cartItems}" varStatus="status">
                                <tr>
                                    <td>${status.index + 1}</td>
                                    <td>${s.service.serviceName}</td>
                                    <td>${(s.service.servicePrice * (100 - s.service.salePrice))/100}00VNĐ</td>
                                    <td>${s.amount}</td>
<!--                                    <td>${s.numberOfPerson}</td>-->
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:if>
                <p>Tổng thanh toán:${total*1000} VNĐ</p>

                <nav class="mt-4">
                    <ul class="pagination justify-content-center">
                        <!-- Nút Trang Trước -->
                        <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                            <a class="page-link" href="${pageContext.request.contextPath}/reservation?page=${currentPage - 1}&search=${param.search}&category=${param.category}">« Trang trước</a>
                        </li>

                        <!-- Hiển thị các số trang -->
                        <c:forEach var="i" begin="1" end="${totalPages}">
                            <li class="page-item ${i == currentPage ? 'active' : ''}">
                                <a class="page-link" href="${pageContext.request.contextPath}/reservation?page=${i}&search=${param.search}&category=${param.category}">${i}</a>
                            </li>
                        </c:forEach>

                        <!-- Nút Trang Sau -->
                        <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                            <a class="page-link" href="${pageContext.request.contextPath}/reservation?page=${currentPage + 1}&search=${param.search}&category=${param.category}">Trang sau »</a>
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
                                <input type="text"  name="address" class="form-control" value="${customer.address}" style="background-color: gray" readonly>
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
                                <input type="text" name="name" class="form-control ${not empty errors.name ? 'is-invalid' : ''}">
                                <div class="invalid-feedback">
                                    ${errors.name}
                                </div>
                            </div>

                            <div class="col-md-6">
                                <label for="address" class="form-label">Địa chỉ:</label>
                                <input type="text" name="address" class="form-control ${not empty errors.address ? 'is-invalid' : ''}">
                                <div class="invalid-feedback">
                                    ${errors.address}
                                </div>
                            </div>

                            <div class="col-md-6">
                                <label for="email" class="form-label">Email:</label>
                                <input type="email" name="email" class="form-control ${not empty errors.email ? 'is-invalid' : ''}" >
                                <div class="invalid-feedback">
                                    ${errors.email}
                                </div>
                            </div>
                            <div class="col-md-6">
                                <label for="phone" class="form-label">Số điện thoại:</label>
                                <input type="tel" name="phone" class="form-control ${not empty errors.phone ? 'is-invalid' : ''}">
                                <div class="invalid-feedback">
                                    ${errors.phone}
                                </div>
                            </div>


                            <div class="col-md-6">
                                <label for="gender" class="form-label">Giới tính:</label>
                                <select class="form-select" name="gender" required>
                                    <option value="0">Nam</option>
                                    <option value="1">Nữ</option>
                                </select>
                            </div>
                            <div class="col-md-12">
                                <label for="note" class="form-label">Ghi chú (nếu có):</label>
                                <small class="form-text text-muted">Nhập ghi chú thêm cho việc đặt chỗ(tối đa 500 ký tự).</small>
                                <textarea name="note" class="form-control ${not empty errors.note ? 'is-invalid' : ''}" rows="3" ></textarea>
                                <div class="invalid-feedback">
                                    ${errors.note}
                                </div>
                            </div>


                        </c:if>
                    </div>
                    <div class="col-md-12">
                        <label class="form-label">Phương thức thanh toán:</label>
                        <div class="btn-group w-100">
                            <input type="radio" class="btn-check" id="creditCard" name="payment" value="0" onclick="toggleCardDetails(true)" >
                            <label class="btn btn-outline-primary" for="creditCard">Thẻ tín dụng</label>

                            <input type="radio" class="btn-check" id="bankApp" name="payment" value="1" onclick="toggleCardDetails(false)" >
                            <label class="btn btn-outline-secondary" for="bankApp">Bank App</label>
                        </div>
                        <div class="text-danger">${errors.payment}</div>
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
                        <button type="submit" class="btn btn-success">Thanh toán</button>
                    </div>                   
                </form>

                <div class="w3-container w3-black w3-padding-32" style="margin-top: 15px">
                    <h1>Subscribe</h1>
                    <p>To get special offers and VIP treatment:</p>
                    <p><input class="w3-input w3-border" type="text" placeholder="Enter e-mail" style="width:100%"></p>
                    <button type="button" class="w3-button w3-red w3-margin-bottom">Subscribe</button>
                </div>
                <footer class="w3-padding-64 w3-light-grey w3-small w3-center" id="footer">
                    <div class="w3-row-padding">
                        <div class="w3-col s4">
                            <h4>Contact</h4>
                            <p>Questions? Go ahead.</p>
                            <form action="/action_page.php" target="_blank">
                                <p><input class="w3-input w3-border" type="text" placeholder="Name" name="Name" required></p>
                                <p><input class="w3-input w3-border" type="text" placeholder="Email" name="Email" required></p>
                                <p><input class="w3-input w3-border" type="text" placeholder="Subject" name="Subject" required></p>
                                <p><input class="w3-input w3-border" type="text" placeholder="Message" name="Message" required></p>
                                <button type="submit" class="w3-button w3-block w3-black">Send</button>
                            </form>
                        </div>

                        <div class="w3-col s4">
                            <h4>About</h4>
                            <p><a href="#">About us</a></p>
                            <p><a href="#">We're hiring</a></p>
                            <p><a href="#">Support</a></p>
                            <p><a href="#">Find store</a></p>
                            <p><a href="#">Shipment</a></p>
                            <p><a href="#">Payment</a></p>
                            <p><a href="#">Gift card</a></p>
                            <p><a href="#">Return</a></p>
                            <p><a href="#">Help</a></p>
                        </div>

                        <div class="w3-col s4 w3-justify">
                            <h4>Store</h4>
                            <p><i class="fa fa-fw fa-map-marker"></i> Company Name</p>
                            <p><i class="fa fa-fw fa-phone"></i> 0044123123</p>
                            <p><i class="fa fa-fw fa-envelope"></i> ex@mail.com</p>
                            <h4>We accept</h4>
                            <p><i class="fa fa-fw fa-cc-amex"></i> Amex</p>
                            <p><i class="fa fa-fw fa-credit-card"></i> Credit Card</p>
                            <br>
                            <i class="fa fa-facebook-official w3-hover-opacity w3-large"></i>
                            <i class="fa fa-instagram w3-hover-opacity w3-large"></i>
                            <i class="fa fa-snapchat w3-hover-opacity w3-large"></i>
                            <i class="fa fa-pinterest-p w3-hover-opacity w3-large"></i>
                            <i class="fa fa-twitter w3-hover-opacity w3-large"></i>
                            <i class="fa fa-linkedin w3-hover-opacity w3-large"></i>
                        </div>
                    </div>
                </footer>

                <div class="w3-black w3-center w3-padding-24">Footer Contents Add later <a href="https://www.w3schools.com/w3css/default.asp" title="W3.CSS" target="_blank" class="w3-hover-opacity">CSS on W3</a></div>
            </div>
            <script>
                function toggleCardDetails(show) {
                    document.getElementById("cardDetails").style.display = show ? "block" : "none";
                }
            </script>
    </body>
</html>
