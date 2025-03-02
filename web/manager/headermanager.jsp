<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
    <a class="navbar-brand" href="dashboard">Dashboard</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarText"
            aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarText">
        <ul class="navbar-nav mr-auto">
            <c:if test="${sessionScope.account.role.roleID == 1 || sessionScope.account.role.roleID == 2}">
                <li class="nav-item">
                    <a class="nav-link" href="FeedbackList">Feedbacklist</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="PostList">PostList</a>
                </li>
            </c:if>
            <li class="nav-item">
                <a class="nav-link" href="servicesmanager">ServicesList</a>
            </li>
            <c:if test="${sessionScope.account.role.roleID == 1}">
                <li class="nav-item">
                    <a class="nav-link" href="userList">UserList</a>
                </li>
            </c:if>
            <li class="nav-item">
                <a class="nav-link" href="reservationList">ReservationList</a>
            </li>
        </ul>
        <div class="dropdown">
            <button class="btn btn-primary dropdown-toggle" type="button" id="account" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                ${sessionScope.account.name}
            </button>
            <div class="dropdown-menu dropdown-menu-right" aria-labelledby="account">
                <a class="dropdown-item" href="userDetail?UserID=${sessionScope.account.userID}">Edit account info</a>
                <a class="dropdown-item" href="changepass">Change Password</a>
                <a class="dropdown-item" href="logout">Log out</a>
            </div>
        </div>
    </div>
</nav>