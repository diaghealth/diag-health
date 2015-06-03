var testCount = 0;
var testHashMap;
$(document).ready( function() {
	testCount = $('table#displayTestsTable tr:last').index() - 1; //1 for header
	var jsonString = $("#hiddenField").val();
	
	//Initialize drop down from json Map
	testHashMap =$.parseJSON(jsonString);
	var $testType = $('#testType');
	$.each(testHashMap,function(key, value) 
			{
		$testType.append("<option value='" + key + "'>" + key + "</option>");
	});
	
	onChange();
	
	$("#addNewTest").click(function(){		
		var data;
		data = "<tr id='rowIndex" + testCount + "'>" +  
				"<td><input name='testList[" + testCount + "].type' readonly='readonly' value='" + $('#testType').val()+ "'/></td>" + 
				"<td><input name='testList[" + testCount + "].name' readonly='readonly' value='" +$('#testName').val() + "'/></td>" +
				"<td><input name='testList[" + testCount + "].price' value='" + $('#testPrice').val()+ "'/></td>" +
				"<td><input name='testList[" + testCount + "].discountPercent' value='" + $('#testDiscount').val()+ "'/></td>" +
				"<td><button type='button' class='deleteButton' onclick='deleteRow(this)'>Delete</button></td>"
				"</tr>"
		$("#endRow").before(data);
	});	
	
	$("#testType").change(onChange);
	$("#testName").change(setPriceDiscount);
});

function onChange(){
	var opt = $("#testType").find('option:selected').val();
	var $testName = $('#testName'); 
	$testName.find('option').remove();  
	$.each(testHashMap[opt],function(index, value) 
	{
		$testName.append("<option value='" + value.name + "'>" + value.name + "</option>");
	});
	setPriceDiscount();
}

function setPriceDiscount(){
	var opt = $("#testType").find('option:selected').val();
	var name = $("#testName")[0].selectedIndex;
	if(!(testHashMap[opt][name].price === undefined))
		$("#testPrice").val(testHashMap[opt][name].price);
	if(!(testHashMap[opt][name].discountPercent === undefined))
		$("#testDiscount").val(testHashMap[opt][name].discountPercent);
}



