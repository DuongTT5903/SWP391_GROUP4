<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Edit Service</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
        <style>
            .required::after { content: " *"; color: red; }
            .error-message { color: red; font-size: 14px; display: none; }
            .image-item { border: 1px solid #ddd; padding: 10px; margin-bottom: 10px; position: relative; }
            .remove-btn { position: absolute; top: 10px; right: 10px; }
        </style>
    </head>
    
    <body class="bg-light">
        <jsp:include page="/manager/headermanager.jsp" />
        <div class="container py-5">
            <div class="row justify-content-center">
                <div class="col-md-8">
                    <div class="card shadow-sm">
                        <div class="card-header bg-primary text-white">
                            <h2 class="mb-0">Edit Service</h2>
                        </div>
                        <div class="card-body">
                            <form action="${pageContext.request.contextPath}/manager/listservice" method="post" onsubmit="return validateForm()">
                                <input type="hidden" name="service" value="savechange">
                                <input type="hidden" name="serviceID" value="${service.serviceID}">

                                <div class="mb-3">
                                    <label for="serviceName" class="form-label required">Service Name</label>
                                    <input type="text" class="form-control" id="serviceName" name="serviceName" 
                                           value="${service.serviceName}" required>
                                </div>

                                <div class="mb-3">
                                    <label for="detailText" class="form-label required">Service Detail</label>
                                    <textarea class="form-control" id="detailText" name="detailText" rows="3" required>${service.serviceDetail}</textarea>
                                </div>

                                <div class="mb-3">
                                    <label class="form-label required">Detail Images</label>
                                    <div id="imageItems">
                                        <c:forEach var="img" items="${detailImages}" varStatus="loop">
                                            <div class="image-item">
                                                <button type="button" class="btn btn-danger btn-sm remove-btn" onclick="removeImageItem(this)">X</button>
                                                <input type="text" class="form-control" name="detailImage_${loop.index}" 
                                                       value="${img.imageURL}" required>
                                                <img src="${img.imageURL}" alt="Detail Image" class="img-fluid mt-2" style="max-height: 100px;">
                                            </div>
                                        </c:forEach>
                                        <c:if test="${empty detailImages}">
                                            <div class="image-item">
                                                <button type="button" class="btn btn-danger btn-sm remove-btn" onclick="removeImageItem(this)">X</button>
                                                <input type="text" class="form-control" name="detailImage_0" placeholder="Enter image URL" required>
                                            </div>
                                        </c:if>
                                    </div>
                                    <button type="button" class="btn btn-success btn-sm mt-2" onclick="addImageItem()">Add New Image</button>
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
                                    <label for="salePrice" class="form-label">Sale Off</label>
                                    <input type="number" step="0.01" class="form-control" id="salePrice" 
                                           name="salePrice" value="${service.salePrice}">
                                    <div id="salePriceError" class="error-message">Sale Price must be less than Price and non-negative.</div>
                                </div>

                                <div class="mb-3">
                                    <label for="imageURL" class="form-label required">Main Image URL</label>
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
            let imageIndex = ${empty detailImages ? 1 : fn:length(detailImages)};

            function addImageItem() {
                let container = document.getElementById("imageItems");
                let newItem = document.createElement("div");
                newItem.className = "image-item";
                newItem.innerHTML = `
                    <button type="button" class="btn btn-danger btn-sm remove-btn" onclick="removeImageItem(this)">X</button>
                    <input type="text" class="form-control" name="detailImage_${imageIndex}" placeholder="Enter image URL" required>
                `;
                container.appendChild(newItem);
                imageIndex++;
            }

            function removeImageItem(button) {
                let imageItems = document.querySelectorAll(".image-item");
                if (imageItems.length > 1) {
                    button.parentElement.remove();
                } else {
                    alert("At least one image is required!");
                }
            }

            function validateForm() {
                let isValid = true;
                let serviceName = document.getElementById("serviceName").value.trim();
                let detailText = document.getElementById("detailText").value.trim();
                let servicePrice = parseFloat(document.getElementById("servicePrice").value);
                let salePrice = document.getElementById("salePrice").value ? parseFloat(document.getElementById("salePrice").value) : null;
                let imageURL = document.getElementById("imageURL").value.trim();

                document.getElementById("priceError").style.display = "none";
                document.getElementById("salePriceError").style.display = "none";

                if (serviceName === "") {
                    alert("Service Name cannot be empty or contain only spaces!");
                    isValid = false;
                }
                if (detailText === "") {
                    alert("Service Detail cannot be empty or contain only spaces!");
                    isValid = false;
                }
                if (isNaN(servicePrice) || servicePrice <= 0) {
                    document.getElementById("priceError").style.display = "block";
                    isValid = false;
                }
                if (salePrice !== null && (salePrice < 0 || salePrice >= servicePrice)) {
                    document.getElementById("salePriceError").style.display = "block";
                    isValid = false;
                }
                if (imageURL === "") {
                    alert("Main Image URL cannot be empty!");
                    isValid = false;
                } else if (!isValidURL(imageURL)) {
                    alert("Main Image URL is invalid. Please enter a valid URL!");
                    isValid = false;
                }

                let imageInputs = document.querySelectorAll("[name^='detailImage_']");
                for (let input of imageInputs) {
                    let value = input.value.trim();
                    if (value === "") {
                        alert("Detail Image URL cannot be empty!");
                        isValid = false;
                    } else if (!isValidURL(value)) {
                        alert("Detail Image URL is invalid. Please enter a valid URL!");
                        isValid = false;
                    }
                }

                return isValid;
            }

            function isValidURL(str) {
                let pattern = new RegExp('^(https?:\\/\\/)?' +
                    '((([a-zA-Z0-9$_.+!*\'(),-]+:\\S*)?@)?' +
                    '(([a-zA-Z0-9.-]+\\.[a-zA-Z]{2,})|' +
                    '((\\d{1,3}\\.){3}\\d{1,3}))' +
                    '(\\:\\d+)?(\\/[-a-zA-Z0-9%_.~+]*)*' +
                    '(\\?[;&a-zA-Z0-9%_.~+=-]*)?' +
                    '(\\#[-a-zA-Z0-9_]*)?$', 'i');
                return pattern.test(str);
            }
        </script>
    </body>
</html>