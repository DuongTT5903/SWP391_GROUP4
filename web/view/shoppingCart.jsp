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
                            <li class="nav-item"><a class="nav-link " href="${pageContext.request.contextPath}\shoppingCart"">Cart</a></li>
                            <li class="nav-item"><a class="nav-link " href="#">Blog</a></li>
                            <li class="nav-item"><a class="nav-link " href="#">Reservations</a></li>
                        </ul>
                    </div>
                </div>
            </nav>
            <!--            <h2 style="text-align: center">Giỏ hàng</h2>-->

            <a href="${pageContext.request.contextPath}/serviceList" class="btn btn-primary mt-3" style="margin-bottom: 16px;">Thêm dịch vụ</a>
            <br>
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
                            <!--                            <th>Số người</th>-->
                            <th>Chức năng</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="s" items="${cartItems}" varStatus="status">
                            <tr>
                                <td>${status.index + 1}</td>
                                <td>${s.service.serviceName}</td>
                                <td>${(s.service.servicePrice * (100 - s.service.salePrice))/100}00VNĐ<td>
                                    <form action="UpdateCart" method="POST">
                                        <input type="hidden" name="UpdateID" value="${s.service.serviceID}">
                                        <div class="input-group d-flex align-items-center">
                                            <button class="btn btn-secondary px-3" type="submit" name="update" value="increase">+</button>
                                            <span class="mx-3 text-center" style="min-width: 20px;">${s.amount}</span>
                                            <button class="btn btn-secondary px-3" type="submit" name="update" value="decrease">-</button>
                                        </div>
                                    </form>
                                </td>

                                <!--                                <td>
                                                                    <form action="UpdateCart" method="POST">
                                                                        <input type="hidden" name="UpdateID" value="${s.service.serviceID}">
                                                                        <div class="input-group d-flex align-items-center">
                                                                            <button class="btn btn-secondary px-3" type="submit" name="update" value="increasePersons">+</button>
                                                                            <span class="mx-3 text-center" style="min-width: 20px;">${s.numberOfPerson}</span>
                                                                            <button class="btn btn-secondary px-3" type="submit" name="update" value="decreasePersons">-</button>
                                                                        </div>
                                                                    </form>
                                                                </td>-->



                                <td>
                                    <form action="DeleteCart" method="POST" onsubmit="return confirm('Bạn có chắc muốn xóa dịch vụ này khỏi giỏ hàng?');">
                                        <input type="hidden" name="DeleteID" value="${s.service.serviceID}">
                                        <button type="submit" class="btn btn-danger">Xóa</button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
            <p>Tổng Thanh Toán: ${total} VNĐ</p>
            <nav class="mt-4">
                <ul class="pagination justify-content-center">
                    <!-- Nút Trang Trước -->
                    <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                        <a class="page-link" href="${pageContext.request.contextPath}/shoppingCart?page=${currentPage - 1}&search=${param.search}&category=${param.category}">« Trang trước</a>
                    </li>

                    <!-- Hiển thị các số trang -->
                    <c:forEach var="i" begin="1" end="${totalPages}">
                        <li class="page-item ${i == currentPage ? 'active' : ''}">
                            <a class="page-link" href="${pageContext.request.contextPath}/shoppingCart?page=${i}&search=${param.search}&category=${param.category}">${i}</a>
                        </li>
                    </c:forEach>

                    <!-- Nút Trang Sau -->
                    <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                        <a class="page-link" href="${pageContext.request.contextPath}/shoppingCart?page=${currentPage + 1}&search=${param.search}&category=${param.category}">Trang sau »</a>
                    </li>
                </ul>
            </nav>
            <c:if test="${not empty cartItems}">
                <a href="${pageContext.request.contextPath}/reservation" class="btn btn-primary" style="margin-bottom: 5px">Thanh toán</a>
            </c:if>
            <div class="w3-container w3-black w3-padding-32">
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
    </body>
</html>
