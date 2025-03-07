<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.User" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <title>Hồ sơ người dùng</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 20px;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .form-container {
            background-color: #fff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            width: 50%;
            max-width: 600px;
            text-align: left;
        }
        .form-container h1 {
            font-size: 24px;
            margin-bottom: 20px;
            text-align: center;
        }
        .form-container input[type="text"],
        .form-container input[type="email"],
        .form-container input[type="tel"] {
            width: calc(100% - 20px);
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        .form-container .readonly-field {
            background-color: #f4f4f4;
        }
        .form-container .button-container {
            display: flex;
            justify-content: center;
            gap: 10px; /* Khoảng cách giữa các nút */
            margin-top: 20px;
        }
        .form-container button {
            padding: 10px 20px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
        }
        .form-container button.edit {
            background-color: #ff9800;
        }
        .form-container .change-link, .form-container .home-link {
            display: inline-block;
            padding: 10px 20px;
            background-color: #007BFF;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            font-size: 16px;
        }
        .form-container .change-link:hover, .form-container .home-link:hover {
            background-color: #0056b3;
        }
    </style>
    <script>
        function toggleEdit() {
            var inputs = document.querySelectorAll('.form-container input[type="text"], .form-container input[type="email"], .form-container input[type="tel"]');
            for (var i = 0; i < inputs.length; i++) {
                inputs[i].readOnly = !inputs[i].readOnly;
                inputs[i].classList.toggle('readonly-field');
            }
        }
    </script>
</head>
<body>
    <div class="form-container">
        <h1>Hồ sơ người dùng</h1>
        <form action="userProfile" method="post">
            <input type="hidden" name="service" value="edit">
            
            <label>Họ và tên:</label>
            <input type="hidden" name="id" value="<%= user != null ? user.getUserID() : "" %>" readonly class="readonly-field"><br>
            <input type="text" name="name" value="<%= user != null ? user.getName() : "" %>" required readonly class="readonly-field"><br>
            
            <label>Tên đăng nhập:</label>
            <input type="text" name="username" value="<%= user != null ? user.getUsername() : "" %>" required readonly class="readonly-field"><br>
            
            <label>Email:</label>
            <input type="email" name="email" value="<%= user != null ? user.getEmail() : "" %>" required readonly class="readonly-field"><br>
            
            <label>Số điện thoại:</label>
            <input type="text" name="phone" value="<%= user != null ? user.getPhone() : "" %>" required readonly class="readonly-field"><br>
            
            <label>Giới tính:</label>
            <select name="gender">
                <option value="true" <%= user != null && user.isGender() ? "selected" : "" %>>Nam</option>
                <option value="false" <%= user != null && !user.isGender() ? "selected" : "" %>>Nữ</option>
            </select><br>

            <div class="button-container">
                <button type="button" onclick="toggleEdit()" class="edit">Chỉnh sửa</button>
                <button type="submit">Lưu thay đổi</button>
                <a href="<%= request.getContextPath() %>/change" class="change-link">Đổi mật khẩu</a>
                <a href="<%= request.getContextPath() %>/homepage" class="home-link">Homepage</a>
            </div>
        </form>
    </div>
</body>
</html>