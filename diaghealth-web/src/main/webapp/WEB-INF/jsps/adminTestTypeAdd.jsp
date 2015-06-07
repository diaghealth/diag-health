<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="css/commonTable.css">
<title>Add Test For Lab (Admin)</title>
</head>
<body>
<table>
<tr>
<th>Test Type</th>
<th>Test Name</th>
</tr>
<c:forEach var="test" items="${labTestExistList}" varStatus="status">
	<tr>
	<td>${test.type}</td>
	<td>${test.name}</td>
	</tr>
</c:forEach>
</table>
<h2> Add Tests</h2>
<form:errors/>
<form:form action="saveLabTestType" modelAttribute="labTest" method="POST">
<table>
<tr>
<th>Test Type</th>
<th>Test Name</th>
</tr>
<tr>
<td><input type="text" name="type"/></td>
<td><input type="text" name="name"/></td>
</tr>
</table>
<input name="submit" type="submit" id="submitButton" class="button" value="Save" />
</form:form>
</body>
</html>