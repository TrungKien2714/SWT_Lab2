<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thêm Chuyến Tàu</title>
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
        }
        .form-container {
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 1200px;
        }
        .form-container h3 {
            text-align: center;
            color: #007bff;
            font-size: 1.8rem;
            margin-bottom: 20px;
        }
        .btn {
            font-size: 0.9rem;
            padding: 8px 15px;
            border-radius: 8px;
        }
        .btn-success {
            background-color: #28a745;
            border: none;
            color: white;
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
    </style>
</head>
<body>
    <div class="header">
        Vé Tàu DSVN
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
            <div class="form-container">
                <h3>Thêm Chuyến Tàu</h3>
                <form action="AddRoute" method="POST">
                    <div class="row mb-3">
                        <div class="col-md-6">
                            <label for="trainid" class="form-label">Loại Tàu</label>
                            <select class="form-select" id="trainid" name="trainid">
                                <option selected>Type</option>
                                <c:forEach items="${listtrain}" var="t">
                                    <option value="${t.id}">${t.trainid}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-md-6">
                            <label for="routecode" class="form-label">Mã Chuyến Đi</label>
                            <input type="text" class="form-control" id="routecode" name="routecode" placeholder="Nhập mã chuyến đi">
                        </div>
                    </div>
                    <div class="row mb-3">
                        <div class="col-md-12">
                            <label for="description" class="form-label">Nội Dung Mô Tả</label>
                            <textarea class="form-control" id="description" name="description" rows="3" placeholder="Nhập nội dung mô tả"></textarea>
                        </div>
                    </div>
                    <div class="row mb-3">
                        <div class="col-md-6">
                            <label for="departureDateTime" class="form-label">Ngày & Giờ Đi</label>
                            <input type="datetime-local" class="form-control" id="departureDateTime" name="departureDateTime">
                        </div>
                        <div class="col-md-6">
                            <label for="returnDateTime" class="form-label">Ngày & Giờ Về</label>
                            <input type="datetime-local" class="form-control" id="returnDateTime" name="returnDateTime">
                        </div>
                    </div>
                    <div class="row mb-3">
                        <div class="col-md-6">
                            <label for="departureStation" class="form-label">Từ Ga</label>
                            <select class="form-select" id="departureStation" name="departureStation">
                                <option selected>Chọn Ga</option>
                                <c:forEach items="${liststation}" var="o">
                                    <option value="${o.id}">${o.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-md-6">
                            <label for="arrivalStation" class="form-label">Đến Ga</label>
                            <select class="form-select" id="arrivalStation" name="arrivalStation">
                                <option selected>Chọn Ga</option>
                                <c:forEach items="${liststation}" var="o">
                                    <option value="${o.id}">${o.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="d-flex justify-content-end">
                        <button type="submit" class="btn btn-success me-2">Thêm</button>
                        <button type="button" class="btn btn-danger" onclick="window.location.href='viewlistroute'">Hủy</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</body>
</html>
