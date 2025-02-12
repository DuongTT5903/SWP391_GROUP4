<%-- 
    Document   : register
    Created on : Mar 2, 2022, 3:22:46 PM
    Author     : Sown
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="author" content="Muhamad Nauval Azhar">
        <meta name="viewport" content="width=device-width,initial-scale=1">
        <meta name="description" content="This is a login page template based on Bootstrap 5">
        <title>Đăng ký</title>

        <link rel=" icon" href="img/icon.png" type="image/icon type" >
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous">
    </head>

    <body>
        <section class="h-100">
            <div class="container h-100">
                <div class="row justify-content-sm-center h-100">
                    <div class="col-xxl-4 col-xl-5 col-lg-5 col-md-7 col-sm-9">

                        <div class="card shadow-lg">
                            <div class="card-body p-5">

                                <h1 class="fs-4 card-title fw-bold mb-4">Sign up</h1>
                                <form action="register" method="POST" class="needs-validation">
                                    <div class="mb-3">
                                        <label class="mb-2 text-muted" for="fullname">Họ tên</label>
                                        <input id="name" type="text" class="form-control" name="fullname" value="" required autofocus>
                                    </div>
                                    <div class="mb-3">
                                        <label class="mb-2 text-muted" for="gender">Giới tính</label>
                                        <select class="form-select" aria-label="gender" id="gender" name="gender">
                                            <option selected value="1">Nam</option>
                                            <option value="2">Nữ</option>
                                        </select>

                                    </div>

                                    <div class="mb-3">
                                        <label class="mb-2 text-muted" for="email">E-Mail</label>
                                        <input id="email" type="email" class="form-control" name="email" value="" required>
                                    </div>
                                    <div class="mb-3">
                                        <label class="mb-2 text-muted" for="phone">Số điện thoại - Định dạng 0xxxxxxxxx </label>
                                        <input id="email" type="tel" class="form-control" name="phone" value="" required pattern="[0]{1}[0-9]{2}[0-9]{3}[0-9]{4}">
                                    </div>
                                    <div class="mb-3">
                                        <label class="mb-2 text-muted" for="user">Tên tài khoản</label>
                                        <input id="name" type="text" class="form-control" name="user" required autofocus>
                                    </div>
                                                                        <div class="mb-3">
                                                                            <label class="mb-2 text-muted" for="pass">Mật khẩu</label>
                                                                            <input id="pass" type="password" class="form-control" name="pass" required autofocus>
                                                                        </div>
                                                                        <div class="mb-3">
                                                                            <label class="mb-2 text-muted" for="repass">Nhập lại mật khẩu: </label>
                                                                            <input id="repass" type="password" class="form-control" name="repass" required autofocus>
                                                                        </div>


                                                                    <p class="form-text text-muted mb-3">
                                                                            By registering you agree with our terms and condition.
                                                                        </p>

                                    <div class="align-items-center d-flex">
                                        <button type="submit" class="btn btn-primary ms-auto" style="background-color: #00c4cc; border-color: #00c4cc" >
                                            Đăng ký	
                                        </button>
                                    </div>
                                </form>
                            </div>

                            <div class="card-footer py-3 border-0 text-center" style="color: red; font-weight: bold;">
                                <label  class="">${e}</label>
                                   <label  class="">${message}</label>
                            </div>


                            <div class="card-footer py-3 border-0">
                                <div class="text-center">
                                    Đã có tài khoản? <a href="login" class="text-dark">Đăng nhập</a>
                                </div>
                            </div>
                        </div>
                        <div class="text-center mt-5 text-muted">
                            ChildrenCare &copy; VÌ SỨC KHỎE
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <script src="js/login.js"></script>
    </body>
</html>
