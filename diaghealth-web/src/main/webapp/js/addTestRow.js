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
		var data;
		data = "<tr id='rowIndex" + testCount + "'>" +  
				"<td><input name='testList[" + testCount + "].type' readonly='readonly' value='" + $('#testType').val()+ "'/></td>" + 
				"<td><input name='testList[" + testCount + "].name' readonly='readonly' value='" +$('#testName').val() + "'/></td>" +
				"<td><input name='testList[" + testCount + "].price' value='" + $('#testPrice').val()+ "'/></td>" +
				"<td><input name='testList[" + testCount + "].discountPercent' value='" + $('#testDiscount').val()+ "'/></td>" +
				"<td><input name='testList[" + testCount + "].refLower' readonly='readonly' value='" + $('#testRefLower').val()+ "' class='refLower'/></td>" +
				"<td><input name='testList[" + testCount + "].refUpper' readonly='readonly' value='" + $('#testRefUpper').val()+ "' class='refUpper'/></td>" +
				"<td><input name='testList[" + testCount + "].unit' readonly='readonly' value='" + $('#testUnit').val()+ "' class='unit'/></td>" +
				"<td><button type='button' class='deleteButton' onclick='deleteRow(this)'>Delete</button></td>"
				"</tr>";
		testCount++;
		$("#endRow").before(data);
	});	
	
	$("#addNewTestPriceReport").click(function(){		
		var data;
		data = "<tr id='rowIndex" + testCount + "'>" +  
				"<td><input name='testList[" + testCount + "].type' readonly='readonly' value='" + $('#testType').val()+ "' class='type'/></td>" + 
				"<td><input name='testList[" + testCount + "].name' readonly='readonly' value='" +$('#testName').val() + "' class='name'/></td>" +
				"<td><input name='testList[" + testCount + "].price' value='" + $('#testPrice').val()+ "' class='price'/></td>" +
				"<td><input name='testList[" + testCount + "].discountPercent' value='" + $('#testDiscount').val()+ "' class='discountPercent'/></td>" +
				"<td><input name='testList[" + testCount + "].resultValue' value='" + $('#testResultValue').val()+ "' class='resultValue'/></td>" +
				"<td><input name='testList[" + testCount + "].refLower' readonly='readonly' value='" + $('#testRefLower').val()+ "' class='refLower'/></td>" +
				"<td><input name='testList[" + testCount + "].refUpper' readonly='readonly' value='" + $('#testRefUpper').val()+ "' class='refUpper'/></td>" +
				"<td><input name='testList[" + testCount + "].unit' readonly='readonly' value='" + $('#testUnit').val()+ "' class='unit'/></td>" +
				"<td><input name='testList[" + testCount + "].date' readonly='readonly' value='" + currentDate + "' class='dateCreated'/></td>" +
				"<td><button type='button' class='deleteButton' onclick='deleteRow(this)'>Delete</button></td>"
				"</tr>";
		testCount++;
		$("#endRow").before(data);
	});
	
	$("#testType").change(onChange);
	$("#testName").change(setPriceDiscountReport);
});

function onChange(){
	var opt = $("#testType").find('option:selected').val();
	var $testName = $('#testName'); 
	$testName.find('option').remove();  
	$.each(testHashMap[opt],function(index, value) 
	{
		$testName.append("<option value='" + value.name + "'>" + value.name + "</option>");
	});
	setPriceDiscountReport();
}

function setPriceDiscountReport(){
	var opt = $("#testType").find('option:selected').val();
	var name = $("#testName")[0].selectedIndex;
	if(!(testHashMap[opt][name].price === undefined))
		$("#testPrice").val(testHashMap[opt][name].price);
	if(!(testHashMap[opt][name].discountPercent === undefined))
		$("#testDiscount").val(testHashMap[opt][name].discountPercent);
	if(!(testHashMap[opt][name].refLower === undefined))
		$("#testRefLower").val(testHashMap[opt][name].refLower);
	if(!(testHashMap[opt][name].refUpper === undefined))
		$("#testRefUpper").val(testHashMap[opt][name].refUpper);
	if(!(testHashMap[opt][name].unit === undefined))
		$("#testUnit").val(testHashMap[opt][name].unit);
}



