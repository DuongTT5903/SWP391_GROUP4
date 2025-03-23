<%@page import="model.User"%>
<%@page import="model.Service"%>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Feedback</title>
        <style>
            body {
                font-family: Arial, sans-serif;
            }
            .sidebar {
                float: left;
                width: 20%;
                background-color: #f0f0f0;
                padding: 10px;
            }
            .mainContent {
                float: left;
                width: 75%;
                margin-left: 5%;
            }
            label {
                display: block;
                margin-top: 10px;
            }
            .serviceResult {
                border: 1px solid #ccc;
                padding: 8px;
                margin: 5px 0;
            }
            .serviceResult a {
                color: blue;
            }
        </style>
    </head>
    <body>
        <!-- Sidebar: chứa form search + categories -->
        <div class="sidebar">
            <h3>Search Service</h3>
            <!-- Form tìm kiếm dịch vụ -->
            <form action="<%= request.getContextPath()%>/samplesearchservice" method="get">
                <input type="text" name="q" placeholder="Nhập tên dịch vụ..." 
                       value="<%= request.getParameter("q") != null ? request.getParameter("q") : ""%>"/>
                <button type="submit">Search</button>
            </form>

            <h4>Categories</h4>
            <ul>
                <c:forEach var="cat" items="${categories}">
                    <li>${cat.categoryName}</li>
                </c:forEach>
            </ul>
            <!-- Bạn cũng có thể thêm các link tĩnh, contact... ở đây -->
        </div>

        <!-- Main content: Hiển thị form Feedback + kết quả tìm kiếm (nếu có) -->
        <div class="mainContent">
            <h1>Give Feedback</h1>

            <!-- Nếu người dùng đã tìm kiếm và có danh sách service trả về trong 'services',
                 ta hiển thị luôn ở đây (hoặc bạn có thể tạo trang riêng).
                 Giả sử Controller trả về List<Service> trong request attribute tên "services". -->
            <c:if test="${not empty services}">
                <h2>Search Results</h2>
                <c:forEach var="svc" items="${services}">
                    <div class="serviceResult">
                        <strong>${svc.serviceName}</strong><br>
                        Giá: ${svc.servicePrice} <br>
                        <a href="<%= request.getContextPath()%>/feedback?serviceID=${svc.serviceID}">
                            Give Feedback
                        </a>
                    </div>
                </c:forEach>
            </c:if>

            <!-- Form gửi feedback (như trước đây) -->
            <form action="<%= request.getContextPath()%>/feedback" method="post">
                <!-- Kiểm tra nếu có service được chọn -->
                <%
                    Service service = (Service) request.getAttribute("service");
                    if (service != null) {
                %>
                <p>Service: <strong><%= service.getServiceName()%></strong></p>
                <input type="hidden" name="serviceID" value="<%= service.getServiceID()%>" />
                <%
                    }
                %>

                <%
                    User user = (User) request.getAttribute("user");
                    if (user != null) {
                %>
                <p><strong>Contact Information (Already logged in)</strong></p>
                <p>Full Name: <%= user.getName()%></p>
                <p>Gender: <c:if test="<%= user.isGender()%>">Male</c:if><c:if test="<%= !user.isGender()%>">Female</c:if></p>
                <p>Email: <%= user.getEmail()%></p>
                <p>Mobile: <%= user.getPhone()%></p>
                <%
                } else {
                %>
                <!-- Nếu user chưa đăng nhập, cho nhập thủ công -->
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
