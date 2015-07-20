<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<!-- <script src="http://code.jquery.com/jquery-2.1.4.min.js"></script> -->
<!-- <script class="cssdeck" src="//cdnjs.cloudflare.com/ajax/libs/jquery/1.8.0/jquery.min.js"></script> -->
<link type="text/css" rel="stylesheet" href="css/menuHeader.css">
</head>

<!-- <nav>
  <ul>
    <li><a href="#1">First</a></li>
    <li><a href="#2">Second</a></li>
    <li><a href="#3">Third</a></li>
    <li><a href="#4">Fourth</a></li>
    <li><a href="#5">Fifth</a></li>
  </ul>
</nav> -->

<!-- <div class="sections">
  <section id="1"><h1>First</h1></section>
  <section id="2"><h1>Second</h1></section>
  <section id="3"><h1>Third</h1></section>
  <section id="4"><h1>Fourth</h1></section>
  <section id="5"><h1>Fifth</h1></section>
</div>
 -->
 <body>
 <div class="menuHeader">
<nav>
  <ul>
<c:if test="${not empty userType}">
<li><a href="welcomeHome">Home</a></li> 
<li><a href="updateDetails">Update Details</a></li>
<li><a href="searchUser">Search Users</a></li>
<c:choose>
    <c:when test="${userType.equals('DOCTOR')}">  	
		<li><a href="related?type=patient">Patients</a></li>
		<li><a href="related?type=lab">Diagnostic</a></li>
		<li><a href="related?type=clinic">Clinic</a></li>
		<li><a href="register">Register User</a></li>
    </c:when>
    <c:when test="${userType.equals('LAB')}">    	
		<li><a href="labTests">Update Tests</a></li>
		<li><a href="related?type=patient">Patients</a></li>
		<li><a href="related?type=doctor">Doctors</a></li>
		<li><a href="related?type=clinic">Clinic</a></li>
		<li><a href="register">Register User</a></li>
		<li><a href="searchReceipt">Find Receipt</a></li>
    </c:when>
    <c:when test="${userType.equals('CLINIC')}">    	
		<li><a href="related?type=patient">Patients</a></li>
		<li><a href="related?type=lab">Diagnostic</a></li>
		<li><a href="related?type=doctor">Doctor</a></li>
		
    </c:when>
    <c:when test="${userType.equals('PATIENT')}">    	
		<li><a href="related?type=clinic">Clinic</a></li>	
		<li><a href="related?type=lab">Diagnostic</a></li>
		<li><a href="related?type=doctor">Doctor</a></li>
    </c:when>
    <c:otherwise>
    </c:otherwise>
</c:choose>
<!-- <li><a href="searchTests">Search Tests</a></li> -->
<li><a href="logout">Logout</a></li>
</c:if>
<c:if test="${userDetails.username.equals('diaghealth') || userDetails.username.equals('admin')}">
	<li><a href="addLabTestType">Add Base Tests</a></li> 
</c:if>
</ul>
</nav>
</div>
<br><br><br><br>
</body>
</html>