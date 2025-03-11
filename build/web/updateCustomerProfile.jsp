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
        <section class="breadcrumb_area">
            <div class="overlay bg-parallax" data-stellar-ratio="0.8" data-stellar-vertical-offset="0" data-background=""></div>
            <div class="container">
                <div class="page-cover text-center">
                    <h2 class="page-cover-tittle">Profile</h2>
                    <ol class="breadcrumb">
                        <li><a href="/tratra">Home</a></li>
                        <li class="active">Change Password</li>
                    </ol>
                </div>
            </div>
        </section>
        <!--================Header Area =================-->
        <%@include file="components/Header.jsp" %>
        <a href="../src/java/model/User.java"></a>
        <!--================Header Area =================-->
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
                            <form action="update-profile" method="post" class="card-body">
                                <input type="hidden" name="id" value="${sessionScope.account.id}">
                                <div class="mb-3">
                                    <label>Full Name</label>
                                    <div class="input-group">
                                        <div class="input-group-text">
                                            <i class="bi bi-person"></i>
                                        </div>
                                        <input class="form-control" type="text" id="fullName" name="fullName"
                                               value="${cus.fullName}">
                                    </div>
                                </div>

                                <div class="mb-3">
                                    <label>Email</label>
                                    <div class="input-group">
                                        <div class="input-group-text">
                                            <i class="bi bi-envelope"></i>
                                        </div>
                                        <input class="form-control" type="email" id="email" name="email"
                                               value="${sessionScope.account.email}">
                                    </div>
                                </div>

                                <div class="mb-3">
                                    <label>Phone Number</label>
                                    <div class="input-group">
                                        <div class="input-group-text">
                                            <i class="bi bi-telephone"></i>
                                        </div>
                                        <input class="form-control" type="tel" id="phoneNumber" name="phoneNumber"
                                               value="${cus.phoneNumber}" >
                                    </div>
                                </div>

                                <div class="mb-3">
                                    <label>Address</label>
                                    <div class="input-group">
                                        <div class="input-group-text">
                                            <i class="bi bi-geo-alt"></i>
                                        </div>
                                        <input class="form-control" type="text" id="address" name="address"
                                               value="${cus.address}" >
                                    </div>
                                </div>
                                <button type="submit" name="submit" value="update" class="btn btn-primary w-100">Update Profile</button>
                            </form>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>


    <!--================ start footer Area  =================-->
    <%@include file="components/Footer.jsp" %>
    <!--================ End footer Area  =================-->


    <%@include file="components/Message.jsp" %>
    <%@include file="components/Script.jsp" %>
</body>
</html>