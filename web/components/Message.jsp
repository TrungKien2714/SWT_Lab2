<%--
    Document   : message
    Created on : Feb 11, 2025, 11:58:12 PM
    Author     : dtam6
--%>

<%@ page pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:if test="${not empty sessionScope.message}">
    <div id="snackbar">
        ${sessionScope.message}
    </div>
    <c:remove var="message" scope="session" />
</c:if>

<script>
    function showMessage() {
        let x = document.getElementById("snackbar");
        if (x) {
            x.className = "show";
            setTimeout(function () {
                x.className = x.className.replace("show", "");
            }, 3000);
        }
    }

    window.onload = showMessage;
</script>