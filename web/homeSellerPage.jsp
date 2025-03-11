<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Seller Home</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <style>
        body {
            display: flex;
            flex-direction: column;
            min-height: 100vh;
            margin: 0;
            font-family: 'Arial', sans-serif;
            background-color: #f5f8fa;
        }
        .header {
            width: 100%;
            background-color: #007bff;
            color: white;
            padding: 10px 20px;
            text-align: center;
            font-size: 1.8rem;
            font-weight: bold;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }
        .main-content {
            display: flex;
            flex: 1;
        }
        .sidebar {
            width: 250px;
            background-color: #f8f9fa;
            padding: 20px;
            color: #333;
            border-right: 1px solid #ddd;
        }
        .sidebar h5 {
            font-size: 1.6rem;
            margin-bottom: 20px;
            text-align: center;
            font-weight: bold;
            color: #007bff;
        }
        .sidebar ul {
            list-style: none;
            padding: 0;
            margin: 0;
        }
        .sidebar ul li {
            margin-bottom: 15px;
        }
        .sidebar ul li a {
            text-decoration: none;
            color: #333;
            font-weight: 500;
            display: block;
            padding: 10px;
            border-radius: 8px;
            transition: all 0.3s ease;
        }
        .sidebar ul li a:hover {
            background-color: #007bff;
            color: white;
        }
        .content {
            flex: 1;
            padding: 30px;
            display: flex;
            justify-content: center;
            align-items: center;
            text-align: center;
        }
        .welcome-box {
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 800px;
        }
        .welcome-box h3 {
            color: #007bff;
            font-size: 2rem;
            margin-bottom: 10px;
        }
    </style>
</head>
<body>
    <div class="header">
        Seller Dashboard
    </div>
    <div class="main-content">
        <div class="sidebar">
            <h5>Menu</h5>
            <ul>
                <li><a href="homeSellerPage.jsp">Thống Kê</a></li>
                <li><a href="viewlistroute">Quản Lý Chuyến Tàu</a></li>
                <li><a href="#">Lịch Trình</a></li>
                <li><a href="#">Tàu</a></li>
                <li><a href="#">Tài Khoản</a></li>
                <li><a href="#">Thông Tin Account</a></li>
            </ul>
        </div>
        <div class="content">
            <div class="welcome-box">
                <h3>Chào mừng đến với Trang Quản Lý Đối Tác</h3>
                <p>Quản lý tàu, chuyến tàu và doanh thu của bạn một cách dễ dàng.</p>
            </div>
        </div>
    </div>
</body>
</html>