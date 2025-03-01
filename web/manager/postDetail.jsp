<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="model.Post" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="post" scope="request" type="model.Post" />

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Edit Blog Post</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    </head>
    <body class="bg-light">
        <div class="container py-5">
            <div class="row justify-content-center">
                <div class="col-md-8">
                    <div class="card shadow-sm">
                        <div class="card-header bg-primary text-white">
                            <h2 class="mb-0">Edit Blog Post</h2>
                        </div>
                        <div class="card-body">
                            <form action="${pageContext.request.contextPath}/manager/postDetail" method="post">
                                <input type="hidden" name="id" value="${post.id}">
                                
                                <div class="mb-3">
                                    <label for="title" class="form-label">Title</label>
                                    <input type="text" class="form-control" id="title" name="title" value="${post.title}" required>
                                </div>
                                
                                <div class="mb-3">
                                    <label for="detail" class="form-label">Content</label>
                                    <textarea class="form-control" id="detail" name="detail" rows="7" required>${post.detail}</textarea>
                                </div>
                                
                                <div class="mb-3">
                                    <label for="category" class="form-label">Category</label>
                                    <input type="text" class="form-control" id="category" name="category" value="${post.category}" required>
                                </div>
                                
                                <div class="mb-3">
                                    <label for="imageLink" class="form-label">Image URL</label>
                                    <input type="text" class="form-control" id="imageLink" name="imageLink" value="${post.imageLink}" required>
                                </div>
                                
                                <div class="mb-3 form-check">
                                    <input type="checkbox" class="form-check-input" id="status" name="status"
                                        <c:if test="${post.status}">checked</c:if>>
                                    <label class="form-check-label" for="status">Active</label>
                                </div>
                                
                                <div class="d-flex justify-content-between">
                                    <button type="submit" class="btn btn-primary">Save Changes</button>
                                    <a href="${pageContext.request.contextPath}/manager/postList" class="btn btn-secondary">Cancel</a>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
