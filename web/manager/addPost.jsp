<%-- 
    Document   : addPost
    Created on : Feb 24, 2025, 8:54:56 AM
    Author     : yugio
--%>

<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Add New Post</title>
        <!-- Bootstrap 5 -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
        <!-- Font Awesome -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
        <style>
            body {
                background-color: #f8f9fa;
            }
            .container {
                max-width: 800px;
                background: white;
                padding: 20px;
                border-radius: 10px;
                box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
                margin-top: 30px;
            }
            .form-label {
                font-weight: bold;
            }
            .img-preview {
                width: 100%;
                max-height: 300px;
                object-fit: cover;
                border: 1px solid #ddd;
                border-radius: 5px;
                margin-top: 10px;
                display: none;
            }
        </style>
    </head>
    <body class="container py-4">
        <div class="container add-post-container">
            <div class="card">
                <div class="card-header">
                    <h2 class="text-center mb-4"><i class="fas fa-plus-circle"></i> Add new Post</h2>

                </div>
                <div class="card-body">
                    <form action="addPost" method="post">
                        <!-- Tiêu đề bài viết -->
                        <div class="mb-3">
                            <label for="postTitle" class="form-label">Title</label>
                            <input type="text" class="form-control" id="title" name="title" placeholder="Enter title" required>
                        </div>

                        <!-- Nội dung bài viết -->
                        <div class="mb-3">
                            <label for="postContent" class="form-label">Detail</label>
                            <textarea class="form-control" id="detail" name="detail" rows="8" required></textarea>
                        </div>
                        <!-- Danh mục -->
                        <div class="mb-3">
                            <label for="category" class="form-label">Category</label>
                            <input type="text" class="form-control" id="category" name="category" required>
                        </div>
                        <!--                        <div class="mb-3">
                                                    <label for="postImage" class="form-label">Image Link</label>
                                                    <input type="text" id="imageLink" name="imageLink" class="form-control" placeholder="Enter image URL">
                                                                                <input class="form-control" type="file" id="postImage" name="image">
                                                </div>-->
                        <!-- Nhập URL Hình ảnh -->
                        <div class="mb-3">
                            <label for="imageLink" class="form-label">URL Hình ảnh:</label>
                            <input type="text" class="form-control" id="imageLink" name="imageLink" 
                                   placeholder="Nhập đường dẫn ảnh..." oninput="previewImage()" 
                                   value="<%= request.getParameter("imageLink") != null ? request.getParameter("imageLink") : ""%>">
                            <img id="imgPreview" class="img-preview" src="<%= request.getParameter("imageLink") != null ? request.getParameter("imageLink") : ""%>"
                                 style="<%= request.getParameter("imageLink") != null ? "display: block;" : "display: none;"%>">
                        </div>

                        <!-- Trạng thái -->
                        <div class="mb-3 form-check">
                            <input type="hidden" name="status" value="off">
                            <input type="checkbox" class="form-check-input" id="status" name="status" value="on"
                                   <%= "on".equals(request.getParameter("status")) ? "checked" : ""%>>

                            <label class="form-check-label" for="postStatus">Hiển thị bài viết</label>
                        </div>

                        <div class="text-center">
                            <button type="submit" class="btn btn-success px-4"><i class="fas fa-save"></i> Đăng bài</button>
                            <a href="postList" class="btn btn-secondary px-4"><i class="fas fa-arrow-left"></i> Quay lại</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script>
            // JavaScript để hiển thị thông báo nếu có trường không hợp lệ
            (function () {
                'use strict'
                var forms = document.querySelectorAll('.needs-validation')
                Array.prototype.slice.call(forms)
                        .forEach(function (form) {
                            form.addEventListener('submit', function (event) {
                                if (!form.checkValidity()) {
                                    event.preventDefault()
                                    event.stopPropagation()
                                }
                                form.classList.add('was-validated')
                            }, false)
                        })
            })()
        </script>
        <!-- Bootstrap Script -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script>
            // Xử lý hiển thị ảnh preview khi nhập URL
            function previewImage() {
                let imageUrl = document.getElementById("imageLink").value;
                let imgPreview = document.getElementById("imgPreview");

                if (imageUrl.trim() !== "") {
                    imgPreview.src = imageUrl;
                    imgPreview.style.display = "block";
                } else {
                    imgPreview.style.display = "none";
                }
            }
        </script>
        <!--        <div class="mb-3">
                    <label for="imageLink" class="form-label">Image Link</label>
                    <input type="text" id="imageLink" name="imageLink" class="form-control" placeholder="Enter image URL">
                </div>-->
    </body>
</html>

