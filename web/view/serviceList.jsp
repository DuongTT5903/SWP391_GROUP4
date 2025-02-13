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
        <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Montserrat">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <style>
            * {
                box-sizing: border-box;
            }
            body {
                font-family: Verdana, sans-serif;
                margin:0;
            }
            .mySlides {
                display: none;
            }
            img {
                vertical-align: middle;
            }
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
    </head>
    <script>

    </script>
    <body>

        <div class="container">
            <nav class="navbar navbar-expand-lg bg-secondary" style="height: 55px;">
                <div class="container-fluid">
                    <c:choose>
                        <c:when test="${sessionScope.roleID == '4'}">
                            <!-- Content for customer role -->
                            <h3 class="w3-wide public"> 
                                <b><a href="/customer" style="text-decoration: none;color: white;">Customer</a></b>
                            </h3>
                        </c:when>
                        <c:when test="${sessionScope.roleID == '2'}">
                            <h3 class="w3-wide public"> 
                                <b><a href="/manager" style="text-decoration: none;color: white;">Manager</a></b>
                            </h3>
                        </c:when>

                        <c:when test="${sessionScope.roleID == '3'}">
                            <!-- Content for staff role -->
                            <h3 class="w3-wide public"> 
                                <b><a href="/staff" style="text-decoration: none;color: white;">Staff</a></b>
                            </h3>
                        </c:when>

                        <c:when test="${sessionScope.roleID == '1'}">
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
                            <li class="nav-item"><a class="nav-link " href="${pageContext.request.contextPath}\shoppingCart"">Cart</a></li>
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
                                <!-- Add to Cart Form -->                            
                                <form action="AddCart" method="POST">
                                    <input type="hidden" name="serviceID" value="${service.serviceID}">
                                    <button class="btn btn-success" type="submit">Thêm vào giỏ hàng</button>
                                </form>
                                <!-- Feedback Button -->
                                <button class="btn btn-outline-secondary" >Phản Hồi</button>
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
                           href="${pageContext.request.contextPath}\serviceList?page=1&search=&category=0">« Trang
                            trước</a>
                    </li>
                    <li class="page-item">
                        <a class="page-link"
                           href="${pageContext.request.contextPath}\serviceList?page=2&search=&category=0">Trang sau
                            »</a>
                    </li>
                </ul>
                <!--            </nav>
                                            modal
                <form action="SubmitFeedbackController" method="post">
                    <input type="hidden" name="serviceID" value="${service.serviceID}">
                    <label>Chọn số sao:</label>
                    <select name="rating">
                        <option value="1">⭐</option>
                        <option value="2">⭐⭐</option>
                        <option value="3">⭐⭐⭐</option>
                        <option value="4">⭐⭐⭐⭐</option>
                        <option value="5">⭐⭐⭐⭐⭐</option>
                    </select>
                    <br>
                    <label>Nhận xét:</label>
                    <textarea name="comment" required></textarea>
                    <br>
                    <button type="submit">Gửi đánh giá</button>
                </form>-->
                <!-- Liên hệ -->
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