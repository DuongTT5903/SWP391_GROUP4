<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Feedback</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f5f5f5;
            margin: 0;
            padding: 0;
        }
        header {
            background-color: #3498db;
            color: #fff;
            padding: 20px;
            text-align: center;
        }
        .container {
            display: flex;
            padding: 20px;
        }
        #sidebar {
            width: 25%;
            background-color: #ecf0f1;
            padding: 15px;
            margin-right: 20px;
            border-radius: 5px;
        }
        #mainContent {
            width: 75%;
            background-color: #fff;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }
        form label {
            font-weight: bold;
            display: block;
            margin-top: 10px;
        }
        form input[type="text"],
        form textarea,
        form select {
            width: 100%;
            padding: 8px;
            margin-top: 5px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        form input[type="submit"] {
            background-color: #3498db;
            color: #fff;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        form input[type="submit"]:hover {
            background-color: #2980b9;
        }
    </style>
</head>
<body>
    <header>
        <h1>Feedback</h1>
    </header>
    <div class="container">
        <div id="sidebar">
            <h3>Sidebar</h3>
            <p>Tìm kiếm dịch vụ, danh mục, liên kết...</p>
        </div>
        <div id="mainContent">
            <h2>Give Feedback</h2>
            <form action="<%= request.getContextPath() %>/feedback" method="post">
                <% 
                    String serviceIDParam = request.getParameter("serviceID");
                    if (serviceIDParam != null) { 
                %>
                    <input type="hidden" name="serviceID" value="<%= serviceIDParam %>" />
                <% 
                    } 
                %>
                
                <label for="feedbackDetail">Your Feedback:</label>
                <textarea name="feedbackDetail" rows="4" cols="50" placeholder="Nhập phản hồi của bạn tại đây..."></textarea>
                
                <label for="rated">Rating (1-5):</label>
                <select name="rated">
                    <option value="1">1 Star</option>
                    <option value="2">2 Stars</option>
                    <option value="3">3 Stars</option>
                    <option value="4">4 Stars</option>
                    <option value="5">5 Stars</option>
                </select>
                
                <label for="imgLink">Image Link:</label>
                <input type="text" name="imgLink" placeholder="Nhập đường dẫn hình ảnh (nếu có)" />
                
                <input type="submit" value="Send Feedback" />
            </form>
        </div>
    </div>
</body>
</html>
