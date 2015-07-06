<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Test Report</title>
	<script src="http://code.jquery.com/jquery-1.10.2.js"></script>
  	<script src="http://code.jquery.com/ui/1.11.4/jquery-ui.js"></script>  	
	<link rel="stylesheet" href="http://code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
	<link type="text/css" rel="stylesheet" href="css/commonTable.css">
	<script src="js/datePicker.js"></script>
	<jsp:include page="menuHeader.jsp" />
</head>
<body>
<form:form action="filterTestsOnDate" modelAttribute="testViewObject" method="POST">
<form:errors class="error"/>

<div id="dateFromToDiv"> 
<table>
<tr>
<td><div class="date">From Date: <form:input type="text" class="datepicker" name="fromDate" path="fromDate"
value="${fromDate}"/></div></td>
<td><div class="date"> To Date: <form:input type="text" class="datepicker" name="toDate" path="toDate"
value="${toDate}"/></div></td>
<td><div class="submitButton"><input name="submit" type="submit" id="submitButton" class="button" value="Filter" /></div></td>
</tr>
</table>
</div>

<table id="displayTable" class="tablesorter">
<thead>
<tr>
<th>Test Name</th>
<th>Result</th>
<th>Lower Ref</th>
<th>Upper Ref</th>
<th>Unit</th>
<th>Comments</th>
<th>Date</th>
</tr>
</thead>
<tbody>
<c:forEach var="test" items="${testViewObject.testList}" varStatus="status">
	<tr>
	<td>${test.name}</td>
	<c:choose>
	<c:when test="${(test.refLower lt test.refUpper) && ((test.resultValue lt test.refLower) || (test.resultValue gt test.refUpper))}">
		<td class="error">${test.resultValue}</td>
	</c:when>
	<c:otherwise>
		<td>${test.resultValue}</td>
	</c:otherwise>
	</c:choose>
	<td>${test.refLower}</td>
	<td>${test.refUpper}</td>
	<td>${test.unit}</td>
	<td>${test.comments}</td>
	<td><fmt:formatDate value="${test.dateCreated}" pattern="dd-MMM-yyyy HH:mm"/></td>
	</tr>
</c:forEach>
<tr><td><input id="hiddenField" type="hidden" class="hiddenField" name="id" value="${testViewObject.id}"></td></tr>
</tbody>
</table>
</form:form>
</body>
</html>