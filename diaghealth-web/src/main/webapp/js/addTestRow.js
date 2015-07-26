var testCount = 0;
var testHashMap;
var currentDate = "";
$(document).ready( function() {
	testCount = $('table#displayTestsTable tr:last').index() - 1; //1 for header
	var jsonString = $("#hiddenField").val();
	//$.datepicker.formatDate('yy/mm/dd', new Date());
	//Initialize drop down from json Map
	testHashMap =$.parseJSON(jsonString);
	currentDate = $.datepicker.formatDate('dd-M-yy', new Date());
	//currentDate += $.timepicker({});
	var now = $.now();
	//currentDate = dateFormat(now, "dddd, mmmm dS, yyyy, h:MM:ss TT");
	var $testType = $('#testType');
	$.each(testHashMap,function(key, value) 
			{
		$testType.append("<option value='" + key + "'>" + key + "</option>");
	});
	
	onChange();
	
	$("#addNewTestPrice").click(function(){		
		addNewTestPriceReport();
	});	
	
	$("#addNewTestPriceNew").click(function(){		
		addNewTestPriceReportNew();
	});
	
	$("#addNewTestPriceReport").click(function(){		
		var data;
		data = "<tr id='rowIndex" + testCount + "'>" +  
				"<td><input name='testList[" + testCount + "].type' readonly='readonly' value='" + $('#testType').val()+ "' class='type'/></td>" + 
				"<td><input name='testList[" + testCount + "].name' readonly='readonly' value='" +$('#testName').val() + "' class='name'/></td>" +
				"<td><input name='testList[" + testCount + "].userGender' value='" + $('#testGender').val()+ "' class='gender'/></td>" +
				"<td><input name='testList[" + testCount + "].price' value='" + $('#testPrice').val()+ "' class='price'/></td>" +
				"<td><input name='testList[" + testCount + "].discountPercent' value='" + $('#testDiscount').val()+ "' class='discountPercent'/></td>" +
				"<td><input name='testList[" + testCount + "].resultValue' value='" + $('#testResultValue').val()+ "' class='resultValue'/></td>" +
				"<td><input name='testList[" + testCount + "].refLower' readonly='readonly' value='" + $('#testRefLower').val()+ "' class='refLower'/></td>" +
				"<td><input name='testList[" + testCount + "].refUpper' readonly='readonly' value='" + $('#testRefUpper').val()+ "' class='refUpper'/></td>" +
				"<td><input name='testList[" + testCount + "].unit' readonly='readonly' value='" + $('#testUnit').val()+ "' class='unit'/></td>" +
				"<td><input name='testList[" + testCount + "].comments' readonly='readonly' value='" + $('#testComments').val()+ "' class='comments'/></td>" +
				"<td><input name='testList[" + testCount + "].date' readonly='readonly' value='" + currentDate + "' class='dateCreated'/></td>" +
				"<td><button type='button' class='deleteButton' onclick='deleteRow(this)'>Delete</button></td>"
				"</tr>";
		testCount++;
		$("#endRow").before(data);
	});
	
	$("#testType").change(onChange);
	$("#testName").change(setGender);
});

function addNewTestPriceReport(){
	var data;
	data = "<tr id='rowIndex" + testCount + "'>" +  
			"<td><input name='testList[" + testCount + "].type' readonly='readonly' value='" + $('#testType').val()+ "'/></td>" + 
			"<td><input name='testList[" + testCount + "].name' readonly='readonly' value='" +$('#testName').val() + "'/></td>" +
			"<td><input name='testList[" + testCount + "].userGender' readonly='readonly' value='" +$('#testGender').val() + "'/></td>" +
			"<td><input name='testList[" + testCount + "].price' value='" + $('#testPrice').val()+ "'/></td>" +
			"<td><input name='testList[" + testCount + "].discountPercent' value='" + $('#testDiscount').val()+ "'/></td>" +
			"<td><input name='testList[" + testCount + "].refLower' readonly='readonly' value='" + $('#testRefLower').val()+ "' class='refLower'/></td>" +
			"<td><input name='testList[" + testCount + "].refUpper' readonly='readonly' value='" + $('#testRefUpper').val()+ "' class='refUpper'/></td>" +
			"<td><input name='testList[" + testCount + "].unit' readonly='readonly' value='" + $('#testUnit').val()+ "' class='unit'/></td>" +
			"<td><input name='testList[" + testCount + "].comments' readonly='readonly' value='" + $('#testComments').val()+ "' class='comments'/></td>" +
			"<td><button type='button' class='deleteButton' onclick='deleteRow(this)'>Delete</button></td>"
			"</tr>";
	testCount++;
	$("#endRow").before(data);
}

function addNewTestPriceReportNew(){
	var data;
	data = "<tr id='rowIndex" + testCount + "'>" +  
			"<td><input name='testList[" + testCount + "].type' readonly='readonly' value='" + $('#testTypeNew').val()+ "'/></td>" + 
			"<td><input name='testList[" + testCount + "].name' readonly='readonly' value='" +$('#testNameNew').val() + "'/></td>" +
			"<td><input name='testList[" + testCount + "].userGender' readonly='readonly' value='" +$('#testGenderNew').val() + "'/></td>" +
			"<td><input name='testList[" + testCount + "].price' value='" + $('#testPriceNew').val()+ "'/></td>" +
			"<td><input name='testList[" + testCount + "].discountPercent' value='" + $('#testDiscountNew').val()+ "'/></td>" +
			"<td><input name='testList[" + testCount + "].refLower' readonly='readonly' value='" + $('#testRefLowerNew').val()+ "' class='refLower'/></td>" +
			"<td><input name='testList[" + testCount + "].refUpper' readonly='readonly' value='" + $('#testRefUpperNew').val()+ "' class='refUpper'/></td>" +
			"<td><input name='testList[" + testCount + "].unit' readonly='readonly' value='" + $('#testUnitNew').val()+ "' class='unit'/></td>" +
			"<td><input name='testList[" + testCount + "].comments' readonly='readonly' value='" + $('#testCommentsNew').val()+ "' class='comments'/></td>" +
			"<td><button type='button' class='deleteButton' onclick='deleteRow(this)'>Delete</button></td>"
			"</tr>";
	testCount++;
	$("#endRow").before(data);
}

function onChange(){
	var opt = $("#testType").find('option:selected').val();
	var $testName = $('#testName'); 
	$testName.find('option').remove();  
	$.each(testHashMap[opt],function(index, value) 
	{
		$testName.append("<option value='" + index + "'>" + index + "</option>");
	});
	setGender();
}

function setGender(){
	var opt = $("#testType").find('option:selected').val();
	var testName = $('#testName').find('option:selected').val(); 
	var $testGender = $('#testGender');
	$testGender.find('option').remove(); 
	$.each(testHashMap[opt][testName],function(index, value) 
	{
		if(!(value.userGender === undefined || value.userGender == null))
			$testGender.append("<option value='" + value.userGender + "'>" + value.userGender + "</option>");
		else {			 
			$testGender.append("<option value='NA'>NA</option>");
		}
	});
	setPriceDiscountReport();
}

function setPriceDiscountReport(){
	var opt = $("#testType").find('option:selected').val();
	//var name = $("#testName")[0].selectedIndex;
	var name = $("#testName").find('option:selected').val()
	var gender = $("#testGender")[0].selectedIndex;
	if(gender == -1)
		gender = 0;
	if(!(testHashMap[opt][name][gender].price === undefined))
		$("#testPrice").val(testHashMap[opt][name][gender].price);
	if(!(testHashMap[opt][name][gender].discountPercent === undefined))
		$("#testDiscount").val(testHashMap[opt][name][gender].discountPercent);
	if(!(testHashMap[opt][name][gender].refLower === undefined))
		$("#testRefLower").val(testHashMap[opt][name][gender].refLower);
	if(!(testHashMap[opt][name][gender].refUpper === undefined))
		$("#testRefUpper").val(testHashMap[opt][name][gender].refUpper);
	if(!(testHashMap[opt][name][gender].unit === undefined))
		$("#testUnit").val(testHashMap[opt][name][gender].unit);
	if(!(testHashMap[opt][name][gender].comments === undefined))
		$("#testComments").val(testHashMap[opt][name][gender].comments);
}



