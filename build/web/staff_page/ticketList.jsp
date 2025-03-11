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
        <title>Account List</title>
        <link href="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css" rel="stylesheet" />
        <link href="css/styles.css" rel="stylesheet" />
        <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    </head>
    <body class="sb-nav-fixed">
        <%@ include file="staffNavBar.jsp" %>

        <div id="layoutSidenav">
            <%@ include file="staffSideBar.jsp" %>
            <div id="layoutSidenav_content">
                <main>
                    <div class="container-fluid px-4">
                        <h1 class="mt-4">List of Tickets</h1>
                        <div style="margin-bottom: 20px; display: flex; justify-content: space-between">
                        </div>
                        <div class="card mb-4">
                            <div class="card-body">
                                <table id="datatablesSimple" class="table table-striped table-bordered">
                                    <thead class="thead-dark">
                                        <tr>
                                            <th>Full Name</th>
                                            <th>Departure Station</th>
                                            <th>Arrival Station</th>
                                            <th>Amount</th>
                                            <th>Status</th>
                                            <th>Actions</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="ticket" items="${sessionScope.ticket}">
                                            <tr>
                                                <td><c:out value="${ticket.fullName}" /></td>
                                                <td><c:out value="${ticket.departureStation}" /></td>
                                                <td><c:out value="${ticket.arrivalStation}" /></td>
                                                <td><c:out value="${ticket.amountPaid}" /></td>
                                                <td style="font-weight: bold; color: ${ticket.status eq 1 ? 'green' : 'red'};">
                                                    <c:out value="${ticket.status eq 1 ? 'Active' : 'Inactive'}" />
                                                </td>
                                                <td>
                                                    <div class="action-group">
                                                        <!-- Nút View -->
                                                        <a class="btn btn-primary btn-sm" href="../staff_page/viewTicket?id=${ticket.id}" role="button">
                                                            <i class="fas fa-eye"></i>
                                                        </a>

                                                        <!-- Nút Edit -->
                                                        <a class="btn btn-warning btn-sm" href="../staff_page/editTicket?id=${ticket.id}" role="button">
                                                            <i class="fas fa-pen"></i>
                                                        </a>

                                                        <!-- Nút Enable/Disable -->
                                                        <c:choose>
                                                            <c:when test="${ticket.status eq 1}">
                                                                <a class="btn btn-danger btn-sm" 
                                                                   href="javascript:void(0);"
                                                                   onclick="confirmStatusChange(${ticket.id}, 'disable')">
                                                                    <i class="fas fa-toggle-on"></i> Disable
                                                                </a>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <a class="btn btn-success btn-sm" 
                                                                   href="javascript:void(0);"
                                                                   onclick="confirmStatusChange(${ticket.id}, 'enable')">
                                                                    <i class="fas fa-toggle-off"></i> Enable
                                                                </a>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </div>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </main>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
        <script src="js/scripts.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/umd/simple-datatables.min.js" crossorigin="anonymous"></script>
        <script src="js/datatables-simple-demo.js"></script>

        <script>
            function confirmStatusChange(ticketId, action) {
                let actionText = action === "enable" ? "bật" : "tắt";
                let redirectUrl = "./ChangeStatusTicket?id=" + ticketId + "&success=true";

                Swal.fire({
                    title: "Bạn có muốn " + actionText + " vé này không?",
                    showCancelButton: true,
                    confirmButtonText: "Xác nhận",
                    cancelButtonText: "Hủy"
                }).then((result) => {
                    if (result.isConfirmed) {
                        window.location.href = redirectUrl;
                    }
                });
            }

            document.addEventListener("DOMContentLoaded", function () {
    const urlParams = new URLSearchParams(window.location.search);
    if (urlParams.has("success")) {
        Swal.fire({
            title: "Đã cập nhật trạng thái vé thành công!",
            icon: "success",
            confirmButtonText: "OK"
        });
        // Xóa tham số để tránh hiển thị lại khi reload trang
        const newUrl = window.location.pathname;
        window.history.replaceState({}, document.title, newUrl);
    }
});

        </script>
    </body>
</html>
