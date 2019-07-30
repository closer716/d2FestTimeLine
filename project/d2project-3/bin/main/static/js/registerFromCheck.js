$(function(){
  	        $("#alert-success").hide();
  	        $("#alert-danger").hide();
  	        $("input").keyup(function(){
  	            var pwd1=$("#password").val();
  	            var pwd2=$("#password-confirm").val();
  	            if(pwd1 != "" || pwd2 != ""){
  	                if(pwd1 == pwd2){
  	                    $("#alert-success").show();
  	                    $("#alert-danger").hide();
  	                }else{
  	                    $("#alert-success").hide();
  	                    $("#alert-danger").show();
  	                }    
  	            }
  	        });
  	    });

function dupCheck(){
	  			// DO POST
	  			$.ajax({
	  				type : "POST",
	  				url : "/register/duplicate",
	  				data : {id : $("#id").val()},
	  				success : function(result) {
	  					if (result.data == "0") {
	  						alert("사용가능한 아이디입니다.");
	  					    $("#registerButton").attr("disabled", false);

	  					} else if(result.data == "1"){
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

  		$(document).ready(
  				function() {
  					$("#registerButton").attr("disabled", true); //비활성화

  					$("#registerButton").click(function() {
  						
  						$.ajax({
				  				type : "POST",
				  				url : "/register/confirm",
				  		  		data : {id : $("#id").val(),
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
  					});
  					
  					$("#id").change(function(){
  						$("#registerButton").attr("disabled", true);
  					});

  					
  	});
