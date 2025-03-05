<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Service" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Hết thời gian truy cập</title>
</head>
<body>
    <h2>Phiên truy cập của bạn đã hết hạn!</h2>
    <p>Vui lòng tải lại trang hoặc đăng nhập lại.</p>
    <a "${pageContext.request.contextPath}\shoppingCart">Quay lại giỏ hàng</a>
</body>
</html>
