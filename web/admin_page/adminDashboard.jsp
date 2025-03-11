<%-- 
    Document   : adminDashboard
    Created on : Feb 8, 2025, 11:27:39 PM
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
        <title>Dashboard - Admin</title>
        <link href="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css" rel="stylesheet" />
        <link href="css/styles.css" rel="stylesheet" />
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css">
        <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
        <style>
            body {
                background-color: #f8f9fa;
            }
            .cardcard {
                color: white;
                border-radius: 10px;
                padding: 20px;
                box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
                transition: transform 0.2s ease-in-out, box-shadow 0.2s ease-in-out;
                cursor: pointer;
            }
            .cardcard:hover {
                transform: scale(1.05); /* Slightly enlarge on hover */
                box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2); /* Add shadow */
            }
            .cardcard.selected {
                transform: scale(0.95); /* Keep it enlarged */
                box-shadow: 0 4px 15px rgba(0, 0, 0, 0.3); /* Stronger shadow */
                border: 2px solid white; /* Add border effect */
                opacity: 0.8
            }
            .stat-box {
                display: flex;
                align-items: center;
                justify-content: space-between;
            }
            .stat-icon {
                font-size: 18px;
                padding: 5px 10px;
                border-radius: 5px;
                font-weight: bold;
            }
            .blue {
                color: #007bff;
                background: #e3f2fd;
            }
            .green {
                color: #28a745;
                background: #d4edda;
            }
            .orange {
                color: #ff9f00;
                background: #fff3cd;
            }
            .red {
                color: #dc3545;
                background: #f8d7da;
            }
            .chart-container {
                background: white;
                border-radius: 10px;
                padding: 20px;
                box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            }
            a {
                text-decoration: none; /* Removes underline */
            }
        </style>
    </head>
    <body class="sb-nav-fixed">
        <%@ include file="adminNavBar.jsp" %>

        <div id="layoutSidenav">
            <%@ include file="adminSideBar.jsp" %>
            <div id="layoutSidenav_content">
                <main>
                    <div class="container-fluid px-4">
                        <h1 class="mt-4">Dashboard</h1>
                        <div class="row g-3">
                            <!-- Stat Cards -->
                            <div class="col-md-3">
                                <a>
                                    <div class="cardcard selected viewChart" style="background: #007bff" onclick="selectCard(this)">
                                        <p class="mb-1">Total Page Views</p>
                                        <div class="stat-box">
                                            <h3>${requestScope.view}</h3>
                                            <span class="stat-icon blue">
                                                <c:choose>
                                                    <c:when test="${requestScope.viewSign eq '1'}">
                                                        <div style="color: #28a745">
                                                            <i class="bi bi-caret-up-fill"></i>
                                                            ${requestScope.viewPercent}%
                                                        </div>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <div style="color: #dc3545">
                                                            <i class="bi bi-caret-down-fill"></i>
                                                            ${requestScope.viewPercent}%
                                                        </div>
                                                    </c:otherwise>
                                                </c:choose>
                                            </span>
                                        </div>
                                        <small>You have <span style="font-weight: bold">${requestScope.viewDiff} ${requestScope.viewSign eq '1' ? 'more' : 'less'}</span> views than last year</small>
                                    </div>
                                </a>
                            </div>
                            <div class="col-md-3">
                                <a>
                                    <div class="cardcard loginChart" style="background: #28a745" onclick="selectCard(this)">
                                        <p class="mb-1">Total Login</p>
                                        <div class="stat-box">
                                            <h3>${requestScope.login}</h3>
                                            <span class="stat-icon green">
                                                <c:choose>
                                                    <c:when test="${requestScope.loginSign eq '1'}">
                                                        <div style="color: #28a745">
                                                            <i class="bi bi-caret-up-fill"></i>
                                                            ${requestScope.loginPercent}%
                                                        </div>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <div style="color: #dc3545">
                                                            <i class="bi bi-caret-down-fill"></i>
                                                            ${requestScope.loginPercent}%
                                                        </div>
                                                    </c:otherwise>
                                                </c:choose>
                                            </span>
                                        </div>
                                        <small>You have <span style="font-weight: bold">${requestScope.loginDiff} ${requestScope.loginSign eq '1' ? 'more' : 'less'}</span> login than last year</small>
                                    </div>
                                </a>
                            </div>
                            <div class="col-md-3">
                                <a>
                                    <div class="cardcard ticketChart" style="background: #ff9f00" onclick="selectCard(this)">
                                        <p class="mb-1">Total Tickets</p>
                                        <div class="stat-box">
                                            <h3>${requestScope.ticket}</h3>
                                            <span class="stat-icon orange">
                                                <c:choose>
                                                    <c:when test="${requestScope.ticketSign eq '1'}">
                                                        <div style="color: #28a745">
                                                            <i class="bi bi-caret-up-fill"></i>
                                                            ${requestScope.ticketPercent}%
                                                        </div>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <div style="color: #dc3545">
                                                            <i class="bi bi-caret-down-fill"></i>
                                                            ${requestScope.ticketPercent}%
                                                        </div>
                                                    </c:otherwise>
                                                </c:choose>
                                            </span>
                                        </div>
                                        <small>You sale <span style="font-weight: bold">${requestScope.ticketDiff} ${requestScope.ticketSign eq '1' ? 'more' : 'less'}</span> ticket than last year</small>
                                    </div>
                                </a>
                            </div>
                            <div class="col-md-3">
                                <a>
                                    <div class="cardcard saleChart" style="background: #dc3545" onclick="selectCard(this)">
                                        <p class="mb-1">Total Sales</p>
                                        <div class="stat-box">
                                            <h3>${requestScope.sale}$</h3>
                                            <span class="stat-icon green">
                                                <c:choose>
                                                    <c:when test="${requestScope.saleSign eq '1'}">
                                                        <div style="color: #28a745">
                                                            <i class="bi bi-caret-up-fill"></i>
                                                            ${requestScope.salePercent}%
                                                        </div>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <div style="color: #dc3545">
                                                            <i class="bi bi-caret-down-fill"></i>
                                                            ${requestScope.salePercent}%
                                                        </div>
                                                    </c:otherwise>
                                                </c:choose>
                                            </span>
                                        </div>
                                        <small>You made <span style="font-weight: bold">${requestScope.saleDiff}$ ${requestScope.saleSign eq '1' ? 'more' : 'less'}</span> than last year</small>
                                    </div>
                                </a>
                            </div>
                        </div>

                        <!-- Charts Section -->
                        <div class="row mt-4">
                            <div id="">
                                <div class="chart-container">
                                    <h5>Bar Chart</h5>
                                    <canvas id="visitorChart"></canvas>
                                </div>
                            </div>
                        </div>
                        <div class="card mb-4">
                            <div class="card-header">
                                <i class="fas fa-table me-1"></i>
                                DataTable Example
                            </div>
                            <div class="card-body">
                                <table id="datatablesSimple">
                                    <thead>
                                        <tr>
                                            <th>Name</th>
                                            <th>Position</th>
                                            <th>Office</th>
                                            <th>Age</th>
                                            <th>Start date</th>
                                            <th>Salary</th>
                                        </tr>
                                    </thead>
                                    <tfoot>
                                        <tr>
                                            <th>Name</th>
                                            <th>Position</th>
                                            <th>Office</th>
                                            <th>Age</th>
                                            <th>Start date</th>
                                            <th>Salary</th>
                                        </tr>
                                    </tfoot>
                                    <tbody>
                                        <tr>
                                            <td>Michael Bruce</td>
                                            <td>Javascript Developer</td>
                                            <td>Singapore</td>
                                            <td>29</td>
                                            <td>2011/06/27</td>
                                            <td>$183,000</td>
                                        </tr>
                                        <tr>
                                            <td>Donna Snider</td>
                                            <td>Customer Support</td>
                                            <td>New York</td>
                                            <td>27</td>
                                            <td>2011/01/25</td>
                                            <td>$112,000</td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </main>
                <footer class="py-4 bg-light mt-auto">
                    <div class="container-fluid px-4">
                        <div class="d-flex align-items-center justify-content-between small">
                            <div class="text-muted">Copyright &copy; Your Website 2023</div>
                            <div>
                                <a href="#">Privacy Policy</a>
                                &middot;
                                <a href="#">Terms &amp; Conditions</a>
                            </div>
                        </div>
                    </div>
                </footer>
            </div>
        </div>
        <script src="js/dashboard.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <script src="https://unpkg.com/htmx.org@2.0.4"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/umd/simple-datatables.min.js" crossorigin="anonymous"></script>
        <script src="js/datatables-simple-demo.js"></script>
    </body>
</html>
