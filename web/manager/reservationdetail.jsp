<%-- 
    Document   : reservationdetail
    Created on : Jul 20, 2022, 12:04:46 AM
    Author     : Dan Chen
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ReservationDetail</title>
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto|Varela+Round">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
    </head>
    <body>
        <jsp:include page="./headermanager.jsp"/>
        <div class="container">
            <div class="d-flex justify-content-center h-100 m-3">
                <div class="searchbar">
                    <form class="form-inline" action="reservationlist">
                        <div class="form-group">
                            <input type="text" class="form-control mr-2" placeholder="Find a name" name="search">
                        </div>
                        <button type="submit" class="btn btn-primary">Search</button>
                    </form>
                </div>
            </div>
            <div class="row">
                <div class="table-responsive">
                    <div class="table-wrapper">
                        <h1 style="text-align: center; background-color: lightgray; width: 100%; position: relative;">Account List</h1>
                        <br>
                        <table class="table table-striped table-hover">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Service</th>						
                                    <th>Child name</th>
                                    <th>Reserved on</th>
                                    <th>Staff</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${details}" var="d">
                                    <tr>
                                        <td>${d.detailID}</td>
                                        <td>${d.service.serviceName}</td>
                                        <td>${d.child.childName}</td>
                                        <td>${d.reservationDate}</td>
                                        <td>${d.service.author.name}</td>
                                        <c:if test="${sessionScope.account.role.roleID == 3}">
                                            <c:if test="${d.status==true}">
                                                <td><a class="btn btn-default" href="changeStatus?status=1">Mark as done</a></td>
                                            </c:if>
                                            <c:if test="${d.status==false}">
                                                <td><a class="btn btn-default" href="changeStatus?status=0">Mark as not done</a></td>
                                            </c:if>
                                        </c:if>
                                        <c:if test="${sessionScope.account.role.roleID == 1||sessionScope.account.role.roleID == 2}">
                                            <c:if test="${d.status==true}">
                                                <td>Completed</td>
                                            </c:if>
                                            <c:if test="${d.status==false}">
                                                <td>Not completed</td>
                                            </c:if>
                                        </c:if>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
