var testCount = 0;
var testHashMap;
var currentDate = "";
$(document).ready( function() {
	testCount = $('table#displayTestsTable tr:last').index() - 1; //1 for header
	var jsonString = $("#hiddenField").val();
	//$.datepicker.formatDate('yy/mm/dd', new Date());
	//Initialize drop down from json Map
	testList =$.parseJSON(jsonString);
	currentDate = $.datepicker.formatDate('dd-M-yy', new Date());
	//currentDate += $.timepicker({});
	var now = $.now();
	//currentDate = dateFormat(now, "dddd, mmmm dS, yyyy, h:MM:ss TT");
	var $testType = $('#testType');
	$.each(testList,function(index, key) 
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
	$("#subGroup1").change(subGroup1Change);
	$("#subGroup2").change(subGroup2Change);
	$("#subGroup3").change(subGroup3Change);
	$("#testName").change(setGender);
	$('#testGender').change(setTestDetails);	
});

function subGroup1Change(){
	subGroupChange('testType', 'subGroup1', 'subGroup2');
}

function subGroup2Change(){
	subGroupChange('subGroup1', 'subGroup2', 'subGroup3')
}

function subGroup3Change(){
	subGroupChange('subGroup2','subGroup3', 'testType')
}

function addNewTestPriceReport(){
	var data;
	data = "<tr id='rowIndex" + testCount + "'>" +  
			"<td><input name='testList[" + testCount + "].ancestorGroupNames[0]' readonly='readonly' value='" + $('#testType').val()+ "'/></td>";
	
	if($('#subGroup1').val() != '' &&  $('#subGroup1').val() != null)
		data = data + "<td><input name='testList[" + testCount + "].ancestorGroupNames[1]' readonly='readonly' value='" + $('#subGroup1').val()+ "'/></td>";
	else
		data = data + "<td></td>";
	
	if($('#subGroup2').val() != '' &&  $('#subGroup2').val() != null)
		data = data + "<td><input name='testList[" + testCount + "].ancestorGroupNames[2]' readonly='readonly' value='" + $('#subGroup2').val()+ "'/></td>";
	else
		data = data + "<td></td>";
	
	if($('#subGroup3').val() != '' &&  $('#subGroup3').val() != null)
		data = data + "<td><input name='testList[" + testCount + "].ancestorGroupNames[3]' readonly='readonly' value='" + $('#subGroup3').val()+ "'/></td>";
	else
		data = data + "<td></td>";
	
			/*"<td><input name='testList[" + testCount + "].ancestorGroupNames[1]' readonly='readonly' value='" + $('#subGroup1').val()+ "'/></td>" + 
			"<td><input name='testList[" + testCount + "].ancestorGroupNames[2]' readonly='readonly' value='" + $('#subGroup2').val()+ "'/></td>" + 
			"<td><input name='testList[" + testCount + "].ancestorGroupNames[3]' readonly='readonly' value='" + $('#subGroup3').val()+ "'/></td>" + */
	data = data + "<td><input name='testList[" + testCount + "].name' readonly='readonly' value='" +$('#testName').val() + "'/></td>" +
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
			"<td><input name='testList[" + testCount + "].ancestorGroupNames[0]' readonly='readonly' value='" + $('#testTypeNew').val()+ "'/></td>" + 
			"<td><input name='testList[" + testCount + "].ancestorGroupNames[1]' readonly='readonly' value='" + $('#subGroup1New').val()+ "'/></td>" + 
			"<td><input name='testList[" + testCount + "].ancestorGroupNames[2]' readonly='readonly' value='" + $('#subGroup2New').val()+ "'/></td>" + 
			"<td><input name='testList[" + testCount + "].ancestorGroupNames[3]' readonly='readonly' value='" + $('#subGroup3New').val()+ "'/></td>" + 
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
	
	$.ajax({
		  type: 'POST',
		  url: getSubGroupUrl(),
		  data: { testType: opt },
		  error: function(){
		        timeoutError();
		    },
		  success: function(json){
				 //alert("Data: " + data + "\nStatus: " + status);
			  	setSubType(json, 'subGroup1');
		    },
		  /*dataType: dataType,*/
		  async:false,
		  timeout: 60000
		});
	
	subGroup1Change();
}

function subGroupChange(prevGroup, selectedGroup, populateGroup){
	var opt = $('#' + selectedGroup).find('option:selected').val();

	if(opt == ''){
		var prevOpt = $('#' + prevGroup).find('option:selected').val();
		$.ajax({
			  type: 'POST',
			  url: getTestsUrl(),
			  data: { testType: prevOpt },
			  error: function(){
			        timeoutError();
			    },
			  success: function(json){
					 //alert("Data: " + data + "\nStatus: " + status);
				  setTests(json);
			    },
			  /*dataType: dataType,*/
			  async:false,
			  timeout: 60000
			});
		setGender();
	}
	else {
		$.ajax({
			  type: 'POST',
			  url: getSubGroupUrl(),
			  data: { testType: opt },
			  error: function(){
			        timeoutError();
			    },
			  success: function(json){
					 //alert("Data: " + data + "\nStatus: " + status);
				  setSubType(json, populateGroup);
			    },
			  /*dataType: dataType,*/
			  async:false,
			  timeout: 60000
			});
		subGroupChange(selectedGroup, populateGroup, null);
	}
}

function timeoutError(){
	
}

function setTests(json){
	var testList =$.parseJSON(json);
	var $testName = $('#testName');
	$testName.find('option').remove(); 
	if(testList.length > 0){
		$testName.append("<option value='All'>(Select All)</option>");
		$.each(testList,function(index, key) 
		{
			$testName.append("<option value='" + key + "'>" + key + "</option>");
		});
	}
}

function setSubType(json, subGroup){
	var testList =$.parseJSON(json);
	var $testType = $('#' + subGroup);
	$testType.find('option').remove(); 
	$testType.append("<option value=''>(None)</option>");
	$.each(testList,function(index, key) 
	{
		$testType.append("<option value='" + key + "'>" + key + "</option>");
	});
}

function getSubGroupUrl(){
	return getBaseUrl() + '/labTests/getLabTestGroupObject';
}

function getTestsUrl(){
	return getBaseUrl() + '/labTests/getLabTestObject';
}

function getGenderUrl(){
	return getBaseUrl() + '/labTests/getLabTestGender';
}

function getTestDetailsUrl(){
	return getBaseUrl() + '/labTests/getLabTestDetails';
}

function getBaseUrl(){
	return window.location.origin + "/" + window.location.pathname.split('/')[1] + "/";	
}


function setGender(){
	//var opt = $("#testType").find('option:selected').val();
	var testName = $('#testName').find('option:selected').val(); 
	
	if(testName != 'All'){
		$.ajax({
			  type: 'POST',
			  url: getGenderUrl(),
			  data: { name: testName },
			  error: function(){
			        timeoutError();
			    },
			  success: function(json){
					 //alert("Data: " + data + "\nStatus: " + status);
				  setGenderCallBack(json);
			    },
			  /*dataType: dataType,*/
			  async:false,
			  timeout: 60000
			});
		setTestDetails();
	}
	/*$.each(testHashMap[opt][testName],function(index, value) 
	{
		if(!(value.userGender === undefined || value.userGender == null))
			$testGender.append("<option value='" + value.userGender + "'>" + value.userGender + "</option>");
		else {			 
			$testGender.append("<option value='NA'>NA</option>");
		}
	});*/
	setPriceDiscountReport();
}

function setGenderCallBack(json){
	var genderList =$.parseJSON(json);
	var $testGender = $('#testGender');
	var $testGender = $('#testGender');
	$testGender.find('option').remove(); 
	$.each(genderList,function(index, key) 
	{
		if(!(key === undefined || key == null))
			$testGender.append("<option value='" + key + "'>" + key + "</option>");
		else
			$testGender.append("<option value='NA'>NA</option>");
	});
}

function setTestDetails(){
	var testName = $('#testName').find('option:selected').val(); 
	var testGender = $('#testGender').find('option:selected').val(); 
	
	if(testName != 'All'){
		$.ajax({
			  type: 'POST',
			  url: getTestDetailsUrl(),
			  data: { name: testName , gender: testGender },
			  error: function(){
			        timeoutError();
			    },
			  success: function(json){
					 //alert("Data: " + data + "\nStatus: " + status);
				  setTestDetailsCallBack(json);
			    },
			  /*dataType: dataType,*/
			  async:false,
			  timeout: 60000
			});
	}
}

function setTestDetailsCallBack(json){
	var jsonParsed =$.parseJSON(json);
	if(jsonParsed.length > 0){
		var testDetails = jsonParsed[0];
		if(!(testDetails.refUpper === undefined))
			$("#testRefUpper").val(testDetails.refUpper);
		if(!(testDetails.refLower === undefined))
			$("#testRefLower").val(testDetails.refLower);
		if(!(testDetails.price === undefined))
			$("#testPrice").val(testDetails.price);
		if(!(testDetails.discountPercent === undefined))
			$("#testDiscount").val(testDetails.discountPercent);
		if(!(testDetails.unit === undefined))
			$("#testUnit").val(testDetails.unit);
		if(!(testDetails.comments === undefined))
			$("#testComments").val(testDetails.comments);
	}
	
}

function setPriceDiscountReport(){
	var opt = $("#testType").find('option:selected').val();
	//var name = $("#testName")[0].selectedIndex;
	var name = $("#testName").find('option:selected').val()
	var gender = $("#testGender")[0].selectedIndex;
	if(gender == -1)
		gender = 0;
	/*if(!(testHashMap[opt][name][gender].price === undefined))
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
		$("#testComments").val(testHashMap[opt][name][gender].comments);*/
}



