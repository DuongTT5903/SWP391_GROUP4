<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>  
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Quản lý Slider</title>
        <!-- Link Bootstrap CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <!-- CSS tùy chỉnh -->
        <style>
            .slider-image {
                width: 100px;
                height: auto;
            }
            .action-btn {
                margin-right: 5px;
            }
        </style>
    </head>
    <body>
        <jsp:include page="./headermanager.jsp" />
        <div class="container mt-4">
            <!-- Phần tìm kiếm và lọc -->
            <form action="sliderList" method="get" class="form-inline mb-3">
                <input type="text" name="search" value="${search}" placeholder="Tìm theo tiêu đề/backlink" class="form-control mr-2">
                <select name="status" class="form-control mr-2">
                    <option value="">Tất cả trạng thái</option>
                    <option value="1" ${status == '1' ? 'selected' : ''}>Hiển thị</option>
                    <option value="0" ${status == '0' ? 'selected' : ''}>Ẩn</option>
                </select>
                <button type="submit" class="btn btn-primary">Tìm kiếm</button>
            </form>

            <!-- Bảng danh sách slider -->
            <table class="table table-bordered table-hover">
                <thead class="thead-light">
                    <tr>
                        <th>ID</th>
                        <th>Tiêu đề</th>
                        <th>Ảnh</th>
                        <th>Backlink</th>
                        <th>Trạng thái</th>
                        <th>Hành động</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="slider" items="${sliders}">
                        <tr>
                            <td>${slider.slideID}</td>
                            <td>${slider.title}</td>
                            <td>
                                <img src="${slider.img}" alt="${slider.title}" class="slider-image">
                            </td>
                            <td>${slider.backLink}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${slider.status}">
                                        Hiển thị
                                    </c:when>
                                    <c:otherwise>
                                        Ẩn
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <!-- Các nút hành động -->
                                <a href="sliderList?action=hide&slideID=${slider.slideID}" class="btn btn-warning btn-sm action-btn">Ẩn</a>
                                <a href="sliderList?action=show&slideID=${slider.slideID}" class="btn btn-success btn-sm action-btn">Hiển thị</a>
                                <a href="sliderDetail?slideID=${slider.slideID}" class="btn btn-info btn-sm action-btn">Sửa</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <!-- Phân trang -->
            <nav aria-label="Trang slider">
                <ul class="pagination">
                    <!-- Giả sử bạn đã set các biến pageIndex, totalPages -->
                    <c:forEach var="i" begin="1" end="${totalPages}">
                        <li class="page-item ${i == pageIndex ? 'active' : ''}">
                            <a class="page-link" href="manager/sliderList?action=list&page=${i}&search=${search}&status=${status}">${i}</a>
                        </li>
                    </c:forEach>
                </ul>
            </nav>
        </div>

        <!-- Link Bootstrap JS và phụ thuộc (jQuery, Popper.js) -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </body>
</html>
