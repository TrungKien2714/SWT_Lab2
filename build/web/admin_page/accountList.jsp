<%-- 
    Document   : accountList
    Created on : Feb 9, 2025, 12:50:30 AM
    Author     : Aus TUF GAMAING
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <title>Account List</title>
        <link href="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css" rel="stylesheet" />
        <link href="css/styles.css" rel="stylesheet" />
        <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
    </head>
    <body class="sb-nav-fixed">
        <%@ include file="adminNavBar.jsp" %>

        <div id="layoutSidenav">
            <%@ include file="adminSideBar.jsp" %>
            <div id="layoutSidenav_content">
                <main>
                    <div class="container-fluid px-4">
                        <h1 class="mt-4">List of accounts</h1>
                        <div style="margin-bottom: 20px; display: flex; justify-content: space-between">
                            <div>
                                <a class="btn btn-primary ${param.role eq null ? 'active' : ''}" href="AccountList" role="button">All Roles</a>
                                <a class="btn btn-primary ${param.role eq 'Manager' ? 'active' : ''}" href="AccountList?role=Manager" role="button">Manager</a>
                                <a class="btn btn-primary ${param.role eq 'Seller' ? 'active' : ''}" href="AccountList?role=Seller" role="button">Seller</a>
                                <a class="btn btn-primary ${param.role eq 'Staff' ? 'active' : ''}" href="AccountList?role=Staff" role="button">Staff</a>
                                <a class="btn btn-primary ${param.role eq 'Customer' ? 'active' : ''}" href="AccountList?role=Customer" role="button">Customer</a>    
                            </div>
                            <div>
                                <a class="btn btn-success" href="AddAccount" role="button">Add a new account</a>  
                            </div>
                        </div>
                        <div class="card mb-4">
                            <div class="card-body">
                                <table id="datatablesSimple">
                                    <thead>
                                        <tr>
                                            <th>Full Name</th>
                                            <th>Email</th>
                                            <th>Status</th>
                                            <th>Action</th>
                                        </tr>
                                    </thead>
                                    <tfoot>
                                        <tr>
                                            <th>Full Name</th>
                                            <th>Email</th>
                                            <th>Status</th>
                                            <th>Action</th>
                                        </tr>
                                    </tfoot>
                                    <tbody>
                                        <c:forEach items="${sessionScope.acc_list}" var="list">
                                            <tr>
                                                <td><c:out value="${list.getFullname()}"/></td>
                                                <td><c:out value="${list.getEmail()}"/></td>
                                                <c:choose>
                                                    <c:when test="${list.getStatus() eq 1}">
                                                        <td><p style="color: green;">Active</p></td>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <td><p style="color: red;">Inactive</p></td>
                                                    </c:otherwise>
                                                </c:choose>
                                                <td>
                                                    <a class="btn btn-primary" href="AccountDetail?id=${list.getId()}&&role=${list.getRole()}" role="button">View Details</a>
                                                    <a class="btn btn-warning" href="EditAccount?id=${list.getId()}&&role=${list.getRole()}" role="button">Edit</a>
                                                    <c:choose>
                                                        <c:when test="${list.getStatus() eq '1'}">
                                                            <a class="btn btn-danger" href="ChangeStatusAccount?id=${list.getId()}&&uRole=${list.getRole()}&&role=${param.role}" role="button" onclick="return confirm('Are you sure you want to disable this account?');">Disable</a>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <a class="btn btn-success" href="ChangeStatusAccount?id=${list.getId()}&&uRole=${list.getRole()}&&role=${param.role}" role="button" onclick="return confirm('Are you sure you want to enable this account?');">Enable</a>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
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