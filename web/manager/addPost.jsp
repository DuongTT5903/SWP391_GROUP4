<%-- 
    Document   : addPost
    Created on : Feb 24, 2025, 8:54:56 AM
    Author     : yugio
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Add New Post</title>
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
        <style>
            body {
                background-color: #f8f9fa;
                font-family: 'Roboto', sans-serif;
            }
            .add-post-container {
                max-width: 800px;
                margin: 40px auto;
                padding: 20px;
            }
            .card {
                border: none;
                box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
            }
            .card-header {
                background-color: #007bff;
                color: #fff;
                font-size: 24px;
                font-weight: 600;
            }
            .form-label {
                font-weight: 500;
            }
            .btn-primary {
                background-color: #007bff;
                border: none;
            }
            .btn-primary:hover {
                background-color: #0056b3;
            }
        </style>
    </head>
    <body class="container py-4">
        <div class="container add-post-container">
            <div class="card">
                <div class="card-header">
                    Add New Post
                </div>
                <div class="card-body">
                    <form action="AddPost" method="post" enctype="multipart/form-data">
                        <div class="mb-3">
                            <label for="postTitle" class="form-label">Title</label>
                            <input type="text" class="form-control" id="postTitle" name="title" placeholder="Enter title" required>
                        </div>
                        <div class="mb-3">
                            <label for="postContent" class="form-label">Content</label>
                            <textarea class="form-control" id="postContent" name="content" rows="8" placeholder="Write your content here..." required></textarea>
                        </div>
                        <div class="mb-3">
                            <label for="postImage" class="form-label">Image Link</label>
                            <input type="text" id="imageLink" name="imageLink" class="form-control" placeholder="Enter image URL">
<!--                            <input class="form-control" type="file" id="postImage" name="image">-->
                        </div>
                        <button type="submit" class="btn btn-primary">Submit Post</button>
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
<!--        <div class="mb-3">
            <label for="imageLink" class="form-label">Image Link</label>
            <input type="text" id="imageLink" name="imageLink" class="form-control" placeholder="Enter image URL">
        </div>-->
    </body>
</html>

