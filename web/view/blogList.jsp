<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List, model.Post" %>
<jsp:useBean id="posts" scope="request" type="java.util.List<model.Post>" />

<!DOCTYPE html>
<html>
    <head>
        <title>Manage Posts</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="styles.css">
    <jsp:include page="/view/head.jsp" />
    </head>
    <body class="container py-4">
         
        <h1 class="mb-4 text-center">Danh sách bài viết</h1>

        <!-- FORM LỌC BÀI VIẾT -->
        <form action="blogList" method="GET" class="row g-3 mb-4">
            <div class="col-md-3">
                <label for="search" class="form-label">Tìm kiếm:</label>
                <input type="text" class="form-control" name="search" id="search" 
                       value="<%= request.getParameter("search") != null ? request.getParameter("search") : ""%>">
            </div>
            <div class="col-md-3">
                <label for="category" class="form-label">Danh mục:</label>
                <select class="form-select" name="category" id="category">
                    <option value="">Tất cả</option>
                    <% List<String> categories = (List<String>) request.getAttribute("categories");
                        String selectedCategory = request.getParameter("category");
                        if (categories != null) {
                            for (String category : categories) { 
                                if (category != null) { %>
                                    <option value="<%= category%>" 
                                            <%= (selectedCategory != null && selectedCategory.equals(category)) ? "selected" : ""%>>
                                        <%= category%>
                                    </option>
                    <% } } } %>
                </select>
            </div>
            <div class="col-md-3">
                <label for="author" class="form-label">Tác giả:</label>
                <select class="form-select" name="author" id="author">
                    <option value="">Tất cả</option>
                    <% List<String> authors = (List<String>) request.getAttribute("authors");
                        String selectedAuthor = request.getParameter("author");
                        if (authors != null) {
                            for (String author : authors) { %>
                                <option value="<%= author%>" <%= author.equals(selectedAuthor) ? "selected" : ""%>><%= author%></option>
                    <% } } %>
                </select>
            </div>
          
            <div class="col-md-1 d-flex align-items-end">
                <button type="submit" class="btn btn-primary w-100">Lọc</button>
            </div>
        </form>

        <!-- DANH SÁCH BÀI VIẾT -->
        <div class="blog-list">
            <% if (posts != null && !posts.isEmpty()) { 
                for (Post p : posts) { %>
                <div class="blog-item">
                    <div class="row">
                        <div class="col-md-3">
                            <img src="<%= p.getImageLink() %>" class="img-fluid rounded">
                        </div>
                        <div class="col-md-9">
                            <h2>
                                <a href="blogDetail?blogID=<%= p.getId() %>" class="blog-title">
                                    <%= p.getTitle() %>
                                </a>
                            </h2>
                            <p class="text-muted">Danh mục: <%= p.getCategory() %> | Tác giả: <%= p.getAuthor().getName() %></p>
                            <p>
                                <a href="blogDetail?blogID=<%= p.getId() %>" class="btn btn-primary">Đọc tiếp</a>
                            </p>
                        </div>
                    </div>
                </div>
            <% } } else { %>
                <p class="text-center text-muted">Không có bài viết nào.</p>
            <% } %>
        </div>

        <!-- PHÂN TRANG -->
        <% 
            int currentPage = (request.getAttribute("currentPage") != null) ? (int) request.getAttribute("currentPage") : 1;
            int totalPages = (request.getAttribute("totalPages") != null) ? (int) request.getAttribute("totalPages") : 1;

            String queryString = "";
            if (request.getQueryString() != null) {
                queryString = "&" + request.getQueryString().replaceAll("&?page=\\d+", "");
            }
        %>

        <% if (totalPages > 1) { %>
        <div class="d-flex justify-content-center mt-4">
            <nav>
                <ul class="pagination">
                    <% if (currentPage > 1) { %>
                        <li class="page-item">
                            <a class="page-link" href="blogList?page=<%= currentPage - 1 %><%= queryString %>" aria-label="Previous">
                                <span aria-hidden="true">&laquo;</span>
                            </a>
                        </li>
                    <% } %>

                    <% for (int i = 1; i <= totalPages; i++) { %>
                        <li class="page-item <%= (i == currentPage) ? "active" : "" %>">
                            <a class="page-link" href="blogList?page=<%= i %><%= queryString %>"><%= i %></a>
                        </li>
                    <% } %>

                    <% if (currentPage < totalPages) { %>
                        <li class="page-item">
                            <a class="page-link" href="blogList?page=<%= currentPage + 1 %><%= queryString %>" aria-label="Next">
                                <span aria-hidden="true">&raquo;</span>
                            </a>
                        </li>
                    <% } %>
                </ul>
            </nav>
        </div>
        <% } %>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
