<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Receipt</title>
<jsp:include page="menuHeader.jsp" />
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
<!-- <script src="js/print.js"></script> -->
<script src="js/printReport.js"></script>
</head>
<body>
<form:form action="saveReceiptDetails" method="POST">
<form:errors class="error"/> 
<div id="printable">
<input type="hidden" name="receipt.id" value="${receiptView.receipt.id}"/>
<h1>
<input type="hidden" name="receipt.receiptId" value="${receiptView.receipt.receiptId}"/>
${receiptView.receipt.receiptId}
</h1>
<div id="receiptUserDetails">
<c:if test="${not empty receiptView.receipt.subject.firstname}">
<input type="hidden" name="receipt.subject.firstname" value="${receiptView.receipt.subject.firstname}"/>
<h3>
${receiptView.receipt.subject.userType}: ${receiptView.receipt.subject.firstname}
</h3>
</c:if>
<!--  Current Lab -->
<c:if test="${not empty receiptView.currentLab.firstname}">
<input type="hidden" name="receipt.currentLab.firstname" value="${receiptView.currentLab.firstname}"/>
<h3>
${receiptView.currentLab.userType}: ${receiptView.currentLab.firstname}
</h3>
</c:if>

<c:forEach items="${receiptView.receipt.relatedUsers}" var="user">				        
<c:if test="${not empty user.firstname}">
<input type="hidden" name="user.firstname" value="${user.firstname}"/>
<h3>
${user.userType}: ${user.firstname}
</h3>
</c:if>
</c:forEach>
</div> <!-- receiptUserDetails div -->
<h3>
<%-- <c:if test="${not empty receiptView.receipt.doctorName}">
<input type="hidden" name="receipt.doctorId" value="${receiptView.receipt.doctorId}"/>
Doctor: ${receiptView.receipt.doctorName}
</c:if> --%>
</h3>
<c:if test="${not empty receiptView.receipt.validTill}"><h3>Valid Till : ${receiptView.receipt.validTill}</h3></c:if>
<c:if test="${not empty receiptView.receipt.dateCreated}"><h3>Date Created : ${receiptView.receipt.dateCreated}</h3></c:if>
<c:choose>
<c:when test="${not empty showTestList}">
<!-- ----------------------------Add new Table -------------------------------->
<jsp:include page="showLabTestPriceReportTable.jsp" />

<p></p>
<input type="submit" name="commit" value="Save" class="button">
<button type="button" id="printReport" class="button">Print Report</button>
<button type="button" id="printPriceReceipt" class="button">Print Receipt</button>
</c:when>
<c:otherwise>
<button type="button" id="printReceipt" class="button">Print</button>
</c:otherwise>
</c:choose>
</div> <!-- printable div -->
</form:form>
</body>
</html>