<%--
    Document   : Style
    Created on : Feb 6, 2025, 10:08:36 PM
    Author     : dtam6
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" href="vendors/linericon/style.css">
<link rel="stylesheet" href="css/font-awesome.min.css">
<link rel="stylesheet" href="vendors/owl-carousel/owl.carousel.min.css">
<link rel="stylesheet" href="vendors/bootstrap-datepicker/bootstrap-datetimepicker.min.css">
<link rel="stylesheet" href="vendors/nice-select/css/nice-select.css">
<link rel="stylesheet" href="vendors/owl-carousel/owl.carousel.min.css">
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/responsive.css">
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<style>
    #snackbar {
        visibility: hidden;
        min-width: 250px;
        margin-left: -125px;
        background-color: #333;
        color: #fff;
        text-align: center;
        border-radius: 2px;
        padding: 16px;
        position: fixed;
        z-index: 1;
        left: 50%;
        bottom: 30px;
    }

    #snackbar.show {
        visibility: visible;
        -webkit-animation: fadein 0.5s, fadeout 0.5s 2.5s;
        animation: fadein 0.5s, fadeout 0.5s 2.5s;
    }

    @-webkit-keyframes fadein {
        from {
            bottom: 0;
            opacity: 0;
        }
        to {
            bottom: 30px;
            opacity: 1;
        }
    }

    @keyframes fadein {
        from {
            bottom: 0;
            opacity: 0;
        }
        to {
            bottom: 30px;
            opacity: 1;
        }
    }

    @-webkit-keyframes fadeout {
        from {
            bottom: 30px;
            opacity: 1;
        }
        to {
            bottom: 0;
            opacity: 0;
        }
    }

    @keyframes fadeout {
        from {
            bottom: 30px;
            opacity: 1;
        }
        to {
            bottom: 0;
            opacity: 0;
        }
    }
    .train-container {
        display: flex !important;
        align-items: flex-start !important;
        gap: 10px !important;
    }

    /* Container bao quanh toa tàu */
    .coach-container {
        position: relative !important; /* Đảm bảo vị trí tương đối */
    }
    .coach-container img {
        position: relative !important; /* Đảm bảo ảnh nằm trên ::before */
        z-index: 1 !important; /* Đặt ảnh lên trên */
    }

    .coach-container::before {
        content: "" !important;
        display: block !important; /* Đảm bảo nó là một khối */
        position: absolute !important;
        width: 100% !important;
        height: 70% !important;
        background-color: RGB(0,131,193) !important; /* Màu xanh lam */
        border-radius: 10px 10px 10px 10px !important;
        top: 15% !important; /* Điều chỉnh nếu cần */
        left: 0 !important;
        z-index: 0 !important; /* Đặt 0 để không bị đè bởi ảnh */
    }


    /* Khi toa tàu được chọn, đổi màu thân tàu */
    .coach-container.selected::before {
        background-color: #a3c600 !important;
    }

    /* Đầu tàu không thể click */
    .head-train {
        pointer-events: none !important;
    }
</style>

