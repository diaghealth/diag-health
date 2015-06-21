<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
<table>
<th>Test Name</th>
<th>Result</th>
<th>Date</th>
<c:forEach var="test" items="${testViewObject.testList}" varStatus="status">
	<tr>
	<td>${test.name}</td>
	<td>${test.resultValue}</td>
	<td><fmt:formatDate value="${test.dateCreated}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>
</c:forEach>
<td><input id="hiddenField" type="hidden" class="hiddenField" name="id" value="${testViewObject.id}"></td></tr>

</table>
</form:form>
</body>
</html>