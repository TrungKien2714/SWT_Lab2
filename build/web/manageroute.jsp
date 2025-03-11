<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Quản Lý Vé</title>
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
                font-size: 1.5rem;
                font-weight: bold;
                box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            }
            .main-content {
                display: flex;
                flex: 1;
            }
            .sidebar {
                width: 250px;
                background-color: #ffffff;
                padding: 20px;
                border-right: 1px solid #eaeaea;
                box-shadow: 2px 0 5px rgba(0, 0, 0, 0.05);
            }
            .sidebar h5 {
                font-size: 1.5rem;
                margin-bottom: 20px;
                color: #007bff;
                text-align: center;
                font-weight: bold;
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
            }
            .table-container {
                background-color: #ffffff;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            }
            .btn {
                font-size: 0.9rem;
                padding: 8px 15px;
                border-radius: 8px;
            }
            .btn-success {
                background-color: #28a745;
                border: none;
            }
            .btn-success:hover {
                background-color: #218838;
            }
            .btn-danger {
                background-color: #dc3545;
                border: none;
            }
            .btn-danger:hover {
                background-color: #c82333;
            }
            .btn-warning {
                background-color: #ffc107;
                border: none;
                color: #212529;
            }
            .btn-warning:hover {
                background-color: #e0a800;
            }
            table {
                border-collapse: collapse;
                width: 100%;
                margin-top: 15px;
            }
            table thead th {
                background-color: #007bff;
                color: white;
                padding: 15px;
                text-align: center;
            }
            table tbody td {
                padding: 15px;
                text-align: center;
                border-bottom: 1px solid #eaeaea;
            }
            table tbody tr:nth-child(even) {
                background-color: #f9f9f9;
            }
            .toggle-switch {
                position: relative;
                display: inline-block;
                width: 40px;
                height: 20px;
            }
            .toggle-switch input {
                opacity: 0;
                width: 0;
                height: 0;
            }
            .toggle-slider {
                position: absolute;
                top: 0;
                left: 0;
                right: 0;
                bottom: 0;
                background-color: #ccc;
                border-radius: 20px;
                transition: 0.4s;
            }
            .toggle-slider:before {
                position: absolute;
                content: "";
                height: 14px;
                width: 14px;
                left: 3px;
                bottom: 3px;
                background-color: white;
                border-radius: 50%;
                transition: 0.4s;
            }
            input:checked + .toggle-slider {
                background-color: #28a745;
            }
            input:checked + .toggle-slider:before {
                transform: translateX(20px);
            }
        </style>
    </head>
    <body>
            <!-- Hiển thị thông báo nếu có -->

            
        <div class="header">
            Vé Tàu TraTra
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
                <h3>Quản Lý Chuyến Tàu</h3>
                <div class="table-container">
                    <div class="d-flex justify-content-end align-items-center mb-3">
                        <a href="liststation" class="btn btn-success me-2">Thêm Chuyến</a>
                        <a href="ViewRouteDeleted"  class="btn btn-danger">Lịch Sử Xóa</a>
                    </div>
                    <table>
                        <thead>
                            <tr>
                                <th>Mã Chuyến</th>
                                <th>Ga Đi</th>
                                <th>Ga Đến</th>
                                <th>Ngày Đi</th>
                                <th>Giờ Đi</th>
                                <th>Giờ Đến</th>
                                <th>Thao Tác</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items ="${listroute}" var="r">
                                <tr>
                                    <td>${r.routeCode}</td>
                                    <td>${r.departureStation}</td>
                                    <td>${r.arrivalStation}</td>
                                    <td>${r.departureDate}</td>  <!-- Lấy ngày đi -->
                                    <td>${r.departureHour}</td>  <!-- Lấy giờ đi -->
                                    <td>${r.arrivalHour}</td>    <!-- Lấy giờ đến -->
                                    <td>
                                        <!-- Form tự động submit khi checkbox thay đổi -->
                                        <form action="updatestatusroute" method="POST" id="form-${r.id}">
                                            <input type="hidden" name="routeid" value="${r.id}">
                                            <input type="hidden" name="status" value="0"> <!-- Nếu unchecked thì gửi giá trị 0 -->
                                            <!-- Công tắc trượt -->
                                            <label class="toggle-switch">
                                                <input type="checkbox" name="status" value="1" ${r.status == 1 ? 'checked' : ''} onchange="document.getElementById('form-${r.routeCode}').submit()">
                                                <span class="toggle-slider"></span>
                                            </label>
                                        </form>
                                        <!-- Nút cập nhật và xóa -->
                                        <a href="updateroute?routeid=${r.id}" class="btn btn-warning btn-sm">✎</a>
                                        <a href="deleteroute?routeid=${r.id}" class="btn btn-danger btn-sm" onclick="return confirm('Bạn chắc chắn xóa chứ?');">🗑</a>
                                    </td>
                                </tr>   
                            </c:forEach>

                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </body>
</html>
