<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Danh sách người dùng</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            padding: 50px;
            background-color: #f4f4f9;
        }
        .table-container {
            max-height: 500px;
            overflow-y: auto;
            border: 1px solid #ddd;
            border-radius: 8px;
            background: #fff;
            padding: 10px;
        }
        th {
            position: sticky;
            top: 0;
            background-color: #007bff;
            color: white;
            z-index: 100;
            border-bottom: 2px solid #ddd;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2 class="text-primary">Danh sách người dùng</h2>
            <a href="${pageContext.request.contextPath}/homepage" class="btn btn-secondary">Quay lại</a>
        </div>

        <div class="row mb-3">
            <div class="col-md-4">
                <input type="text" id="myInput" onkeyup="filterTable()" placeholder="Tìm kiếm tên người dùng..." class="form-control">
            </div>
            <div class="col-md-3">
                <select id="statusFilter" class="form-select" onchange="filterTable()">
                    <option value="">Tất cả trạng thái</option>
                    <option value="1">Hoạt động</option>
                    <option value="0">Bị khóa</option>
                </select>
            </div>
        </div>

        <div class="table-container">
            <table id="myTable" class="table table-striped table-bordered">
                <thead>
                    <tr>
                        <th>Tên</th>
                        <th>Email</th>
                        <th>Số điện thoại</th>
                        <th>Vai trò</th>
                        <th>Trạng thái</th>
                        <th>Hành động</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="user" items="${users}">
                        <tr>
                            <td>${user.name}</td>
                            <td>${user.email}</td>
                            <td>${user.phone}</td>
                            <td>${user.role}</td>
                            <td data-status="${user.status.status ? '1' : '0'}">
                                <span class="badge ${user.status.status ? 'bg-success' : 'bg-danger'}">
                                    ${user.status.status ? 'Hoạt động' : 'Bị khóa'}
                                </span>
                            </td>
                            <td>
                                <a href="${pageContext.request.contextPath}/manager/customerDetail?id=${user.userID}" class="btn btn-sm btn-primary">Chi tiết</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <!-- Nút Thêm Người Dùng -->
        <button class="btn btn-success mt-3" onclick="showAddUserForm()">Thêm người dùng mới</button>

        <!-- Form ẩn để thêm người dùng -->
        <div id="addUserForm" class="card mt-4" style="display: none;">
            <div class="card-header bg-primary text-white">Thêm người dùng mới</div>
            <div class="card-body">
                <form action="${pageContext.request.contextPath}/manager/customerList" method="POST">
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
                    <button type="submit" class="btn btn-success">Thêm</button>
                </form>
            </div>
        </div>
    </div>

    <script>
        function filterTable() {
            let input = document.getElementById("myInput").value.toUpperCase();
            let statusFilter = document.getElementById("statusFilter").value;
            let table = document.getElementById("myTable");
            let tr = table.getElementsByTagName("tr");

            for (let i = 1; i < tr.length; i++) {
                let nameCol = tr[i].getElementsByTagName("td")[0];
                let statusCol = tr[i].getElementsByTagName("td")[4];

                if (nameCol && statusCol) {
                    let nameTxt = nameCol.textContent || nameCol.innerText;
                    let statusValue = statusCol.getAttribute("data-status") || "";

                    let matchesName = nameTxt.toUpperCase().indexOf(input) > -1;
                    let matchesStatus = statusFilter === "" || statusValue === statusFilter;

                    tr[i].style.display = matchesName && matchesStatus ? "" : "none";
                }
            }
        }

        function showAddUserForm() {
            let form = document.getElementById("addUserForm");
            form.style.display = form.style.display === "none" ? "block" : "none";
        }
    </script>
</body>
</html>
