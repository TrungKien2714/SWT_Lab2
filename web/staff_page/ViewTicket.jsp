<%-- 
    Document   : viewTicket
    Created on : Feb 27, 2025
    Author     : Aus TUF GAMING
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
        <style>
            th{
                text-align: center;
            }
        </style>
    </head >
<body class="sb-nav-fixed">
    
    <%@ include file="staffNavBar.jsp" %>
    
    <div id="layoutSidenav">
        <%@ include file="staffSideBar.jsp" %>
        
        <div id="layoutSidenav_content">
            <main>
                <h2 class="mb-4 text-center" >View Ticket Details</h2>
                <div class="container mt-5 d-flex" style="justify-content: center">
                    <div class="card col-md-6">
                        <div class="card-header bg-primary text-white text-center">
                            Ticket Information
                        </div>
                        <div class="card-body">
                            <c:choose>
                                <c:when test="${not empty sessionScope.ticket}">
                                    <c:set var="ticket" value="${sessionScope.ticket}" />

                                    <table class="table table-bordered table-striped col-md-6">
                                        <tbody>
                                            <tr>
                                                <th class="col-md-3 ">Full Name</th>
                                                <td><c:out value="${ticket.fullName}" /></td>
                                            </tr>
                                            <tr>
                                                <th>Phone Number</th>
                                                <td><c:out value="${ticket.phoneNumber}" /></td>
                                            </tr>
                                            <tr>
                                                <th>Departure Station</th>
                                                <td><c:out value="${ticket.departureStation}" /></td>
                                            </tr>
                                            <tr>
                                                <th>Arrival Station</th>
                                                <td><c:out value="${ticket.arrivalStation}" /></td>
                                            </tr>
                                            <tr>
                                                <th>Departure Time</th>
                                                <td><c:out value="${ticket.departureTime}" /></td>
                                            </tr>
                                            <tr>
                                                <th>Arrival Time</th>
                                                <td><c:out value="${ticket.arrivalTime}" /></td>
                                            </tr>
                                            <tr>
                                                <th>Seat ID</th>
                                                <td><c:out value="${ticket.seatId}" /></td>
                                            </tr>
                                            <tr>
                                                <th>Amount Paid</th>
                                                <td><c:out value="${ticket.amountPaid}" /> VND</td>
                                            </tr>
                                            <tr>
                                                <th>Luggage Type</th>
                                                <td><c:out value="${ticket.luggageType}" /></td>
                                            </tr>
                                            <tr>
                                                <th>Status</th>
                                                <td style="color: ${ticket.status eq 1 ? 'green' : 'red'}; font-weight: bold;">
                                                    <c:out value="${ticket.status eq 1 ? 'Active' : 'Inactive'}" />
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>

                                    <div class="text-center">
                                        <a href="./ticketList" class="btn btn-primary">Back to List</a>
                                    </div>

                                </c:when>
                                <c:otherwise>
                                    <div class="alert alert-danger text-center">Ticket not found.</div>
                                    <div class="text-center">
                                        <a href="ticketList.jsp" class="btn btn-secondary">Back to List</a>
                                    </div>
                                </c:otherwise>
                            </c:choose>
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
