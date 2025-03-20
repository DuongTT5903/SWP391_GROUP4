<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Slider Detail</title>
        <!-- Link Bootstrap CSS nếu cần -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    </head>
    <body>
        <div class="container mt-4">
            <h1>Chi tiết Slider</h1>
            <!-- Form chỉnh sửa slider -->
            <form action="sliderDetail" method="post">
                <!-- Ẩn slideID để biết chúng ta đang sửa slider nào -->
                <input type="hidden" name="id" value="${slider.slideID}" />

                <div class="form-group">
                    <label for="title">Tiêu đề:</label>
                    <input type="text" class="form-control" id="title" name="title"
                           value="${slider.title}" required>
                </div>

                <div class="form-group">
                    <label for="backLink">Backlink:</label>
                    <input type="text" class="form-control" id="backLink" name="backLink"
                           value="${slider.backLink}" required>
                </div>

                <div class="form-group">
                    <label for="img">URL Ảnh:</label>
                    <input type="text" class="form-control" id="img" name="img"
                           value="${slider.img}" required>
                </div>

                <!-- Hiển thị ảnh mẫu (nếu có) -->
                <div class="form-group">
                    <c:if test="${not empty slider.img}">
                        <img src="${slider.img}" alt="${slider.title}" style="max-width: 300px;" />
                    </c:if>
                </div>

                <div class="form-group form-check">
                    <input type="checkbox" class="form-check-input" id="status" name="status"
                           <c:if test="${slider.status}">checked</c:if>>
                    <label class="form-check-label" for="status">Hiển thị</label>
                </div>

<!--                <div class="form-group">
                    <label for="notes">Ghi chú:</label>
                    <textarea class="form-control" id="notes" name="notes" rows="3">{slider.notes}</textarea>
                </div>-->

                <button type="submit" class="btn btn-primary">Lưu thay đổi</button>
                <a href="${pageContext.request.contextPath}/manager/sliderList" class="btn btn-secondary">Quay lại</a>
            </form>
        </div>

        <!-- Link Bootstrap JS nếu cần -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </body>
</html>
