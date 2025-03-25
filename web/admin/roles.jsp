<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Quản lý Roles</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        #myInput {
            width: 100%;
            padding: 12px;
            border: 1px solid #ddd;
            margin-bottom: 12px;
        }
        #table-container {
            max-height: 500px;
            overflow-y: auto;
            border: 1px solid #ddd;
        }
        #myTable {
            width: 100%;
            border-collapse: collapse;
        }
        #myTable th {
            position: sticky;
            top: 0;
            background-color: #f8f9fa;
            border-bottom: 2px solid #ddd;
        }
        .error {
            color: red;
            font-style: italic;
            margin-bottom: 10px;
        }
    </style>
     <jsp:include page="./AdminHeader.jsp" />
</head>
<body>
    <div class="container mt-4">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2>Quản lý Roles</h2>
          
        </div>

        <c:if test="${not empty error}">
            <div class="error">${error}</div>
        </c:if>

        <input type="text" id="myInput" onkeyup="myFunction()" placeholder="Tìm kiếm tên role..">

        <div id="table-container">
            <table id="myTable" class="table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Tên Role</th>
                        <th>Trạng thái</th>
                        <th>Hành động</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="role" items="${roles}">
                        <tr>
                            <td>${role.roleID}</td>
                            <td>${role.roleName}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${role.status.status}">
                                        <span style="color: green; font-weight: bold;">Hoạt động</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span style="color: red; font-weight: bold;">Bị khóa</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <a href="${pageContext.request.contextPath}/admin/editRole?id=${role.roleID}" class="btn btn-warning">Sửa</a>

                                <form action="${pageContext.request.contextPath}/admin/settingList" method="post" style="display:inline;">
                                    <input type="hidden" name="roleID" value="${role.roleID}">
                                    <input type="hidden" name="currentStatus" value="${role.status.status}">
                                    <button type="submit" class="btn btn-info">
                                        <c:choose>
                                            <c:when test="${role.status.status}">Vô hiệu hóa</c:when>
                                            <c:otherwise>Kích hoạt</c:otherwise>
                                        </c:choose>
                                    </button>
                                </form>

                                <form action="${pageContext.request.contextPath}/admin/deleteRole" method="post" style="display:inline;" onsubmit="return confirm('Bạn có chắc muốn xóa role này?');">
                                    <input type="hidden" name="roleID" value="${role.roleID}">
                                    <button type="submit" class="btn btn-danger">Xóa</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <a href="${pageContext.request.contextPath}/admin/addRole" class="btn btn-success mt-3">Thêm role mới</a>
    </div>

    <script>
        function myFunction() {
            var input = document.getElementById("myInput");
            var filter = input.value.toUpperCase();
            var table = document.getElementById("myTable");
            var tr = table.getElementsByTagName("tr");
            for (var i = 1; i < tr.length; i++) {
                var td = tr[i].getElementsByTagName("td")[1];
                if (td) {
                    var txtValue = td.textContent || td.innerText;
                    tr[i].style.display = txtValue.toUpperCase().indexOf(filter) > -1 ? "" : "none";
                }
            }
        }
    </script>
</body>
</html>