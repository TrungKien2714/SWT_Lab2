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
        <style>
            th{
                text-align: center;
            }
        </style>
    </head >
    <body class="sb-nav-fixed">

        <%@ include file="staffNavBar.jsp" %>

        <div id="layoutSidenav">
            <%@ include file="staffSideBar.jsp" %>

            <div id="layoutSidenav_content">
                <main>
                    <div class="container mt-5 d-flex justify-content-center">
                        <div class="card col-md-6">
                            <div class="card-header bg-primary text-white text-center">
                                Edit Ticket Information
                            </div>
                            <div class="card-body">
                                <c:choose>
                                    <c:when test="${not empty sessionScope.ticket}">
                                        <c:set var="ticket" value="${sessionScope.ticket}" />

                                        <form action="editTicket" method="post">
                                            <input type="hidden" name="id" value="${ticket.id}" />

                                            <div class="mb-3">
                                                <label class="form-label">Full Name</label>
                                                <input type="text" class="form-control" name="fullName" value="${ticket.fullName}" required>
                                            </div>

                                            <div class="mb-3">
                                                <label class="form-label">Phone Number</label>
                                                <input type="text" class="form-control" name="phoneNumber" value="${ticket.phoneNumber}" required>
                                            </div>
                                            <div class="mb-3">
                                                <label class="form-label">Address</label>
                                                <input type="text" class="form-control" name="address" value="${ticket.address}" required>
                                            </div>

                                            <div class="mb-3">
                                                <label class="form-label">Departure Station</label>
                                                <input type="text" class="form-control" name="departureStation" value="${ticket.departureStation}" required readonly="">
                                            </div>

                                            <div class="mb-3">
                                                <label class="form-label">Arrival Station</label>
                                                <input type="text" class="form-control" name="arrivalStation" value="${ticket.arrivalStation}" required readonly="">
                                            </div>

                                            <div class="mb-3">
                                                <label class="form-label">Departure Time</label>
                                                <input type="datetime-local" class="form-control" name="departureTime" value="${ticket.departureTime}" required readonly="">
                                            </div>

                                            <div class="mb-3">
                                                <label class="form-label">Arrival Time</label>
                                                <input type="datetime-local" class="form-control" name="arrivalTime" value="${ticket.arrivalTime}" required readonly="">
                                            </div>

                                            <div class="mb-3">
                                                <label class="form-label">Seat ID</label>
                                                <input type="number" class="form-control" name="seatId" value="${ticket.seatId}" required readonly="">
                                            </div>

                                            <div class="mb-3">
                                                <label class="form-label">Amount Paid (VND)</label>
                                                <input type="number" class="form-control" name="amountPaid" value="${ticket.amountPaid}" required readonly="">
                                            </div>

                                            <div class="mb-3">
                                                <label class="form-label">Luggage Type</label>
                                                <select class="form-select" name="luggageType" required>
                                                    <option value="1" ${ticket.luggageType == 1 ? 'selected' : ''}>15kg</option>
                                                    <option value="2" ${ticket.luggageType == 2 ? 'selected' : ''}>40kg</option>
                                                    <option value="3" ${ticket.luggageType == 3 ? 'selected' : ''}>75kg</option>
                                                </select>
                                            </div>

                                            <div class="text-center">
                                                <button type="submit" class="btn btn-success">Save</button>
                                                <a href="../staff_page/ticketList" class="btn btn-danger">Cancel</a>
                                            </div>
                                        </form>

                                    </c:when>
                                    <c:otherwise>
                                        <div class="alert alert-danger text-center">Ticket not found.</div>
                                        <div class="text-center">
                                            <a href="ticketList.jsp" class="btn btn-secondary">Back to List</a>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                </main>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
        <script src="js/scripts.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/umd/simple-datatables.min.js" crossorigin="anonymous"></script>
        <script src="js/datatables-simple-demo.js">
        </script>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <script>
            document.addEventListener("DOMContentLoaded", function () {
                document.querySelector("form[action='editTicket']").addEventListener("submit", function (event) {
                    event.preventDefault(); // Ngăn chặn form gửi ngay lập tức

                    Swal.fire({
                        title: "Bạn có chắc chắn muốn thay đổi thông tin?",
                        icon: "warning",
                        showCancelButton: true,
                        confirmButtonText: "Lưu",
                        cancelButtonText: "Hủy"
                    }).then((result) => {
                        if (result.isConfirmed) {
                            event.target.submit(); // Gửi form nếu xác nhận
                        }
                    });
                });
            });
        </script>

    </body>
</html>
