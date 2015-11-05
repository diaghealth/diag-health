<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="css/commonTable.css">
<jsp:include page="menuHeader.jsp" />
<title>Add Test For Lab (Admin)</title>
</head>
<body>
<table>
<tr>
<th>Test Type</th>
<th>Sub Group1</th>
<th>Sub Group2</th>
<th>Sub Group3</th>
<th>Test Name</th>
<th>Reference Lower</th>
<th>Reference Upper</th>
<th>Gender</th>
<th>Unit</th>
<th>Comments</th>
</tr>
<c:forEach var="test" items="${labTestExistList}" varStatus="status">
	<tr>
	<%-- <td>${test.type}</td> --%>
	<td><c:if test="${not empty test.ancestorGroupNames[0]}">${test.ancestorGroupNames[0]}</c:if></td>
	<td><c:if test="${not empty test.ancestorGroupNames[1]}">${test.ancestorGroupNames[1]}</c:if></td>
	<td><c:if test="${not empty test.ancestorGroupNames[2]}">${test.ancestorGroupNames[2]}</c:if></td>
	<td><c:if test="${not empty test.ancestorGroupNames[2]}">${test.ancestorGroupNames[3]}</c:if></td>
	<td>${test.name}</td>
	<td>${test.refLower}</td>
	<td>${test.refUpper}</td>
	<td>${test.userGender}</td>
	<td>${test.unit}</td>
	<td>${test.comments}</td>
	</tr>
</c:forEach>
</table>
<h2> Add Tests</h2>
<form:form action="saveLabTestType" modelAttribute="labTest" method="POST">
<form:errors/>
<table>
<tr>
<th>Test Type</th>
<th>Sub Group1</th>
<th>Sub Group2</th>
<th>Sub Group3</th>
<th>Test Name</th>
<th>Reference Lower</th>
<th>Reference Upper</th>
<th>Gender</th>
<th>Unit</th>
<th>Comments</th>
</tr>
<tr>
<td><input type="text" name="ancestorGroupNames[0]"/></td>
<td><input type="text" name="ancestorGroupNames[1]"/></td>
<td><input type="text" name="ancestorGroupNames[2]"/></td>
<td><input type="text" name="ancestorGroupNames[3]"/></td>
<td><input type="text" name="name"/></td>
<td><input type="text" name="refLower" value="0.0"/></td>
<td><input type="text" name="refUpper" value="0.0"/></td>
<td>
<form:select id="userGender" path="userGender" size="1" name="userGender">
					  <c:forEach items="${userGender}" var="userGender">
				          <form:option value="${userGender}">${userGender}</form:option>
				        </c:forEach>
					</form:select>
					<!-- <select name="userGender">
		<option value="MALE">MALE</option>
		<option value="FEMALE">FEMALE</option>
		<option value="NA">NA</option>
	</select> -->
</td>
<td><input type="text" name="unit"/></td>
<td><input type="text" name="comments"/></td>
</tr>
</table>
<input name="submit" type="submit" id="submitButton" class="button" value="Save" />
</form:form>
</body>
</html>