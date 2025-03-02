<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.Service" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Service</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f2f2f2;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .container {
            width: 400px;
            background: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.2);
        }
        h2 {
            text-align: center;
            color: #333;
        }
        label {
            font-weight: bold;
            display: block;
            margin-top: 10px;
        }
        input, textarea, select {
            width: 100%;
            padding: 8px;
            margin-top: 5px;
            border: 1px solid #ccc;
            border-radius: 4px;
            font-size: 14px;
        }
        .button-group {
            display: flex;
            justify-content: space-between;
            margin-top: 15px;
        }
        button {
            padding: 10px 15px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
        }
        .btn-save {
            background-color: #4CAF50;
            color: white;
        }
        .btn-cancel {
            background-color: #d9534f;
            color: white;
            text-decoration: none;
            text-align: center;
            line-height: 38px;
            display: inline-block;
        }
    </style>
</head>
<body>

<div class="container">
    <h2>Edit Service</h2>

    <%
        Service service = (Service) request.getAttribute("service");
        if (service == null) {
    %>
        <p style="color: red; text-align: center;">Service not found!</p>
    <%
        } else {
    %>

    <form action="ManagerServiceController" method="post">
        <input type="hidden" name="action" value="editService">
        <input type="hidden" name="serviceID" value="<%= service.getServiceID() %>">

        <label>Service Name:</label>
        <input type="text" name="serviceName" value="<%= service.getServiceName() %>" required>

        <label>Service Details:</label>
        <textarea name="serviceDetail" rows="3"><%= service.getServiceDetail() %></textarea>

        <label>Price:</label>
        <input type="number" name="servicePrice" step="0.01" value="<%= service.getServicePrice() %>" required>

        <label>Discount Price:</label>
        <input type="number" name="salePrice" step="0.01" value="<%= service.getSalePrice() %>">

        <label>Image URL:</label>
        <input type="text" name="imageURL" value="<%= service.getImageURL() %>">

        <label>Status:</label>
        <select name="status">
            <option value="true" <%= service.isStatus() ? "selected" : "" %>>Active</option>
            <option value="false" <%= !service.isStatus() ? "selected" : "" %>>Inactive</option>
        </select>

        <div class="button-group">
            <button type="submit" class="btn-save">Save Changes</button>
            <a href="ManagerServiceController?action=list" class="btn-cancel">Cancel</a>
        </div>
    </form>

    <% } %>
</div>

</body>
</html>
