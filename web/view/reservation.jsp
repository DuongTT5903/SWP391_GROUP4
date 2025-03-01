<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Reservation</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css">
    <script src="https://kit.fontawesome.com/bf043842f3.js" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
</head>
<body class="bg-light">
    
<div class="container my-5">
    <h2 class="text-center mb-4">Đặt Chỗ</h2>

    <form action="reservation" method="GET" class="row g-3">
        <div class="col-md-4">
            <select name="category" class="form-select">
                <option value="0">Tất cả loại</option>
                <option value="1">Nha Khoa</option>
                <option value="2">Y tế tổng quát</option>
                <option value="3">Tiêm chủng</option>
            </select>
        </div>
        <div class="col-md-6">
            <div class="input-group">
                <input type="text" name="search" class="form-control" placeholder="Tìm kiếm..." value="">
                <button class="btn btn-primary">
                    <i class="fa-solid fa-magnifying-glass"></i>
                </button>
            </div>
        </div>
    </form>

    <table class="table table-striped table-bordered mt-4">
        <thead class="table-dark">
            <tr>
                <th>#</th>
                <th>Tên dịch vụ</th>
                <th>Giá</th>
                <th>Số lượng</th>
                <th>Số người</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="s" items="${cartItems}" varStatus="status">
                <tr>
                    <td>${status.index + 1}</td>
                    <td>${s.service.serviceName}</td>
                    <td>${(s.service.servicePrice * (100 - s.service.salePrice) / 100)}00 VNĐ</td>
                    <td>${s.amount}</td>
                    <td>${s.numberOfPerson}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <nav class="mt-4">
        <ul class="pagination justify-content-center">
            <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                <a class="page-link" href="${pageContext.request.contextPath}/reservation?page=1&search=&category=0">« Trang trước</a>
            </li>
            <li class="page-item">
                <a class="page-link" href="${pageContext.request.contextPath}/reservation?page=2&search=&category=0">Trang sau »</a>
            </li>
        </ul>
    </nav>

    <form action="reservation" method="POST" class="mt-4">
        <div class="row g-3">
            <div class="col-md-6">
                <label for="name" class="form-label">Họ và tên:</label>
                <input type="text" id="name" name="name" class="form-control" minlength="6" maxlength="50" required>
            </div>

            <div class="col-md-6">
                <label for="address" class="form-label">Địa chỉ:</label>
                <input type="text" id="address" name="address" class="form-control" minlength="40" maxlength="200" required>
            </div>

            <div class="col-md-6">
                <label for="email" class="form-label">Email:</label>
                <input type="email" id="email" name="email" class="form-control">
            </div>

            <div class="col-md-6">
                <label for="phone" class="form-label">Số điện thoại:</label>
                <input type="tel" id="phone" name="phone" class="form-control" pattern="[0-9]{3}-[0-9]{3}-[0-9]{4}" placeholder="123-456-7890">
            </div>

            <div class="col-md-6">
                <label for="gender" class="form-label">Giới tính:</label>
                <select class="form-select" name="gender">
                    <option value="0">Nam</option>
                    <option value="1">Nữ</option>
                </select>
            </div>

            <div class="col-md-12">
                <label for="description" class="form-label">Ghi chú:</label>
                <textarea id="description" name="description" class="form-control" rows="3"></textarea>
            </div>

            <div class="col-md-12">
                <label class="form-label">Phương thức thanh toán:</label>
                <div class="btn-group w-100">
                    <button type="button" class="btn btn-outline-primary" onclick="selectPayment('creditOrDebitCard')">Thẻ tín dụng</button>
                    <button type="button" class="btn btn-outline-secondary" onclick="selectPayment('bankApp')">Bank App</button>
                </div>
                <input type="hidden" id="payment" name="payment">
            </div>

            <div class="col-md-12 mt-3" id="cardDetails" style="display: none;">
                <div class="row g-3">
                    <div class="col-md-6">
                        <label for="cardName" class="form-label">Tên chủ thẻ:</label>
                        <input type="text" id="cardName" name="cardName" class="form-control" minlength="6" maxlength="50">
                    </div>

                    <div class="col-md-6">
                        <label for="cardNumber" class="form-label">Số thẻ:</label>
                        <input type="text" id="cardNumber" name="cardNumber" class="form-control" minlength="13" maxlength="19">
                    </div>

                    <div class="col-md-6">
                        <label for="CVV" class="form-label">CVV:</label>
                        <input type="text" id="CVV" name="CVV" class="form-control" pattern="\d{3,4}" title="3-4 digits">
                    </div>

                    <div class="col-md-6">
                        <label for="expirationDate" class="form-label">Ngày hết hạn:</label>
                        <input type="date" id="expirationDate" name="expirationDate" class="form-control">
                    </div>
                </div>
            </div>

            <div class="col-md-12 text-center mt-4">
                <button type="submit" class="btn btn-success">Thanh toán</button>
            </div>
        </div>
    </form>
</div>

<script>
    function selectPayment(method) {
        document.getElementById("payment").value = method;
        document.getElementById("cardDetails").style.display = (method === "creditOrDebitCard") ? "block" : "none";
    }
</script>

</body>
</html>
