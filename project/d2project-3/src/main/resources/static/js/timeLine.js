var from = 10;
var getPost = false;
var allData = {"from" : from};

$(document).ready(
		function() {

			// GET REQUEST
			$(window).on("scroll", function() {
				var scrollHeight = $(document).height();
				var scrollPosition = $(window).height() + $(window).scrollTop();

				$("#scrollHeight").text(scrollHeight);
				$("#scrollPosition").text(scrollPosition);
				$("#bottom").text(scrollHeight - scrollPosition);

				if (scrollPosition > scrollHeight - 1000) {
					if (!getPost) {
						getPost = true;
						ajaxGet();
					}
				}
			});

			// DO GET
			function ajaxGet() {
				$.ajax({
					type : "GET",
					url : "getPosts",
					data : allData,
					success : function(result, data) {
						if (result.status == "success") {
							allData.from += 10;
							var custList = "";
							$.each(result.data, function(i, post) {
								var user = "<div class='card mb-4'>"
										+ "<div class='card-body'>" + "<h2 class='card-title'>"
										+ post.name + "</h2>" + "<p class='card-text'>"
										+ post.contents + "</p>" + "</div>"
										+ "<div class='card-footer text-muted'>" + post.postDate
										+ "</div>"
								// "이름 : " +
								// post.name +
								// "<br>" + "
								// contents = "
								// +
								// post.contents+
								// "<br>";
								$('#getResultDiv').append(user)
							});
							console.log("Success: ", result);
							getPost = false;
						} else {
							$("#getResultDiv").html("<strong>Error</strong>");
							console.log("Fail: ", result);
						}
					},
					error : function(e) {

						console.log("ERROR: ", e);
					}
				});
			}
			// SUBMIT FORM
			$("#posting").submit(function(event) {
				// Prevent the form from submitting via the browser.
				event.preventDefault();
				ajaxPost();
			});

			function ajaxPost() {
				var contents = $("#contents").val()

				// DO POST
				$.ajax({
					type : "POST",
					url : "savePost",
					data : contents,
					contentType : "application/json; charset=UTF-8",
					success : function(result) {
						if (result.status == "success") {
							$('#mainPost').load('#mainPost');
							allData.from = 0;
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

		});