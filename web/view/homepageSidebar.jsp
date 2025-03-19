<%-- 
    Document   : homepageSidebar
    Created on : Mar 17, 2025, 8:48:17 AM
    Author     : yugio
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
          }">
        <!-- Check if the userRole is available -->



        <!-- Sidebar/menu -->
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
            .dropdown {
                position: relative;
                display: inline-block;
            }

            .dropdown-content {
                display: none;
                position: absolute;
                right: 0;
                background-color: white;
                min-width: 150px;
                box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.2);
                z-index: 1;
                border-radius: 5px;
            }

            .dropdown-content a {
                color: black;
                padding: 10px;
                text-decoration: none;
                display: block;
            }

            .dropdown-content a:hover {
                background-color: #f1f1f1;
            }

            .dropdown:hover .dropdown-content {
                display: block;
            }

            .icon-btn {
                background: none;
                border: none;
                cursor: pointer;
                font-size: 18px;
            }
        </style>
        <nav id="mySidebar" class="w3-sidebar w3-bar-block w3-collapse w3-top custom-sidebar" style="z-index:3;">
            <div class="w3-container w3-display-container w3-padding-16">
                <i onclick="w3_close()" class="fa fa-remove w3-hide-large w3-button w3-display-topright"></i>

                <!-- Login / User Info Section -->
                <c:choose>
                    <c:when test="${sessionScope.roleID == '4'}"><!-- Customer -->
                        <h3 class="w3-wide">
                            <b><a href="${pageContext.request.contextPath}/userProfile" style="text-decoration: none;">${sessionScope.user.username}</a></b>
                            <br>
                            <a href="${pageContext.request.contextPath}/logout">Logout</a>
                        </h3>
                        <a href="${pageContext.request.contextPath}/userProfile">User profile</a>
                    </c:when>

                    <c:when test="${sessionScope.roleID == '2'}">
                        <h3 class="w3-wide">
                            <b><a href="${pageContext.request.contextPath}/userProfile" style="text-decoration: none;">${sessionScope.user.username}</a></b>
                            <br>
                            <a href="${pageContext.request.contextPath}/logout">Logout</a>
                        </h3>
                        <a href="${pageContext.request.contextPath}/manager/customerList">Go to Customer List</a>
                        <a href="${pageContext.request.contextPath}/manager/feedbackdetail">Go to Feedback</a>
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
                <c:choose>
                    <c:when test="${sessionScope.roleID == '4'}">
                        <a href="${pageContext.request.contextPath}/reservation" class="w3-bar-item w3-button">Reservations</a>
                    </c:when>
                    <c:otherwise>                       
                    </c:otherwise>
                </c:choose>
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
        <script src="https://kit.fontawesome.com/a076d05399.js" crossorigin="anonymous"></script>
</html>
