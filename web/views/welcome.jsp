<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%
    // Sử dụng biến session ngầm định của JSP
    String roleID = (String) session.getAttribute("roleID");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Welcome</title>
</head>
<body>
    <h1>Welcome</h1>
    <% if (roleID != null) { %>
        <p>Your role is: <%= roleID %></p>
    <% } else { %>
        <p>No role found in session.</p>
    <% } %>
</body>
</html>