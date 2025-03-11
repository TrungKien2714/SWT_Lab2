<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page import="com.google.gson.Gson" %>
<%@page import="java.util.Map" %>
<% Map<String, Object> routeData = (Map<String, Object>) request.getAttribute("routeData"); %>
<script>
    let cabins = <%= new Gson().toJson(request.getAttribute("cabins")) %>;
    let selectedSeatsByCabin = {}; // Object to store selected seats by cabin ID
    let currentCabin = null; // Keep track of current cabin
</script>
<!DOCTYPE html>
<html>
    <style>
        body{
            background:#f5f5f5;
            margin-top:20px;
            padding-top: 80px;
        }
        .icon-hover:hover {
            border-color: #3b71ca !important;
            background-color: white !important;
            color: #3b71ca !important;
        }

        .icon-hover:hover i {
            color: #3b71ca !important;
        }

        /* Thêm CSS cho phần sơ đồ ghế */
        .seat-map-container {
            margin-top: 20px;
            border: 1px solid #ddd;
            border-radius: 8px;
            padding: 15px;
            background-color: #fff;
        }

        .seat-map-title {
            color: #3080c7;
            margin-bottom: 15px;
            font-weight: bold;
        }

        .coach-row {
            display: flex;
            justify-content: space-between;
            margin-bottom: 15px;
        }

        .seat {
            width: 40px;
            height: 40px;
            border: 1px solid #b89d58;
            border-radius: 5px;
            display: flex;
            align-items: center;
            justify-content: center;
            margin: 0 5px;
            font-weight: bold;
            cursor: pointer;
            position: relative;
            background-color: white;
        }

        .seat::before {
            content: '';
            position: absolute;
            left: -2px;
            top: 0;
            bottom: 0;
            width: 4px;
            background-color: #b89d58;
            border-radius: 2px 0 0 2px;
        }

        .seat.reserved {
            background-color: #e74c3c;
            color: white;
        }

        .seat.selected {
            background-color: #27ae60;
            color: white;
        }

        .aisle {
            width: 30px;
            background-color: #ccc;
            margin: 0 10px;
        }

        .legend {
            display: flex;
            justify-content: center;
            margin-top: 20px;
        }

        .legend-item {
            display: flex;
            align-items: center;
            margin: 0 15px;
        }

        .legend-color {
            width: 20px;
            height: 20px;
            margin-right: 8px;
            border-radius: 3px;
        }

        .free {
            background-color: white;
            border: 1px solid #b89d58;
        }

        .reserved-color {
            background-color: #e74c3c;
        }

        .selected-color {
            background-color: #27ae60;
        }

        .summary {
            margin-top: 20px;
            padding: 15px;
            background-color: #f8f9fa;
            border-radius: 5px;
            box-shadow: 0 0.125rem 0.25rem rgba(0, 0, 0, 0.075);
        }

        .summary-title {
            color: #3080c7;
            margin-bottom: 15px;
            font-weight: bold;
        }

        .coach-container.selected {
            border: 2px solid #27ae60;
            border-radius: 5px;
        }

        .coach-container {
            display: inline-block;
            margin: 0 5px;
            cursor: pointer;
        }

    </style>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${route.routeCode}</title>
        <%@include file="components/Style.jsp" %>
    </head>
    <%@include file="components/Header.jsp" %>

    <!-- content -->
    <section class="py-5">
        <div class="container">
            <div class="row gx-5">
                <div class="border rounded-2 px-3 py-2 bg-white shadow">
                    <main class="col-lg-12">
                        <div class="ps-lg-3">
                            <h4 class="title text-dark">Transaction</h4>
                            <div class="mb-3">
                                <span class="h5 text-success">
                                    Total: ${amount} VND
                                </span>
                                <span class="mx-2"> | </span>
                                <span class="h5 text-primary">
                                    Business: <fmt:formatNumber value="${route.businessPrice}" type="number" groupingUsed="true" /> VND
                                </span>
                            </div>
                            <div class="row d-flex justify-content-between">
                                <div class="col-md-6 d-flex">
                                    <dt class="fw-bold me-2">Seats Amount:</dt>
                                    <dd class="me-4">${numSeats}</dd>
                                </div>
                                <div class="col-md-6 d-flex">
                                    <dt class="fw-bold me-2">Selected Seats:</dt>
                                    <dd>${selectedSeats}</dd>
                                </div>
                            </div>
                            <c:set var="qrCodeUrl" value="https://img.vietqr.io/image/MB-0394110940-compact2.jpg?amount=${amount}" />
                            <img src="${qrCodeUrl}" alt="QR Code Payment" style="max-width: 50%; height: auto;">
                        </div>
                </div>
                </main>
            </div>
        </div>
    </div>
</section>
