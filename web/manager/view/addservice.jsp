<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Service</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f2f2f2;
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
        }
        label {
            font-weight: bold;
            display: block;
            margin-top: 10px;
        }
        input, textarea {
            width: 100%;
            padding: 8px;
            margin-top: 5px;
            border: 1px solid #ccc;
            border-radius: 4px;
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
    <h2>Add New Service</h2>

    <form action="ManagerServiceController" method="post">
        <input type="hidden" name="action" value="addService">

        <label>Service Name:</label>
        <input type="text" name="serviceName" required>

        <label>Details:</label>
        <textarea name="serviceDetail" rows="3"></textarea>

        <label>Category ID:</label>
        <input type="number" name="categoryID" required>

        <label>Price:</label>
        <input type="number" name="servicePrice" step="0.01" required>

        <label>Sale Price:</label>
        <input type="number" name="salePrice" step="0.01">

        <label>Image URL:</label>
        <input type="text" name="imageURL">

        <label>Author ID:</label>
        <input type="number" name="authorID" required>

        <div class="button-group">
            <button type="submit" class="btn-save">Add Service</button>
            <a href="ManagerServiceController?service=listservice" class="btn-cancel">Cancel</a>
        </div>
    </form>
</div>

</body>
</html>
