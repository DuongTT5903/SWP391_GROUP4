<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Feedback List</title>
    <style>
        table { width: 100%; border-collapse: collapse; }
        th, td { border: 1px solid black; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
        .search-box { margin-bottom: 20px; }
    </style>
</head>
<body>
    <h1>Feedback List</h1>

    <!-- Search Form -->
    <form action="/manager/feedbackList" method="GET" class="search-box">
        <label for="userID">Search by User ID:</label>
        <input type="text" id="userID" name="userID" placeholder="Enter User ID">
        <button type="submit">Search</button>
        <a href="/manager/feedbackList"><button type="button">Reset</button></a>
    </form>

    <!-- Error Message -->
    <c:if test="${not empty error}">
        <p style="color: red;">${error}</p>
    </c:if>

    <table>
        <thead>
            <tr>
                <th>Feedback ID</th>
                <th>Feedback Detail</th>
                <th>Customer ID</th>
                <th>Rated</th>
                <th>Service ID</th>
                <th>Creation Date</th>
                <th>Status</th>
                <th>User</th>
            </tr>
        </thead>
        <tbody>
            <c:choose>
                <c:when test="${empty feedbacks}">
                    <tr>
                        <td colspan="8" style="text-align:center;">No feedbacks found.</td>
                    </tr>
                </c:when>
                <c:otherwise>
                    <c:forEach var="feedback" items="${feedbacks}">
                        <tr>
                            <td>${feedback.id}</td>
                            <td>${feedback.feedbackDetail}</td>
                            <td>${feedback.customerID}</td>
                            <td>${feedback.rated}</td>  
                            <td>${feedback.services.serviceID}</td>
                            <td>${feedback.creationDate}</td>
                            <td>${feedback.status ? 'Active' : 'Inactive'}</td>
                            <td>${feedback.user.name}</td>
                        </tr>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </tbody>
    </table>
</body>
</html>
