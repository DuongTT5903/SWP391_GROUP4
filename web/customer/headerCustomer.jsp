
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Service" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
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
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
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
                            <c:choose>
                                <c:when test="${sessionScope.roleID == '4'}">
                                <li class="nav-item"><a class="nav-link " href="${pageContext.request.contextPath}\customer\shoppingCart">Cart</a></li>
                                </c:when>
                                <c:otherwise>
                                <li class="nav-item"><a class="nav-link " href="${pageContext.request.contextPath}/login">Cart</a></li>
                                </c:otherwise>
                            </c:choose>
                        <li class="nav-item"><a class="nav-link " href="${pageContext.request.contextPath}/blogList">Blogs</a></li>                       
                            <c:choose>
                                <c:when test="${sessionScope.roleID == '4'}">
                                <li class="nav-item"><a class="nav-link " href="${pageContext.request.contextPath}/customer/myReservation">My Reservations</a></li>
                                </c:when>
                                <c:otherwise>
                                <li class="nav-item"><a class="nav-link " href="${pageContext.request.contextPath}/login"> My Reservations</a></li>
                                </c:otherwise>
                            </c:choose>
                    </ul>
                </div>
            </div>
        </nav>
    </body>
</html>
