<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search Users</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">	
	<link type="text/css" rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500">
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
	<script src="https://maps.googleapis.com/maps/api/js?v=3.exp&signed_in=true&libraries=places"></script>
	<script src="js/locationFinder.js"></script> 
	<link type="text/css" rel="stylesheet" href="css/searchUser.css">
	<jsp:include page="menuHeader.jsp" />
</head>
<body onload="initialize()">
<div class="main">
<div class="search">
<form:form action="search" id="searchForm" modelAttribute="searchForm" method="POST">
<div id="LoginTable">
			<h3>User Type</h3>
			<p></p>
			<table border="0">
			<tr>
				<td class="labelField" width="33%">User Type</td>
				<td>
					<form:select id="userType" path="userType" size="1">
					  <c:forEach items="${userTypes}" var="userType">
				          <form:option value="${userType}">${userType}</form:option>
				        </c:forEach>
					</form:select>
				</td>
			</tr>
			</table>
			</div>
			<h3>User Details</h3>
			<p></p>
			<table border="0">
				<tr>
				  <td class="labelField" width="33%">Firstname</td>
				  <td width="67%"><form:input path="name" name="name" type="text" size="30" /></td>
				</tr>
				<tr>
				  <td class="labelField" width="33%">Phone</td>
				  <td width="67%"><form:input path="phoneNumber" name="phoneNumber" type="text" size="30" /></td>
				</tr>
				<tr>
					<td class="labelField" width=33%>Distance (in kms)</td>
					<td><form:input path="distance" type="range" min="0" max="20" value="3" oninput="outputUpdate(value)" id="selector"/></td>
				</tr>
				<tr>
					<td></td>
					<td>
					<output for="selector" id="distance">3 km</output>
					
					<script>
					
					function outputUpdate(dist) {					
						document.querySelector('#distance').value = dist + " km";
					}
					
					</script>
					</td>
					</tr>
				<tr>
				  <td class="labelField" >Location</td>
				  <td>
				  	<form:input onFocus="geolocate()" path="location" id="location" name="location" type="text" size="50" /></td>
				</tr>
				<tr>
					<td></td>
				  <td><form:input path="latLocation" id="latitude" type="text" size="30" readonly="true"/></td>
				</tr>
				<tr>
					<td></td>
				  <td><form:input path="longLocation" id="longitude" type="text" size="30" readonly="true"/></td>
				</tr>
			</table>
			<div id="map_canvas"></div>
			<p></p>
			<input name="submit" type="submit" id="submitButton" value="Submit" class="button"/>
		</div>
</form:form>
</div>
</div>
</body>
</html>