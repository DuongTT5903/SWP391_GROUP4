<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Service" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Service Cart</title>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css">
</head>
<body>

<div class="container mt-5">
    <h2>Giỏ hàng</h2>

    <c:if test="${not empty cartMessage}">
        <p class="text-danger fw-bold">${cartMessage}</p>
    </c:if>

    <c:if test="${not empty cartItems}">
        <table class="table table-bordered">
            <thead>
                <tr>
                    <th>#</th>
                    <th>Tên dịch vụ</th>
                    <th>Giá</th>
                    <th>Chức năng</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="service" items="${cartItems}" varStatus="status">
                    <tr>
                        <td>${status.index + 1}</td>
                        <td>${service.serviceName}</td>
                        <td>${service.servicePrice} VNĐ</td>
                        <td>
                            <form action="DeleteCart" method="POST">
                                <input type="hidden" name="DeleteID" value="${service.serviceID}">
                                <button type="submit" class="btn btn-danger">Xóa</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>

    <!-- Nút trở về danh sách dịch vụ -->
<a href="${pageContext.request.contextPath}/ServiceList" class="btn btn-primary mt-3">Trở về danh sách dịch vụ</a>


</div>

</body>
</html>
