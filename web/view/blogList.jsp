<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List, model.Post" %>
<jsp:useBean id="posts" scope="request" type="java.util.List<model.Post>" />
<jsp:useBean id="categories" scope="request" type="java.util.List<java.lang.String>" />
<jsp:useBean id="authors" scope="request" type="java.util.List<java.lang.String>" />
<!DOCTYPE html>
<html>
    <head>
        <title>News Page</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
        <style>
    body {
    font-family: 'Times New Roman', Times, serif;
    background-color: #f8f9fa;
    color: #333;
}

h1 {
    font-size: 36px;
    font-weight: bold;
    text-align: center;
    margin-bottom: 30px;
    text-transform: uppercase;
    letter-spacing: 1px;
}

.news-item {
    display: flex;
    flex-wrap: wrap;
    border-bottom: 2px solid #ddd;
    padding: 20px 0;
    transition: background-color 0.3s ease-in-out;
}

.news-item:hover {
    background-color: #f1f1f1;
}

.news-image {
    width: 100%;
    max-height: 250px;
    object-fit: cover;
    border-radius: 5px;
}

.news-title {
    font-size: 28px;
    font-weight: bold;
    margin-bottom: 10px;
    line-height: 1.4;
}

.news-title:hover {
    text-decoration: underline;
}

.news-link {
    text-decoration: none;
    color: #000;
}

.news-meta {
    font-size: 14px;
    color: #777;
    margin-bottom: 10px;
}

.news-summary {
    font-size: 16px;
    line-height: 1.6;
}

form {
    background: #fff;
    padding: 15px;
    border-radius: 8px;
    box-shadow: 0px 2px 10px rgba(0, 0, 0, 0.1);
}

button {
    font-size: 18px;
    padding: 10px;
    border-radius: 5px;
}
.filter-form {
    background: #fff;
    padding: 20px;
    border-radius: 8px;
    box-shadow: 0px 2px 10px rgba(0, 0, 0, 0.1);
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
    align-items: center;
    gap: 15px;
}

.filter-form .form-control,
.filter-form .form-select {
    height: 45px;
    font-size: 16px;
}

.filter-form .btn {
    height: 45px;
    font-size: 18px;
    padding: 10px 20px;
}


        </style>
    </head>
    <body class="container py-4">
        <h1 class="mb-4 text-center">Latest News</h1>

        <!-- Form Lọc Tin Tức -->
       <form method="GET" action="" class="filter-form row">
    <div class="col-md-3">
        <input type="text" name="title" class="form-control" placeholder="Search by title">
    </div>
    <div class="col-md-2">
        <select name="category" class="form-select">
            <option value="">All Categories</option>
            <% for (String category : categories) { %>
            <option value="<%= category %>"><%= category %></option>
            <% } %>
        </select>
    </div>
    <div class="col-md-2">
        <select name="author" class="form-select">
            <option value="">All Authors</option>
            <% for (String author : authors) { %>
            <option value="<%= author %>"><%= author %></option>
            <% } %>
        </select>
    </div>
    <div class="col-md-3 d-grid">
        <button type="submit" class="btn btn-primary">Filter</button>
    </div>
</form>


        <!-- Danh sách bài viết -->
        <% for (Post p : posts) { %>
        <a href="blogDetail?blogID=<%= p.getId() %>" class="news-link">
            <div class="news-item row">
                <div class="col-md-4">
                    <img src="<%= p.getImageLink() %>" class="news-image rounded">
                </div>
                <div class="col-md-8">
                    <div class="news-title"><%= p.getTitle() %></div>
                    <p><strong>Category:</strong> <%= p.getCategory() %></p>
                    <p><strong>Author:</strong> <%= p.getAuthor().getName() %></p>
                   
                </div>
            </div>
        </a>
        <% } %>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
