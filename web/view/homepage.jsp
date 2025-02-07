<%-- 
    Document   : homepage
    Created on : Jan 20, 2025, 7:59:05 AM
    Author     : yugio
--%>

<%@page import="model.Blog"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Montserrat">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="../script/homepage.js" type="text/javascript"></script>
        <title>Home Page</title>
    </head>
            <body class="w3-content" style="max-width:1200px">
<!-- Check if the userRole is available -->



            !-- Sidebar/menu -->
            <nav class="w3-sidebar w3-bar-block w3-white w3-collapse w3-top" style="z-index:3;width:250px" id="mySidebar">
              <div class="w3-container w3-display-container w3-padding-16">
                <i onclick="w3_close()" class="fa fa-remove w3-hide-large w3-button w3-display-topright"></i>
                
                <!-- Có thể chèn phần login vào đây -->
 <c:choose>
    <c:when test="${sessionScope.userRole == 'customer'}">
        <!-- Content for customer role -->
        <h3 class="w3-wide public"> 
                    <b><a href="/customer" style="text-decoration: none">Customer</a></b>
        </h3>
        <a href="customerDashboard.jsp">Go to Dashboard</a>
    </c:when>
        <c:when test="${sessionScope.userRole == 'manager'}">
            <h3 class="w3-wide public"> 
                    <b><a href="/manager" style="text-decoration: none">Manager</a></b>
        </h3>
        <a href="customerDashboard.jsp">Go to Dashboard</a> 
    </c:when>

    <c:when test="${sessionScope.userRole == 'staff'}">
        <!-- Content for staff role -->
        <h3 class="w3-wide public"> 
                    <b><a href="/staff" style="text-decoration: none">Staff</a></b>
        </h3>
        <a href="staffDashboard.jsp">Go to Dashboard</a>
    </c:when>

    <c:when test="${sessionScope.userRole == 'admin'}">
        <!-- Content for admin role -->
        <h3 class="w3-wide public"> 
                    <b><a href="/admin" style="text-decoration: none">Admin</a></b>
        </h3>
        <a href="adminDashboard.jsp">Go to Dashboard</a>
    </c:when>

    <c:otherwise>
        <!-- Default content if userRole is not recognized -->
        <h3 class="w3-wide public"> 
                    <b><a href="${pageContext.request.contextPath}/login" style="text-decoration: none">LOGIN</a></b>
        </h3>
    </c:otherwise>
</c:choose>
   
              </div>
              <div class="w3-padding-64 w3-large w3-text-grey" style="font-weight:bold">
                <a href="#" class="w3-bar-item w3-button">Blogs</a>
                <a href="#" class="w3-bar-item w3-button">Services</a>
                <!--
                <a onclick="myAccFunc()" href="javascript:void(0)" class="w3-button w3-block w3-white w3-left-align" id="myBtn">
                  Jeans <i class="fa fa-caret-down"></i>
                </a>
                <div id="demoAcc" class="w3-bar-block w3-hide w3-padding-large w3-medium">
                  <a href="#" class="w3-bar-item w3-button w3-light-grey"><i class="fa fa-caret-right w3-margin-right"></i>Skinny</a>
                  <a href="#" class="w3-bar-item w3-button">Exa1</a>
                  <a href="#" class="w3-bar-item w3-button">Bootcut</a>
                  <a href="#" class="w3-bar-item w3-button">Straight</a>
                </div>
                -->
                <a href="#" class="w3-bar-item w3-button">Reservations</a>
              </div>
              <a href="#footer" class="w3-bar-item w3-button w3-padding">Contact</a> 
              <a href="javascript:void(0)" class="w3-bar-item w3-button w3-padding" onclick="document.getElementById('newsletter').style.display='block'">Newsletter</a> 
              <a href="#footer"  class="w3-bar-item w3-button w3-padding">Subscribe</a>
            </nav>

            <!-- Top menu on small screens -->
            <header class="w3-bar w3-top w3-hide-large w3-black w3-xlarge">
                <div class="w3-bar-item w3-padding-24 w3-wide">
                    
<!--
<c:choose> 
    <c:when test="${sessionScope.userRole == 'customer'}">
        <!-- Content for customer role -->
        <h3 class="w3-wide public"> 
                    <b><a href="#" style="text-decoration: none">Customer</a></b>
        </h3>
        <a href="customerDashboard.jsp">Go to Dashboard</a>
    </c:when>
        <c:when test="${sessionScope.userRole == 'manager'}">
            <h3 class="w3-wide public"> 
                    <b><a href="#" style="text-decoration: none">Manager</a></b>
        </h3>
        <a href="customerDashboard.jsp">Go to Dashboard</a> 
    </c:when>

    <c:when test="${sessionScope.userRole == 'staff'}">
        <!-- Content for staff role -->
        <h3 class="w3-wide public"> 
                    <b><a href="#" style="text-decoration: none">Staff</a></b>
        </h3>
        <a href="staffDashboard.jsp">Go to Dashboard</a>
    </c:when>

    <c:when test="${sessionScope.userRole == 'admin'}">
        <!-- Content for admin role -->
        <h3 class="w3-wide public"> 
                    <b><a href="#" style="text-decoration: none">Admin</a></b>
        </h3>
        <a href="adminDashboard.jsp">Go to Dashboard</a>
    </c:when>

    <c:otherwise>
        <!-- Default content if userRole is not recognized -->
        <h3 class="w3-wide public"> 
                    <b><a href="login.jsp" style="text-decoration: none">LOGIN</a></b>
        </h3>
    </c:otherwise>
</c:choose>
-->
                    
                </div>
              <a href="javascript:void(0)" class="w3-bar-item w3-button w3-padding-24 w3-right" onclick="w3_open()"><i class="fa fa-bars"></i></a>
            </header>

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
                  
                  <i class="fa fa-search"></i>
         

              </header>

              <!-- Image header -->
              <div class="w3-display-container w3-container" style="height: 600px">
                <img src="${pageContext.request.contextPath}/img/childcare.jpg" alt="Jeans" style="width:100%; height: 600px">
                <div class="w3-display-topleft w3-text-white" style="padding:24px 48px">
                  <h1 class="w3-jumbo w3-hide-small" style=" background-color: rgba(255, 255, 255, 0.5); color: black"> Children's Care PBS</h1>
                  <h1 class="w3-hide-large w3-hide-medium" style=" background-color: rgba(255, 255, 255, 0.5); color: black">Medical Service</h1>
                  <h1 class="w3-hide-small" style=" background-color: rgba(255, 255, 255, 0.5); color: black ; width: 250px" >Pre patch 0.0</h1>
                  <p><a href="#jeans" class="w3-button w3-black w3-padding-large w3-large">Get Slot Now</a></p>
                </div>
              </div>
              
              <br>
              
              
 <!-- Style for slider -->               
    <style>
        * {box-sizing: border-box;}
        body {font-family: Verdana, sans-serif; margin:0;}
        .mySlides {display: none;}
        img {vertical-align: middle;}

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
            from {opacity: .4;} 
            to {opacity: 1;}
        }

        @media only screen and (max-width: 300px) {
            .prev, .next,.text {font-size: 11px;}
        }
    </style>


<% 
List<Blog> blogs = (List<Blog>) request.getAttribute("blogs"); 
%>

<div class="slideshow-container">
<% if (blogs != null && !blogs.isEmpty()) { %>
    <% for (int i = 0; i < blogs.size(); i++) { %>
        <% String imgFileName = "homepage_slider1." + (i+1) + ".jpg"; %>
        <div class="mySlides fade">
            <a href="${pageContext.request.contextPath}/blog?blogID=<%= blogs.get(i).getBlogID() %>">
            <img src="<%= blogs.get(i).getImageLink() %>" alt="" style="width:100%; height: 600px;"/>
            <div class="text" style="color: black"><%= blogs.get(i).getBlogTitle() %></div>
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
    <% for (int i = 0; i < blogs.size(); i++) { %>
        <span class="dot" onclick="currentSlide(<%= i+1 %>)"></span>
    <% } %>
<% } %>
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
  if (n > slides.length) {slideIndex = 1;}    
  if (n < 1) {slideIndex = slides.length;}
  for (i = 0; i < slides.length; i++) {
    slides[i].style.display = "none";  
  }
  for (i = 0; i < dots.length; i++) {
    dots[i].className = dots[i].className.replace(" active", "");
  }
  slides[slideIndex-1].style.display = "block";  
  dots[slideIndex-1].className += " active";
}
</script>

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
                  <i onclick="document.getElementById('newsletter').style.display='none'" class="fa fa-remove w3-right w3-button w3-transparent w3-xxlarge"></i>
                  <h2 class="w3-wide">NEWSLETTER</h2>
                  <p>Join our mailing list to receive updates on new arrivals and special offers.</p>
                  <p><input class="w3-input w3-border" type="text" placeholder="Enter e-mail"></p>
                  <button type="button" class="w3-button w3-padding-large w3-red w3-margin-bottom" onclick="document.getElementById('newsletter').style.display='none'">Subscribe</button>
                </div>
              </div>
            </div>

            </body>
            </html>
