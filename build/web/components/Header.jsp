<%--
    Document   : Header
    Created on : Feb 6, 2025, 10:05:22 PM
    Author     : dtam6
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<header class="header_area">
    <div class="container">
        <nav class="navbar navbar-expand-lg navbar-light">
            <!-- Brand and toggle get grouped for better mobile display -->
            <a class="navbar-brand logo_h" href="/tratra">Tratra</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse offset" id="navbarSupportedContent">
                <ul class="nav navbar-nav menu_nav ml-auto">
                    <li class="nav-item active"><a class="nav-link" href="/tratra">Home</a></li>
                    <li class="nav-item"><a class="nav-link" href="about.html">About us</a></li>
                    <li class="nav-item"><a class="nav-link" href="RouteServlet">Route</a></li>
                    <li class="nav-item"><a class="nav-link" href="VoucherServlet">Voucher</a></li>
                    <c:choose>
                        <c:when test="${not empty sessionScope.account}">
                            <li class="nav-item"><a class="nav-link" href="profile">Profile</a></li>
                            <li class="nav-item"><a class="nav-link" href="logout">Logout</a></li>
                            </c:when>
                            <c:otherwise>
                            <li class="nav-item"><a class="nav-link" href="customer/login">Login</a></li>
                            <li class="nav-item"><a class="nav-link" href="register.jsp">SignUp</a></li>
                            </c:otherwise>
                    </c:choose>
                </ul>
            </div>
        </nav>
    </div>
</header>
