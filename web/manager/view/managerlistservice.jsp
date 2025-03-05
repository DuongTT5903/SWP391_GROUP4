<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Manager List Service</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                margin: 20px;
            }
            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 10px;
            }
            table, th, td {
                border: 1px solid black;
            }
            th, td {
                padding: 10px;
                text-align: left;
            }
            th {
                background-color: #f2f2f2;
                cursor: pointer;
            }
            th:hover {
                background-color: #ddd;
            }
            img {
                border-radius: 5px;
            }
            .button-container {
                margin-top: 15px;
            }
            .button-container button {
                padding: 8px 15px;
                margin-right: 5px;
                cursor: pointer;
            }
            .filter-container {
                margin-bottom: 15px;
            }
            select {
                padding: 5px;
            }
        </style>
    </head>
    <body>
        <c:if test="${not empty message}">
            <div style="color: green; text-align: center; margin-bottom: 15px;">
                ${message}
            </div>
        </c:if>

        <h1>Manager List Service</h1>

        <form action="${pageContext.request.contextPath}/manager/listservice" method="get" onsubmit="return validateSearch()">
            <input type="hidden" name="service" value="searchById">
            <label for="searchID">Search by ID:</label>
            <input type="text" id="searchID" name="searchID" placeholder="Enter Service ID" value="${searchID}">
            <button type="submit">Search</button>
        </form>

        <br>

        <div class="filter-container">
            <label for="statusFilter">Filter by Status:</label>
            <select id="statusFilter" onchange="filterStatus()">
                <option value="all">All</option>
                <option value="active">Active</option>
                <option value="inactive">Inactive</option>
            </select>
        </div>

        <table id="serviceTable">
            <tr>
                <th>ID</th>
                <th>Title</th>
                <th>Detail</th>
                <th>Category</th>
                <th onclick="sortTable()">Service Price 🔽</th>
                <th>Sale Price</th>
                <th>Image</th>
                <th>Status</th>
                <th>Actions</th>
            </tr>
            <c:forEach var="service" items="${list}">
                <tr class="service-row" data-status="${service.status ? 'active' : 'inactive'}">
                    <td>${service.serviceID}</td>
                    <td>${service.serviceName}</td>
                    <td>${service.serviceDetail}</td>
                    <td>${service.category.categoryName}</td>
                    <td class="service-price">${service.servicePrice}</td>
                    <td>${service.salePrice}</td>
                    <td>
                        <img src="${service.imageURL}" alt="Service Image" width="50" height="50">
                    </td>
                    <td>
                        <form action="${pageContext.request.contextPath}/manager/listservice" method="get">
                            <input type="hidden" name="service" value="editStatus">
                            <input type="hidden" name="serviceID" value="${service.serviceID}">
                            <select name="editStatus" onchange="this.form.submit()">
                                <option value="true" ${service.status ? "selected" : ""}>Active</option>
                                <option value="false" ${!service.status ? "selected" : ""}>Inactive</option>
                            </select>
                        </form>
                    </td>
                    <td>
                        <form action="${pageContext.request.contextPath}/manager/listservice" method="get" style="display:inline;">
                            <input type="hidden" name="serviceID" value="${service.serviceID}">
                            <input type="hidden" name="service" value="viewDetail">
                            <button type="submit">View</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>

        <div class="button-container">
            <form action="${pageContext.request.contextPath}/manager/listservice" method="get" style="display:inline;">
                <input type="hidden" name="service" value="addRequest">
                <button type="submit">Add New Service</button>
            </form>
            <a href="homepage"><button>Back to Home</button></a>
        </div>

        <script>
            function validateSearch() {
                let searchInput = document.getElementById("searchID").value.trim();
                if (searchInput === "") {
                    alert("Please enter a Service ID to search!");
                    return false;
                }
                return true;
            }

            function filterStatus() {
                let filter = document.getElementById("statusFilter").value;
                let rows = document.querySelectorAll(".service-row");
                rows.forEach(row => {
                    let status = row.getAttribute("data-status");
                    if (filter === "all" || status === filter) {
                        row.style.display = "";
                    } else {
                        row.style.display = "none";
                    }
                });
            }

            let ascending = true;
            function sortTable() {
                let table = document.getElementById("serviceTable");
                let rows = Array.from(table.getElementsByClassName("service-row"));
                let icon = table.rows[0].cells[4];
                rows.sort((a, b) => {
                    let priceA = parseFloat(a.cells[4].innerText);
                    let priceB = parseFloat(b.cells[4].innerText);
                    return ascending ? priceA - priceB : priceB - priceA;
                });
                ascending = !ascending;
                icon.innerHTML = `Service Price ${ascending ? '🔽' : '🔼'}`;
                rows.forEach(row => table.appendChild(row));
            }
        </script>
    </body>
</html>