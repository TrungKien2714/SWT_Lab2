<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!doctype html>
<html lang="en">
    <head>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel="icon" href="image/favicon.png" type="image/png">
        <title>Royal Hotel</title>
        <%@include file="components/Style.jsp" %>
    </head>
    <body>
        <!--================Header Area =================-->
        <%@include file="components/Header.jsp" %>
        <!--================Header Area =================-->

        <!--================Breadcrumb Area =================-->
        <section class="breadcrumb_area">
            <div class="overlay bg-parallax" data-stellar-ratio="0.8" data-stellar-vertical-offset="0" data-background=""></div>
            <div class="container">
                <div class="page-cover text-center">
                    <h2 class="page-cover-tittle">Profile</h2>
                    <ol class="breadcrumb">
                        <li><a href="index.html">Home</a></li>
                        <li class="active">Your Profile</li>
                    </ol>
                </div>
            </div>
        </section>
        <!--================Breadcrumb Area =================-->

        <!--================Contact Area =================-->
        <section class="contact_area section_gap">
            <div class="container">
                <div class="row">
                    <div class="col-md-3 mb-4">
                        <ul class="list-group">
                            <li class="list-group-item">
                                <a href="profile">View Profile</a>
                            </li>
                            <li class="list-group-item">
                                <a href="update-profile">Update Profile</a>
                            </li>
                            <li class="list-group-item">
                                <a href="change-password">Change Password</a>
                            </li>
                            <li class="list-group-item">
                                <a href="transaction">Transaction History</a>
                            </li>

                        </ul>
                    </div>
                    <div class="col-md-9">
                        <div class="card">
                            <div class="card-body">
                                <div class="mb-3">
                                    <label>Full Name</label>
                                    <div class="input-group">
                                        <div class="input-group-text">
                                            <i class="bi bi-person"></i>
                                        </div>
                                        <input class="form-control" type="email" id="email" name="email"
                                               value="${cus.fullName}" disabled >
                                    </div>
                                </div>

                                <div class="mb-3">
                                    <label>Email</label>
                                    <div class="input-group">
                                        <div class="input-group-text">
                                            <i class="bi bi-envelope"></i>
                                        </div>
                                        <input class="form-control" type="email" id="email" name="email"
                                               value="${sessionScope.account.email}" disabled >
                                    </div>
                                </div>

                                <div class="mb-3">
                                    <label>Phone Number</label>
                                    <div class="input-group">
                                        <div class="input-group-text">
                                            <i class="bi bi-telephone"></i>
                                        </div>
                                        <input class="form-control" type="email" id="email" name="email"
                                               value="${cus.phoneNumber}" disabled >
                                    </div>
                                </div>

                                <div class="mb-3">
                                    <label>Address</label>
                                    <div class="input-group">
                                        <div class="input-group-text">
                                            <i class="bi bi-geo-alt"></i>
                                        </div>
                                        <input class="form-control" type="email" id="email" name="email"
                                               value="${cus.address}" disabled >
                                    </div>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <!--================Contact Area =================-->

    <!--================ start footer Area  =================-->
    <%@include file="components/Footer.jsp" %>
    <!--================ End footer Area  =================-->

    <%@include file="components/Script.jsp" %>
</body>
</html>