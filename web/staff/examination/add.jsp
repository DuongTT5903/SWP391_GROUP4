<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Add Medical Examination</title>
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

        form textarea {
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
            width: 300px;
            height: 100px;
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

        /* Thông báo lỗi */
        .error {
            color: #e74c3c;
            font-style: italic;
            display: none;
            margin-bottom: 10px;
        }
    </style>
</head>
<body>
    <!-- Thanh điều hướng -->
    <div class="navbar">
        <ul>
            <li><a href="${pageContext.request.contextPath}/staff/dashboard">Dashboard</a></li>
            <li><a href="${pageContext.request.contextPath}/staff/examination/list">Medical Examination History</a></li>
        </ul>
    </div>

    <div class="container">
        <h1>Add New Medical Examination</h1>
        <form method="post" action="${pageContext.request.contextPath}/staff/examination/add" onsubmit="return validateForm()">
            <input type="hidden" name="reservationId" value="${examination.reservation.reservationID}" />
            <p><strong>Reservation ID:</strong> ${examination.reservation.reservationID}</p>
            <label>Prescription:</label>
            <textarea name="prescription" rows="5" cols="30"></textarea>
            <div id="prescriptionError" class="error">Prescription cannot be empty or contain only spaces.</div>
            <br/>
            <button type="submit">Save</button>
        </form>
        <a href="${pageContext.request.contextPath}/staff/examination/list" class="action-link">Back to List</a>
    </div>

    <script>
        function validateForm() {
            var prescription = document.querySelector('textarea[name="prescription"]').value;
            var errorElement = document.getElementById("prescriptionError");

            // Kiểm tra nếu prescription rỗng hoặc chỉ chứa dấu cách
            if (prescription.trim() === "") {
                errorElement.style.display = "block";
                return false; // Ngăn form gửi
            } else {
                errorElement.style.display = "none";
                return true; // Cho phép gửi form
            }
        }
    </script>
</body>
</html>