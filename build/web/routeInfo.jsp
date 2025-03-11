<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page import="com.google.gson.Gson" %>
<%@page import="java.util.Map" %>
<% Map<String, Object> routeData = (Map<String, Object>) request.getAttribute("routeData"); %>
<script>
    let cabins = <%= new Gson().toJson(request.getAttribute("cabins")) %>;
    let selectedSeatsByCabin = {}; // Object to store selected seats by cabin ID
    let currentCabin = null; // Keep track of current cabin
</script>
<!DOCTYPE html>
<html>
    <style>
        body{
            background:#f5f5f5;
            margin-top:20px;
            padding-top: 80px;
        }
        .icon-hover:hover {
            border-color: #3b71ca !important;
            background-color: white !important;
            color: #3b71ca !important;
        }

        .icon-hover:hover i {
            color: #3b71ca !important;
        }

        /* Thêm CSS cho phần sơ đồ ghế */
        .seat-map-container {
            margin-top: 20px;
            border: 1px solid #ddd;
            border-radius: 8px;
            padding: 15px;
            background-color: #fff;
        }

        .seat-map-title {
            color: #3080c7;
            margin-bottom: 15px;
            font-weight: bold;
        }

        .coach-row {
            display: flex;
            justify-content: space-between;
            margin-bottom: 15px;
        }

        .seat {
            width: 40px;
            height: 40px;
            border: 1px solid #b89d58;
            border-radius: 5px;
            display: flex;
            align-items: center;
            justify-content: center;
            margin: 0 5px;
            font-weight: bold;
            cursor: pointer;
            position: relative;
            background-color: white;
        }

        .seat::before {
            content: '';
            position: absolute;
            left: -2px;
            top: 0;
            bottom: 0;
            width: 4px;
            background-color: #b89d58;
            border-radius: 2px 0 0 2px;
        }

        .seat.reserved {
            background-color: #e74c3c;
            color: white;
        }

        .seat.selected {
            background-color: #27ae60;
            color: white;
        }

        .aisle {
            width: 30px;
            background-color: #ccc;
            margin: 0 10px;
        }

        .legend {
            display: flex;
            justify-content: center;
            margin-top: 20px;
        }

        .legend-item {
            display: flex;
            align-items: center;
            margin: 0 15px;
        }

        .legend-color {
            width: 20px;
            height: 20px;
            margin-right: 8px;
            border-radius: 3px;
        }

        .free {
            background-color: white;
            border: 1px solid #b89d58;
        }

        .reserved-color {
            background-color: #e74c3c;
        }

        .selected-color {
            background-color: #27ae60;
        }

        .summary {
            margin-top: 20px;
            padding: 15px;
            background-color: #f8f9fa;
            border-radius: 5px;
            box-shadow: 0 0.125rem 0.25rem rgba(0, 0, 0, 0.075);
        }

        .summary-title {
            color: #3080c7;
            margin-bottom: 15px;
            font-weight: bold;
        }

        .coach-container.selected {
            border: 2px solid #27ae60;
            border-radius: 5px;
        }

        .coach-container {
            display: inline-block;
            margin: 0 5px;
            cursor: pointer;
        }

    </style>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${route.routeCode}</title>
        <%@include file="components/Style.jsp" %>
    </head>
    <%@include file="components/Header.jsp" %>

    <!-- content -->
    <section class="py-5">
        <div class="container">
            <div class="row gx-5">
                <div class="border rounded-2 px-3 py-2 bg-white shadow">
                    <main class="col-lg-12">
                        <div class="ps-lg-3">
                            <h4 class="title text-dark">${route.routeCode}</h4>
                            <p>${route.description}</p>
                            <div class="mb-3">
                                <span class="h5 text-success">
                                    Economy: <fmt:formatNumber value="${route.economyPrice}" type="number" groupingUsed="true" /> VND
                                </span>
                                <span class="mx-2"> | </span>
                                <span class="h5 text-primary">
                                    Business: <fmt:formatNumber value="${route.businessPrice}" type="number" groupingUsed="true" /> VND
                                </span>
                            </div>
                            <div class="row d-flex justify-content-between">
                                <div class="col-md-6 d-flex">
                                    <dt class="fw-bold me-2">Departure Station:</dt>
                                    <dd class="me-4">${route.departureStation}</dd>
                                </div>
                                <div class="col-md-6 d-flex">
                                    <dt class="fw-bold me-2">Arrival Station:</dt>
                                    <dd>${route.arrivalStation}</dd>
                                </div>
                            </div>
                            <div class="row d-flex justify-content-between">
                                <div class="col-md-6 d-flex">
                                    <dt class="fw-bold me-2">Departure Time:</dt>
                                    <dd class="me-4">${formattedDeparture}</dd>
                                </div>
                                <div class="col-md-6 d-flex">
                                    <dt class="fw-bold me-2">Arrival Time:</dt>
                                    <dd>${formattedArrival}</dd>
                                </div>
                            </div>
                            <div class="row d-flex justify-content-between">
                                <div class="col-md-6 d-flex">
                                    <dt class="fw-bold me-2">Railway company:</dt>
                                    <dd class="me-4">${route.owner}</dd>
                                </div>
                                <div class="col-md-6 d-flex">
                                    <dt class="fw-bold me-2">Train:</dt>
                                    <dd>${route.trainName}</dd>
                                </div>
                            </div>
                            <div class="row d-flex justify-content-between">
                                <div class="col-md-6 d-flex">
                                    <dt class="fw-bold me-2">Seat economy amount:</dt>
                                    <dd class="me-4">${route.economyAmount}</dd>
                                </div>
                                <div class="col-md-6 d-flex">
                                    <dt class="fw-bold me-2">Seat business amount:</dt>
                                    <dd>${route.businessAmount}</dd>
                                </div>
                            </div>
                        </div>
                    </main>
                </div>
            </div>
        </div>
    </section>
    <!-- content -->
    <form id="paymentForm" action="PayTicketServlet" method="POST">
        <input type="hidden" name="amount" id="amount">
        <input type="hidden" name="numSeats" id="numSeats">
        <input type="hidden" name="selectedSeats" id="selectedSeats">

        <div class="container">
            <div class="row gx-4">
                <div class="col-lg-8 mb-4">
                    <div class="border rounded-2 px-3 py-2 bg-white shadow">
                        <div class="tab-content" id="ex1-content">
                            <div class="tab-pane fade show active" id="ex1-pills-1" role="tabpanel" aria-labelledby="ex1-tab-1">
                                <div class="train-container" id="trainContainer"></div>
                                <hr>
                                <div id="cabinInfo" class="cabin-info">
                                    <h4>Select a train to view information</h4>
                                </div>
                                <script>
                                    function generateTrain(cabins) {
                                        const trainContainer = document.getElementById("trainContainer");
                                        trainContainer.innerHTML = "";

                                        cabins.forEach((cabin, index) => {
                                            const coachDiv = document.createElement("div");
                                            coachDiv.classList.add("coach-container");
                                            coachDiv.innerHTML = `<img src="image/route/train.png" class="coach">`;
                                            coachDiv.dataset.index = index;
                                            coachDiv.onclick = function () {
                                                selectCoach(this, cabins[index]);
                                            };
                                            trainContainer.appendChild(coachDiv);
                                        });

                                        // Thêm đầu tàu vào cuối
                                        const headTrainDiv = document.createElement("div");
                                        headTrainDiv.classList.add("head-train");
                                        headTrainDiv.innerHTML = `<img src="image/route/head_train.png" class="coach">`;
                                        trainContainer.appendChild(headTrainDiv);
                                    }

                                    function selectCoach(selectedCoach, cabin) {
                                        console.log("📌 Cabin được chọn:", cabin);

                                        if (!cabin) {
                                            console.error("❌ Lỗi: Cabin không tồn tại!");
                                            return;
                                        }

                                        document.querySelectorAll(".coach-container").forEach(coach => {
                                            coach.classList.remove("selected");
                                        });
                                        selectedCoach.classList.add("selected");

                                        let cabinInfo = document.getElementById("cabinInfo");
                                        if (!cabinInfo) {
                                            console.error("❌ Không tìm thấy phần tử 'cabinInfo'");
                                            return;
                                        }

                                        // Xóa nội dung cũ
                                        cabinInfo.innerHTML = "";

                                        // Store current cabin
                                        currentCabin = cabin;

                                        // Initialize selected seats array for this cabin if it doesn't exist
                                        if (!selectedSeatsByCabin[cabin.id]) {
                                            selectedSeatsByCabin[cabin.id] = [];
                                        }

                                        // Hiển thị sơ đồ ghế dựa trên cabin.numSeat
                                        generateSeatMap(cabin);
                                    }

                                    // Hàm tạo sơ đồ ghế
                                    function generateSeatMap(cabin) {
                                        // Tạo hoặc lấy container cho sơ đồ ghế
                                        let seatMapContainer = document.getElementById("seatMapContainer");
                                        if (!seatMapContainer) {
                                            seatMapContainer = document.createElement("div");
                                            seatMapContainer.id = "seatMapContainer";
                                            seatMapContainer.className = "seat-map-container";
                                            document.getElementById("cabinInfo").after(seatMapContainer);
                                        }

                                        // Xóa nội dung cũ
                                        seatMapContainer.innerHTML = "";

                                        // Tạo tiêu đề
                                        const mapTitle = document.createElement("h4");
                                        mapTitle.className = "seat-map-title";
                                        mapTitle.textContent = cabin.cabinName + " - " + cabin.type;
                                        seatMapContainer.appendChild(mapTitle);

                                        // Tạo khung toa xe
                                        const coach = document.createElement("div");
                                        coach.className = "coach";
                                        seatMapContainer.appendChild(coach);

                                        const numSeats = parseInt(cabin.numSeat);
                                        let seatsPerRow = 14; // Số ghế mỗi hàng (2 bên hành lang, mỗi bên 2 ghế)

                                        if (cabin.type && cabin.type.toLowerCase().includes("business")) {
                                            seatsPerRow = 14; // Business class có ít ghế hơn mỗi hàng
                                        }

                                        // Tính số hàng cần thiết
                                        let numRows = Math.ceil(numSeats / seatsPerRow);
                                        let seatNumber = 1;

                                        // Tạo ngẫu nhiên một số ghế đã đặt để minh họa
                                        const reservedSeats = generateRandomReservedSeats(numSeats);

                                        // Tạo các hàng ghế
                                        for (let i = 0; i < numRows && seatNumber <= numSeats; i++) {
                                            const row = document.createElement("div");
                                            row.className = "coach-row";
                                            coach.appendChild(row);

                                            // Thêm ghế bên trái hành lang
                                            const leftSeats = seatsPerRow / 2;
                                            for (let j = 0; j < leftSeats && seatNumber <= numSeats; j++) {
                                                const seat = createSeatElement(seatNumber, reservedSeats.includes(seatNumber), cabin.id);
                                                row.appendChild(seat);
                                                seatNumber++;
                                            }

                                            // Thêm hành lang
                                            const aisle = document.createElement("div");
                                            aisle.className = "aisle";
                                            row.appendChild(aisle);

                                            // Thêm ghế bên phải hành lang
                                            const rightSeats = seatsPerRow / 2;
                                            for (let j = 0; j < rightSeats && seatNumber <= numSeats; j++) {
                                                const seat = createSeatElement(seatNumber, reservedSeats.includes(seatNumber), cabin.id);
                                                row.appendChild(seat);
                                                seatNumber++;
                                            }
                                        }

                                        // Thêm phần chú thích
                                        const legend = document.createElement("div");
                                        legend.className = "legend";
                                        legend.innerHTML = `
                                            <div class="legend-item">
                                                <div class="legend-color free"></div>
                                                <span>Empty Seat</span>
                                            </div>
                                            <div class="legend-item">
                                                <div class="legend-color reserved-color"></div>
                                                <span>Booked Seat</span>
                                            </div>
                                            <div class="legend-item">
                                                <div class="legend-color selected-color"></div>
                                                <span>Your Selected Seat</span>
                                            </div>
                                        `;
                                        seatMapContainer.appendChild(legend);

                                        // Add click events to seats - IMPORTANT: Do this after creating seats
                                        addSeatClickEvents();
                                    }

                                    // Hàm tạo một phần tử ghế
                                    function createSeatElement(seatNumber, isReserved, cabinId) {
                                        const seat = document.createElement("div");
                                        seat.className = isReserved ? "seat reserved" : "seat";
                                        seat.setAttribute("data-seat", seatNumber);
                                        seat.setAttribute("data-cabin", cabinId);
                                        seat.textContent = seatNumber;

                                        // Check if this seat is in our selectedSeats array for this cabin
                                        if (selectedSeatsByCabin[cabinId] && selectedSeatsByCabin[cabinId].includes(seatNumber.toString())) {
                                            seat.classList.add('selected');
                                        }

                                        return seat;
                                    }

                                    // Hàm tạo ngẫu nhiên các ghế đã đặt
                                    function generateRandomReservedSeats(totalSeats) {
                                        const reservedSeats = [];
                                        const reservedCount = Math.floor(totalSeats * 0.3); // Khoảng 30% ghế đã đặt

                                        while (reservedSeats.length < reservedCount) {
                                            const randomSeat = Math.floor(Math.random() * totalSeats) + 1;
                                            if (!reservedSeats.includes(randomSeat)) {
                                                reservedSeats.push(randomSeat);
                                            }
                                        }

                                        //return reservedSeats;
                                        return [1, 2, 3, 4, 5];
                                    }

                                    // Hàm thêm sự kiện click cho ghế
                                    function addSeatClickEvents() {
                                        const seats = document.querySelectorAll('.seat:not(.reserved)');

                                        seats.forEach(seat => {
                                            seat.addEventListener('click', function () {
                                                const seatNumber = this.getAttribute('data-seat');
                                                const cabinId = this.getAttribute('data-cabin');

                                                // Make sure the cabin array exists
                                                if (!selectedSeatsByCabin[cabinId]) {
                                                    selectedSeatsByCabin[cabinId] = [];
                                                }

                                                if (this.classList.contains('selected')) {
                                                    // Bỏ chọn ghế
                                                    this.classList.remove('selected');
                                                    selectedSeatsByCabin[cabinId] = selectedSeatsByCabin[cabinId].filter(s => s !== seatNumber);
                                                } else {
                                                    // Chọn ghế
                                                    this.classList.add('selected');
                                                    selectedSeatsByCabin[cabinId].push(seatNumber);
                                                }

                                                // Cập nhật thông tin summary
                                                updateSummary();
                                            });
                                        });
                                    }

                                    function updateSummary() {
                                        // Get or create the summary container
                                        let summaryContainer = document.getElementById('seat-summary-container');
                                        if (!summaryContainer) {
                                            console.error("Summary container not found");
                                            return;
                                        }

                                        // Clear the container
                                        summaryContainer.innerHTML = '<h4 class="summary-title">Your Seat Selection</h4>';

                                        // Keep track of total seats and total price
                                        let totalSeats = 0;
                                        let totalPrice = 0;
                                        console.log("Selected Seats Data:", selectedSeatsByCabin);


                                        // Iterate through each cabin with selected seats
                                        for (const cabinId in selectedSeatsByCabin) {
                                            if (selectedSeatsByCabin[cabinId].length > 0) {
                                                // Find cabin details
                                                const cabin = cabins.find(c => c.id == cabinId);
                                                if (!cabin)
                                                    continue;

                                                // Calculate price for this cabin's seats
                                                let unitPrice = 0;
                                                if (cabin.type && cabin.type.toLowerCase().includes("business")) {
                                                    unitPrice = parseInt("${route.businessPrice}".replace(/,/g, ''));
                                                } else {
                                                    unitPrice = parseInt("${route.economyPrice}".replace(/,/g, ''));
                                                }

                                                const cabinSeatsCount = selectedSeatsByCabin[cabinId].length;
                                                const cabinPrice = unitPrice * cabinSeatsCount;

                                                // Create summary element for this cabin
                                                const cabinSummary = document.createElement('div');
                                                cabinSummary.className = 'summary mb-3';

                                                const cabinTitle = document.createElement('h5');
                                                cabinTitle.textContent = "Cabin: " + cabin.cabinName + " - " + cabin.type;

                                                const selectedSeatsText = document.createElement('h5');
                                                selectedSeatsText.textContent = "Selected seat(s): " + selectedSeatsByCabin[cabinId].join(', ');

                                                const numberOfSeatsText = document.createElement('h5');
                                                numberOfSeatsText.textContent = "Number of seats: " + cabinSeatsCount;

                                                const priceText = document.createElement('h5');
                                                priceText.textContent = "Price: " + cabinPrice.toLocaleString() + " VND";

                                                cabinSummary.appendChild(cabinTitle);
                                                cabinSummary.appendChild(selectedSeatsText);
                                                cabinSummary.appendChild(numberOfSeatsText);
                                                cabinSummary.appendChild(priceText);

                                                summaryContainer.appendChild(cabinSummary);

                                                // Update totals
                                                totalSeats += cabinSeatsCount;
                                                totalPrice += cabinPrice;
                                            }
                                        }

                                        // Add total summary if there are any selected seats
                                        if (totalSeats > 0) {
                                            const totalSummary = document.createElement('div');
                                            totalSummary.className = 'summary';
                                            const totalSeatsText = document.createElement('h5');
                                            totalSeatsText.className = "text-primary";
                                            totalSeatsText.textContent = "Total number of seats: " + totalSeats;

                                            const totalPriceText = document.createElement('h5');
                                            totalPriceText.className = "text-primary";
                                            totalPriceText.textContent = "Total price: " + totalPrice.toLocaleString() + " VND";

                                            const checkoutButton = document.createElement('a');
                                            checkoutButton.className = "btn btn-primary mt-3 w-100";
                                            checkoutButton.textContent = "Buy ticket";
                                            checkoutButton.onclick = function () {
                                                // Tạo form gửi dữ liệu POST đến PayTicketServlet
                                                const form = document.createElement("form");
                                                form.method = "POST";
                                                form.action = "PayTicketServlet"; // Đường dẫn đến Servlet xử lý thanh toán

                                                // Thêm input ẩn để truyền dữ liệu
                                                const amountInput = document.createElement("input");
                                                amountInput.type = "hidden";
                                                amountInput.name = "amount";
                                                amountInput.value = totalPrice;

                                                const numSeatsInput = document.createElement("input");
                                                numSeatsInput.type = "hidden";
                                                numSeatsInput.name = "numSeats";
                                                numSeatsInput.value = totalSeats;

                                                const selectedSeatsInput = document.createElement("input");
                                                selectedSeatsInput.type = "hidden";
                                                selectedSeatsInput.name = "selectedSeats";
                                                selectedSeatsInput.value = JSON.stringify(selectedSeatsByCabin);
                                                
                                                // Thêm vào form
                                                form.appendChild(amountInput);
                                                form.appendChild(numSeatsInput);
                                                form.appendChild(selectedSeatsInput);
                                                document.body.appendChild(form);

                                                // Gửi form
                                                form.submit();
                                            };


                                            totalSummary.appendChild(totalSeatsText);
                                            totalSummary.appendChild(totalPriceText);
                                            totalSummary.appendChild(checkoutButton);

                                            summaryContainer.appendChild(totalSummary);
                                        } else {
                                            // If no seats selected, show default message
                                            const noSeatsMsg = document.createElement('div');
                                            noSeatsMsg.className = 'summary';
                                            noSeatsMsg.innerHTML = `
                                                <h5>No seats selected</h5>
                                                <p>Please select seats from the train carriages</p>
                                            `;
                                            summaryContainer.appendChild(noSeatsMsg);
                                        }
                                    }

                                    // Initialize everything when DOM is loaded
                                    document.addEventListener('DOMContentLoaded', function () {
                                        generateTrain(cabins);

                                        // Initialize the seat summary in the right column
                                        const summaryContainer = document.getElementById('seat-summary-container');
                                        if (summaryContainer) {
                                            summaryContainer.innerHTML = `
                                                <h4 class="summary-title">Your Seat Selection</h4>
                                                <div class="summary">
                                                    <h5>No seats selected</h5>
                                                    <p>Please select seats from the train carriages</p>
                                                </div>
                                            `;
                                        }
                                    });
                                </script>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-4">
                    <div class="px-0 border rounded-2 shadow-0">
                        <div class="card">
                            <div class="card-body">
                                <!-- Summary section moved here -->
                                <div id="seat-summary-container" class="mb-4">
                                    <!-- Summary will be inserted here by JavaScript -->
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </form>
    <body>
    </body>
    <%@include file="components/Script.jsp" %>
</html>
