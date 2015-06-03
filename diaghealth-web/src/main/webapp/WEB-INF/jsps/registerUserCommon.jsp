<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>DiagOnline</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">	
	<link type="text/css" rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500">
	<link type="text/css" rel="stylesheet" href="css/registerUserCommon.css">
	<script src="https://maps.googleapis.com/maps/api/js?v=3.exp&signed_in=true&libraries=places"></script>
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>	
	<script src="js/locationFinder.js"></script> 
	<jsp:include page="menuHeader.jsp" />
</head>

<body onload="initialize()">
<div class="main">
	<div class="register">
	<h3 align="center">Register New User</h3>      
	<%-- <label>${errorMessage}</label> --%>      
	<form:errors/> 
	<form:form action="register" id="detailsForm" modelAttribute="detailsForm" method="POST">
		<div class="loginTable">
			<label>Login Details</label>
			<p></p>
			<table>
			<tr>
				<td class="labelField" width="33%">User Type *</td>
				<td>
					<form:select id="userType" path="userType" size="1">
					  <c:forEach items="${userTypes}" var="userType">
				          <form:option value="${userType}">${userType}</form:option>
				        </c:forEach>
					</form:select>
				</td>
			</tr>
			<tr>
				<td class="labelField" width="33%">Login UserName</td>
				<td width="67%"><form:input path="username" name="username" type="text" size="30" /></td>
				<form:errors path="username" cssClass="error" />
			</tr>
		    <tr>
		    	<td class="labelField" >Password</td>
		    	<td><form:input path="password" type="password" name="validatePassword" id="validatePassword" size="30"/></td>
		    	<form:errors path="password" cssClass="error" />
		    </tr>
			</table>
			
			</div>
			<div class="sep">
          	</div>
          	
          	<table>
          	<tr>
          	<td>
          	<div class="addressTable">          	
			<label>Address Details</label>
			<p></p>
			<table>
				<tr>
				  <td class="labelField" width="33%">First Name *</td>
				  <td width="67%"><form:input path="firstname" name="firstname" type="text" size="30" /></td>
				  <form:errors path="firstname" cssClass="error" />
				</tr>
				<tr>
				  <td class="labelField" width="33%">Last Name</td>
				  <td width="67%"><form:input path="lastname" name="lastname" type="text" size="30" /></td>
				  <form:errors path="lastname" cssClass="error" />
				</tr>
				<tr>
				  <td class="labelField" width="33%">Phone *</td>
				  <td width="67%"><form:input path="phoneNumber" name="phoneNumber" type="text" size="30" /></td>
				  <form:errors path="phoneNumber" cssClass="error" />
				</tr>
				<tr>
				  <td class="labelField" width="33%">Email</td>
				  <td width="67%"><form:input path="email" name="email" type="text" size="30" /></td>
				  <form:errors path="email" cssClass="error" />
				</tr>
				<tr>
				  <td class="labelField" >Address Line 1</td>
				  <td><form:input path="addressLine1" name="addressLine1" type="text" size="50" /></td>
				</tr>
				<tr>
				  <td class="labelField" >Address Line 2</td>
				  <td><form:input path="addressLine2" name="addressLine2" type="text" size="50" /></td>
				</tr>
				<tr>
				  <td class="labelField" >Location *</td>
				  <td>
				  	<form:input onFocus="geolocate()" path="location" id="location" name="location" type="text" size="50" /></td>
				  	<form:errors path="location" cssClass="error" />
				</tr>
				<tr>
				  <td class="labelField" >State</td>
				  <td><form:input path="state" name="state" type="text" size="30" /></td>
				</tr>
				<tr>
				  <td class="labelField" >Pincode</td>
				  <td><form:input path="pincode" name="pincode" type="text" size="10" /></td>
				  <form:errors path="pincode" cssClass="error" />
				</tr>
				<tr>
					<td></td>
				  <td><form:input path="latLocation" id="latitude" type="text" size="30" readonly="true" /></td>
				  <form:errors path="latLocation" cssClass="error" />
				</tr>
				<tr>
					<td></td>
				  <td><form:input path="longLocation" id="longitude" type="text" size="30" readonly="true"/></td>
				  </tr>
				  <tr><td class="labelField" >* Mandatory Fields</td></tr>
			</table>
			</div>
			</td>
			<td>
			<div id="map_canvas" class="map_canvas"></div>
			</td>
			</tr>
			</table>
			<p></p>
			<div class="submitButton"><input name="submit" type="submit" id="submitButton" class="button" value="Submit" /></div>
		
	</form:form>
	</div>
	</div>
</body>
</html>
