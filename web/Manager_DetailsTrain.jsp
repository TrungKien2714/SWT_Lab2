<%-- 
    Document   : Manager_DetailsTrain
    Created on : Mar 2, 2025, 4:47:10 PM
    Author     : ASUS
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Train Details</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        body {
            background-color: #f8f9fa;
            font-family: Arial, sans-serif;
        }
        .container {
            background: white;
            padding: 20px;
            margin-top: 50px;
            border-radius: 10px;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
        }
        .train-header {
            display: flex;
            align-items: center;
            justify-content: space-between;
        }
        .cabin-card {
            border-radius: 10px;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
            transition: 0.3s;
        }
        .cabin-card:hover {
            transform: scale(1.05);
        }
        .cabin-img {
            width: 100%;
            height: 150px;
            object-fit: cover;
            border-top-left-radius: 10px;
            border-top-right-radius: 10px;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="train-header">
            <h2>Train Details</h2>
            <a href="listtrain" class="btn btn-secondary"><i class="fa fa-arrow-left"></i> Back</a>
        </div>
        <hr>
        
        <h4>Train Information</h4>
        <table class="table table-bordered">
            <tr><th>ID</th><td>${train.id}</td></tr>
            <tr><th>Model</th><td>${train.trainid}</td></tr>
            <tr><th>Total Seats</th><td>${train.totalSeats}</td></tr>
            <tr><th>Total Cabins</th><td>${train.numCabin}</td></tr>
            <tr><th>Status</th><td>
                <c:choose>
                    <c:when test="${train.status == 1}"><span class="badge bg-success">Active</span></c:when>
                    <c:when test="${train.status == 0}"><span class="badge bg-warning text-dark">Under Maintenance</span></c:when>
                    <c:when test="${train.status == 2}"><span class="badge bg-danger">Inactive</span></c:when>
                </c:choose>
            </td></tr>
            <tr><th>Owner</th><td>${train.owner}</td></tr>
        </table>
        
        <h4>Cabins</h4>
        <div class="row">
            <c:forEach items="${cabinlist}" var="cabin">
                <div class="col-md-4 mb-4">
                    <div class="card cabin-card">
                        <img src="${cabin.imgUrl}" class="cabin-img" alt="Cabin Image">
                        <div class="card-body">
                            <h5 class="card-title">${cabin.cabinName}</h5>
                            <p class="card-text"><strong>Class:</strong> ${cabin.type}</p>
                            <p class="card-text"><strong>Seats:</strong> ${cabin.numSeat}</p>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
