<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="model.User" %>

<header id="topnav" class="defaultscroll sticky">
    <div class="container">
        <!-- Logo container-->
        <a class="logo" href="${pageContext.request.contextPath}/home">
            <img src="${pageContext.request.contextPath}/assets/images/logo-dark.png?v=<%= System.currentTimeMillis() %>" height="70" class="logo-light-mode" alt="">
            <img src="${pageContext.request.contextPath}/assets/images/logo-light.png?v=<%= System.currentTimeMillis() %>" height="70" class="logo-dark-mode" alt="">
        </a>                
        <!-- Logo End -->

        <!-- Start Dropdown -->
        <ul class="dropdowns list-inline mb-0">
            <li class="list-inline-item mb-0">
                <a href="${pageContext.request.contextPath}/mycart" >
                    <div class="btn btn-icon btn-pills btn-primary"><i data-feather="shopping-cart" class="fea icon-sm"></i></div>
                </a>
            </li>


            <li class="list-inline-item mb-0 ms-1">
                <div class="dropdown dropdown-primary">
                    <c:set var="defaultImage" value="https://thumbs.dreamstime.com/b/default-avatar-profile-icon-social-media-user-vector-image-icon-default-avatar-profile-icon-social-media-user-vector-image-209162840.jpg"/>

                    <!-- Get user image -->
                    <c:set var="userImage" value="${not empty sessionScope.account.user_image ? sessionScope.account.user_image : defaultImage}"/>

                    <!-- Dropdown button with user image -->
                    <button type="button" class="btn btn-pills btn-soft-primary dropdown-toggle p-0" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <img src="${userImage}" class="avatar avatar-ex-small rounded-circle" alt="">
                    </button>

                    <!-- Dropdown Menu -->
                    <div class="dropdown-menu dd-menu dropdown-menu-end bg-white shadow border-0 mt-3 py-3" style="min-width: 200px;">
                        <a class="dropdown-item d-flex align-items-center text-dark" href="#">
                            <img src="${userImage}" class="avatar avatar-md-sm rounded-circle border shadow" alt="">
                            <div class="flex-1 ms-2">
                                <span class="d-block mb-1">${not empty sessionScope.account ? sessionScope.account.user_fullname : "Guest User"}</span>
                                <c:if test="${empty sessionScope.account}">
                                    <small class="text-muted">Please Login</small>
                                </c:if>
                            </div>
                        </a>
                        <a class="dropdown-item text-dark" href="${pageContext.request.contextPath}/myReservationController">
                            <span class="mb-0 d-inline-block me-1"><i class="uil uil-setting align-middle h6"></i></span> Profile Settings
                        </a>
                        <div class="dropdown-divider border-top"></div>
                        <c:choose>
                            <c:when test="${not empty sessionScope.account.user_id}">
                                <a class="dropdown-item text-dark" href="<c:url value='/logout' />">
                                    <span class="mb-0 d-inline-block me-1">
                                        <i class="uil uil-sign-out-alt align-middle h6"></i>
                                    </span> Logout
                                </a>
                            </c:when>
                            <c:otherwise>
                                <a class="dropdown-item text-dark" href="<c:url value='/loginnavigation' />">
                                    <span class="mb-0 d-inline-block me-1">
                                        <i class="uil uil-sign-in-alt align-middle h6"></i>
                                    </span> Login   
                                </a>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </li>

        </ul>
        <!-- Start Dropdown -->

        <div id="navigation">
            <!-- Navigation Menu-->   
            <ul class="navigation-menu nav-left">
                <li class="has-submenu parent-menu-item">
                    <a href="${pageContext.request.contextPath}/home">Home</a><span class="sub-menu-item"></span>
                </li>

                <li class="has-submenu parent-menu-item">
                    <a href="#">Blogs</a><span class="sub-menu-item"></span>
                </li>

                <li class="has-submenu parent-menu-item">
                    <a href="${pageContext.request.contextPath}/customer/customerlistService">Services</a><span class="sub-menu-item"></span>
                </li>

                <c:if test="${sessionScope.account.role_id <= 3}">
                    <li> <a href="${pageContext.request.contextPath}/nav/dashboard" class="sub-menu-item"">Admin</a></li>
                    </c:if>

            </ul><!--end navigation menu-->
        </div><!--end navigation-->
    </div><!--end container-->
</header>