<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List, model.Post" %>
<jsp:useBean id="posts" scope="request" type="java.util.List<model.Post>" />
<jsp:useBean id="categories" scope="request" type="java.util.List<java.lang.String>" />
<jsp:useBean id="authors" scope="request" type="java.util.List<java.lang.String>" />
<!DOCTYPE html>
<html>
    <head>
        <title>Manage Posts</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    </head>
    <body class="container py-4">
        <h1 class="mb-4 text-center">Post List</h1>

        <form method="GET" class="row g-3 mb-4">
            <div class="col-md-3">
                <input type="text" name="title" class="form-control" placeholder="Search by title">
            </div>
            <div class="col-md-2">
                <select name="category" class="form-select">
                    <option value="">All Categories</option>
                    <% for (String category : categories) {%>
                    <option value="<%= category%>"><%= category%></option>
                    <% } %>
                </select>
            </div>
            <div class="col-md-2">
                <select name="author" class="form-select">
                    <option value="">All Authors</option>
                    <% for (String author : authors) {%>
                    <option value="<%= author%>"><%= author%></option>
                    <% } %>
                </select>
            </div>
            <div class="col-md-2">
                <select name="status" class="form-select">
                    <option value="">All Status</option>
                    <option value="true">Active</option>
                    <option value="false">Hidden</option>
                </select>
            </div>
            <div class="col-md-3">
                <button type="submit" class="btn btn-primary w-100">Filter</button>
            </div>
        </form>

        <table class="table table-bordered table-hover text-center">
            <thead class="table-dark">
                <tr>
                    <th>ID</th>
                    <th>Thumbnail</th>
                    <th>Title</th>
                    <th>Category</th>
                    <th>Author</th>
                    <th>Status</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <% for (Post p : posts) {%>
                <tr>
                    <td><%= p.getId()%></td>
                    <td><img src="<%= p.getImageLink()%>" width="50" height="50" class="rounded"></td>
                    <td><%= p.getTitle()%></td>
                    <td><%= p.getCategory()%></td>
                    <td><%= p.getAuthor().getName()%></td>
                    <td>
                        <span class="badge <%= p.isStatus() ? "bg-success" : "bg-secondary"%>">
                            <%= p.isStatus() ? "Active" : "Hidden"%>
                        </span>
                    </td>
                    <td>

                        <a href="postDetail?id=<%= p.getId()%>" class="btn btn-info btn-sm">View</a>
                       
                        <% if (p.isStatus()) {%>
                       
                        <a href="postList?action=hide&id=<%= p.getId()%>&page=${currentPage}" class="btn btn-danger btn-sm">Hide</a>
                        <% } else {%>
                        <a href="postList?action=show&id=<%= p.getId()%>&page=${currentPage}" class="btn btn-success btn-sm">Show</a>
                        <% } %>
                    </td>
                </tr>
                <% }%>
            </tbody>
        </table>
        <div class="d-flex justify-content-center mt-4">
            <nav>
                <ul class="pagination">
                    <%
                        int currentPage = (request.getAttribute("currentPage") != null)
                                ? (int) request.getAttribute("currentPage") : 1;
                        int totalPages = (request.getAttribute("totalPages") != null)
                                ? (int) request.getAttribute("totalPages") : 1;
                        String queryString = "";
                        // Giả sử bạn cần nối thêm các tham số filter vào liên kết, có thể xử lý thêm nếu cần.
                    %>
                    <% if (currentPage > 1) {%>
                    <li class="page-item">
                        <a class="page-link" href="postList?page=<%= currentPage - 1%><%= queryString%>" aria-label="Previous">
                            <span aria-hidden="true">&laquo;</span>
                        </a>
                    </li>
                    <% } else { %>
                    <li class="page-item disabled">
                        <a class="page-link" href="#" aria-label="Previous">
                            <span aria-hidden="true">&laquo;</span>
                        </a>
                    </li>
                    <% } %>

                    <% for (int i = 1; i <= totalPages; i++) {%>
                    <li class="page-item <%= (i == currentPage) ? "active" : ""%>">
                        <a class="page-link" href="postList?page=<%= i%><%= queryString%>"><%= i%></a>
                    </li>
                    <% } %>

                    <% if (currentPage < totalPages) {%>
                    <li class="page-item">
                        <a class="page-link" href="postList?page=<%= currentPage + 1%><%= queryString%>" aria-label="Next">
                            <span aria-hidden="true">&raquo;</span>
                        </a>
                    </li>
                    <% } else { %>
                    <li class="page-item disabled">
                        <a class="page-link" href="#" aria-label="Next">
                            <span aria-hidden="true">&raquo;</span>
                        </a>
                    </li>
                    <% }%>
                </ul>
            </nav>
        </div>


        <div class="text-end">
            <a href="addPost" class="btn btn-success">Add New Post</a>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
