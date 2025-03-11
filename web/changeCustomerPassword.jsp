<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Change Password</title>
        <%@include file="components/Style.jsp" %>
    </head>
    <body>
        <%@include file="components/Header.jsp" %>
        <!--================Breadcrumb Area =================-->
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
        <!--================Breadcrumb Area =================-->

        <section class="contact_area section_gap">
            <div class="container">
                <div class="row">
                    <div class="col-md-3">
                        <ul class="list-group">
                            <li class="list-group-item"><a href="profile">View Profile</a></li>
                            <li class="list-group-item"><a href="update-profile">Update Profile</a></li>
                            <li class="list-group-item"><a href="change-password">Change Password</a></li>
                            <li class="list-group-item"><a href="transaction">Transaction History</a></li>
                        </ul>
                    </div>
                    <div class="col-md-9">
                        <div class="card">
                            <form action="change-password" method="post" class="card-body">
                                <input type="hidden" name="id" value="${sessionScope.account.id}">
                                <div class="mb-3">
                                    <label class="form-label fw-bold">Enter old password</label>
                                    <input class="form-control" type="password" name="oldPassword" required>
                                </div>
                                <div class="mb-3">
                                    <label class="form-label fw-bold">Enter new password</label>
                                    <input class="form-control" type="password" name="newPassword" required >
                                </div>
                                <div class="mb-3">
                                    <label class="form-label fw-bold">Enter renew password</label>
                                    <input class="form-control" type="password" name="renewPassword" required>
                                </div>
                                <button type="submit" class="btn btn-primary w-100">Change Password</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <%@include file="components/Footer.jsp" %>
        <%@include file="components/Message.jsp" %>
        <%@include file="components/Script.jsp" %>
    </body>
</html>
