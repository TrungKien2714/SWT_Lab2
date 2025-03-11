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
        <title>Transaction History</title>
        <%@include file="components/Style.jsp" %>
        <style>
            .container {
                width: 80%;
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
                text-align: center;
            }
            th {
                background-color: #f4f4f4;
            }
            a {
                display: inline-block;
                margin-top: 20px;
                text-decoration: none;
                color: white;
                padding: 10px 15px;
                border-radius: 5px;
            }

            .btn-back {
                background: #007bff;
            }
            .btn-view {
                display: inline-block;
                background: #28a745; /* Màu xanh lá */
                color: white;
                padding: 8px 12px;
                border-radius: 5px;
                text-decoration: none;
                font-size: 14px;
                font-weight: bold;
            }

            .btn-view:hover {
                background: #218838;
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
                        <li class="active">Transaction History</li>
                    </ol>
                </div>
            </div>
        </section>
        <!--================Header Area =================-->
        <%@include file="components/Header.jsp" %>
        <!--================Header Area =================-->

        <div class="container">
            <h2>Customer Transaction History</h2>

            <c:if test="${not empty transactions}">
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Payment Method</th>
                            <th>Status</th>
                            <th>Payment Date</th>
                            <th>Tickets</th>
                            <th>Amount</th>
                            <th>Voucher Code</th>
                            <th>View Info</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="t" items="${transactions}">
                            <tr>
                                <td>${t.id}</td>
                                <td>${t.paymentMethod}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${t.paymentStatus == 1}">
                                            <span style="color: green;">Paid</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span style="color: red;">Unpaid</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td><fmt:formatDate value="${t.paymentDate}" pattern="dd/MM/yyyy HH:mm" /></td>
                                <td>${t.quantity}</td>
                                <td><fmt:formatNumber value="${t.amountPaid}" type="number" minFractionDigits="2" maxFractionDigits="2" /> VND</td>
                                <td>${t.vouchercode != null ? t.vouchercode : "None"}</td>
                                <td>
                                    <a href="ticket-detail?transactionId=${t.id}" class="btn-view">
                                        View Details
                                    </a>
                                </td>

                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>

            <c:if test="${not empty transactions}">
                <p>Total transactions: ${fn:length(transactions)}</p>
            </c:if>

            <c:if test="${empty transactions}">
                <p>No transactions found!</p>
            </c:if>

            <a href="index.jsp" class="btn-back">Back to Home</a>
        </div>

        <!--================ start footer Area  =================-->
        <%@include file="components/Footer.jsp" %>
        <!--================ End footer Area  =================-->

        <%@include file="components/Script.jsp" %>
    </body>
</html>
