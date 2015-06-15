<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Receipt</title>
<jsp:include page="menuHeader.jsp" />
</head>
<body>

<h3>Enter Receipt Id:</h3>
<form:form action="searchReceipt" modelAttribute="receiptObj" method="POST">
<form:errors class="error"/>
<%-- <label>${errorMessage}</label> --%>
<br>
<%-- <%
	String errorString = (String)request.getAttribute("error");
	if(errorString != null){
	out.println(errorString);
	}
%> --%>
<input size="30" type="text" name="receiptId" value="" />
<p class="submit">
	<input type="submit" name="commit" value="Search" class="button">
</p>
</form:form>
<%-- <jsp:include page="viewReceipt.jsp" /> --%>
</body>
</html>