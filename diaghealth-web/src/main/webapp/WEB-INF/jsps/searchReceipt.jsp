<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Receipt</title>
<jsp:include page="menuHeader.jsp" />
<link rel="stylesheet" type="text/css" href="css/commonTable.css">
</head>
<body>

<h3>Enter Receipt Id:</h3>
<form:form action="searchReceipt" modelAttribute="receiptObj" method="POST">
<form:errors class="error"/>
<%-- <label>${errorMessage}</label> --%>
<br>
<%-- <%
	String errorString = (String)request.getAttribute("error");
	if(errorString != null){
	out.println(errorString);
	}
%> --%>
<input size="30" type="text" name="receiptId" value="" />
<p class="submit">
	<input type="submit" name="commit" value="Search" class="button">
</p>
</form:form>
<%-- <jsp:include page="viewReceipt.jsp" /> --%>

<!-- Display Receipts -->
<table>
<tr><th>Receipt Id</th><th>Patient</th><th>Date</th><tr>
<c:if test="${not empty allReceipts and fn:length(allReceipts) > 0}">
	<%-- <c:if test="${fn:length(allReceipts) > 0}"> --%>
	<c:forEach var="receipt" items="${allReceipts}" varStatus="status">
		<tr>
		<td><a href="searchReceiptById?receiptId=${receipt.receiptId}&id=0">${receipt.receiptId}</a></td>
		<td>${receipt.subject.firstname} ${receipt.subject.lastname}</td>
		<td>${receipt.dateCreated}</td>
		</tr>
	</c:forEach>
	<%-- </c:if> --%>
</c:if>
</table>
</body>
</html>