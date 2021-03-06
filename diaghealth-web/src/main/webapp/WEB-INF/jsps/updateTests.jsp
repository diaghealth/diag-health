<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Update Tests</title>
<!-- <script src="http://code.jquery.com/jquery-1.10.2.js"></script>
<script src="http://code.jquery.com/ui/1.11.4/jquery-ui.js"></script>  -->
<script src="js/jquery.js"></script>
<script src="js/jquery-ui.js"></script>
<script src="js/baseTestDropDownPopulator.js"></script>
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
<jsp:include page="menuHeader.jsp" />
<form:form action="saveTests" modelAttribute="labTests" method="POST">
<!-- -------------------------Display Table starts-------------------------- -->

<table id="displayTestsTable">
<tr><th>Test Type</th><th>Sub Group1</th><th>Sub Group2</th><th>Sub Group3</th><th>Test Name</th><th>Gender</th>
<th>Age Lower</th><th>Age Upper</th><th>Price</th><th>Discount %</th>
<c:if test="${not empty buildResult}">
<th>Result</th>
</c:if>
<th>Lower Ref</th><th>Upper Ref</th><th>Unit</th><th>Comments</th>
<th>Date</th>
<th>Delete</th>
</tr>
<c:if test="${fn:length(labTests.testList) > 0}">
<c:forEach var="test" items="${labTests.testList}" varStatus="status">
	
	<tr id="rowIndex${status.index}">
	<%-- <div class="test_item_<c:out value='${status.index}' />"> --%>		
		<%-- <td>
			<input name='testList[${status.index}].type' readonly='readonly' value='${test.type}' class='type'/>
		</td> --%>	
		<%-- <td>
			<input name='testList[${status.index}].ancestorGroupNames[0]' readonly='readonly' value='${test.ancestorGroupNames[0]}' class='type'/>
		</td>	
		<td>
			<input name='testList[${status.index}].ancestorGroupNames[1]' readonly='readonly' value='${test.ancestorGroupNames[1]}' class='type'/>
		</td>
		<td>
			<input name='testList[${status.index}].ancestorGroupNames[2]' readonly='readonly' value='${test.ancestorGroupNames[2]}' class='type'/>
		</td>
		<td>
			<input name='testList[${status.index}].nancestorGroupNames[3]' readonly='readonly' value='${test.ancestorGroupNames[3]}' class='type'/>
		</td> --%>
		<td><c:if test="${not empty test.ancestorGroupNames[0]}">${test.ancestorGroupNames[0]}</c:if></td>
		<td><c:if test="${not empty test.ancestorGroupNames[1]}">${test.ancestorGroupNames[1]}</c:if></td>
		<td><c:if test="${not empty test.ancestorGroupNames[2]}">${test.ancestorGroupNames[2]}</c:if></td>
		<td><c:if test="${not empty test.ancestorGroupNames[3]}">${test.ancestorGroupNames[3]}</c:if></td>
		<td>
			<input name='testList[${status.index}].name' readonly='readonly' value='${test.name}' class='name'/>
		</td>
		<td>
			<input name='testList[${status.index}].userGender' readonly='readonly' value='${test.userGender}' class='gender'/>
		</td>
		<td>
			<input name='testList[${status.index}].ageLower' readonly='readonly' value='${test.ageLower}' class='ageLower'/>
		</td>
		<td>
			<input name='testList[${status.index}].ageUpper' readonly='readonly' value='${test.ageUpper}' class='ageUpper'/>
		</td>
		<td>
			<input name='testList[${status.index}].price' value='${test.price}' class='price'/>
		</td>
		<td>
			<input name='testList[${status.index}].discountPercent' 
			value='${test.discountPercent}' class='discountPercent'/>
		</td>	
		<c:choose>
		<c:when test="${not empty buildResult}">				
			<c:choose>
				<c:when test="${(test.refLower lt test.refUpper) && ((test.resultValue lt test.refLower) || (test.resultValue gt test.refUpper))}">
					<td class="error">						
				</c:when>
				<c:otherwise>
					<td>
				</c:otherwise>
			</c:choose>
			<input name='testList[${status.index}].resultValue' value='${test.resultValue}' class='resultValue'/>
			</td>						
		</c:when>	
		</c:choose>	
		<td>
			<input name='testList[${status.index}].refLower' readonly='readonly' value='${test.refLower}' class='refLower'/>
		</td>
		<td>
			<input name='testList[${status.index}].refUpper' readonly='readonly' value='${test.refUpper}' class='refUpper'/>
		</td>
		<td>
			<input name='testList[${status.index}].unit' readonly='readonly' value='${test.unit}' class='unit'/>
		</td>	
		<td>
			<input name='testList[${status.index}].comments' readonly='readonly' value='${test.comments}' class='unit'/>
		</td>	
		<td>
			<fmt:formatDate value="${test.dateCreated}" var="dateString" pattern="dd-MMM-yyyy HH:mm" />
			<input name='testList[${status.index}].dateCreated' readonly='readonly' value='${dateString}' class='dateCreated'/>
		</td>	
		<td>
			<input type='hidden' name='testList[${status.index}].id' readonly='readonly' value='${test.id}' class='id'/>
		</td> <!-- empty delete button -->
		<!-- <td><button type='button' class='deleteButton' onclick='deleteRow(this)'>Delete</button></td> -->	
		</tr>
</c:forEach>
</c:if>
	<tr id="endRow">
	</tr>
</table>
<input id="hiddenField" type="hidden" class="hiddenField" value='${jsonMap}'>

<br>

<!-- ----------------------------Add from Base Test Table -------------------------------->

<h2> Add Tests</h2>

<table id="addNewTestPriceReportTable">
<tr><th>Test Type</th><th>Sub Group1</th><th>Sub Group2</th><th>Sub Group3</th><th>Test Name</th><th>Gender</th>
<th>Age Range</th><th>Price</th><th>Discount %</th>
<c:if test="${not empty buildResult}">
<th>Result</th>
</c:if>
<th>Lower Ref</th><th>Upper Ref</th><th>Unit</th><th>Comments</th>
</tr>
<tr>
<td><select id="testType" size="1">
					</select>
</td>
<td><select id="subGroup1" size="1">
					</select>
</td>
<td><select id="subGroup2" size="1">
					</select>
</td>
<td><select id="subGroup3" size="1">
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
<td><select id="testGender" size="1">
					</select>
</td>
<td><select id="testAgeRange" size="1">
					</select>
</td>
<td><input size="30" type="text" id="testPrice" name="price" value=""/></td>
<td><input size="30" type="text" id="testDiscount" name="discountPercent" value="0.0"/></td>
<c:choose>
<c:when test="${not empty buildResult}">
<c:choose>
	<c:when test="${(test.resultValue lt test.refLower) || (test.resultValue gt test.refUpper)}">
		<td class="error">						
	</c:when>
	<c:otherwise>
		<td>
	</c:otherwise>
</c:choose>
<input size="30" type="text" id="testResultValue" name="resultValue" value=""/></td>
</c:when>
</c:choose>
<td><input size="30" type="text" id="testRefLower" name="refLower" value="0.0"/></td>
<td><input size="30" type="text" id="testRefUpper" name="refUpper" value="0.0"/></td>
<td><input size="30" type="text" id="testUnit" name="unit" value=""/></td>
<td><input size="30" type="text" id="testComments" name="comments" value="0.0"/></td>
</tr>
</table>
<%-- <c:choose>
<c:when test="${not empty buildResult}">
<button type="button" id="addNewTestPriceReport">Add Test</button>
</c:when>
<c:otherwise> --%>
<button type="button" id="addNewTestPrice">Add Test</button>
<%-- </c:otherwise>
</c:choose> --%>

<!-- ----------------------------Add New Test Table -------------------------------->

<h2> Add Tests</h2>

<table id="addNewTestPriceReportTable">
<tr><th>Test Type</th><th>Sub Group1</th><th>Sub Group2</th><th>Sub Group3</th><th>Test Name</th><th>Gender</th>
<th>Age Lower</th><th>Age Upper</th><th>Price</th><th>Discount %</th>
<c:if test="${not empty buildResult}">
<th>Result</th>
</c:if>
<th>Lower Ref</th><th>Upper Ref</th><th>Unit</th><th>Comments</th>
</tr>
<tr>
<td><input size="30" type="text" id="testTypeNew" name="ancestorGroupNames[0]" value=""/></td>
<td><input size="30" type="text" id="subGroup1New" name="ancestorGroupNames[1]" value=""/></td>
<td><input size="30" type="text" id="subGroup2New" name="ancestorGroupNames[2]" value=""/></td>
<td><input size="30" type="text" id="subGroup3New" name="ancestorGroupNames[3]" value=""/></td>
<td><input size="30" type="text" id="testNameNew" name="testName" value=""/></td>
<td>
<select id="testGenderNew" name="userGender" size="1">
  <c:forEach items="${userGender}" var="userGender">
         <option value="${userGender}">${userGender}</option>
       </c:forEach>
</select>
</td>
<td><input size="30" type="text" id="testAgeLowerNew" name="ageLower" value=""/></td>
<td><input size="30" type="text" id="testAgeUpperNew" name="ageUpper" value=""/></td>
<td><input size="30" type="text" id="testPriceNew" name="price" value=""/></td>
<td><input size="30" type="text" id="testDiscountNew" name="discountPercent" value="0.0"/></td>
<%-- <c:choose>
<c:when test="${not empty buildResult}">
<c:choose>
	<c:when test="${(test.resultValue lt test.refLower) || (test.resultValue gt test.refUpper)}">
		<td class="error">						
	</c:when>
	<c:otherwise>
		<td>
	</c:otherwise>
</c:choose>
<input size="30" type="text" id="testResultValueNew" name="resultValue" value=""/></td>
</c:when>
</c:choose> --%>
<td><input size="30" type="text" id="testRefLowerNew" name="refLower" value="0.0"/></td>
<td><input size="30" type="text" id="testRefUpperNew" name="refUpper" value="0.0"/></td>
<td><input size="30" type="text" id="testUnitNew" name="unit" value=""/></td>
<td><input size="30" type="text" id="testCommentsNew" name="comments" value="0.0"/></td>
</tr>
</table>
<%-- <c:choose>
<c:when test="${not empty buildResult}">
<button type="button" id="addNewTestPriceReport">Add Test</button>
</c:when>
<c:otherwise> --%>
<button type="button" id="addNewTestPriceNew">Add Test</button>
<%-- </c:otherwise>
</c:choose> --%>


<p></p>
<input type="submit" class="button" name="commit" value="Save">
</form:form>
<h5>
* Note: For Ages in months input as e.g.
3 months = 0.25, 6 months = 0.5, 9 months = 0.75, 12 months = 1 year
15 months = 1.25, 18 months = 1.5, 21 months = 1.75 and so on
</h5>
</body>
</html>