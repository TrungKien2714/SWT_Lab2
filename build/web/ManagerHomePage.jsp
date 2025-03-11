<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manager Dashboard</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        body {
            background-color: #f8f9fa;
            font-family: Arial, sans-serif;
        }
        .sidebar {
            width: 250px;
            height: 100vh;
            background: #002147;
            color: white;
            position: fixed;
            padding-top: 20px;
        }
        .sidebar h4 {
            text-align: center;
            margin-bottom: 30px; /* Tăng khoảng cách giữa tiêu đề và các option bên dưới */
        }
        .sidebar a {
            color: white;
            display: block;
            padding: 15px;
            text-decoration: none;
        }
        .sidebar a:hover {
            background: #003366;
        }
        .content {
            margin-left: 260px;
            padding: 20px;
        }
        .card {
            border-radius: 10px;
        }
    </style>
</head>
<body>
    <div class="sidebar">
        <h4 class="text-center">Manager Panel</h4>
        <a href="ManagerHomePage.jsp"><i class="fa fa-home"></i> Dashboard</a>
        <a href="#"><i class="fa fa-users"></i> Manage Users</a>
        <a href="listtrain"><i class="fa fa-ticket"></i> Manage Train</a>
        <a href="#"><i class="fa fa-ticket"></i> Manage Route</a>
        <a href="#"><i class="fa fa-ticket"></i> Account</a>
        <a href="#"><i class="fa fa-chart-bar"></i> Reports</a>
        <a href="#"><i class="fa fa-cog"></i> Settings</a>
    </div>


    <div class="content">
        <h2>Dashboard</h2>
        <div class="row">
            <div class="col-md-4">
                <div class="card text-white bg-primary mb-3">
                    <div class="card-body">
                        <h5 class="card-title">Total Users</h5>
                        <p class="card-text">1200</p>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card text-white bg-success mb-3">
                    <div class="card-body">
                        <h5 class="card-title">Total Tickets</h5>
                        <p class="card-text">3000</p>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card text-white bg-warning mb-3">
                    <div class="card-body">
                        <h5 class="card-title">Pending Requests</h5>
                        <p class="card-text">50</p>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
