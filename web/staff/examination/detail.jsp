<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Examination Detail</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f9;
            color: #333;
        }

        .container {
            width: 80%;
            margin: 0 auto;
            padding: 20px;
        }

        h1 {
            color: #2c3e50;
            text-align: center;
            margin-bottom: 20px;
        }

        /* Thanh điều hướng */
        .navbar {
            background-color: #2c3e50;
            padding: 10px 0;
            margin-bottom: 20px;
        }

        .navbar ul {
            list-style-type: none;
            margin: 0;
            padding: 0;
            text-align: center;
        }

        .navbar li {
            display: inline;
            margin-right: 20px;
        }

        .navbar a {
            color: white;
            text-decoration: none;
            font-weight: bold;
        }

        .navbar a:hover {
            color: #3498db;
        }

        /* Thông tin chi tiết */
        p {
            background-color: white;
            padding: 10px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            margin-bottom: 10px;
        }

        p strong {
            display: inline-block;
            width: 150px;
            font-weight: bold;
        }

        /* Liên kết hành động */
        a {
            color: #3498db;
            text-decoration: none;
        }

        a:hover {
            text-decoration: underline;
        }

        .action-link {
            background-color: #3498db;
            color: white;
            padding: 5px 10px;
            border-radius: 4px;
        }

        .action-link:hover {
            background-color: #2980b9;
            text-decoration: none;
        }
    </style>
</head>
<body>
    

    <div class="container">
        <h1>Examination Detail</h1>
        <p><strong>ID:</strong> ${examination.examinationId}</p>
        <p><strong>Customer Name:</strong> ${examination.reservation.customerName}</p>
        <p><strong>Date:</strong> <fmt:formatDate value="${examination.creationDate}" pattern="yyyy-MM-dd"/></p>
        <p><strong>Services:</strong>
            <c:forEach var="service" items="${examination.services}">
                ${service.serviceName}<br/>
            </c:forEach>
        </p>
        <p><strong>Prescription:</strong> ${examination.prescription}</p>
        <p><strong>Doctor ID:</strong> ${examination.doctorId}</p>
        <a href="${pageContext.request.contextPath}/staff/examination/list" class="action-link">Back to List</a>
    </div>
</body>
</html>