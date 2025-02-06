<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="author" content="Muhamad Nauval Azhar">
        <meta name="viewport" content="width=device-width,initial-scale=1">
        <meta name="description" content="This is a login page template based on Bootstrap 5">
        <title>Lấy lại mật khẩu</title>
        
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

                                <h1 class="fs-4 card-title fw-bold mb-4">Lấy lại mật khẩu</h1>
                                <form action="reset" method="POST" class="needs-validation">
                                    <div class="mb-3">

                                        
                                        <label class="mb-2 text-muted" for="email">Vui lòng nhập email:</label>
                                        <input id="email" type="email" class="form-control" name="email" value="" required autofocus>

                                    </div>
                                    
                                    


                                    <div class="align-items-center d-flex">
                                        <button type="submit" class="btn btn-primary ms-auto" style="background-color: #00c4cc; border-color: #00c4cc" >
                                    Lấy lại mật khẩu
                                        </button>
                                    </div>
                                    
                                </form>
                            </div>

                            <div class="card-footer py-3 border-0 text-center" style="color: red; font-weight: bold;">
                                <label  class="">${e}</label>
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