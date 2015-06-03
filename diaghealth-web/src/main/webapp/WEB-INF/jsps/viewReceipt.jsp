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
<form:form action="saveReceiptDetails" modelAttribute="receiptView" method="POST">
<input type="hidden" name="receipt.id" value="${receiptView.receipt.id}"/>
<h1>
<input type="hidden" name="receipt.receiptId" value="${receiptView.receipt.receiptId}"/>
${receiptView.receipt.receiptId}
</h1>
<h3>
<c:if test="${not empty receiptView.receipt.subject.firstname}">
<input type="hidden" name="receipt.subject.firstname" value="${receiptView.receipt.subject.firstname}"/>
${receiptView.receipt.subject.userType}: ${receiptView.receipt.subject.firstname}
</c:if>
</h3>
<h3>
<c:forEach items="${receiptView.receipt.relatedUsers}" var="user">				        
<c:if test="${not empty user.firstname}">
<input type="hidden" name="user.firstname" value="${user.firstname}"/>
${user.userType}: ${user.firstname}
</c:if>
</c:forEach>
</h3>
<h3>
<%-- <c:if test="${not empty receiptView.receipt.doctorName}">
<input type="hidden" name="receipt.doctorId" value="${receiptView.receipt.doctorId}"/>
Doctor: ${receiptView.receipt.doctorName}
</c:if> --%>
</h3>
<h3>
<c:if test="${not empty receiptView.receipt.validTill}">Valid Till : ${receiptView.receipt.validTill}</c:if>
</h3>
<h3>
<c:if test="${not empty receiptView.receipt.dateCreated}">Date Created : ${receiptView.receipt.dateCreated}</c:if>
</h3>
<c:if test="${not empty showTestList}">
<!-- ----------------------------Add new Table -------------------------------->
<jsp:include page="showLabTestTableCommon.jsp" />
<p></p>
<input type="submit" name="commit" value="Save" class="button">
</c:if>
</form:form>
</body>
</html>