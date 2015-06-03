<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="menuHeader.jsp" />
</head>
<body>
<%-- <c:choose>
    <c:when test="${userType.equals('DOCTOR')}">
    	<jsp:include page="doctorMenuHeader.jsp" />
    </c:when>
    <c:when test="${myvar.equals('bar')}">
    </c:when>
    <c:otherwise>
    </c:otherwise>
</c:choose> --%>

<form:form action="userDetails" id="userDetails" modelAttribute="userDetails" method="POST">
<h1>Welcome</h1>
<h2>Name: ${userDetails.firstname} ${userDetails.lastname}</h2>
<h2>Address:  ${userDetails.addressLine1} ${userDetails.addressLine2}</h2>
<h2>Location: ${userDetails.location}</h2>
<h2>Phone: ${userDetails.phoneNumber}</h2>
</form:form>
</body>
</html>