

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
          <form action="login" method="POST">

                            <i class="login__icon fas fa-user"></i>
                            <label for="role">Select Role:</label>
                            <select name="roleID" id="role">
                                <c:forEach items="${requestScope.roles}" var="role">
                                    <option value="${role.roleID}">${role.roleName}</option>
                                </c:forEach>
                            </select><br><br>
                     
                              </form>
                                
    </body>
      
</html> 