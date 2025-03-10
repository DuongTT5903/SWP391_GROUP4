<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Reset Password</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css">
    </head>
    <body>
        <section class="h-100 d-flex align-items-center justify-content-center">
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-xxl-4 col-xl-5 col-lg-5 col-md-7 col-sm-9">
                        <div class="card shadow-lg">
                            <div class="card-body p-5">
                                <h3 class="fw-bold mb-4 text-center">Send to Email</h3>
                                <form action="request" method="POST" class="needs-validation" novalidate>
                                    <div class="mb-3">
                                        <label for="email" class="form-label">Email</label>
                                        <input type="email" class="form-control" name="email" id="email" placeholder="name@example.com" required>
                                        <div class="invalid-feedback">Vui lòng nhập email hợp lệ</div>
                                    </div>
                                    <div class="d-grid">
                                        <button class="btn btn-primary" type="submit">Reset Password</button>
                                    </div>
                                </form>
                                <p class="text-danger text-center mt-3">${mess}</p>
                            </div>
                        </div>
                        <div class="d-flex justify-content-center mt-3">
                            <a href="${pageContext.request.contextPath}/homepage" class="btn btn-outline-primary px-4 py-2 fw-bold rounded-pill">
                                ⬅ Back to Home
                            </a>
                        </div>
                        <div class="text-center mt-5 text-muted">
                            ChildrenCare &copy; VÌ SỨC KHỎE
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
