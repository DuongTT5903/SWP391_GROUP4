<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="utf-8" />
        <title>Feedback</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="Premium Bootstrap 4 Landing Page Template" />
        <meta name="keywords" content="Appointment, Booking, System, Dashboard, Health" />
        <meta name="author" content="Shreethemes" />
        <meta name="email" content="support@shreethemes.in" />
        <meta name="website" content="https://shreethemes.in" />
        <meta name="Version" content="v1.2.0" />
        <!-- favicon -->
        <link rel="shortcut icon" href="<%= request.getContextPath() %>/assets/images/favicon.ico.png">
        <!-- Bootstrap -->
        <link href="<%= request.getContextPath() %>/assets/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
        <!-- Icons -->
        <link href="<%= request.getContextPath() %>/assets/css/materialdesignicons.min.css" rel="stylesheet" type="text/css" />
        <link href="<%= request.getContextPath() %>/assets/css/remixicon.css" rel="stylesheet" type="text/css" />
        <link href="https://unicons.iconscout.com/release/v3.0.6/css/line.css"  rel="stylesheet">
        <!-- SLIDER -->
        <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/tiny-slider.css"/>
        <!-- Css -->
        <link href="<%= request.getContextPath() %>/assets/css/style.min.css" rel="stylesheet" type="text/css" id="theme-opt" />

    </head>

    <body>
        <!-- Loader -->

        <!-- Loader -->

        <!-- Navbar STart -->
        <jsp:include page="../CustomerNavbar.jsp"/>
        <!-- Navbar End -->

        <!-- Start Hero -->
        <section class="bg-half-150 d-table w-100 bg-light">
            <div class="container">
                <div class="row mt-5 justify-content-center">
                    <div class="col-12">
                        <div class="section-title text-center">
                            <h3 class="sub-title mb-4">Feedback so we can improve our service</h3>
                        </div>
                    </div><!--end col-->
                </div><!--end row-->
            </div><!--end container-->
        </section><!--end section-->
        <div class="position-relative">
            <div class="shape overflow-hidden text-white">
                <svg viewBox="0 0 2880 48" fill="none" xmlns="http://www.w3.org/2000/svg">
                <path d="M0 48H1437.5H2880V0H2160C1442.5 52 720 0 720 0H0V48Z" fill="currentColor"></path>
                </svg>
            </div>
        </div>
        <!-- End Hero -->

        <!-- Start -->
        <section class="section">
            <div class="container">
                <div class="row">
                    <div class="col-lg-12 col-lg-7">
                        <!-- Feedback List & Pagination (GET) -->
                        <form action="${pageContext.request.contextPath}/customer/service/serviceFeedBack" method="get">
                            <input type="hidden" name="service_id" value="${service_id}">
                            <h5 class="card-title mt-4 mb-0">Comments :</h5>

                            <ul class="media-list list-unstyled mb-0">
                                <c:forEach var="feed" items="${requestScope.allfeedback}">
                                    <li class="mt-4">
                                        <div class="d-flex justify-content-between">
                                            <div class="d-flex align-items-center">
                                                <a class="pe-3" href="#">
                                                    <img src="${feed.userImage}" class="img-fluid avatar avatar-md-sm rounded-circle shadow" alt="img">
                                                </a>
                                                <div class="commentor-detail">
                                                    <h6 class="mb-0">
                                                        <a href="javascript:void(0)" class="text-dark media-heading">${feed.name}</a>
                                                    </h6>
                                                    <small class="text-muted">${feed.createdDate}</small>

                                                    <!-- Rating Stars -->
                                                    <ul class="list-inline mb-0">
                                                        <c:if test="${feed.rateStar != 0}">
                                                        <c:forEach var="i" begin="1" end="5">
                                                            <li class="list-inline-item">
                                                                <i class="mdi ${i <= feed.rateStar ? 'mdi-star' : 'mdi-star-outline'}"></i>
                                                            </li>
                                                        </c:forEach>
                                                        </c:if>
                                                    </ul>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="mt-3">
                                            <p class="text-muted font-italic p-3 bg-light rounded">
                                                ${feed.content}
                                            </p>
                                        </div>
                                    </li>
                                </c:forEach>
                            </ul>

                            <!-- Pagination -->
                            <div class="d-md-flex align-items-center text-center justify-content-between" style="margin-top: 25px">
                                <span class="text-muted me-3">Showing ${pageNo * pageSize + 1}
                                    - ${totalElements.intValue() < ((pageNo + 1) * pageSize) ? totalElements.intValue() : ((pageNo + 1) * pageSize)} out of
                                    ${totalElements.intValue()}</span>
                                <ul class="pagination justify-content-center mb-0 mt-3 mt-sm-0">
                                    <c:set var="totalPages" value="${(totalElements + pageSize - 1) div pageSize}" />
                                    <li class="page-item ${pageNo <= 0 ? 'disabled' : ''}">
                                        <button type="submit" name="pageNo" value="0" class="page-link">First</button>
                                    </li>
                                    <c:if test="${pageNo >= 1}">
                                        <li class="page-item">
                                            <button type="submit" name="pageNo" value="${pageNo - 1}" class="page-link">${pageNo}</button>
                                        </li>
                                    </c:if>
                                    <li class="page-item active">
                                        <button type="submit" name="pageNo" value="${pageNo}" class="page-link" disabled>${pageNo + 1}</button>
                                    </li>
                                    <c:if test="${(pageNo + 1).intValue() < totalPages.intValue()}">
                                        <li class="page-item">
                                            <button type="submit" name="pageNo" value="${pageNo + 1}" class="page-link">${pageNo + 2}</button>
                                        </li>
                                    </c:if>
                                    <li class="page-item ${pageNo + 1 >= totalPages.intValue() ? 'disabled' : ''}">
                                        <button type="submit" name="pageNo" value="${(totalPages - 1).intValue()}" class="page-link">Last</button>
                                    </li>
                                </ul>
                            </div>
                        </form>

                        <!-- Feedback Submission Form (POST) -->
                        <form action="${pageContext.request.contextPath}/customer/service/updateRating" method="post" onsubmit="return validateFeedback()">
                            <h5 class="card-title mt-4 mb-0">Leave A Comment :</h5>

                            <div class="mt-3">
                                <div class="row">
                                    <div class="col-md-12">
                                        <c:if test="${not empty loggedin}">
                                            <p class="text-danger">Please Login/Register to access feedback</p>
                                        </c:if>

                                        <c:if test="${empty loggedin and empty purchased}">
                                            <p class="text-danger">Please purchase the service before leaving feedback</p>
                                        </c:if>

                                        <c:if test="${not empty purchased}">
                                            <input type="hidden" name="service_id" value="${service_id}">

                                            <!-- Comment Input -->
                                            <div class="mb-3">
                                                <label class="form-label">Your Comment</label>
                                                <textarea id="message" placeholder="Your Comment" rows="5" name="message" class="form-control"></textarea>
                                            </div>

                                            <!-- Rating Dropdown -->
                                            <div class="col-md-12 d-flex justify-content-between align-items-center">
                                                <div class="rate-product">
                                                    <select class="form-select" name="rateStar" id="rateStar">
                                                        <option value="-1" selected>Rate Product</option>
                                                        <option value="5">⭐⭐⭐⭐⭐ (5 Stars)</option>
                                                        <option value="4">⭐⭐⭐⭐ (4 Stars)</option>
                                                        <option value="3">⭐⭐⭐ (3 Stars)</option>
                                                        <option value="2">⭐⭐ (2 Stars)</option>
                                                        <option value="1">⭐ (1 Star)</option>
                                                    </select>
                                                </div>

                                                <!-- Submit Button -->
                                                <div class="send">
                                                    <button type="submit" class="btn btn-primary">Submit Feedback</button>
                                                </div>
                                            </div>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                        </form>

                    </div><!--end col-->


                </div><!--end row-->
            </div><!--end container-->
            <!-- Notification Modal -->
            <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header border-bottom p-3">
                            <h5 class="modal-title" id="exampleModalLabel">Notification</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            Please enter a comment or select a rating.
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        </div>
                    </div>
                </div>
            </div>

        </section><!--end section-->
        <!-- End -->

        <!-- Start -->
        <jsp:include page="../Footer.jsp"/>
        <!-- End -->

        <!-- Back to top -->
        <a href="#" onclick="topFunction()" id="back-to-top" class="btn btn-icon btn-pills btn-primary back-to-top"><i data-feather="arrow-up" class="icons"></i></a>
        <!-- Back to top -->


        <!-- javascript -->
        <script src="<%= request.getContextPath() %>/assets/js/bootstrap.bundle.min.js"></script>
        <!-- SLIDER -->
        <script src="<%= request.getContextPath() %>/assets/js/tiny-slider.js"></script>
        <script src="<%= request.getContextPath() %>/assets/js/tiny-slider-init.js"></script>
        <!-- Icons -->
        <script src="<%= request.getContextPath() %>/assets/js/feather.min.js"></script>
        <!-- Main Js -->
        <script src="<%= request.getContextPath() %>/assets/js/app.js"></script>

        <script>
            function validateFeedback() {
                var message = document.getElementById("message").value.trim();
                var rateStar = document.getElementById("rateStar").value;

                if (message === "" && rateStar === "-1") {
                    var modal = new bootstrap.Modal(document.getElementById('exampleModal'));
                    modal.show(); // Show the Bootstrap modal
                    return false; // Prevent form submission
                }
                return true; // Allow form submission
            }
        </script>

    </body>

</html>