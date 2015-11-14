<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search Tests</title>
<script src="http://code.jquery.com/jquery-1.10.2.js"></script>
 <script src="http://code.jquery.com/ui/1.11.4/jquery-ui.js"></script>  	
<!-- <link rel="stylesheet" href="http://code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css"> -->
<link rel="stylesheet" href="css/jquery-ui.css">
<link type="text/css" rel="stylesheet" href="css/commonTable.css">
<script src="js/datePicker.js"></script>
<jsp:include page="menuHeader.jsp" />
</head>
<body>
<form:form action="searchTests" id="searchForm" modelAttribute="searchForm" method="POST">
<table border="0">
<c:if test="${not empty userType}">
	<tr>
	<c:if test="${userType != 'DOCTOR'}">
	<td class="labelField">Doctor Name</td>
	</c:if>
	<c:if test="${userType != 'PATIENT'}">
	<td class="labelField">Patient Name</td>
	</c:if>
	<c:if test="${userType != 'LAB'}">
	<td class="labelField">Lab Name</td>
	</c:if>
	<c:if test="${userType != 'CLINIC'}">
	<td class="labelField">Clinic Name</td>
	</c:if>
	</tr>
	<tr>
	<c:if test="${userType != 'DOCTOR'}">
	<td><form:input path="doctor" name="doctor" type="text" size="30" /></td>
	</c:if>
	<c:if test="${userType != 'PATIENT'}">
	<td><form:input path="patient" name="patient" type="text" size="30" /></td>
	</c:if>
	<c:if test="${userType != 'LAB'}">
	<td><form:input path="lab" name="lab" type="text" size="30" /></td>
	</c:if>
	<c:if test="${userType != 'CLINIC'}">
	<td><form:input path="clinic" name="clinic" type="text" size="30" /></td>
	</c:if>
	</tr>
</c:if>
	<tr>
	<td class="labelField">Test Type</td>
	<td class="labelField">Test Name</td>
	<td class="labelField">Date From</td>
	<td class="labelField">Date To</td>
	</tr>
	<tr>
	<td><form:input path="testName" name="testName" type="text" size="30" /></td>
	<td><form:input path="testType" name="testType" type="text" size="30" /></td>
	<td><div class="date"><form:input type="text" class="datepicker" name="fromDate" path="fromDate" value="${fromDate}"/></div></td>
	<td><div class="date"><form:input type="text" class="datepicker" name="toDate" path="toDate" value="${toDate}"/></div></td>
	</tr>
</table>
<p></p>
<input name="submit" type="submit" id="submitButton" value="Submit" class="button"/>
</form:form>
</body>
</html>