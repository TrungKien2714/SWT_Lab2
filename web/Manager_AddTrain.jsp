<%-- 
    Document   : Manager_AddTrain
    Created on : Mar 2, 2025, 8:53:19 PM
    Author     : ASUS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Add Train</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style>
        /* Reset và style cơ bản */
        body, html {
            margin: 0;
            padding: 0;
            background-color: #f8f9fa;
            font-family: Arial, sans-serif;
        }
        .container {
            width: 75%;
            margin: 40px auto;
            background-color: #ffffff;
            border: 1px solid #003366;
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
            padding: 30px;
        }
        h2 {
            text-align: center;
            color: #002147;
            margin-bottom: 30px;
        }
        fieldset {
            border: 1px solid #003366;
            border-radius: 4px;
            padding: 20px;
            margin-bottom: 20px;
        }
        legend {
            font-weight: bold;
            color: #002147;
            padding: 0 10px;
        }
        label {
            display: block;
            margin-bottom: 8px;
            font-weight: bold;
            color: #002147;
        }
        input[type="text"],
        input[type="number"],
        select {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #003366;
            border-radius: 4px;
            box-sizing: border-box;
        }
        /* Style cho các khối cabin */
        .cabin {
            border: 1px solid #003366;
            border-radius: 4px;
            padding: 15px;
        }
        .cabin h3 {
            margin-top: 0;
            color: #002147;
        }
        .btn {
            background-color: #003366;
            color: #ffffff;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            margin-top: 10px;
        }
        .btn:hover {
            background-color: #002147;
        }
        .disabled-fieldset {
            opacity: 0.6;
        }
        /* Hiển thị các cabin theo dạng lưới: 1 hàng 3 cabin */
        #cabinContainer {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            grid-gap: 15px;
        }
        .hidden {
            display: none;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Add Train</h2>
    <form action="AddTrain" method="post">
        <!-- Thông tin tàu -->
        <fieldset id="trainInfo">
            <legend>Thông tin tàu</legend>
            <label for="train_model">Train Model:</label>
            <input type="text" id="train_model" name="train_model" required>
            
            <label for="total_seats">Total Seats:</label>
            <input type="number" id="total_seats" name="total_seats" required>
            
            <label for="numcabin">Number of Cabins:</label>
            <input type="number" id="numcabin" name="numcabin" required>
            
            <label for="owner" class="form-label">Owner</label>
            <select class="form-select" id="owner" name="owner" required>
                <option value="">Select Owner</option>
                <c:forEach items="${listseller}" var="s">
                    <option value="${s.id}">${s.fullName}</option> 
                </c:forEach>
            </select>
            <!-- Nút Continue để xác nhận thông tin train -->
            <button type="button" class="btn" id="btnContinue">Continue</button>
        </fieldset>
        
        <!-- Thông tin các toa tàu (Cabin) -->
        <fieldset id="cabinDetails" disabled class="disabled-fieldset">
            <legend>Thông tin các toa tàu (Cabin)</legend>
            <div id="cabinContainer">
                <!-- Các khối cabin sẽ được tạo tự động tại đây -->
            </div>
        </fieldset>
        
        <!-- Nút submit ẩn ban đầu -->
        <input type="submit" value="Thêm tàu" class="btn hidden" id="btnSubmit">
    </form>
</div>
<script>
    
    // Hàm kiểm tra xem thông tin train đã đầy đủ chưa
    function isTrainInfoComplete() {
        var model = document.getElementById("train_model").value.trim();
        var totalSeats = document.getElementById("total_seats").value.trim();
        var numCabin = document.getElementById("numcabin").value.trim();
        var owner = document.getElementById("owner").value.trim();
        return model !== "" && totalSeats !== "" && numCabin !== "" && owner !== "";
    }
    
    // Tạo các khối cabin theo số lượng đã nhập, kèm số thứ tự
    function generateCabins() {
        var numCabinValue = document.getElementById("numcabin").value;
        var cabinContainer = document.getElementById("cabinContainer");
        cabinContainer.innerHTML = "";
        
        for (var i = 0; i < numCabinValue; i++) {
            var cabinDiv = document.createElement("div");
            cabinDiv.className = "cabin";
            cabinDiv.innerHTML = '<h3>Cabin ' + (i + 1) + '</h3>' +
                                 '<label>Cabin Name:</label>' +
                                 '<input type="text" name="cabinName" required>' +
                                 '<label>Cabin Class:</label>' +
                                 '<input type="text" name="cabinClass" required>' +
                                 '<label>Cabin Seats:</label>' +
                                 '<input type="number" name="cabinNumseat" required>' +
                                 '<label>Image URL:</label>' +
                                 '<input type="text" name="cabinImgUrl" required>';
            cabinContainer.appendChild(cabinDiv);
        }
    }
    
    // Khi nhấn nút Continue
    document.getElementById("btnContinue").addEventListener("click", function() {
        if (!isTrainInfoComplete()) {
            alert("Vui lòng nhập đầy đủ thông tin về tàu.");
            return;
        }
        // Bật phần cabin và tạo các khối cabin
        document.getElementById("cabinDetails").disabled = false;
        document.getElementById("cabinDetails").classList.remove("disabled-fieldset");
        generateCabins();
        // Ẩn nút Continue và hiện nút Thêm tàu
        document.getElementById("btnContinue").classList.add("hidden");
        document.getElementById("btnSubmit").classList.remove("hidden");
    });
    
    // Nếu bất kỳ trường nào của train bị thay đổi, reset lại phần cabin và quay lại nút Continue
    var trainFields = ["train_model", "total_seats", "numcabin", "owner"];
    trainFields.forEach(function(id) {
        document.getElementById(id).addEventListener("change", function() {
            // Reset cabin
            document.getElementById("cabinDetails").disabled = true;
            document.getElementById("cabinDetails").classList.add("disabled-fieldset");
            document.getElementById("cabinContainer").innerHTML = "";
            // Hiện nút Continue và ẩn nút Submit
            document.getElementById("btnContinue").classList.remove("hidden");
            document.getElementById("btnSubmit").classList.add("hidden");
        });
    });
    
    // Kiểm tra tổng số ghế của các cabin có bằng với Total Seats không trước khi submit form
    document.querySelector("form").addEventListener("submit", function(event) {
        var totalSeats = parseInt(document.getElementById("total_seats").value);
        var cabinSeatInputs = document.getElementsByName("cabinNumseat");
        var sum = 0;
        for (var i = 0; i < cabinSeatInputs.length; i++) {
            sum += parseInt(cabinSeatInputs[i].value) || 0;
        }
        if (sum !== totalSeats) {
            alert("Tổng số ghế của các toa (" + sum + ") không bằng với Total Seats (" + totalSeats + "). Không thể thêm tàu.");
            event.preventDefault();
        }
    });
</script>
</body>
</html>
