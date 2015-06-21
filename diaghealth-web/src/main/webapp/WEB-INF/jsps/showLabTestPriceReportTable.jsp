<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<!-- <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script> -->
<script src="js/addTestRow.js"></script>
<link rel="stylesheet" type="text/css" href="css/commonTable.css">
<style>
*
{
    font-family: Tahoma;
    font-size: 13px;
}
#displayTestsTable input[readonly='readonly']
{
    background-color:transparent;
    border:0px;
    padding:0px;
}
#addNewTestPriceReportTable input[readonly='readonly']
{
	background-color:transparent;
    border:0px;
    padding:0px;
}
</style>
<script>

function deleteRow(obj){
	var rowId = $(obj).closest('tr').attr('id');
	var index = rowId.split("rowIndex");	
	$(obj).closest('tr').remove();
	$.get("<%=request.getContextPath()%>/deleteTestRow", { fieldId: index[1] }, null);
}
</script>
</head>
<body>
<!-- -------------------------Display Table starts-------------------------- -->

<table id="displayTestsTable">
<tr><th>Test Type</th><th>Test Name</th><th>Price</th><th>Discount %</th>
<c:if test="${not empty buildResult}">
<th>Result</th><th>Lower</th><th>Upper</th><th>Unit</th>
</c:if>
<th>Date</th>
<th>Delete</th>

</tr>
<c:if test="${fn:length(labTests.testList) > 0}">
<c:forEach var="test" items="${labTests.testList}" varStatus="status">
	
	<tr id="rowIndex${status.index}">
	<%-- <div class="test_item_<c:out value='${status.index}' />"> --%>
	
		<td>
			<input name='testList[${status.index}].type' readonly='readonly' value='${test.type}' class='type'/>
		</td>
		<td>
			<input name='testList[${status.index}].name' readonly='readonly' value='${test.name}' class='name'/>
		</td>
		<td>
			<input name='testList[${status.index}].price' readonly='readonly' value='${test.price}' class='price'/>
		</td>
		<td>
			<input name='testList[${status.index}].discountPercent' readonly='readonly' 
			value='${test.discountPercent}' class='discountPercent'/>
		</td>	
		<c:if test="${not empty buildResult}">	
		<td>
			<input name='testList[${status.index}].resultValue' value='${test.resultValue}' class='resultValue'/>
		</td>
		<td>
			<input name='testList[${status.index}].refLower' readonly='readonly' value='${test.refLower}' class='refLower'/>
		</td>
		<td>
			<input name='testList[${status.index}].refUpper' readonly='readonly' value='${test.refUpper}' class='refUpper'/>
		</td>
		<td>
			<input name='testList[${status.index}].unit' readonly='readonly' value='${test.unit}' class='unit'/>
		</td>	
		</c:if>		
		<td>
			<input name='testList[${status.index}].dateCreated' readonly='readonly' value='${test.dateCreated}' class='dateCreated'/>
		</td>	
		<td></td> <!-- empty delete button -->
		<!-- <td><button type='button' class='deleteButton' onclick='deleteRow(this)'>Delete</button></td> -->	
		</tr>
</c:forEach>
</c:if>
	<tr id="endRow">
	</tr>
</table>
<input id="hiddenField" type="hidden" class="hiddenField" value='${jsonMap}'>

<br>

<!-- ----------------------------Add new Table -------------------------------->

<h2> Add Tests</h2>

<table id="addNewTestPriceReportTable">
<tr><th>Test Type</th><th>Test Name</th><th>Price</th><th>Discount %</th>
<c:if test="${not empty buildResult}">
<th>Result</th><th>Lower</th><th>Upper</th><th>Unit</th>
</c:if>
</tr>
<tr>
<td><select id="testType" size="1">
					  <%-- <c:forEach items="${allTests}" var="testsMap" varStatus="index" >
				          <option value="${testsMap.key}">${testsMap.key}</option>
				        </c:forEach> --%>
					</select>
</td>
<td><select id="testName" size="1">
					  <%-- <c:forEach items="${allTests}" var="testsMap" varStatus="status" begin="0" end="0">
					  		<c:if test="${status.index eq 0}"> <!-- display only the 0th index of test type -->
					  			<c:forEach items="${testsMap.value}" var="testObject">
				          			<option value="${testObject.name}">${testObject.name}</option>
				          		</c:forEach>
				          </c:if>
				        </c:forEach> --%>
					</select>
</td>
<td><input size="30" type="text" id="testPrice" name="price" value=""/></td>
<td><input size="30" type="text" id="testDiscount" name="discountPercent" value="0.0"/></td>
<c:if test="${not empty buildResult}">
<td><input size="30" type="text" id="testResultValue" name="resultValue" value="0.0"/></td>
<td><input size="30" type="text" id="testRefLower" readonly='readonly' name="refLower" value="0.0"/></td>
<td><input size="30" type="text" id="testRefUpper" readonly='readonly' name="refUpper" value="0.0"/></td>
<td><input size="30" type="text" id="testUnit" readonly='readonly' name="unit" value="0.0"/></td>
</c:if>
</tr>
</table>
<c:choose>
<c:when test="${not empty buildResult}">
<button type="button" id="addNewTestPriceReport">Add Test</button>
</c:when>
<c:otherwise>
<button type="button" id="addNewTestPrice">Add Test</button>
</c:otherwise>
</c:choose>
</body>
</html>