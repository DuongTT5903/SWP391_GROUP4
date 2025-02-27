<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List" %>
<%@page import="model.Blog" %>

<!DOCTYPE html>
<html>
<head>
    <title>Danh sách Blog</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f5f5f5;
            margin: 0;
            padding: 20px;
        }
        .blog-container {
            width: 80%;
            margin: 0 auto;
            background: white;
            padding: 20px;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
            border-radius: 8px;
        }
        .blog-item {
            display: flex;
            align-items: center;
            border-bottom: 1px solid #ddd;
            padding: 15px 0;
        }
        .blog-image {
            width: 150px;
            height: auto;
            border-radius: 8px;
            margin-right: 20px;
        }
        .blog-title {
            font-size: 20px;
            font-weight: bold;
            color: #333;
            text-decoration: none;
        }
        .blog-title:hover {
            color: #007bff;
        }
    </style>
</head>
<body>
    <div class="blog-container">
        <h2>📰 Danh sách Blog</h2>
        <%
            List<Blog> blogs = (List<Blog>) request.getAttribute("blogs");
            if (blogs != null && !blogs.isEmpty()) {
                for (Blog blog : blogs) {
        %>
        <div class="blog-item">
            <% if (blog.getImageLink() != null && !blog.getImageLink().isEmpty()) { %>
                <img class="blog-image" src="<%= blog.getImageLink() %>" alt="Blog Image">
            <% } %>
            <div>
                <a class="blog-title" href="${pageContext.request.contextPath}/blogDetail?blogID=<%= blog.getBlogID() %>"><%= blog.getBlogTitle() %></a>
            </div>
        </div>
        <%
                }
            } else {
        %>
        <p><em>Không có bài viết nào.</em></p>
        <%
            }
        %>
    </div>
</body>
</html>
