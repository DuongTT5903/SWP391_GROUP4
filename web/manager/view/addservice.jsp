<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Add New Service</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    </head>
    <body class="bg-light">
        <div class="container py-5">
            <div class="row justify-content-center">
                <div class="col-md-8">
                    <div class="card shadow-sm">
                        <div class="card-header bg-success text-white">
                            <h2 class="mb-0">Add New Service</h2>
                        </div>
                        <div class="card-body">
                            <form action="${pageContext.request.contextPath}/manager/listservice" method="get">
                                <input type="hidden" name="service" value="addDone">

                                <div class="mb-3">
                                    <label for="serviceName" class="form-label">Service Name</label>
                                    <input type="text" class="form-control" id="serviceName" name="serviceName" 
                                           required placeholder="Enter service name">
                                </div>

                                <div class="mb-3">
                                    <label for="serviceDetail" class="form-label">Details</label>
                                    <textarea class="form-control" id="serviceDetail" name="serviceDetail" rows="3" 
                                              placeholder="Enter service details"></textarea>
                                </div>

                                <div class="mb-3">
                                    <label for="categoryID" class="form-label">Category</label>
                                    <select class="form-select" id="categoryID" name="categoryID" required>
                                        <option value="">-- Select Category --</option>
                                        <c:forEach var="category" items="${categories}">
                                            <option value="${category.categoryID}">${category.categoryName}</option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <div class="mb-3">
                                    <label for="servicePrice" class="form-label">Price</label>
                                    <input type="number" step="0.01" class="form-control" id="servicePrice" 
                                           name="servicePrice" required placeholder="Enter price">
                                </div>

                                <div class="mb-3">
                                    <label for="salePrice" class="form-label">Sale Price</label>
                                    <input type="number" step="0.01" class="form-control" id="salePrice" 
                                           name="salePrice" placeholder="Enter sale price (optional)">
                                </div>

                                <div class="mb-3">
                                    <label for="imageURL" class="form-label">Image URL</label>
                                    <input type="text" class="form-control" id="imageURL" name="imageURL" 
                                           placeholder="Enter image URL (optional)">
                                </div>

                                <div class="d-flex justify-content-between">
                                    <button type="submit" class="btn btn-success">Add Service</button>
                                    <a href="${pageContext.request.contextPath}/manager/listservice?service=listservice" 
                                       class="btn btn-danger">Cancel</a>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>