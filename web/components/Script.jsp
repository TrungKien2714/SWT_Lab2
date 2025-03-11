<%--
    Document   : Script
    Created on : Feb 6, 2025, 10:09:05 PM
    Author     : dtam6
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="js/popper.js"></script>
<script src="vendors/owl-carousel/owl.carousel.min.js"></script>
<script src="js/jquery.ajaxchimp.min.js"></script>
<script src="js/mail-script.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.4/moment.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="vendors/bootstrap-datepicker/bootstrap-datetimepicker.min.js"></script>
<script src="vendors/nice-select/js/jquery.nice-select.js"></script>
<script src="js/mail-script.js"></script>
<script src="js/stellar.js"></script>
<script src="vendors/lightbox/simpleLightbox.min.js"></script>
<script src="js/custom.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.min.js"></script>
<script>
    $("#departure_station").autocomplete({
        source: function (request, response) {
            $.ajax({
                url: "/tratra/Search", // Đảm bảo URL đúng
                method: "GET",
                data: {
                    query: request.term
                },
                dataType: "json",
                success: function (data) {
                    console.log("Received data:", data);  // Kiểm tra dữ liệu trả về
                    if (data && data.length > 0) {
                        var stationNames = data.map(function (station) {
                            return station.name; // Đảm bảo server trả về tên đúng
                        });
                        response(stationNames);
                    } else {
                        response([]);  // Trả về mảng rỗng nếu không có kết quả
                    }
                },
                error: function (xhr, status, error) {
                    console.log("AJAX error: " + status + " - " + error);
                }
            });
        },
        minLength: 1,
        select: function (event, ui) {
            console.log("Selected Departure Station: " + ui.item.value);  // In ra giá trị đã chọn
            $(this).val(ui.item.value);  // Cập nhật giá trị vào trường nhập liệu
        }
    });
    $("#arrival_station").autocomplete({
        source: function (request, response) {
            $.ajax({
                url: "/tratra/Search",
                method: "GET",
                data: {
                    query: request.term
                },
                dataType: "json",
                success: function (data) {
                    console.log("Received data:", data); // Kiểm tra dữ liệu trả về
                    if (data && data.length > 0) {
                        var stationNames = data.map(function (station) {
                            return station.name; // Đảm bảo server trả về tên đúng
                        });
                        response(stationNames);
                    } else {
                        response([]); // Trả về mảng rỗng nếu không có kết quả
                    }
                },
                error: function (xhr, status, error) {
                    console.log("AJAX error: " + status + " - " + error);
                }
            });
        },
        minLength: 1,
        select: function (event, ui) {
            console.log("Selected Arrival Station: " + ui.item.value); // In ra giá trị đã chọn
            $(this).val(ui.item.value); // Cập nhật giá trị vào trường nhập liệu
        }
    });
</script>
<script>
    $(document).ready(function () {
        var departurePicker = $('#datetimepicker11').datetimepicker({
            format: 'YYYY-MM-DD HH:mm',
            icons: {
                time: 'fa fa-clock-o',
                date: 'fa fa-calendar',
                up: 'fa fa-chevron-up',
                down: 'fa fa-chevron-down',
                previous: 'fa fa-chevron-left',
                next: 'fa fa-chevron-right',
                today: 'fa fa-crosshairs',
                clear: 'fa fa-trash',
                close: 'fa fa-times'
            }
        });

        var arrivalPicker = $('#datetimepicker1').datetimepicker({
            format: 'YYYY-MM-DD HH:mm',
            icons: {
                time: 'fa fa-clock-o',
                date: 'fa fa-calendar',
                up: 'fa fa-chevron-up',
                down: 'fa fa-chevron-down',
                previous: 'fa fa-chevron-left',
                next: 'fa fa-chevron-right',
                today: 'fa fa-crosshairs',
                clear: 'fa fa-trash',
                close: 'fa fa-times'
            }
        });

        // Attach event listeners
        departurePicker.on('dp.show', function () {
            console.log('Departure picker opened');
        });

        departurePicker.on('dp.hide', function () {
            console.log('Departure picker closed');
        });

        departurePicker.on('dp.change', function (e) {
            console.log('Departure date changed:', e.date ? e.date.format('YYYY-MM-DD HH:mm') : null);
            $('#departure_date').trigger('change');
        });

        arrivalPicker.on('dp.show', function () {
            console.log('Arrival picker opened');
        });

        arrivalPicker.on('dp.hide', function () {
            console.log('Arrival picker closed');
        });

        arrivalPicker.on('dp.change', function (e) {
            console.log('Arrival date changed:', e.date ? e.date.format('YYYY-MM-DD HH:mm') : null);
            $('#arrival_date').trigger('change');
        });

        // Add direct input change listeners
        $('#departure_date, #arrival_date').on('change', function () {
            console.log(this.id + ' value changed to:', $(this).val());
        });
    });
</script>
<script>
    function updateHiddenInputs() {
        document.getElementById("departureStationHidden").value = document.getElementById("departure_station").value;
        document.getElementById("arrivalStationHidden").value = document.getElementById("arrival_station").value;
        document.getElementById("departureDateHidden").value = document.getElementById("departure_date").value;
        document.getElementById("arrivalDateHidden").value = document.getElementById("arrival_date").value;
        document.getElementById("tripTypeHidden").value = document.getElementById("trip_type").value;
    }
</script>

