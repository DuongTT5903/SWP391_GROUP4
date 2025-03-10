<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Reset Password</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous">
    </head>
    <body class="bg-primary">
        <section class="h-100">
            <div class="container h-100">
                <div class="row justify-content-sm-center h-100">
                    <div class="col-xxl-4 col-xl-5 col-lg-5 col-md-7 col-sm-9">
                        <div class="card shadow-lg" style="margin-top: 170px">
                            <div class="card-body p-5">
                                <h3 class="fs-4 card-title fw-bold mb-4">Reset Password</h3>
                                <form action="reset" method="POST" class="needs-validation" autocomplete="off">
                                    <div class="mb-3">
                                        <label class="mb-2 text-muted" for="email">Email</label>
                                        <input type="email" class="form-control" name="email" id="email" value="${email}" required>
                                    </div>
                                    <div class="mb-3">
                                        <label class="mb-2 text-muted" for="password">Password</label>
                                        <input type="password" class="form-control" name="password" id="password" required>
                                    </div>
                                    <div class="mb-3">
                                        <label class="mb-2 text-muted" for="confirm_password">Confirm Password</label>
                                        <input type="password" class="form-control" name="confirm_password" id="confirm_password" required>
                                    </div>
                                    <div class="d-grid">
                                        <button type="submit" class="btn btn-primary">Reset Password</button>
                                    </div>
                                    <p class="text-danger mt-3">${mess}</p>
                                </form>
                            </div>
                        </div>
                        <div class="d-flex justify-content-center mt-3">
                            <a href="${pageContext.request.contextPath}/homepage" class="btn btn-outline-light px-4 py-2 fw-bold rounded-pill">
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
    </body>
</html>
