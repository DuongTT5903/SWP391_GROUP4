<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Service Detail & Edit</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    </head>
    <body class="bg-light">
        <div class="container py-5">
            <div class="row justify-content-center">
                <div class="col-md-8">
                    <div class="card shadow-sm">
                        <div class="card-header bg-primary text-white">
                            <h2 class="mb-0">Service Detail & Edit</h2>
                        </div>
                        <div class="card-body">
                            <form action="${pageContext.request.contextPath}/manager/listservice" method="get">
                                <input type="hidden" name="service" value="savechange">
                                <input type="hidden" name="serviceID" value="${service.serviceID}">

                                <div class="mb-3">
                                    <label for="serviceName" class="form-label">Service Name</label>
                                    <input type="text" class="form-control" id="serviceName" name="serviceName" 
                                           value="${service.serviceName}" required>
                                </div>

                                <div class="mb-3">
                                    <label for="serviceDetail" class="form-label">Service Detail</label>
                                    <textarea class="form-control" id="serviceDetail" name="serviceDetail" rows="5" 
                                              required>${service.serviceDetail}</textarea>
                                </div>

                                <div class="mb-3">
                                    <label for="categoryID" class="form-label">Category</label>
                                    <select class="form-select" id="categoryID" name="categoryID" required>
                                        <c:forEach var="category" items="${categories}">
                                            <option value="${category.categoryID}" 
                                                    ${category.categoryID == service.category.categoryID ? 'selected' : ''}>
                                                ${category.categoryName}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <div class="mb-3">
                                    <label for="servicePrice" class="form-label">Service Price</label>
                                    <input type="number" step="0.01" class="form-control" id="servicePrice" 
                                           name="servicePrice" value="${service.servicePrice}" required>
                                </div>

                                <div class="mb-3">
                                    <label for="salePrice" class="form-label">Sale Price</label>
                                    <input type="number" step="0.01" class="form-control" id="salePrice" 
                                           name="salePrice" value="${service.salePrice}" required>
                                </div>

                                <div class="mb-3">
                                    <label for="imageURL" class="form-label">Image URL</label>
                                    <input type="text" class="form-control" id="imageURL" name="imageURL" 
                                           value="${service.imageURL}" required>
                                    <img src="${service.imageURL}" alt="Service Image" class="img-fluid mt-2" 
                                         style="max-height: 200px;">
                                </div>

                                <div class="mb-3 form-check">
                                    <input type="checkbox" class="form-check-input" id="status" name="status" 
                                           value="true" ${service.status ? "checked" : ""}>
                                    <label class="form-check-label" for="status">Active</label>
                                </div>

                                <div class="mb-3">
                                    <label for="author" class="form-label">Author</label>
                                    <input type="text" class="form-control" id="author" 
                                           value="${service.author.name}" disabled>
                                </div>

                                <div class="d-flex justify-content-between">
                                    <button type="submit" class="btn btn-primary">Save Changes</button>
                                    <a href="${pageContext.request.contextPath}/manager/listservice?service=listservice" 
                                       class="btn btn-secondary">Back to List</a>
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