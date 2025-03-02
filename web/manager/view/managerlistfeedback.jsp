<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Feedback List</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            flex-direction: column;
        }
        .container {
            background-color: #fff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            width: 90%;
            max-width: 1000px;
            margin-top: 20px;
        }
        h1 {
            font-size: 24px;
            margin-bottom: 20px;
            text-align: center;
        }
        .search-box, .filter-box {
            margin-bottom: 20px;
            display: flex;
            justify-content: center;
            gap: 10px;
        }
        .search-box input, .filter-box select {
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            width: 200px;
        }
        .search-button {
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            background-color: #007BFF;
            color: white;
            cursor: pointer;
        }
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            border: 1px solid #ccc;
            padding: 10px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
        tr:nth-child(even) {
            background-color: #f9f9f9;
        }
        tr:hover {
            background-color: #f1f1f1;
        }
        .pagination {
            margin-top: 20px;
            display: flex;
            justify-content: center;
            gap: 5px;
        }
        .pagination button {
            padding: 5px 10px;
            border: none;
            border-radius: 5px;
            background-color: #007BFF;
            color: white;
            cursor: pointer;
        }
        .pagination button:disabled {
            background-color: #ccc;
            cursor: not-allowed;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Feedback List</h1>

        <!-- Search Form -->
        <div class="search-box">
            <input type="text" id="search" placeholder="Search by Username or Content" onkeyup="updateFeedbacks()">
        </div>

        <!-- Filter Form -->
        <div class="filter-box">
            <select id="status" name="status" onchange="updateFeedbacks()">
                <option value="">Status: All</option>
                <option value="active">Active</option>
                <option value="inactive">Inactive</option>
            </select>

            <select id="serviceID" name="serviceID" onchange="updateFeedbacks()">
                <option value="">Service ID: All</option>
                <option value="1">1</option>
                <option value="2">2</option>
                <option value="3">3</option>
            </select>

            <select id="rating" name="rating" onchange="updateFeedbacks()">
                <option value="">Rating: All</option>
                <option value="1">1 star</option>
                <option value="2">2 stars</option>
                <option value="3">3 stars</option>
                <option value="4">4 stars</option>
                <option value="5">5 stars</option>
            </select>
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
            <tbody id="feedbackBody">
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

        <!-- Pagination Controls -->
        <div class="pagination">
            <button onclick="prevPage()" id="prevBtn">Prev</button>
            <span id="pageInfo"></span>
            <button onclick="nextPage()" id="nextBtn">Next</button>
        </div>
    </div>

    <script>
        let currentPage = 1;
        const rowsPerPage = 5;
        const table = document.getElementById("feedbackTable");
        const tbody = document.getElementById("feedbackBody");
        const rows = Array.from(tbody.getElementsByTagName("tr"));

        function updateFeedbacks() {
            let searchValue = document.getElementById("search").value.toLowerCase();
            let statusFilter = document.getElementById("status").value.toLowerCase();
            let serviceIDFilter = document.getElementById("serviceID").value;
            let ratingFilter = document.getElementById("rating").value;

            rows.forEach(row => {
                let username = row.cells[7].textContent.toLowerCase();
                let content = row.cells[1].textContent.toLowerCase();
                let status = row.cells[6].textContent.toLowerCase();
                let serviceID = row.cells[4].textContent;
                let rating = row.cells[3].textContent;

                let matchesSearch = username.includes(searchValue) || content.includes(searchValue);
                let matchesStatus = (statusFilter === "" || status === statusFilter);
                let matchesService = (serviceIDFilter === "" || serviceID === serviceIDFilter);
                let matchesRating = (ratingFilter === "" || rating === ratingFilter);

                row.style.display = (matchesSearch && matchesStatus && matchesService && matchesRating) ? "" : "none";
            });

            currentPage = 1;
            displayPage(currentPage);
        }

        function displayPage(page) {
            let visibleRows = rows.filter(row => row.style.display !== "none");
            let start = (page - 1) * rowsPerPage;
            let end = start + rowsPerPage;

            visibleRows.forEach((row, index) => {
                row.style.display = (index >= start && index < end) ? "" : "none";
            });

            document.getElementById("pageInfo").textContent = `Page ${currentPage} of ${Math.ceil(visibleRows.length / rowsPerPage)}`;
            document.getElementById("prevBtn").disabled = currentPage === 1;
            document.getElementById("nextBtn").disabled = currentPage === Math.ceil(visibleRows.length / rowsPerPage);
        }

        function prevPage() {
            if (currentPage > 1) {
                currentPage--;
                displayPage(currentPage);
            }
        }

        function nextPage() {
            if (currentPage < Math.ceil(rows.length / rowsPerPage)) {
                currentPage++;
                displayPage(currentPage);
            }
        }

        displayPage(currentPage);
    </script>
</body>
</html>
