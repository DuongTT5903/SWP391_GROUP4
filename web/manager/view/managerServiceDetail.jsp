<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Service Details</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        img { border-radius: 5px; }
        a { text-decoration: none; color: blue; }
        a:hover { text-decoration: underline; }
    </style>
</head>
<body>
    <h1>Service Details</h1>
    <p>ID: ${service.serviceID}</p>
    <p>Title: ${service.serviceName}</p>
    <p>Detail: ${service.serviceDetail}</p>
    <p>Category: ${service.category.categoryName}</p>
    <p>List Price: ${service.servicePrice}</p>
    <p>Sale Price: ${service.salePrice}</p>
    <p>Thumbnail: 
        <c:if test="${not empty service.imageURL}">
            <img src="${service.imageURL}" alt="Service Image" width="100" height="100">
        </c:if>
        <c:if test="${empty service.imageURL}">
            No Image
        </c:if>
    </p>
    <p>Featured: ${service.featured ? 'Yes' : 'No'}</p>
    <p>Status: ${service.status ? 'Active' : 'Inactive'}</p>
    <a href="managerlistservice?service=listservice">Back to List</a>
</body>
</html>