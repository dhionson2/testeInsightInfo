<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Lista de Hora extra</title>
<link
	href="${pageContext.request.contextPath}/resources/bootstrap-5.1.3-dist/css/bootstrap.min.css"
	rel="stylesheet" />
<link
	href="${pageContext.request.contextPath}/resources/bootstrap-5.1.3-dist/css/bootstrap.min.css"
	rel="stylesheet" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/styles.css">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/flatpickr@4.6.6/dist/flatpickr.min.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/jquery3.7/code.jquery.com_jquery-3.7.0.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/bootstrap-5.1.3-dist/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.16/jquery.mask.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/flatpickr@4.6.6/dist/flatpickr.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/flatpickr@4.6.6/dist/plugins/confirmDate/confirmDate.min.js"></script>
<!-- Restante das importações de CSS e JS -->
</head>
<body>
	<jsp:include page="/publica/publica-nav.jsp" />

	<div class="container">
    <div class="row">
        <div class="col-md-6">
            <h1>Horas extra</h1>
            <table class="table mt-3">
                <thead class="bgRoxoEscuro">
                    <tr style="color: #1DFF9E;">
                        <th>Data</th>
                        <th>Horas extras</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="entry" items="${atrasosPorData}">
                        <tr style="color: #1DFF9E;">
                            <td>${entry.key}</td>
                            <td>${entry.value.horasExtras}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="col-md-6">
            <img class="ml-5 rounded-corners" alt="" src="${pageContext.request.contextPath}/resources/imagens/undraw_Performance_overview_re_mqrq.png" style="width: 89%;height: auto;border-radius: 130px;">

        </div>
    </div>
</div>




</body>
</html>
