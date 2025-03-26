<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Manager List Service</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
        <style>
            .action-buttons {
                display: flex;
                gap: 5px;
            }
        </style>
    </head>

    <body class="bg-light">
        <jsp:include page="/manager/headermanager.jsp" />
        <div class="container py-5">
            <div class="row justify-content-center">
                <div class="col-md-12">
                    <div class="card shadow-sm">
                        <div class="card-header bg-primary text-white">
                            <h2 class="mb-0">Manager List Service</h2>
                        </div>
                        <div class="card-body">
                            <!-- Thông báo -->
                            <c:if test="${not empty param.message}">
                                <div class="alert alert-success text-center" role="alert">
                                    ${param.message}
                                </div>
                            </c:if>

                            <!-- Form tìm kiếm -->
                            <form action="${pageContext.request.contextPath}/manager/listservice" method="get" class="mb-3">
                                <div class="input-group">
                                    <span class="input-group-text">Search</span>
                                    <input type="text" class="form-control" id="searchInput" name="search" 
                                           placeholder="Enter Title or Detail" value="${search}">
                                    <button type="submit" class="btn btn-primary">Search</button>
                                </div>
                            </form>

                            <!-- Bộ lọc theo danh mục -->
                            <form action="${pageContext.request.contextPath}/manager/listservice" method="get" class="mb-3">
                                <div class="input-group">
                                    <span class="input-group-text">Category</span>
                                    <select name="categoryID" class="form-select" onchange="this.form.submit()">
                                        <option value="0" ${categoryID == 0 ? 'selected' : ''}>All Categories</option>
                                        <c:forEach var="category" items="${categories}">
                                            <option value="${category.categoryID}" ${categoryID == category.categoryID ? 'selected' : ''}>
                                                ${category.categoryName}
                                            </option>
                                        </c:forEach>
                                    </select>
                                    <input type="hidden" name="search" value="${search}">
                                </div>
                            </form>

                            <!-- Bảng danh sách dịch vụ -->
                            <div class="table-responsive">
                                <table class="table table-bordered table-hover" id="serviceTable">
                                    <thead class="table-light">
                                        <tr>
                                            <th>ID</th>
                                            <th>Title</th>
                                            <th>Detail</th>
                                            <th>Category</th>
                                            <th onclick="sortTable()" style="cursor: pointer;">Service Price <span id="sortIcon">🔽</span></th>
                                            <th>Sale Off</th>
                                            <th>Image</th>
                                            <th>Status</th>
                                            <th>Actions</th>
                                            <th>Delete</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="service" items="${list}">
                                            <tr class="service-row" data-status="${service.status ? 'active' : 'inactive'}">
                                                <td>${service.serviceID}</td>
                                                <td>${service.serviceName}</td>
                                                <td>${service.serviceDetail}</td>
                                                <td>${service.category.categoryName}</td>
                                                <td class="service-price">${service.servicePrice}</td>
                                                <td>${service.salePrice}</td>

                                                <!-- Hiển thị nhiều hình ảnh -->
                                                <td>
                                                    <c:forEach var="img" items="${listImg}">
                                                        <c:if test="${img.serviceID == service.serviceID}">
                                                            <img src="${img.imageURL}" alt="Service Image" class="img-fluid" style="max-width: 50px; max-height: 50px; margin: 2px;">
                                                        </c:if>
                                                    </c:forEach>
                                                </td>
                                                <td>
                                                    <form action="${pageContext.request.contextPath}/manager/listservice" method="get" class="mb-0">
                                                        <input type="hidden" name="service" value="editStatus">
                                                        <input type="hidden" name="serviceID" value="${service.serviceID}">
                                                        <select name="editStatus" class="form-select" onchange="this.form.submit()">
                                                            <option value="true" ${service.status ? "selected" : ""}>Active</option>
                                                            <option value="false" ${!service.status ? "selected" : ""}>Inactive</option>
                                                        </select>
                                                    </form>
                                                </td>
                                                <td>
                                                    <div class="action-buttons">
                                                        <!-- Sửa nút Edit để điều hướng đến trang chỉnh sửa -->
                                                        <form action="${pageContext.request.contextPath}/manager/listservice" method="get" class="mb-0">
                                                            <input type="hidden" name="serviceID" value="${service.serviceID}">
                                                            <input type="hidden" name="service" value="viewDetail"> <!-- Giữ nguyên để khớp với servlet -->
                                                            <button type="submit" class="btn btn-sm btn-info">Edit</button>
                                                        </form>
                                                        <form action="${pageContext.request.contextPath}/manager/listservice" method="get" class="mb-0">
                                                            <input type="hidden" name="serviceID" value="${service.serviceID}">
                                                            <input type="hidden" name="service" value="view">
                                                            <button type="submit" class="btn btn-sm btn-success">View</button>
                                                        </form>
                                                    </div>
                                                </td>
                                                <td>
                                                    <form action="${pageContext.request.contextPath}/manager/listservice" method="get" class="mb-0" onsubmit="return confirmDelete()">
                                                        <input type="hidden" name="service" value="delete">
                                                        <input type="hidden" name="serviceID" value="${service.serviceID}">
                                                        <button type="submit" class="btn btn-sm btn-danger">Delete</button>
                                                    </form>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>

                            <!-- Phân trang -->
                            <nav aria-label="Page navigation" class="mt-3">
                                <ul class="pagination justify-content-center">
                                    <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                                        <a class="page-link" href="${pageContext.request.contextPath}/manager/listservice?page=${currentPage - 1}&search=${search}&categoryID=${categoryID}" 
                                           aria-label="Previous">
                                            <span aria-hidden="true">« Previous</span>
                                        </a>
                                    </li>
                                    <c:forEach begin="1" end="${totalPages}" var="i">
                                        <li class="page-item ${currentPage == i ? 'active' : ''}">
                                            <a class="page-link" href="${pageContext.request.contextPath}/manager/listservice?page=${i}&search=${search}&categoryID=${categoryID}">${i}</a>
                                        </li>
                                    </c:forEach>
                                    <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                                        <a class="page-link" href="${pageContext.request.contextPath}/manager/listservice?page=${currentPage + 1}&search=${search}&categoryID=${categoryID}" 
                                           aria-label="Next">
                                            <span aria-hidden="true">Next »</span>
                                        </a>
                                    </li>
                                </ul>
                            </nav>

                            <!-- Nút điều hướng -->
                            <div class="d-flex justify-content-between mt-3">
                                <form action="${pageContext.request.contextPath}/manager/listservice" method="get" class="mb-0">
                                    <input type="hidden" name="service" value="addRequest">
                                    <button type="submit" class="btn btn-success">Add New Service</button>
                                </form>
                                <a href="${pageContext.request.contextPath}/homepage" class="btn btn-secondary">Back to Home</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script>
            let ascending = true;
            function sortTable() {
                let table = document.getElementById("serviceTable");
                let rows = Array.from(table.getElementsByClassName("service-row"));
                let icon = document.getElementById("sortIcon");

                rows.sort((a, b) => {
                    let priceA = parseFloat(a.querySelector(".service-price").innerText);
                    let priceB = parseFloat(b.querySelector(".service-price").innerText);
                    return ascending ? priceA - priceB : priceB - priceA;
                });

                ascending = !ascending;
                icon.textContent = ascending ? "🔽" : "🔼";

                rows.forEach(row => table.querySelector("tbody").appendChild(row));
            }

            function confirmDelete() {
                return confirm("Are you sure you want to delete this service?");
            }
        </script>
    </body>
</html>