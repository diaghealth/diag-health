<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="css/commonTable.css">
<title>User List</title>
</head>
<body>
<jsp:include page="menuHeader.jsp" />
<%-- <form:form action="register" id="userAdd" modelAttribute="userAdd" method="POST"> --%>
<table class="rwd-table">
<tr>
<th>First Name</th>
<th>Last Name</th>
<th>User Type</th>
<th>Phone</th>
<th>Email</th>
<th>Address</th>
<th>Location</th>
<c:if test="${not empty userHistory}">
<th>Tests Price</th>
<c:if test="${displayUserType eq 'PATIENT'}">
<th>Tests Report</th>
</c:if>
</c:if>
<c:if test="${(userType eq 'LAB') && (displayUserType eq 'PATIENT')}">
<th>Add Tests</th>
</c:if>
<th>Receipt</th>
</tr>
<c:forEach var="users" items="${userList}" varStatus="status">
<tr id="rowNum${status.index}">
<td>${users.firstname}</td>
<td>${users.lastname}</td>
<td>${users.userType}</td>
<td>${users.phoneNumber}</td>
<td>${users.email}</td>
<td>${users.addressLine1} ${users.addressLine2}</td>
<td>${users.location}</td>
<c:if test="${not empty addUser}">
<td><a href="addUser?id=${users.id}&type=${users.userType}">Add</a></td>
</c:if>
<c:if test="${not empty userHistory}">
<td><a href="userRelationHistory?id=${users.id}&userType=${users.userType}">Tests Price</a></td>
<c:if test="${displayUserType eq 'PATIENT'}">
<td><a href="showReport?id=${users.id}&userType=${users.userType}">Tests Report</a></td>
</c:if>
</c:if>
<c:if test="${(userType eq 'LAB') && (displayUserType eq 'PATIENT')}">
<td><a href="searchReceiptById?receiptId=DUMMY&id=${users.id}">Add Tests</a></td>
</c:if>
<c:if test="${not empty buildReceipt}">
<td><a href="buildReceipt?id=${users.id}">Generate Receipt</a></td>
</c:if>
</tr>
</c:forEach>
</table>
<%-- </form:form> --%>
</body>
</html>