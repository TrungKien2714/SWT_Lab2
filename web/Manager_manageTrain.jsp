<%-- 
    Document   : Manager_manageTrain
    Created on : Mar 2, 2025, 10:58:40 AM
    Author     : ASUS
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Manage Train</title>
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
                margin-bottom: 30px;
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
            .table-container {
                background: white;
                padding: 20px;
                border-radius: 10px;
                box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
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
            <h2>Manage Train</h2>
            <div class="table-container">
                <div class="d-flex justify-content-between mb-3">
                    <h4>Train List</h4>
                    <a href="AddTrain" class="btn btn-primary"><i class="fa fa-plus"></i> Add Train</a>
                </div>
                <form action="FilterTrain" method="get" class="mb-3">
                    <div class="row">
                        <!-- Select cho Model tàu -->
                        <div class="col-md-4">
                            <select name="trainModel" class="form-control">
                                <option value="">-- Model Type --</option>
                                <c:forEach var="t" items="${alltrain}">
                                    <option value="${t.trainid}"
                                            <c:if test="${param.trainModel eq t.trainid}">selected</c:if>
                                            >${t.trainid}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <!-- Select cho Owner -->
                        <div class="col-md-4">
                            <select name="owner" class="form-control">
                                <option value="">-- Owner --</option>
                                <c:forEach var="entry" items="${sellerMap}">
                                    <option value="${entry.key}" <c:if test="${param.owner eq entry.key}">selected</c:if>>${entry.value}</option>
                                </c:forEach>
                            </select>

                        </div>

                        <div class="col-md-4">
                            <button type="submit" class="btn btn-primary">
                                <i class="fa fa-search"></i> Search
                            </button>
                        </div>
                    </div>
                </form>


                <table class="table table-striped">
                    <thead class="table-dark">
                        <tr>
                            <th>ID</th>
                            <th>Model</th>
                            <th>Total Seat</th>
                            <th>Total Cabin</th>
                            <th>Status</th>
                            <th>Owner</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${alltrain}" var="t">
                            <!-- Chỉ hiển thị tàu có status là Active -->
                            <c:if test="${t.status == 1}">
                                <tr>
                                    <td>${t.id}</td>
                                    <td>${t.trainid}</td>
                                    <td>${t.totalSeats}</td>
                                    <td>${t.numCabin}</td>
                                    <td>
                                        <!-- Form cập nhật trạng thái tàu -->
                                        <form action="UpdateStatusTrain" method="get">
                                            <input type="hidden" name="trainId" value="${t.id}">
                                            <select name="status" onchange="
                                                    var newStatus = this.options[this.selectedIndex].text;
                                                    if (confirm('Bạn có chắc chắn muốn chuyển sang trạng thái ' + newStatus + '?')) {
                                                        this.form.submit();
                                                    } else {
                                                        // Nếu người dùng bấm Cancel, thiết lập lại giá trị về 'Active'
                                                        this.selectedIndex = 0;
                                                    }
                                                    ">
                                                <option value="1" selected>Active</option>
                                                <option value="2">Maintenance</option>
                                                <option value="0">Inactive</option>
                                            </select>
                                        </form>
                                    </td>



                                    <td>${sellerMap[t.owner]}</td>
                                    <td>
                                        <a href="editTrain?id=${t.id}" class="btn btn-warning btn-sm" title="Edit">
                                            <i class="fa fa-pencil-alt"></i>
                                        </a>
                                        <a href="DetailsTrain?id=${t.id}" class="btn btn-info btn-sm" title="Details">
                                            <i class="fa fa-eye"></i>
                                        </a>
                                    </td>
                                </tr>  
                            </c:if>
                        </c:forEach>
                    </tbody>

                </table>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
