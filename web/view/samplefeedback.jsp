<%@page import="model.User"%>
<%@page import="model.Service"%>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Feedback</title>
        <style>
            /* Style cơ bản, tuỳ chỉnh thêm */
            .sidebar {
                float: left;
                width: 25%;
                background: #f0f0f0;
            }
            .mainContent {
                float: left;
                width: 70%;
                margin-left: 5%;
            }
            label {
                display: block;
                margin-top: 10px;
            }
        </style>
    </head>
    <body>
        <div class="sidebar">
            <h3>Search Service</h3>
            <form action="<%= request.getContextPath()%>/search" method="get">
                <input type="text" name="q" placeholder="Search..." />
                <button type="submit">Search</button>
            </form>
            <h4>Categories</h4>
            <ul>
                <c:forEach var="cat" items="${categories}">
                    <li>${cat.categoryName}</li>
                </c:forEach>
            </ul>
            <!-- Thêm các link tĩnh, contact... -->
        </div>

        <div class="mainContent">
            <h1>Give Feedback</h1>

            <!-- Form gửi feedback -->
            <form action="<%= request.getContextPath()%>/feedback" method="post">
                <!-- Nếu feedback cho 1 dịch vụ cụ thể, ta có trường ẩn -->
                <%
                    Service service = (Service) request.getAttribute("service");
                    if (service != null) {
                %>
                <p>Service: <strong><%= service.getServiceName()%></strong></p>
                <input type="hidden" name="serviceID" value="<%= service.getServiceID()%>" />
                <%
                    }
                %>

                <!-- Thông tin user (nếu đăng nhập) -->
                <%
                    User user = (User) request.getAttribute("user");
                    if (user != null) {
                %>
                <p><strong>Contact Information (Already logged in)</strong></p>
                <p>Full Name: <%= user.getName()%></p>
                <p>Gender: <c:if test="<%= user.isGender()%>">Male</c:if><c:if test="<%= !user.isGender()%>">Female</c:if></p>
                <p>Email: <%= user.getEmail()%></p>
                <p>Mobile: <%= user.getPhone()%></p>
                <!-- Ở đây, ta không cần input, vì user đã đăng nhập -->
                <%
                } else {
                %>
                <!-- Trường hợp user chưa đăng nhập, cho nhập contact info -->
                <label>Full Name:</label>
                <input type="text" name="fullName" required />

                <label>Gender:</label>
                <select name="gender">
                    <option value="1">Male</option>
                    <option value="0">Female</option>
                </select>

                <label>Email:</label>
                <input type="email" name="email" />

                <label>Mobile:</label>
                <input type="text" name="mobile" />
                <%
                    }
                %>

                <label>Your Feedback:</label>
                <textarea name="feedbackDetail" rows="4" cols="50"></textarea>

                <label>Rating (1-5):</label>
                <select name="rated">
                    <option value="1">1 Star</option>
                    <option value="2">2 Stars</option>
                    <option value="3">3 Stars</option>
                    <option value="4">4 Stars</option>
                    <option value="5" selected>5 Stars</option>
                </select>

                <label>Image Link (optional):</label>
                <input type="text" name="imgLink" placeholder="http://..." />

                <br><br>
                <input type="submit" value="Send Feedback" />
            </form>
        </div>
    </body>
</html>
