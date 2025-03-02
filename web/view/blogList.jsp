<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.Blog" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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

    <!-- Form tìm kiếm -->
    <form action="blogList" method="get">
        <input type="text" name="search" value="${param.search}" placeholder="Nhập tên blog">
        <button type="submit">Tìm kiếm</button>
    </form>

    <div class="blog-container">
        <h2>📰 Danh sách Blog</h2>

        <!-- Hiển thị danh sách blog -->
        <c:choose>
            <c:when test="${not empty blogs}">
                <c:forEach var="blog" items="${blogs}">
                    <div class="blog-item">
                        <c:if test="${not empty blog.imageLink}">
                            <img class="blog-image" src="${blog.imageLink}" alt="Blog Image">
                        </c:if>
                        <div>
                            <a class="blog-title" href="${pageContext.request.contextPath}/blogDetail?blogID=${blog.blogID}">
                                ${blog.blogTitle}
                            </a>
                        </div>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <p><em>Không có bài viết nào.</em></p>
            </c:otherwise>
        </c:choose>
    </div>

</body>
</html>
