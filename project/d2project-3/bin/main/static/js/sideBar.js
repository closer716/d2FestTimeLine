function ajaxConfirm(obj) {

	// DO POST
	$.ajax({
		type : "POST",
		url : "/friend/friendAccept",
		data : {
			notificationId : $(obj).parent('form').parent('div').parent('div').children(':nth-child(3)').attr('value'),
			friendId : $(obj).parent('form').parent('div').parent('div').children(':nth-child(4)').attr('value'), 
			contents : $(obj).parent('form').children(':nth-child(2)').attr('value')
		},
		success : function(result) {
			if (result.status == "success") {
				console.log("success");
				$('#mainPost').load('#mainPost');
			} else {
			}
			console.log(result);
		},
		error : function(e) {
			alert("Error!")
			console.log("ERROR: ", e);
		}
	});
	return false;
}

function ajaxRequest(obj) {

	// DO POST
	$.ajax({
		type : "POST",
		url : "/friend/friendRequest",
		data : {
			friendId : $(obj).parent('form').parent('div').parent('div').children(':nth-child(3)').attr('value')},
		success : function(result) {
			if (result.status == "success") {
				console.log("success");
				$('#mainPost').load('#mainPost');
			} else {
			}
			console.log(result);
		},
		error : function(e) {
			alert("Error!")
			console.log("ERROR: ", e);
		}
	});
	return false;
}