<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
    <title>Medical Examination History</title>
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

        /* Form */
        form {
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            margin-bottom: 20px;
        }

        form label {
            display: inline-block;
            width: 120px;
            font-weight: bold;
            margin-right: 10px;
        }

        form input[type="date"],
        form input[type="text"],
        form select {
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
            width: 200px;
            margin-bottom: 10px;
        }

        form button {
            background-color: #3498db;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        form button:hover {
            background-color: #2980b9;
        }

        /* Bảng */
        table {
            width: 100%;
            border-collapse: collapse;
            background-color: white;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }

        table th, table td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }

        table th {
            background-color: #2c3e50;
            color: white;
        }

        table tr:hover {
            background-color: #f1f1f1;
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

        /* Debug */
        .debug {
            color: #e74c3c;
            font-style: italic;
        }
    </style>
</head>
<body>
   

    <div class="container">
        <h1>Medical Examination History</h1>

        <!-- Nút để thêm mới -->
        <p><a href="${pageContext.request.contextPath}/staff/examination/add?reservationId=1" class="action-link">Add New Examination</a></p>

        <!-- Form lọc -->
        <form method="get" action="${pageContext.request.contextPath}/staff/examination/list">
            <label>Date:</label>
            <input type="date" name="date" />
            <label>Service:</label>
            <select name="serviceId">
                <option value="">All</option>
                <c:forEach var="service" items="${services}">
                    <option value="${service.serviceID}">${service.serviceName}</option>
                </c:forEach>
            </select>
            <label>Medicine:</label>
            <input type="text" name="medicineName" />
            <button type="submit">Filter</button>
        </form>

        <!-- Danh sách -->
        <table>
            <tr>
                <th>ID</th>
                <th>Customer Name</th>
                <th>Date</th>
                <th>Services</th>
                <th>Prescription</th>
                <th>Actions</th>
            </tr>
            <c:forEach var="exam" items="${examinations}">
                <tr>
                    <td>${exam.examinationId}</td>
                    <td>${exam.reservation.customerName}</td>
                    <td><fmt:formatDate value="${exam.creationDate}" pattern="yyyy-MM-dd"/></td>
                    <td>
                        <c:forEach var="service" items="${exam.services}">
                            ${service.serviceName}<br/>
                        </c:forEach>
                    </td>
                    <td>${exam.prescription}</td>
                    <td><a href="${pageContext.request.contextPath}/staff/examination/detail?id=${exam.examinationId}" class="action-link">View</a></td>
                </tr>
            </c:forEach>
        </table>
    </div>
</body>
</html>