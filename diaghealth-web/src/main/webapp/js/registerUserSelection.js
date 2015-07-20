$(document).ready( function() {
	$("#userType").change(onUserTypeChange);
});

function onUserTypeChange(){
	var opt = $("#userType").find('option:selected').val();
	if(opt == 'DOCTOR' || opt == 'PATIENT'){
		$('#userGender').removeAttr('disabled');		
	} else {
		$('#userGender option[value="NA"]').attr('selected','selected');
		$('#userGender').attr('disabled', 'disabled');
	}
}