<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Slider Detail</title>
        <!-- Bootstrap 5 -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            body {
                background-color: #f8f9fa;
            }
            .form-container {
                max-width: 600px;
                margin: 50px auto;
                padding: 20px;
                border-radius: 10px;
                background: #fff;
                box-shadow: 0px 0px 15px rgba(0, 0, 0, 0.1);
            }
            .btn-custom {
                transition: 0.3s;
            }
            .btn-custom:hover {
                transform: scale(1.05);
            }
            .form-check-input {
                width: 20px;
                height: 20px;
            }
            .preview-img {
                max-width: 100%;
                border-radius: 5px;
                margin-top: 10px;
                box-shadow: 0px 2px 10px rgba(0, 0, 0, 0.1);
            }
        </style>
    </head>
    <body>

        <div class="container">
            <div class="form-container p-4">
                <h3 class="text-center mb-4">Chi tiết Slider</h3>

                <!-- Form chỉnh sửa slider -->
                <form action="sliderDetail" method="post">
                    <!-- Ẩn slideID -->
                    <input type="hidden" name="id" value="${slider.slideID}" />

                    <div class="mb-3">
                        <label for="title" class="form-label">Tiêu đề:</label>
                        <input type="text" class="form-control" id="title" name="title" value="${slider.title}" required>
                    </div>

                    <div class="mb-3">
                        <label for="backLink" class="form-label">Backlink:</label>
                        <input type="text" class="form-control" id="backLink" name="backLink" value="${slider.backLink}" required>
                    </div>

                    <div class="mb-3">
                        <label for="img" class="form-label">URL Ảnh:</label>
                        <input type="text" class="form-control" id="img" name="img" value="${slider.img}" required>
                    </div>

                    <!-- Hiển thị ảnh mẫu (nếu có) -->
                    <div class="mb-3 text-center">
                        <c:if test="${not empty slider.img}">
                            <img src="${slider.img}" alt="${slider.title}" class="preview-img">
                        </c:if>
                    </div>

                    <div class="form-check mb-3">
                        <input type="checkbox" class="form-check-input" id="status" name="status" <c:if test="${slider.status}">checked</c:if>>
                            <label class="form-check-label" for="status">Hiển thị</label>
                        </div>

                        <!-- Nút thao tác -->
                        <div class="d-flex justify-content-between">
                            <button type="submit" class="btn btn-primary btn-custom">Lưu thay đổi</button>
                            <a href="${pageContext.request.contextPath}/manager/sliderList" class="btn btn-secondary btn-custom">Quay lại</a>
                    </div>
                </form>
            </div>
        </div>

        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

    </body>
</html>
