<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!doctype html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel="icon" href="image/favicon.png" type="image/png">
        <title>Ticket Details</title>
        <%@include file="components/Style.jsp" %>
        <style>
            .container {
                width: 60%;
                margin: auto;
                padding: 20px;
            }
            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 20px;
            }
            th, td {
                padding: 10px;
                border: 1px solid #ddd;
                text-align: left;
            }
            th {
                background-color: #f4f4f4;
            }
            .btn-back {
                display: inline-block;
                background: #007bff;
                color: white;
                padding: 10px 15px;
                border-radius: 5px;
                text-decoration: none;
                margin-top: 20px;
            }
        </style>
    </head>
    <body>
        <section class="breadcrumb_area">
            <div class="overlay bg-parallax" data-stellar-ratio="0.8" data-stellar-vertical-offset="0" data-background=""></div>
            <div class="container">
                <div class="page-cover text-center">
                    <h2 class="page-cover-tittle">Profile</h2>
                    <ol class="breadcrumb">
                        <li><a href="/tratra">Home</a></li>
                        <li class="active">Ticket Details</li>
                    </ol>
                </div>
            </div>
        </section>
        <%@include file="components/Header.jsp" %>

        <div class="container">
            <h2>Transaction Details</h2>

            <c:if test="${not empty tickets}">
                <c:forEach var="ticket" items="${tickets}">
                    <table class="table">
                        <tr>
                            <td>ID</td>
                            <td>${ticket.id}</td>
                        </tr>
                        <tr>
                            <td>Status</td>
                            <td>${ticket.status}</td>
                        </tr>
                        <tr>
                            <td>Luggage Type</td>
                            <td>${ticket.luggageType}</td>
                        </tr>
                        <tr>
                            <td>Booking Date</td>
                            <td>${ticket.bookingDate}</td>
                        </tr>
                        <tr>
                            <td>Route</td>
                            <td>${ticket.route.departureStation} - ${ticket.route.arrivalStation}</td>
                        </tr>
                        <tr>
                            <td>Departure Time</td>
                            <td>${ticket.route.departureTime}</td>
                        </tr>
                        <tr>
                            <td>Arrival Time</td>
                            <td>${ticket.route.arrivalTime}</td>
                        </tr>
                        <tr>
                            <td>Train</td>
                            <td>${ticket.train.trainid} - ${ticket.train.owner}</td>
                        </tr>
                        <tr>
                            <td>Cabin</td>
                            <td>${ticket.cabin.cabin_name} - ${ticket.cabin.cabinclass}</td>
                        </tr>
                        <tr>
                            <td>Seat</td>
                            <td>${ticket.seat.name}</td>
                        </tr>
                    </table>
                </c:forEach>
            </c:if>

            <a href="transaction" class="btn-back">Back to Transactions</a>
        </div>

        <%@include file="components/Footer.jsp" %>
        <%@include file="components/Script.jsp" %>
    </body>
</html>
