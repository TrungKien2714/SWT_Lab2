<%-- 
    Document   : AccountDetail
    Created on : Mar 1, 2025, 11:09:23 PM
    Author     : Aus TUF GAMAING
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <title>Account Detail</title>
        <link href="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css" rel="stylesheet" />
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css">
        <link href="css/styles.css" rel="stylesheet" />
        <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
        <style>
            body {
                font-family: 'Arial', sans-serif;
                background-color: #f8f9fa;
            }
            .profile-container {
                background: #fff;
                border-radius: 10px;
                margin-top: 15px;
                border: 2px solid RGB(33, 37, 41);
                box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
                overflow: hidden;
            }
            .form-control {
                background: #f5f5f5;
                border: 1px solid #ddd; /* Light gray border for inputs */
                border-radius: 8px;
                padding: 12px;
                font-size: 16px;
            }
            .form-control:focus {
                background: #fff;
                border: 1px solid #007bff;
                box-shadow: none;
            }
            .form-label {
                font-weight: bold;
                margin-bottom: 5px;
            }
            .back-button {
                text-decoration: none; /* No underline by default */
                color: black; /* Adjust color as needed */
                font-weight: bold;
            }

            .back-button:hover {
                text-decoration: underline; /* Underline appears on hover */
                color: #007bff; /* Optional: Change color on hover */
            }
        </style>
    </head>
    <body>
        <%@ include file="adminNavBar.jsp" %>
        <div id="layoutSidenav">
            <%@ include file="adminSideBar.jsp" %>
            <div id="layoutSidenav_content">
                <main>
                    <div style="margin-top: 30px;" class="container-fluid px-4">
                        <div style="display: flex; justify-content: space-between;">
                            <h5 style="margin-top: auto; margin-bottom: auto" class="back-button"><a onclick="history.back()"><i class="bi bi-arrow-left"></i>Back</a></h5>
                            <a class="btn btn-warning" href="EditAccount?id=${requestScope.ud.getId()}&&role=${requestScope.ud.getRole()}" role="button">Edit this user information</a>
                        </div>
                        <div class="profile-container">
                            <div style="background:  RGB(33, 37, 41); color: white;">
                                <h2 class=" mb-4" style="padding: 10px">User Profile</h2>                                
                            </div>
                            <form style="padding: 20px">
                                <div class="row g-3">
                                    <div class="col-md-6">
                                        <label class="form-label">Email</label>
                                        <input type="email" class="form-control" value="${requestScope.ud.getEmail()}" readonly>
                                    </div>
                                    <div class="col-md-6">
                                        <label class="form-label">Full Name</label>
                                        <input type="text" class="form-control" value="${requestScope.ud.getFullname()}" readonly>
                                    </div>
                                    <div class="col-md-6">
                                        <label class="form-label">Phone Number</label>
                                        <input type="text" class="form-control" value="${requestScope.ud.getPhonenumber()}" readonly>
                                    </div>
                                    <div class="col-md-6">
                                        <label class="form-label">Address</label>
                                        <c:choose>
                                            <c:when test="${requestScope.ud.getAddress() eq ''}">
                                                <input type="text" class="form-control" value="N/A" readonly>
                                            </c:when>
                                            <c:otherwise>
                                                <input type="text" class="form-control" value="${requestScope.ud.getAddress()}" readonly>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                    <div class="col-md-6">
                                        <label class="form-label">Role</label>
                                        <div class="input-group">
                                            <c:choose>
                                                <c:when test="${requestScope.ud.getRole() eq 'Seller'}">
                                                    <span class="input-group-text"><i class="bi bi-train-front-fill"></i></span>
                                                    </c:when>
                                                    <c:when test="${requestScope.ud.getRole() eq 'Manager'}">
                                                    <span class="input-group-text"><i class="bi bi-gear-fill"></i></span>
                                                    </c:when>
                                                    <c:when test="${requestScope.ud.getRole() eq 'Staff'}">
                                                    <span class="input-group-text"><i class="bi bi-pencil-fill"></i></span>
                                                    </c:when>
                                                    <c:when test="${requestScope.ud.getRole() eq 'Customer'}">
                                                    <span class="input-group-text"><i class="bi bi-person-fill"></i></span>
                                                    </c:when>
                                                </c:choose>
                                            <input type="text" class="form-control" value="${requestScope.ud.getRole()}" readonly>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <label class="form-label">Status</label>
                                        <div class="d-flex align-items-center justify-content-between">
                                            <c:choose>
                                                <c:when test="${requestScope.ud.getStatus() eq 1}">
                                                    <input type="text" class="form-control me-3" style="color: green" value="Active" readonly>
                                                </c:when>
                                                <c:otherwise>
                                                    <input type="text" class="form-control me-3" style="color: red" value="Inactive" readonly>
                                                </c:otherwise>
                                            </c:choose>
                                            <c:choose>
                                                <c:when test="${requestScope.ud.getStatus() eq 1}">
                                                    <a class="btn btn-danger" href="ChangeStatusAccount?id=${requestScope.ud.getId()}&&uRole=${requestScope.ud.getRole()}&&role=${param.role}" role="button" onclick="return confirm('Are you sure you want to disable this account?');">Disable</a>
                                                </c:when>
                                                <c:otherwise>
                                                    <a class="btn btn-success" href="ChangeStatusAccount?id=${requestScope.ud.getId()}&&uRole=${requestScope.ud.getRole()}&&role=${param.role}" role="button" onclick="return confirm('Are you sure you want to enable this account?');">Enable</a>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </main>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
        <script src="js/scripts.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/umd/simple-datatables.min.js" crossorigin="anonymous"></script>
        <script src="js/datatables-simple-demo.js"></script>
    </body>
</html>
