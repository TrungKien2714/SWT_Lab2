<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="Staff Dashboard">
        <meta name="author" content="Spike Admin">
        <title>Dashboard - Staff</title>

        <!-- CSS -->
        <link href="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css" rel="stylesheet">
        <link href="css/styles.css" rel="stylesheet">
        <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
    </head>
    <body class="sb-nav-fixed">
        
        <%@ include file="staffNavBar.jsp" %>

        <div id="layoutSidenav">
            <%@ include file="staffSideBar.jsp" %>

            <div id="layoutSidenav_content">
                <main>
                    <div class="container-fluid px-4">
                        <h1 class="mt-4">Dashboard</h1>
                        <ol class="breadcrumb mb-4">
                            <li class="breadcrumb-item active">Staff Overview</li>
                        </ol>

                        <!-- Biểu đồ Profit & Expenses -->
                        <div class="row">
                            <div class="col-xl-8">
                                <div class="card mb-4">
                                    <div class="card-header">
                                        <i class="fas fa-chart-bar me-1"></i>
                                        Profit & Expenses
                                    </div>
                                    <div class="card-body">
                                        <canvas id="profitExpensesChart" width="100%" height="40"></canvas>
                                    </div>
                                </div>
                            </div>

                            <!-- Traffic & Product Sales -->
                            <div class="col-xl-4">
                                <div class="card mb-4">
                                    <div class="card-body">
                                        <h5 class="card-title">Traffic Distribution</h5>
                                        <h3>$36,358</h3>
                                        <small>+9% last year</small>
                                        <canvas id="trafficChart"></canvas>
                                    </div>
                                </div>

                                <div class="card mb-4">
                                    <div class="card-body">
                                        <h5 class="card-title">Product Sales</h5>
                                        <h3>$6,820</h3>
                                        <small>+9% last year</small>
                                        <canvas id="productSalesChart"></canvas>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Lịch trình & Khách hàng -->
                        <div class="row">
                            <div class="col-xl-6">
                                <div class="card mb-4">
                                    <div class="card-header">Upcoming Schedules</div>
                                    <div class="card-body">
                                        <ul class="list-group">
                                            <li class="list-group-item">09:30 AM - Payment received from John Doe</li>
                                            <li class="list-group-item">10:00 AM - New sale recorded #ML-3467</li>
                                            <li class="list-group-item">12:00 AM - Payment of $64.95 to Michael</li>
                                        </ul>
                                    </div>
                                </div>
                            </div>

                            <div class="col-xl-6">
                                <div class="card mb-4">
                                    <div class="card-header">Top Paying Clients</div>
                                    <div class="card-body">
                                        <table class="table">
                                            <thead>
                                                <tr>
                                                    <th>Profile</th>
                                                    <th>Hour Rate</th>
                                                    <th>Extra Classes</th>
                                                    <th>Status</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <tr>
                                                    <td>Mark J. Freeman</td>
                                                    <td>$150/hour</td>
                                                    <td>+53</td>
                                                    <td><span class="badge bg-success">Available</span></td>
                                                </tr>
                                                <tr>
                                                    <td>Nina R. Oldman</td>
                                                    <td>$150/hour</td>
                                                    <td>+68</td>
                                                    <td><span class="badge bg-primary">In Class</span></td>
                                                </tr>
                                                <tr>
                                                    <td>Arya H. Shah</td>
                                                    <td>$150/hour</td>
                                                    <td>+94</td>
                                                    <td><span class="badge bg-danger">Absent</span></td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </main>

                <!-- Footer -->
                <footer class="py-4 bg-light mt-auto">
                    <div class="container-fluid px-4">
                        <div class="d-flex align-items-center justify-content-between small">
                            <div class="text-muted">Copyright &copy; Spike Admin 2025</div>
                            <div>
                                <a href="#">Privacy Policy</a>
                                &middot;
                                <a href="#">Terms & Conditions</a>
                            </div>
                        </div>
                    </div>
                </footer>
            </div>
        </div>

        <!-- JavaScript -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
        <script src="js/scripts.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js" crossorigin="anonymous"></script>

        <script>
            // Biểu đồ Profit & Expenses
            var ctx = document.getElementById('profitExpensesChart').getContext('2d');
            var profitExpensesChart = new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: ['Aug', 'Sep', 'Oct', 'Nov', 'Dec', 'Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul'],
                    datasets: [{
                        label: 'Profit',
                        backgroundColor: 'blue',
                        data: [20, 10, 15, 30, 18, 22, 20, 10, 28, 25, 12, 14]
                    }, {
                        label: 'Expenses',
                        backgroundColor: 'salmon',
                        data: [-10, -15, -8, -12, -10, -15, -10, -12, -15, -8, -10, -12]
                    }]
                }
            });

            // Biểu đồ Traffic
            var trafficCtx = document.getElementById('trafficChart').getContext('2d');
            var trafficChart = new Chart(trafficCtx, {
                type: 'doughnut',
                data: {
                    labels: ['Organic', 'Referral'],
                    datasets: [{
                        data: [60, 40],
                        backgroundColor: ['blue', 'salmon']
                    }]
                }
            });

            // Biểu đồ Product Sales
            var productSalesCtx = document.getElementById('productSalesChart').getContext('2d');
            var productSalesChart = new Chart(productSalesCtx, {
                type: 'line',
                data: {
                    labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun'],
                    datasets: [{
                        label: 'Sales',
                        borderColor: 'purple',
                        data: [5000, 6000, 5500, 7000, 6500, 6820]
                    }]
                }
            });
        </script>
    </body>
</html>
