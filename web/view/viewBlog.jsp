<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Blog" %>
<%@ page import="model.User" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Chi Tiết Blog</title>
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;700&display=swap');

        body {
            font-family: 'Roboto', sans-serif;
            line-height: 1.8;
            background-color: #f5f5f5;
            color: #333;
            margin: 0;
            padding: 0;
        }

        .container {
            width: 60%;
            margin: 50px auto;
            background: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        h2 {
            font-size: 28px;
            font-weight: bold;
            color: #222;
            margin-bottom: 10px;
            text-align: center;
        }

        .blog-image {
            display: block;
            max-width: 100%;
            height: auto;
            margin: 10px auto;
            border-radius: 5px;
        }

        p {
            font-size: 18px;
            text-align: justify;
            margin-bottom: 15px;
        }

        .category {
            font-weight: bold;
            color: #0073e6;
            text-transform: uppercase;
        }

        .author {
            font-style: italic;
            color: #555;
        }

        .back-btn {
            display: block;
            width: 150px;
            margin: 20px auto;
            padding: 10px;
            text-align: center;
            font-size: 18px;
            font-weight: bold;
            color: white;
            background-color: #007bff;
            border: none;
            border-radius: 5px;
            text-decoration: none;
            cursor: pointer;
        }

        .back-btn:hover {
            background-color: #0056b3;
        }

        hr {
            border: 0;
            height: 1px;
            background: #ddd;
            margin: 20px 0;
        }
    </style>
    <jsp:include page="/view/head.jsp" />
</head>
<body>
    <div class="container">
        <% for (Blog blog : (List<Blog>) request.getAttribute("blogs")) { %>
            <h2><%= blog.getBlogTitle() %></h2>

            <% if (blog.getImageLink() != null && !blog.getImageLink().isEmpty()) { %>
                <img class="blog-image" src="<%= blog.getImageLink() %>" width="300"/>
            <% } else { %>
                <p><em>Không có ảnh</em></p>
            <% } %>

            <p class="category">Thể loại: <%= blog.getCategory() %></p>

            <% if (blog.getAuthor() != null) { %>
                <p class="author">Tác giả: <%= blog.getAuthor().getName() %></p>
            <% } else { %>
                <p class="author">Tác giả: Không xác định</p>
            <% } %>

            <p><%= blog.getBlogDetail() %></p>
            <hr>
        <% } %>

        <!-- Nút "Quay lại" -->
        <button class="back-btn" onclick="goBack()">⬅ Quay lại</button>
    </div>

    <script>
        function goBack() {
            if (document.referrer.includes("blogList")) {
                window.history.back();
            } else {
                window.location.href = "blogList";
            }
        }
    </script>
</body>
</html>
