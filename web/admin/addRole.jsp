<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Thêm Role Mới</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .error {
            color: red;
            font-style: italic;
            margin-bottom: 10px;
        }
        .container {
            max-width: 600px;
            margin-top: 50px;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2>Thêm Role Mới</h2>
            <a href="${pageContext.request.contextPath}/admin/settingList" class="btn btn-secondary">Quay lại danh sách</a>
        </div>

        <c:if test="${not empty error}">
            <div class="error">${error}</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/admin/addRole" method="POST">
            <div class="mb-3">
                <label for="roleName" class="form-label">Tên Role</label>
                <input type="text" class="form-control" id="roleName" name="roleName" required>
            </div>
            <button type="submit" class="btn btn-primary">Thêm Role</button>
        </form>
    </div>
</body>
</html>