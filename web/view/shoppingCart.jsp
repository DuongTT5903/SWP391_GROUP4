<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Service" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

    <h2 cla
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Service Cart</title>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css">
</head>
<body>
   <h2 class="mt-5">Cart</h2>
<table class="table">
    <thead>
        <tr>
            <th>ID</th>
            <th>Tên dịch vụ</th>
            <th>Giá</th>
            <th>Chức năng</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="service" items="${cartItems}" varStatus="status">
            <tr>
                <td>${status.index + 1}</td> <!-- Auto-increment ID -->
                <td>${service.serviceName}</td>
                <td>${service.servicePrice} VNĐ</td> <!-- Assuming price format -->
                <td>
                    <form action="DeleteCart" method="POST">
                        <input type="hidden" name="DeleteID" value="${status.index }">
                        <button type="submit" class="btn btn-danger">Delete</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>

</body>
</html>