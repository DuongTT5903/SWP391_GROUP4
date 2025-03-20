<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Add New Service</title>
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
            .detail-item {
                border: 1px solid #ddd;
                padding: 10px;
                margin-bottom: 10px;
                position: relative;
            }
            .remove-btn {
                position: absolute;
                top: 10px;
                right: 10px;
            }
        </style>
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
                            <form action="${pageContext.request.contextPath}/manager/listservice" method="get" onsubmit="return validateForm()">
                                <input type="hidden" name="service" value="addDone">

                                <div class="mb-3">
                                    <label for="serviceName" class="form-label required">Service Name</label>
                                    <input type="text" class="form-control" id="serviceName" name="serviceName" 
                                           required placeholder="Enter service name">
                                </div>

                                <!-- Phần chi tiết với nhiều mục -->
                                <div class="mb-3">
                                    <label class="form-label required">Service Details</label>
                                    <div id="detailItems">
                                        <div class="detail-item">
                                            <button type="button" class="btn btn-danger btn-sm remove-btn" onclick="removeDetailItem(this)">X</button>
                                            <div class="mb-2">
                                                <label class="form-label">Detail Text</label>
                                                <textarea class="form-control" name="detailText_0" rows="2" required placeholder="Enter detail text"></textarea>
                                            </div>
                                            <div class="mb-2">
                                                <label class="form-label">Detail Image URL</label>
                                                <input type="text" class="form-control" name="detailImage_0" placeholder="Enter image URL (optional)">
                                            </div>
                                        </div>
                                    </div>
                                    <button type="button" class="btn btn-success btn-sm mt-2" onclick="addDetailItem()">Add New Detail</button>
                                </div>

                                <div class="mb-3">
                                    <label for="categoryID" class="form-label required">Category</label>
                                    <select class="form-select" id="categoryID" name="categoryID" required>
                                        <option value="">-- Select Category --</option>
                                        <c:forEach var="category" items="${categories}">
                                            <option value="${category.categoryID}">${category.categoryName}</option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <div class="mb-3">
                                    <label for="servicePrice" class="form-label required">Price</label>
                                    <input type="number" step="0.01" class="form-control" id="servicePrice" 
                                           name="servicePrice" required placeholder="Enter price">
                                    <div id="priceError" class="error-message">Price must be greater than 0.</div>
                                </div>

                                <div class="mb-3">
                                    <label for="salePrice" class="form-label">Sale Off</label>
                                    <input type="number" step="0.01" class="form-control" id="salePrice" 
                                           name="salePrice" placeholder="Enter sale price (optional)">
                                    <div id="salePriceError" class="error-message">Sale Price must be less than Price and non-negative.</div>
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
        <script>
            let detailIndex = 1;

            function addDetailItem() {
                let container = document.getElementById("detailItems");
                let newItem = document.createElement("div");
                newItem.className = "detail-item";
                newItem.innerHTML = `
                    <button type="button" class="btn btn-danger btn-sm remove-btn" onclick="removeDetailItem(this)">X</button>
                    <div class="mb-2">
                        <label class="form-label">Detail Text</label>
                        <textarea class="form-control" name="detailText_${detailIndex}" rows="2" required placeholder="Enter detail text"></textarea>
                    </div>
                    <div class="mb-2">
                        <label class="form-label">Detail Image URL</label>
                        <input type="text" class="form-control" name="detailImage_${detailIndex}" placeholder="Enter image URL (optional)">
                    </div>
                `;
                container.appendChild(newItem);
                detailIndex++;
            }

            function removeDetailItem(button) {
                button.parentElement.remove();
            }

            function validateForm() {
                let isValid = true;

                let serviceName = document.getElementById("serviceName").value.trim();
                let servicePrice = parseFloat(document.getElementById("servicePrice").value);
                let salePrice = document.getElementById("salePrice").value ? parseFloat(document.getElementById("salePrice").value) : null;
                let imageURL = document.getElementById("imageURL").value.trim();

                document.getElementById("priceError").style.display = "none";
                document.getElementById("salePriceError").style.display = "none";

                if (serviceName === "") {
                    alert("Service Name không được để trống hoặc chỉ chứa dấu cách!");
                    isValid = false;
                }

                let detailTexts = document.querySelectorAll("[name^='detailText_']");
                detailTexts.forEach(textarea => {
                    if (textarea.value.trim() === "") {
                        alert("Detail Text không được để trống hoặc chỉ chứa dấu cách!");
                        isValid = false;
                    }
                });

                if (isNaN(servicePrice) || servicePrice <= 0) {
                    document.getElementById("priceError").style.display = "block";
                    isValid = false;
                }

                if (salePrice !== null) {
                    if (salePrice < 0 || salePrice >= servicePrice) {
                        document.getElementById("salePriceError").style.display = "block";
                        isValid = false;
                    }
                }

                if (imageURL !== "" && !isValidURL(imageURL)) {
                    alert("Image URL không hợp lệ. Vui lòng nhập URL đúng định dạng!");
                    isValid = false;
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