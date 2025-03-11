<%-- 
    Document   : Manager_EditTrain
    Created on : Mar 3, 2025, 11:06:37 AM
    Author     : ASUS
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
    <title>Edit Train</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style>
        /* Reset & style chung */
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
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
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
        /* Hiển thị các cabin dạng lưới: 1 hàng 3 cabin */
        #cabinContainer {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            grid-gap: 15px;
        }
        .hidden {
            display: none;
        }
        .error {
            color: red;
            text-align: center;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Edit Train</h2>
    <c:if test="${not empty errorMessage}">
        <p class="error">${errorMessage}</p>
    </c:if>
    <form action="editTrain" method="post">
        <!-- Thông tin tàu -->
        <fieldset id="trainInfo">
            <legend>Thông tin tàu</legend>
            <!-- Hidden field chứa id của train -->
            <input type="hidden" id="trainId" name="trainId" value="${train.id}" />
            <label for="train_model">Train Model:</label>
            <input type="text" id="train_model" name="train_model" value="${train.trainid}" required>
            
            <label for="total_seats">Total Seats:</label>
            <input type="number" id="total_seats" name="total_seats" value="${train.totalSeats}" required>
            
            <label for="numcabin">Number of Cabins:</label>
            <input type="number" id="numcabin" name="numcabin" value="${train.numCabin}" required>
            
            <label for="owner" class="form-label">Owner</label>
            <select class="form-select" id="owner" name="owner" required>
                <option value="">Select Owner</option>
                <c:forEach items="${listseller}" var="s">
                    <option value="${s.id}" <c:if test="${s.id == train.owner}">selected</c:if>>${s.fullName}</option>
                </c:forEach>
            </select>
            <!-- Nút Continue xác nhận thông tin tàu -->
            <button type="button" class="btn" id="btnContinue">Continue</button>
        </fieldset>
        
        <!-- Thông tin các toa tàu (Cabin) -->
        <fieldset id="cabinDetails" disabled class="disabled-fieldset">
            <legend>Thông tin các toa tàu (Cabin)</legend>
            <div id="cabinContainer">
                <!-- Các khối cabin sẽ được tạo tự động -->
            </div>
        </fieldset>
        
        <!-- Nút submit ẩn ban đầu -->
        <input type="submit" value="Cập nhật tàu" class="btn hidden" id="btnSubmit">
    </form>
</div>
<script>
    // Mảng chứa dữ liệu các cabin có sẵn từ server
    var existingCabins = [];
    <c:forEach var="cabin" items="${cabins}">
        existingCabins.push({
            name: '${cabin.cabinName}',
            cabinClass: '${cabin.type}', // trường trong CSDL là "class"
            numseat: ${cabin.numSeat},
            imgUrl: '${cabin.imgUrl}'
        });
    </c:forEach>
    
    function isTrainInfoComplete() {
        var model = document.getElementById("train_model").value.trim();
        var totalSeats = document.getElementById("total_seats").value.trim();
        var numCabin = document.getElementById("numcabin").value.trim();
        var owner = document.getElementById("owner").value.trim();
        return model !== "" && totalSeats !== "" && numCabin !== "" && owner !== "";
    }
    
    // Tạo các khối cabin dựa trên số lượng và điền dữ liệu có sẵn (nếu có)
    function generateCabins() {
        var numCabinValue = document.getElementById("numcabin").value;
        var cabinContainer = document.getElementById("cabinContainer");
        cabinContainer.innerHTML = "";
        for (var i = 0; i < numCabinValue; i++) {
            var cabinDiv = document.createElement("div");
            cabinDiv.className = "cabin";
            // Nếu dữ liệu có sẵn, lấy đối tượng tương ứng, nếu không thì để trống
            var cabinData = (existingCabins.length > i) ? existingCabins[i] : {name: "", cabinClass: "", numseat: "", imgUrl: ""};
            cabinDiv.innerHTML = '<h3>Cabin ' + (i + 1) + '</h3>' +
                                 '<label>Cabin Name:</label>' +
                                 '<input type="text" name="cabinName" value="' + cabinData.name + '" required>' +
                                 '<label>Cabin Class:</label>' +
                                 '<input type="text" name="cabinClass" value="' + cabinData.cabinClass + '" required>' +
                                 '<label>Cabin Seats:</label>' +
                                 '<input type="number" name="cabinNumseat" value="' + cabinData.numseat + '" required>' +
                                 '<label>Image URL:</label>' +
                                 '<input type="text" name="cabinImgUrl" value="' + cabinData.imgUrl + '" required>';
            cabinContainer.appendChild(cabinDiv);
        }
    }
    
    // Khi nhấn nút Continue
    document.getElementById("btnContinue").addEventListener("click", function() {
        if (!isTrainInfoComplete()) {
            alert("Vui lòng nhập đầy đủ thông tin về tàu.");
            return;
        }
        document.getElementById("cabinDetails").disabled = false;
        document.getElementById("cabinDetails").classList.remove("disabled-fieldset");
        generateCabins();
        document.getElementById("btnContinue").classList.add("hidden");
        document.getElementById("btnSubmit").classList.remove("hidden");
    });
    
    // Nếu bất kỳ trường nào của train thay đổi thì reset phần cabin
    var trainFields = ["train_model", "total_seats", "numcabin", "owner"];
    trainFields.forEach(function(id) {
        document.getElementById(id).addEventListener("change", function() {
            document.getElementById("cabinDetails").disabled = true;
            document.getElementById("cabinDetails").classList.add("disabled-fieldset");
            document.getElementById("cabinContainer").innerHTML = "";
            document.getElementById("btnContinue").classList.remove("hidden");
            document.getElementById("btnSubmit").classList.add("hidden");
        });
    });
    
    // Trước khi submit, kiểm tra tổng số ghế của các cabin có khớp với Total Seats không
    document.querySelector("form").addEventListener("submit", function(event) {
        var totalSeats = parseInt(document.getElementById("total_seats").value);
        var cabinSeatInputs = document.getElementsByName("cabinNumseat");
        var sum = 0;
        for (var i = 0; i < cabinSeatInputs.length; i++) {
            sum += parseInt(cabinSeatInputs[i].value) || 0;
        }
        if (sum !== totalSeats) {
            alert("Tổng số ghế của các toa (" + sum + ") không bằng với Total Seats (" + totalSeats + "). Không thể cập nhật tàu.");
            event.preventDefault();
        }
    });
</script>
</body>
</html>

