$(function() {
	$("#alert-success").hide();
	$("#alert-danger").hide();
	$("input").keyup(function() {
		var pwd1 = $("#password").val();
		var pwd2 = $("#password-confirm").val();
		if (pwd1 != "" || pwd2 != "") {
			if (pwd1 == pwd2) {
				$("#alert-success").show();
				$("#alert-danger").hide();
			} else {
				$("#alert-success").hide();
				$("#alert-danger").show();
			}
		}
	});
});

function dupCheck() {
	// DO POST
	$.ajax({
		type : "POST",
		url : "/register/duplicate",
		data : {
			id : $("#id").val()
		},
		success : function(result) {
			if (result.data == "0") {
				alert("사용가능한 아이디입니다.");
				$("#registerButton").attr("disabled", false);

			} else if (result.data == "1") {
				alert("존재하는 아이디입니다.");
				$("#registerButton").attr("disabled", true);
			}

		},
		error : function(e) {
			alert("Error!")
			console.log("ERROR: ", e);
		}
	});
	return false;
}




$(document).ready(function() {
	$("#registerButton").attr("disabled", true); // 비활성화
  
	function sendIt() {
	  var regex = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/;
	    var msg, ss, cc;
	    var a=$("#id").val().length;
	    //정규표현식

	  //아이디 유효성 검사 (영문소문자, 숫자만 허용)
	 for (var i = 0; i <$("#id").val().length; i++) {
	      ch = $("#id").val().charAt(i);
	      if (!(ch >= '0' && ch <= '9') && !(ch >= 'a' && ch <= 'z')&&!(ch >= 'A' && ch <= 'Z')) {
	          alert("아이디는 영문 대소문자, 숫자만 입력가능합니다.");
	          $("#id").focus();
	          $("#id").select();
	          return false;
	      }
	  }
	  //아이디에 공백 사용하지 않기
	  if ($("#id").val().indexOf(" ") >= 0) {
	      alert("아이디에 공백을 사용할 수 없습니다.")
	      $("#id").focus();
	      $("#id").select();
	      return false;
	  }
	  //아이디 길이 체크 (4~12자)
	 if ($("#id").val().length<4 || $("#id").val().length>12) {
	      alert("아이디를 4~12자까지 입력해주세요.")
	      $("#id").focus();
	      $("#id").select();
	      return false;
	  }

	  //비밀번호 길이 체크(4~8자 까지 허용)
	  if (document.reg.password.value.length<4 || document.reg.password.value.length>12) {
	      alert("비밀번호를 4~12자까지 입력해주세요.")
	      document.reg.password.focus();
	      document.reg.password.select();
	      return false;
	  }

	  if (document.reg.name.value == "") {
	      alert("이름을 입력하지 않았습니다.")
	      document.reg.name.focus();
	      return false;
	  }

	  if(document.reg.name.value.length<2){
	      alert("이름을 2자 이상 입력해주십시오.")
	      document.reg.name.focus();
	      return false;
	  }
	}
	function isNumeric(s) { 
	    for (i=0; i<s.length; i++) { 
	      c = s.substr(i, 1); 
	      if (c < "0" || c > "9") return false; 
	    } 
	    return true; 
	}
	    
	$("#registerButton").click(function() {
		var result=false;
		var a=sendIt();
		if(a)
			result=true;
		
		if(result)
			register();
	});

	function register(){
		$.ajax({
			type : "POST",
			url : "/register/confirm",
			data : {
				id : $("#id").val(),
				password : $("#password").val(),
				name : $("#name").val(),
				birthday : $("#birthday").val(),
				city : $("#city").val(),
				school : $("#school").val(),
				office : $("#office").val()
			},
			success : function(result) {
				alert("회원가입이 완료되었습니다.")
			},
			error : function(e) {
				alert("Error!")
				console.log("ERROR: ", e);
			}
		});
	}
		
	$("#userId").change(function() {
		$("#registerButton").attr("disabled", true);
	});
});
