<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Update Details</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">	
	<link type="text/css" rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500">
	<link type="text/css" rel="stylesheet" href="css/registerUserCommon.css">
	<script src="https://maps.googleapis.com/maps/api/js?v=3.exp&signed_in=true&libraries=places"></script>
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
	<script src="js/locationFinder.js"></script> 
	<jsp:include page="menuHeader.jsp" />
	<link type="text/css" rel="stylesheet" href="css/registerUserCommon.css">
</head>
<body>
<div class="main">
	<div class="register">
<h2 align="center">Update Details</h2>  
	<%-- <label>${errorMessage}</label>   --%>     
	<form:form action="updateDetails" id="detailsForm" modelAttribute="detailsForm" method="POST">
	<form:errors  class="error"/>    
	<h3>Password Change</h3>
	<table>
	<tr>
	<td>Old Password:</td><td><td width="67%"><form:input path="oldPassword" name="oldPassword" value="" type="password" size="30" /></td>
	</tr>
	<tr>
	<td>New Password:</td><td><td width="67%"><form:input path="newPassword" name="newPassword" value="" type="password" size="30" /></td>
	</tr>
	<tr>
	<td>Retype New Password:</td><td><td width="67%"><form:input path="newPasswordRepeat" name="newPasswordRepeat" value="" type="password" size="30" /></td>	
	</tr>
	</table>
	<h3>Personal Details</h3>
	<p></p>
	<table>	
	<tr>
	<td>First Name*</td>
	<td><form:input path="userDetails.firstname" name="userDetails.firstname" value="${userDetails.firstname}" readonly="true"/></td>
	</tr>
	<tr>
				  <td class="labelField" width="33%">Last Name</td>
				  <td width="67%"><form:input path="userDetails.lastname" name="userDetails.lastname" value="${userDetails.lastname}" type="text" size="30" /></td>
				</tr>
				<tr>
				  <td class="labelField" width="33%">Phone *</td>
				  <td width="67%"><form:input path="userDetails.phoneNumber" name="userDetails.phoneNumber" type="text" size="30" readonly="true"/></td>
				 </tr>
				<tr>
				  <td class="labelField" width="33%">Email</td>
				  <td width="67%"><form:input path="userDetails.email" name="userDetails.email" type="text" size="30" /></td>
				  <%-- <form:errors path="email" cssClass="error" /> --%>
				</tr>
				<tr>
				  <td class="labelField" >Address Line 1</td>
				  <td><form:input path="userDetails.addressLine1" name="userDetails.addressLine1" type="text" size="50" /></td>
				</tr>
				<tr>
				  <td class="labelField" >Address Line 2</td>
				  <td><form:input path="userDetails.addressLine2" name="userDetails.addressLine2" type="text" size="50" /></td>
				</tr>
				<tr>
				  <td class="labelField" >Location</td>
				  <td>
				  	<form:input onFocus="geolocate()" path="userDetails.location" id="location" name="userDetails.location" type="text" size="50" /></td>
				  	<%-- <form:errors path="location" cssClass="error" /> --%>
				</tr>
				<tr>
				  <td class="labelField" >State</td>
				  <td><form:input path="userDetails.state" name="userDetails.state" type="text" size="30" /></td>
				</tr>
				<tr>
				  <td class="labelField" >Pincode</td>
				  <td><form:input path="userDetails.pincode" name="userDetails.pincode" type="text" size="10" /></td>
				  <%-- <form:errors path="pincode" cssClass="error" /> --%>
				</tr>
				<tr>
					<td></td>
				  <td><form:input path="userDetails.latLocation" id="latitude" name="userDetails.latLocation" type="text" size="30" readonly="true"/></td>
				  <%-- <form:errors path="latLocation" cssClass="error" /> --%>
				</tr>
				<tr>
					<td></td>
				  <td><form:input path="userDetails.longLocation" id="longitude" name="userDetails.latLocation" type="text" size="30" readonly="true"/></td>
				  </tr>
				  <tr><td class="labelField" >* Non Editable Fields</td></tr>
			</table>
			         <div id="map_canvas" style="width:50%;height:300px"></div>
			<p></p>
			<input name="submit" type="submit" id="submitButton" value="Submit" class="button"/>
	</form:form>
	</div>
	</div>
</body>
</html>