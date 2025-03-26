<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>View Service</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
        <style>
            .detail-item { border: 1px solid #ddd; padding: 10px; margin-bottom: 10px; }
        </style>
    </head>
    
    <body class="bg-light">
        <jsp:include page="/manager/headermanager.jsp" />
        <div class="container py-5">
            <div class="row justify-content-center">
                <div class="col-md-8">
                    <div class="card shadow-sm">
                        <div class="card-header bg-info text-white">
                            <h2 class="mb-0">View Service</h2>
                        </div>
                        <div class="card-body">
                            <div class="mb-3">
                                <label class="form-label"><strong>Service ID:</strong></label>
                                <p>${service.serviceID}</p>
                            </div>

                            <div class="mb-3">
                                <label class="form-label"><strong>Service Name:</strong></label>
                                <p>${service.serviceName}</p>
                            </div>

                            <div class="mb-3">
                                <label class="form-label"><strong>Service Detail:</strong></label>
                                <p>${service.serviceDetail}</p>
                            </div>

                            <div class="mb-3">
                                <label class="form-label"><strong>Detail Images:</strong></label>
                                <c:choose>
                                    <c:when test="${not empty detailImages}">
                                        <c:forEach var="img" items="${detailImages}">
                                            <div class="detail-item">
                                                <img src="${img.imageURL}" alt="Detail Image" class="img-fluid" style="max-height: 100px;">
                                            </div>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <p>No detail images available</p>
                                    </c:otherwise>
                                </c:choose>
                            </div>

                            <div class="mb-3">
                                <label class="form-label"><strong>Category:</strong></label>
                                <p>${service.category.categoryName}</p>
                            </div>

                            <div class="mb-3">
                                <label class="form-label"><strong>Service Price:</strong></label>
                                <p>${service.servicePrice}</p>
                            </div>

                            <div class="mb-3">
                                <label class="form-label"><strong>Sale Price:</strong></label>
                                <p>${service.salePrice}</p>
                            </div>

                            <div class="mb-3">
                                <label class="form-label"><strong>Main Image:</strong></label>
                                <p>${service.imageURL}</p>
                                <img src="${service.imageURL}" alt="Service Image" class="img-fluid mt-2" style="max-height: 200px;">
                            </div>

                            <div class="mb-3">
                                <label class="form-label"><strong>Status:</strong></label>
                                <p>${service.status ? 'Active' : 'Inactive'}</p>
                            </div>

                            <div class="d-flex justify-content-between">
                                <a href="${pageContext.request.contextPath}/manager/listservice?service=listservice" 
                                   class="btn btn-secondary">Back to List</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>