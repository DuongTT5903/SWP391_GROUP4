<%-- 
    Document   : service_staff
    Created on : Feb 4, 2025, 10:50:48 PM
    Author     : trung
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="icon" type="image/png" href="image/favicon-16x16.png">
        <title>Service List</title>
        <link rel="stylesheet" href="style.css">
        <script src="https://kit.fontawesome.com/bf043842f3.js" crossorigin="anonymous"></script>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </head>

    <body>
        <div class="container my-4">
            <a class="btn btn-secondary" href="/homepage"><b>Homepage</b></a>

            <div class="navbar row" style="display: flex;">
                <div class="input-group mb-3 col-md-6" style="width: 300px;">
                    <input type="text" class="form-control" placeholder="Search" aria-label="Search">
                    <button class="btn btn-primary" type="button">
                        <i class="fa-solid fa-magnifying-glass"></i>
                    </button>
                </div>
                <button class="btn btn-success mb-3 col-md-6" style="width: 100px; margin-right: 12px;" type="button"
                        data-bs-toggle="modal" data-bs-target="#addModal">
                    <i class="fa-solid fa-plus"></i> Add
                </button>
            </div>

            <!-- Add Service Modal -->
            <div class="modal" id="addModal">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <form>
                            <div class="modal-header">
                                <h4 class="modal-title">Add Service</h4>
                                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                            </div>

                            <div class="modal-body">
                                <label for="addService">Service:</label>
                                <input type="text" id="addService" class="form-control" minlength="6" maxlength="40" required>

                                <label for="addServiceDetail">Service Detail:</label>
                                <input type="text" id="addServiceDetail" class="form-control" minlength="6" maxlength="80" required>

                                <label for="addCategory">Category:</label>
                                <select id="addCategory" class="form-control">
                                    <option value="nhaKhoa">Nha khoa</option>
                                    <option value="yTeTongQuat">Y tế tổng quát</option>
                                    <option value="tiemChung">Tiêm chủng</option>
                                </select>

                                <label for="addPrice">Price:</label>
                                <input type="number" id="addPrice" class="form-control" min="100000" max="5000000" value="100000">

                                <label for="addSalePrice">Sale:</label>
                                <input type="number" id="addSalePrice" class="form-control" min="0" max="90" value="0">%

                                <label for="addImage">Image:</label>
                                <input type="file" id="addImage" class="form-control" accept="image/png, image/jpeg">
                            </div>

                            <div class="modal-footer">
                                <button type="button" class="btn btn-danger" data-bs-dismiss="modal">Close</button>
                                <button type="submit" class="btn btn-success">Add</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>

            <!-- Update Service Modal -->
            <div class="modal" id="updateModal">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <form>
                            <div class="modal-header">
                                <h4 class="modal-title">Update Service</h4>
                                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                            </div>

                            <div class="modal-body">
                                <label for="updateService">Service:</label>
                                <input type="text" id="updateService" class="form-control" minlength="6" maxlength="40" required>

                                <label for="updateServiceDetail">Service Detail:</label>
                                <input type="text" id="updateServiceDetail" class="form-control" minlength="6" maxlength="80" required>

                                <label for="updateCategory">Category:</label>
                                <select id="updateCategory" class="form-control">
                                    <option value="nhaKhoa">Nha khoa</option>
                                    <option value="yTeTongQuat">Y tế tổng quát</option>
                                    <option value="tiemChung">Tiêm chủng</option>
                                </select>

                                <label for="updatePrice">Price:</label>
                                <input type="number" id="updatePrice" class="form-control" min="100000" max="5000000" value="100000">

                                <label for="updateSalePrice">Sale:</label>
                                <input type="number" id="updateSalePrice" class="form-control" min="0" max="90" value="0">%

                                <label for="updateImage">Image:</label>
                                <input type="file" id="updateImage" class="form-control" accept="image/png, image/jpeg">
                            </div>

                            <div class="modal-footer">
                                <button type="button" class="btn btn-danger" data-bs-dismiss="modal">Close</button>
                                <button type="submit" class="btn btn-warning">Update</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>

            <!-- Table -->
            <div class="table-responsive">
                <table class="table table-bordered table-striped align-middle">
                    <thead class="table-dark">
                        <tr>
                            <th>ID</th>
                            <th>Service</th>
                            <th>Service Detail</th>
                            <th>Category</th>
                            <th>Price</th>
                            <th>Sale</th>
                            <th>Image</th>
                            <th>Author Email</th>
                            <th>Function</th>
                        </tr>
                    </thead>
                    <tbody>
                       
                    <c:forEach var="service" items="${services}">
                        
                        <tr>
                            <td>${service.serviceID}</td>
                            <td>${service.serviceName}</td>
                            <td>${service.serviceDetail}</td>
                            <td>${service.category.categoryName}</td>
                            <td>${service.servicePrice}</td>
                            <td>${service.salePrice}</td>
                            <td>
                        <c:choose>
                            <c:when test="${not empty service.imageURL}">
                                <img src="${pageContext.request.contextPath}/${service.imageURL}" alt="Service Image" width="50">
                            </c:when>
                            <c:otherwise>
                                <span class="text-muted">[No Image]</span>
                            </c:otherwise>
                        </c:choose>
                        </td>
                        <td>${service.author.email}</td>
                        <td>
                            <form method="post" action="StaffServiceServlet">
                                <input type="hidden" name="id" value="${service.serviceID}">
                                <button type="submit" name="action" value="update" class="btn btn-warning btn-sm" data-bs-toggle="modal" data-bs-target="#updateModal"><i class="fa-solid fa-wrench"></i></button>
                                <button type="submit" name="action" value="delete" class="btn btn-danger btn-sm" onclick="return confirm('Are you sure you want to delete this service?');"><i class="fa-solid fa-x"></i></button>
                            </form>
                        </td>
                        </tr>
                    </c:forEach>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>

    </body>
</html>