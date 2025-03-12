<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Chi tiết người dùng</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            body {
                display: flex;
                justify-content: center;
                align-items: center;
                flex-direction: column;
                padding: 20px;
            }

            .container {
                width: 50%;
            }

            h2 {
                text-align: center;
                margin-bottom: 20px;
            }
        </style>
    </head>
    <body>

        <div class="container">
            <h2>Chi tiết người dùng</h2>

            <!-- Hiển thị lỗi nếu có -->
            <c:if test="${not empty error}">
                <div class="alert alert-danger" role="alert">
                    ${error}
                </div>
            </c:if>

            <form action="${pageContext.request.contextPath}/manager/customerDetail" method="POST">
                <input type="hidden" name="userID" value="${user.userID}">
                <div class="mb-3">
                    <label for="name" class="form-label">Tên</label>
                    <input type="text" class="form-control" id="name" name="name" value="${user.name}" required>
                </div>
                <div class="mb-3">
                    <label for="gender" class="form-label">Giới tính</label>
                    <select class="form-control" id="gender" name="gender">
                        <option value="true" ${user.gender ? 'selected' : ''}>Nam</option>
                        <option value="false" ${!user.gender ? 'selected' : ''}>Nữ</option>
                    </select>
                </div>
                <div class="mb-3">
                    <label for="email" class="form-label">Email</label>
                    <input type="email" class="form-control" id="email" name="email" value="${user.email}" required>
                </div>
                <div class="mb-3">
                    <label for="username" class="form-label">Tên tài khoản</label>
                    <input type="text" class="form-control" id="username" name="username" value="${user.username}" required>
                </div>
                <div class="mb-3">
                    <label for="password" class="form-label">Mật khẩu</label>
                    <input type="password" class="form-control" id="password" name="password">
                    <small class="text-muted">Để trống nếu không muốn đổi mật khẩu.</small>
                </div>

                <div class="mb-3">
                    <label for="phone" class="form-label">Số điện thoại</label>
                    <input type="text" class="form-control" id="phone" name="phone" value="${user.phone}" required>
                </div>
                <div class="mb-3">
                    <label for="role" class="form-label">Vai trò</label>
                    <select class="form-control" id="role" name="role">
                        <option value="Customer" ${user.role == 'Customer' ? 'selected' : ''}>Customer</option>
                    </select>
                </div>
                <div class="d-flex justify-content-between">
                    <a href="${pageContext.request.contextPath}/manager/customerList" class="btn btn-secondary">Quay lại</a>
                    <button type="submit" class="btn btn-primary">Cập nhật</button>
                </div>
            </form>
        </div>

    </body>
</html>
