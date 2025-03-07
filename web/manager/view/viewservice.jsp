<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Service Detail & Edit</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <style>
        .required::after {
            content: " *";
            color: red;
        }
        .error-message {
            color: red;
            font-size: 14px;
            display: none;
        }
    </style>
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
                        <form action="${pageContext.request.contextPath}/manager/listservice" method="get" onsubmit="return validateForm()">
                            <input type="hidden" name="service" value="savechange">
                            <input type="hidden" name="serviceID" value="${service.serviceID}">

                            <div class="mb-3">
                                <label for="serviceName" class="form-label required">Service Name</label>
                                <input type="text" class="form-control" id="serviceName" name="serviceName" 
                                       value="${service.serviceName}" required>
                            </div>

                            <div class="mb-3">
                                <label for="serviceDetail" class="form-label required">Service Detail</label>
                                <textarea class="form-control" id="serviceDetail" name="serviceDetail" rows="5" 
                                          required>${service.serviceDetail}</textarea>
                            </div>

                            <div class="mb-3">
                                <label for="categoryID" class="form-label required">Category</label>
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
                                <label for="servicePrice" class="form-label required">Service Price</label>
                                <input type="number" step="0.01" class="form-control" id="servicePrice" 
                                       name="servicePrice" value="${service.servicePrice}" required>
                                <div id="priceError" class="error-message">Price must be greater than 0.</div>
                            </div>

                            <div class="mb-3">
                                <label for="salePrice" class="form-label">Sale Price</label>
                                <input type="number" step="0.01" class="form-control" id="salePrice" 
                                       name="salePrice" value="${service.salePrice}">
                                <div id="salePriceError" class="error-message">Sale Price must be less than Price and non-negative.</div>
                            </div>

                            <div class="mb-3">
                                <label for="imageURL" class="form-label required">Image URL</label>
                                <input type="text" class="form-control" id="imageURL" name="imageURL" 
                                       value="${service.imageURL}" required>
                                <img src="${service.imageURL}" alt="Service Image" class="img-fluid mt-2" 
                                     style="max-height: 200px;">
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
    <script>
        function validateForm() {
            let isValid = true;
            let servicePrice = parseFloat(document.getElementById("servicePrice").value);
            let salePrice = document.getElementById("salePrice").value ? parseFloat(document.getElementById("salePrice").value) : null;

            // Reset thông báo lỗi
            document.getElementById("priceError").style.display = "none";
            document.getElementById("salePriceError").style.display = "none";

            // Validate servicePrice
            if (isNaN(servicePrice) || servicePrice <= 0) {
                document.getElementById("priceError").style.display = "block";
                isValid = false;
            }

            // Validate salePrice
            if (salePrice !== null) {
                if (salePrice < 0 || salePrice >= servicePrice) {
                    document.getElementById("salePriceError").style.display = "block";
                    isValid = false;
                }
            }

            return isValid;
        }
    </script>
</body>
</html>