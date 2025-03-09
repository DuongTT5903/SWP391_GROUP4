<%-- 
    Document   : homepage
    Created on : Jan 20, 2025, 7:59:05 AM
    Author     : yugio
--%>

<%@page import="model.Blog"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@page import="java.util.List, model.Blog, model.Service, model.Slider"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="sliders" scope="request" type="java.util.List"/>
<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Montserrat">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <title>Home Page</title>
    </head>
    <body class="w3-content" style="max-width:max-content ; a {
              text-decoration: none;
              color: inherit;
          }">
        <!-- Check if the userRole is available -->



        !-- Sidebar/menu -->
        <!-- Custom CSS for Sidebar and Header -->
        <style>
            /* Sidebar Styles */
            .custom-sidebar {
                background-color: #fff;
                border-right: 1px solid #e0e0e0;
                box-shadow: 2px 0 5px rgba(0, 0, 0, 0.1);
                width: 250px;
            }
            .custom-sidebar .w3-container {
                padding: 20px;
            }
            .custom-sidebar h3 {
                font-family: 'Montserrat', sans-serif;
                color: #333;
                margin-bottom: 10px;
            }
            .custom-sidebar a {
                font-family: 'Roboto', sans-serif;
                color: #555;
                padding: 10px 15px;
                text-decoration: none;
                display: block;
                transition: background 0.3s, color 0.3s;
            }
            .custom-sidebar a:hover {
                background-color: #f8f8f8;
                color: #007bff;
            }

            /* Links in the extra menu section */
            .custom-sidebar .w3-bar-item {
                padding: 12px 15px;
            }

            /* Top Header for Small Screens */
            .custom-header {
                background-color: #343a40;
                color: #fff;
                padding: 16px;
            }
            .custom-header h3 {
                font-family: 'Montserrat', sans-serif;
                margin: 0;
            }
            .custom-header a {
                color: #fff;
                text-decoration: none;
            }
            .custom-header a:hover {
                color: #d1d1d1;
            }
        </style>
        <nav id="mySidebar" class="w3-sidebar w3-bar-block w3-collapse w3-top custom-sidebar" style="z-index:3;">
            <div class="w3-container w3-display-container w3-padding-16">
                <i onclick="w3_close()" class="fa fa-remove w3-hide-large w3-button w3-display-topright"></i>

                <!-- Login / User Info Section -->
                <c:choose>
                    <c:when test="${sessionScope.roleID == '4'}">
                        <h3 class="w3-wide">
                            <b><a href="${pageContext.request.contextPath}/userProfile" style="text-decoration: none;">${sessionScope.user.username}</a></b>
                            <br>
                            <a href="${pageContext.request.contextPath}/logout">Logout</a>
                        </h3>
                        <a href="customerDashboard.jsp">Go to Dashboard</a>
                    </c:when>
                    <c:when test="${sessionScope.roleID == '2'}">
                        <h3 class="w3-wide">
                            <b><a href="${pageContext.request.contextPath}/userProfile" style="text-decoration: none;">${sessionScope.user.username}</a></b>
                            <br>
                            <a href="${pageContext.request.contextPath}/logout">Logout</a>
                        </h3>
                        <a href="${pageContext.request.contextPath}/manager/customerList">Go to Customer List</a>
                        <a href="${pageContext.request.contextPath}/manager/feedbackList">Go to Feedback</a>
                        <a href="${pageContext.request.contextPath}/manager/postList">Go to Post List</a>
                    </c:when>
                    <c:when test="${sessionScope.roleID == '3'}">
                        <h3 class="w3-wide">
                            <b><a href="/staff" style="text-decoration: none;">${sessionScope.user.username}</a></b>
                            <br>
                            <a href="${pageContext.request.contextPath}/logout">Logout</a>
                        </h3>
                        <a href="staffDashboard.jsp">Go to Dashboard</a>
                    </c:when>
                    <c:when test="${sessionScope.roleID == '1'}">
                        <h3 class="w3-wide">
                            <b><a href="/admin" style="text-decoration: none;">${sessionScope.user.username}</a></b>
                            <br>
                            <a href="${pageContext.request.contextPath}/logout">Logout</a>
                        </h3>
                        <a href="admin/userList">Go to User List</a>
                    </c:when>
                    <c:otherwise>
                        <h3 class="w3-wide">
                            <b><a href="${pageContext.request.contextPath}/login" style="text-decoration: none;">LOGIN</a></b>
                        </h3>
                    </c:otherwise>
                </c:choose>
                <br>
            </div>
            <div class="w3-padding-64 w3-large w3-text-grey" style="font-weight:bold">
                <a href="${pageContext.request.contextPath}/blogList" class="w3-bar-item w3-button">Blogs</a>
                <a href="${pageContext.request.contextPath}/serviceList" class="w3-bar-item w3-button">Services</a>
                <a href="${pageContext.request.contextPath}/reservation"  class="w3-bar-item w3-button">Reservations</a>
                <c:if test="${sessionScope.roleID == '2'}">
                    <a href="${pageContext.request.contextPath}/manager/listservice" class="w3-bar-item w3-button">Service Manager</a>
                </c:if>
            </div>
            <!-- Nút điều hướng tới ManagerServiceController cho Manager -->

            <a href="#footer" class="w3-bar-item w3-button w3-padding">Contact</a>
            <a href="javascript:void(0)" class="w3-bar-item w3-button w3-padding" onclick="document.getElementById('newsletter').style.display = 'block'">Newsletter</a>
            <a href="#footer"  class="w3-bar-item w3-button w3-padding">Subscribe</a>
        </nav>

        <!-- Top Menu on Small Screens -->
        <header class="w3-bar w3-top w3-hide-large custom-header w3-xlarge">
            <div class="w3-bar-item w3-padding-24 w3-wide">
                <c:choose>
                    <c:when test="${sessionScope.roleID == '4'}">
                        <h3 class="w3-wide">
                            <b><a href="${pageContext.request.contextPath}/userProfile" style="text-decoration: none;">${sessionScope.user.username}</a></b>
                            <a href="${pageContext.request.contextPath}/logout">Logout</a>
                        </h3>
                        <a href="customerDashboard.jsp">Go to Dashboard</a>
                    </c:when>
                    <c:when test="${sessionScope.roleID == '2'}">
                        <h3 class="w3-wide">
                            <b><a href="/manager" style="text-decoration: none;">${sessionScope.user.username}</a></b>
                            <br>
                            <a href="${pageContext.request.contextPath}/logout">Logout</a>
                        </h3>
                        <a href="${pageContext.request.contextPath}/manager/feedbackList">Go to Feedback</a>
                    </c:when>
                    <c:when test="${sessionScope.roleID == '3'}">
                        <h3 class="w3-wide">
                            <b><a href="/staff" style="text-decoration: none;">${sessionScope.user.username}</a></b>
                            <br>
                            <a href="${pageContext.request.contextPath}/logout">Logout</a>
                        </h3>
                        <a href="staffDashboard.jsp">Go to Dashboard</a>
                    </c:when>
                    <c:when test="${sessionScope.roleID == '1'}">
                        <h3 class="w3-wide">
                            <b><a href="/admin" style="text-decoration: none;">${sessionScope.user.username}</a></b>
                            <br>
                            <a href="${pageContext.request.contextPath}/logout">Logout</a>
                        </h3>
                        <a href="admin/userList">Go to User List</a>
                    </c:when>
                    <c:otherwise>
                        <h3 class="w3-wide">
                            <b><a href="${pageContext.request.contextPath}/login" style="text-decoration: none;">LOGIN</a></b>
                        </h3>
                    </c:otherwise>
                </c:choose>
                <br>
            </div>
            <a href="javascript:void(0)" class="w3-bar-item w3-button w3-padding-24 w3-right" onclick="w3_open()">
                <i class="fa fa-bars"></i>
            </a>
        </header>
        <!-- ------------------------------------------------------------------------------------------------------------------------------------------------------------ -->

        <!-- Overlay effect when opening sidebar on small screens -->
        <div class="w3-overlay w3-hide-large" onclick="w3_close()" style="cursor:pointer" title="close side menu" id="myOverlay"></div>

        <!-- !PAGE CONTENT! -->
        <div class="w3-main" style="margin-left:250px">

            <!-- Push down content on small screens -->
            <div class="w3-hide-large" style="margin-top:83px"></div>

            <!-- Top header Slider -->
            <header class="w3-container w3-xlarge">
                <p class="w3-left">Children Care</p>
                <p class="w3-right">


                <style>
                    .tamthoi a {
                        display: inline-block;
                        background-color: #007bff;  /* Màu xanh dương */
                        color: #fff;               /* Màu chữ trắng */
                        padding: 10px 20px;
                        border-radius: 5px;
                        text-decoration: none;
                        font-weight: bold;
                        transition: background-color 0.3s ease;
                    }
                    .tamthoi a:hover {
                        background-color: #0056b3;
                    }
                </style>

                <i class="tamthoi">
                    <a href="${pageContext.request.contextPath}/shoppingCart">Your Cart</a>
                </i>



            </header>

            <!-- Image header
            <div class="w3-display-container w3-container" style="height: 600px">
                <img src="${pageContext.request.contextPath}/img/childcare.jpg" alt="Jeans" style="width:100%; height: 600px">
                <div class="w3-display-topleft w3-text-white" style="padding:24px 48px">
                    <h1 class="w3-jumbo w3-hide-small" style=" background-color: rgba(255, 255, 255, 0.5); color: black"> Children's Care PBS</h1>
                    <h1 class="w3-hide-large w3-hide-medium" style=" background-color: rgba(255, 255, 255, 0.5); color: black">Medical Service</h1>
                    <h1 class="w3-hide-small" style=" background-color: rgba(255, 255, 255, 0.5); color: black ; width: 250px" >Pre patch 0.0</h1>
                    <p><a href="#jeans" class="w3-button w3-black w3-padding-large w3-large">Get Slot Now</a></p>
                </div>
            </div>
            -->
            <br>


            <!-- Style for slider -->               
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

                .slideshow-container {
                    max-width: 1000px;
                    position: relative;
                    margin: 12px auto;
                }

                .prev, .next {
                    cursor: pointer;
                    position: absolute;
                    top: 50%;
                    width: auto;
                    padding: 16px;
                    margin-top: -22px;
                    color: white;
                    font-weight: bold;
                    font-size: 18px;
                    transition: 0.6s ease;
                    border-radius: 0 3px 3px 0;
                    user-select: none;
                }

                .next {
                    right: 0;
                    border-radius: 3px 0 0 3px;
                }

                .prev:hover, .next:hover {
                    background-color: rgba(0,0,0,0.8);
                }

                .text {
                    color: #f2f2f2;
                    font-size: 15px;
                    padding: 8px 12px;
                    position: absolute;
                    bottom: 8px;
                    width: 100%;
                    text-align: center;
                }

                .numbertext {
                    color: #f2f2f2;
                    font-size: 12px;
                    padding: 8px 12px;
                    position: absolute;
                    top: 0;
                }

                .dot {
                    cursor: pointer;
                    height: 15px;
                    width: 15px;
                    margin: 0 2px;
                    background-color: #bbb;
                    border-radius: 50%;
                    display: inline-block;
                    transition: background-color 0.6s ease;
                }

                .active, .dot:hover {
                    background-color: #717171;
                }

                .fade {
                    animation-name: fade;
                    animation-duration: 1.5s;
                }

                @keyframes fade {
                    from {
                        opacity: .4;
                    }
                    to {
                        opacity: 1;
                    }
                }

                @media only screen and (max-width: 300px) {
                    .prev, .next,.text {
                        font-size: 11px;
                    }
                }
            </style>


            <%
                List<Blog> blogs = (List<Blog>) request.getAttribute("blogs");
            %>

            <div class="slideshow-container">
                <% if (blogs != null && !blogs.isEmpty()) { %>
                <% for (int i = 0; i < blogs.size(); i++) {%>

                <div class="mySlides fade">
                    <a href="${pageContext.request.contextPath}/blogDetail?blogID=<%= blogs.get(i).getBlogID()%>">
                        <img src="<%= blogs.get(i).getImageLink()%>" alt="" style="width:100%; height: 600px;"/>
                        <div class="text" style="color: black; background-color: #f2f2f2; " ><%= blogs.get(i).getBlogTitle()%></div>
                    </a>

                </div>
                <% } %>

                <a class="prev" onclick="plusSlides(-1)">❮</a>
                <a class="next" onclick="plusSlides(1)">❯</a>
                <% } else { %>
                <p>No blogs available at the moment.</p>
                <% } %>
            </div>
            <br>

            <div style="text-align:center">
                <% if (blogs != null && !blogs.isEmpty()) { %>
                <% for (int i = 0; i < blogs.size(); i++) {%>
                <span class="dot" onclick="currentSlide(<%= i + 1%>)"></span>
                <% } %>
                <% }%>
            </div>

            <script>
                let slideIndex = 1;
                showSlides(slideIndex);

                function plusSlides(n) {
                    showSlides(slideIndex += n);
                }

                function currentSlide(n) {
                    showSlides(slideIndex = n);
                }

                function showSlides(n) {
                    let i;
                    let slides = document.getElementsByClassName("mySlides");
                    let dots = document.getElementsByClassName("dot");
                    if (n > slides.length) {
                        slideIndex = 1;
                    }
                    if (n < 1) {
                        slideIndex = slides.length;
                    }
                    for (i = 0; i < slides.length; i++) {
                        slides[i].style.display = "none";
                    }
                    for (i = 0; i < dots.length; i++) {
                        dots[i].className = dots[i].className.replace(" active", "");
                    }
                    slides[slideIndex - 1].style.display = "block";
                    dots[slideIndex - 1].className += " active";
                }
            </script>
            <!-- Custom Styles for Service Grid -->
            <style>
                .service-container {
                    margin-bottom: 20px;
                }
                .service-card {
                    background: #fff;
                    border: 1px solid #e0e0e0;
                    border-radius: 5px;
                    overflow: hidden;
                    transition: transform 0.3s, box-shadow 0.3s;
                    padding: 10px;
                }
                .service-card:hover {
                    transform: translateY(-5px);
                    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                }
                .service-image {
                    width: 100%;
                    height: 200px; /* Ensures all images have equal height */
                    object-fit: cover;
                    display: block;
                }
                .service-info {
                    padding: 10px 0;
                }
                .service-title {
                    font-size: 16px;
                    font-weight: bold;
                    color: #333;
                    margin: 0;
                }
                .service-price {
                    font-size: 14px;
                    color: #e91e63;
                    margin: 5px 0 0;
                }
                .add-to-cart {
                    background-color: #000;
                    color: #fff;
                    border: none;
                    padding: 8px 16px;
                    font-size: 14px;
                    cursor: pointer;
                    transition: background-color 0.3s;
                    width: 100%;
                }
                .add-to-cart:hover {
                    background-color: #333;
                }
                .service-info {
                    padding: 10px 0;
                }
                .service-info p {
                    margin: 0;
                    text-decoration: none; /* Remove any underline */
                }
                .service-title {
                    font-size: 18px;
                    font-weight: 600;
                    color: #333;
                }
                .service-price {
                    font-size: 16px;
                    color: #e91e63;
                    margin-top: 5px;
                }
            </style>

            <div class="w3-container w3-text-grey" id="services">
                <p>${services.size()} items</p>
            </div>

            <!-- Product Grid -->
            <div class="w3-row w3-grayscale">
                <c:forEach var="service" items="${services}">
                    <div class="w3-col l3 s6 service-container">
                        <div class="service-card" style="a {
                                 text-decoration: none;
                                 color: inherit;
                             }">
                            <a href="${pageContext.request.contextPath}/service?serviceID=${service.serviceID}">
                                <img src="${service.imageURL}" alt="${service.serviceName}" class="service-image">
                                <style>
                                    .service-info {
                                        padding: 10px 0;
                                    }
                                    .service-info p {
                                        margin: 0;
                                        text-decoration: none; /* Remove any underline */
                                    }
                                    .service-title {
                                        font-size: 18px;
                                        font-weight: 600;
                                        color: #333;
                                    }
                                    .service-price {
                                        font-size: 16px;
                                        color: #e91e63;
                                        margin-top: 5px;
                                    }
                                    a {
                                        text-decoration: none;
                                        color: inherit;
                                    }
                                </style>
                                <div class="service-info">
                                    <p class="service-title" style="text-decoration: none;">${service.serviceName}</p>
                                    <p class="service-price" style="text-decoration: none;"><b>${service.servicePrice}00 VND</b></p>
                                    <p class="service-title" style="text-decoration: none;">${service.serviceDetail}</p>
                                </div>
                            </a>
                            <form action="AddCart" method="post">
                                <input type="hidden" name="serviceID" value="${service.serviceID}">
                                <button type="submit" class="add-to-cart">Add to Cart</button>
                            </form>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <br>

            <%
                List<Slider> slider = (List<Slider>) request.getAttribute("sliders");
            %>
            <!-- Slider Khuyến mãi -->
            <h1>Khuyến mãi</h1>
            <div class="w3-row w3-grayscale">
                <c:forEach var="slider" items="${sliders}">
                    <div class="w3-col l3 s6 service-container">
                        <div class="service-card">
                            <a href="${pageContext.request.contextPath}/slider?sliderID=${slider.slideID}">
                                <img src="${slider.img}" alt="${slider.title}" class="service-image">
                                <div class="service-info">
                                    <p class="service-title">${slider.title}</p>
                                </div>
                            </a>
                        </div>
                    </div>
                </c:forEach>
            </div>

            <style>
                .service-container {
                    margin-bottom: 20px;
                }
                .service-card {
                    background: #fff;
                    border: 1px solid #e0e0e0;
                    border-radius: 5px;
                    overflow: hidden;
                    transition: transform 0.3s, box-shadow 0.3s;
                    padding: 10px;
                }
                .service-card:hover {
                    transform: translateY(-5px);
                    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                }
                .service-image {
                    width: 100%;
                    height: 200px; /* Kích thước cố định cho ảnh */
                    object-fit: cover;
                    display: block;
                }
                .service-info {
                    padding: 10px 0;
                    text-align: center;
                }
                .service-title {
                    font-size: 18px;
                    font-weight: 600;
                    color: #333;
                    margin: 0;
                }
            </style>


            <!--               
                          <div class="w3-container w3-text-grey" id="jeans">
                            <p>8 items</p>
                          </div>
            
            <!-- Product grid --
            <div class="w3-row w3-grayscale">
              <div class="w3-col l3 s6">
                <div class="w3-container">
                  <img src="/w3images/jeans1.jpg" style="width:100%">
                  <p>Ripped Skinny Jeans<br><b>$24.99</b></p>
                </div>
                <div class="w3-container">
                  <img src="/w3images/jeans2.jpg" style="width:100%">
                  <p>Mega Ripped Jeans<br><b>$19.99</b></p>
                </div>
              </div>

              <div class="w3-col l3 s6">
                <div class="w3-container">
                  <div class="w3-display-container">
                    <img src="/w3images/jeans2.jpg" style="width:100%">
                    <span class="w3-tag w3-display-topleft">New</span>
                    <div class="w3-display-middle w3-display-hover">
                      <button class="w3-button w3-black">Buy now <i class="fa fa-shopping-cart"></i></button>
                    </div>
                  </div>
                  <p>Mega Ripped Jeans<br><b>$19.99</b></p>
                </div>
                <div class="w3-container">
                  <img src="/w3images/jeans3.jpg" style="width:100%">
                  <p>Washed Skinny Jeans<br><b>$20.50</b></p>
                </div>
              </div>

              <div class="w3-col l3 s6">
                <div class="w3-container">
                  <img src="/w3images/jeans3.jpg" style="width:100%">
                  <p>Washed Skinny Jeans<br><b>$20.50</b></p>
                </div>
                <div class="w3-container">
                  <div class="w3-display-container">
                    <img src="/w3images/jeans4.jpg" style="width:100%">
                    <span class="w3-tag w3-display-topleft">Sale</span>
                    <div class="w3-display-middle w3-display-hover">
                      <button class="w3-button w3-black">Buy now <i class="fa fa-shopping-cart"></i></button>
                    </div>
                  </div>
                  <p>Vintage Skinny Jeans<br><b class="w3-text-red">$14.99</b></p>
                </div>
              </div>

              <div class="w3-col l3 s6">
                <div class="w3-container">
                  <img src="/w3images/jeans4.jpg" style="width:100%">
                  <p>Vintage Skinny Jeans<br><b>$14.99</b></p>
                </div>
                <div class="w3-container">
                  <img src="/w3images/jeans1.jpg" style="width:100%">
                  <p>Ripped Skinny Jeans<br><b>$24.99</b></p>
                </div>
              </div>
            </div>
            -->

            <!-- Subscribe section -->
            <div class="w3-container w3-black w3-padding-32">
                <h1>Subscribe</h1>
                <p>To get special offers and VIP treatment:</p>
                <p><input class="w3-input w3-border" type="text" placeholder="Enter e-mail" style="width:100%"></p>
                <button type="button" class="w3-button w3-red w3-margin-bottom">Subscribe</button>
            </div>

            <!-- Footer -->
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

            <!-- End page content -->
        </div>

        <!-- Newsletter Modal -->
        <div id="newsletter" class="w3-modal">
            <div class="w3-modal-content w3-animate-zoom" style="padding:32px">
                <div class="w3-container w3-white w3-center">
                    <i onclick="document.getElementById('newsletter').style.display = 'none'" class="fa fa-remove w3-right w3-button w3-transparent w3-xxlarge"></i>
                    <h2 class="w3-wide">NEWSLETTER</h2>
                    <p>Join our mailing list to receive updates on new arrivals and special offers.</p>
                    <p><input class="w3-input w3-border" type="text" placeholder="Enter e-mail"></p>
                    <button type="button" class="w3-button w3-padding-large w3-red w3-margin-bottom" onclick="document.getElementById('newsletter').style.display = 'none'">Subscribe</button>
                </div>
            </div>
        </div>

    </body>
</html>
