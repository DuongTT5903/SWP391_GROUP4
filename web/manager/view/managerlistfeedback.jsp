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
    <script>
        function searchFeedbacks() {
            let input = document.getElementById('username').value.toLowerCase();
            let table = document.getElementById('feedbackTable');
            let rows = table.getElementsByTagName('tr');

            for (let i = 1; i < rows.length; i++) {
                let cells = rows[i].getElementsByTagName('td');
                let username = cells[7].textContent.toLowerCase();
                if (username.indexOf(input) > -1) {
                    rows[i].style.display = '';
                } else {
                    rows[i].style.display = 'none';
                }
            }
        }
    </script>
</head>
<body>
    <h1>Feedback List</h1>

    <!-- Search Form -->
    <div class="search-box">
        <label for="username">Search by Username:</label>
        <input type="text" id="username" name="username" onkeyup="searchFeedbacks()" placeholder="Enter Username">
    </div>

    <table id="feedbackTable">
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
        </tbody>
    </table>
</body>

=======
</html>
>>>>>>> 36e60900f559ef461f4c5d4997f4fde22fe64a6b
