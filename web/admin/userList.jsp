<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Danh sách người dùng</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            * {
                box-sizing: border-box;
            }

            #myInput {
                
                background-position: 10px 10px;
                background-repeat: no-repeat;
                width: 100%;
                font-size: 16px;
                padding: 12px 20px 12px 40px;
                border: 1px solid #ddd;
                margin-bottom: 12px;
            }

            #myTable {
                border-collapse: collapse;
                width: 100%;
                border: 1px solid #ddd;
                font-size: 18px;
                overflow: auto; /* Tạo thanh cuộn */
                max-height: 500px; /* Giới hạn chiều cao bảng */
            }

            #myTable th, #myTable td {
                text-align: left;
                padding: 12px;
            }

            #myTable tr {
                border-bottom: 1px solid #ddd;
            }

            #myTable tr.header, #myTable tr:hover {
                background-color: #f1f1f1;
            }
            #table-container {
                max-height: 500px; /* Giới hạn chiều cao của bảng */
                overflow-y: auto; /* Tạo thanh cuộn dọc */
                border: 1px solid #ddd;
            }

            #myTable {
                width: 100%;
                border-collapse: collapse;
            }

            #myTable th {
                position: sticky;
                top: 0;
                background-color: #f8f9fa; /* Màu nền cố định */
                z-index: 100;
                border-bottom: 2px solid #ddd;
            }
        </style>
    </head>

    <body style="padding: 50px;">

        
                <div class="d-flex justify-content-between">
                    <h2>Danh sách người dùng</h2>
                    <a href="${pageContext.request.contextPath}/homepage" class="btn btn-secondary" style="margin-bottom:10px">Quay lại</a>
                        
                </div>
        <input type="text" id="myInput" onkeyup="myFunction()" placeholder="Tìm kiếm tên người dùng.." title="Nhập tên người dùng">
        <div id="table-container" style="max-height: 500px; overflow-y: auto;">

            <table id="myTable">
                <tr class="header">
                    <th style="width:20%;">Tên</th>
                    <th style="width:20%;">Email</th>
                    <th style="width:20%;">Số điện thoại</th>
                    <th style="width:20%;">Vai trò</th>
                    <th style="width:20%;">Hành động</th>
                </tr>
                <c:forEach var="user" items="${users}">
                    <tr>
                        <td>${user.name}</td>
                        <td>${user.email}</td>
                        <td>${user.phone}</td>
                        <td>${user.role}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/admin/userDetail?id=${user.userID}" class="btn btn-primary">Chi tiết</a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
        <button class="btn btn-success mt-3" onclick="showAddUserForm()">Thêm người dùng mới</button>

        <div id="addUserForm" style="display: none;">
            <h3>Thêm người dùng mới</h3>
            <% String error = (String) request.getAttribute("error"); %>
            <% if (error != null) {%>
            <div style="color: red; font-weight: bold;">
                <%= error%>
            </div>
            <% }%>
            <form action="${pageContext.request.contextPath}/admin/userList" method="POST">
                <div class="mb-3">
                    <label for="name" class="form-label">Tên</label>
                    <input type="text" class="form-control" id="name" name="name" required>
                </div>
                <div class="mb-3">
                    <label for="email" class="form-label">Email</label>
                    <input type="email" class="form-control" id="email" name="email" required>
                </div>
                <div class="mb-3">
                    <label for="phone" class="form-label">Số điện thoại</label>
                    <input type="text" class="form-control" id="phone" name="phone" required>
                </div>
                <div class="mb-3">
                    <label for="username" class="form-label">Tên tài khoản</label>
                    <input type="text" class="form-control" id="username" name="username" required>
                </div>
                <div class="mb-3">
                    <label for="password" class="form-label">Mật khẩu</label>
                    <input type="password" class="form-control" id="password" name="password" required>
                </div>
                <div class="mb-3">
                    <label for="role" class="form-label">Vai trò</label>
                    <select class="form-control" id="role" name="role">
                        <option value="1">Admin</option>
                        <option value="2">Manager</option>
                        <option value="3">Staff</option>
                        <option value="4">Customer</option>
                    </select>
                </div>
                <button type="submit" class="btn btn-primary">Thêm</button>
            </form>
        </div>

        <script>
            function myFunction() {
                var input, filter, table, tr, i, j, td, txtValue;
                input = document.getElementById("myInput");
                filter = input.value.toUpperCase();
                table = document.getElementById("myTable");
                tr = table.getElementsByTagName("tr");

                for (i = 1; i < tr.length; i++) { // Start from 1 to skip header row
                    tr[i].style.display = "none"; // Hide row initially
                    for (j = 0; j < 4; j++) { // Check first 4 columns
                        td = tr[i].getElementsByTagName("td")[j];
                        if (td) {
                            txtValue = td.textContent || td.innerText;
                            if (txtValue.toUpperCase().indexOf(filter) > -1) {
                                tr[i].style.display = "";
                                break; // Show row if match found
                            }
                        }
                    }
                }
            }
            function showAddUserForm() {
                var form = document.getElementById("addUserForm");
                if (form.style.display === "none") {
                    form.style.display = "block";
                } else {
                    form.style.display = "none";
                }
            }
        </script>

    </body>
</html>
