$(function(){

	/////////////
	// tag 추가 //
	/////////////
	
	$('.tags').on("keydown click",'input[type="text"]',function(event){
		
		// input text width 조절
		// $(this).attr("placeholder","tag");
		$(this).attr("size", $(this).val().length+1);
		
		// enter key fn
		if (event.keyCode === 13) {
			event.preventDefault();
			addTags();
		};
	}); 
	
	$('.tags').on("click",'.tagMinus',function(event){
			if($('.inputBox').length == 1) {
				alert("모든 태그를 지울 수 없습니다.");
			}

			if($('.inputBox').length > 1) {
				$(this).prev().prev().remove();
				$(this).prev().remove();
				$(this).remove();
			}
	});
	function addTags(){
		$(".append").append("<span class='mr-1 ml-2'>#</span><input class='border border-white inputBox' type='text' name='tagName' placeholder='태그 입력'><i class='fa fa-times tagMinus' aria-hidden='true'></i>");
		$('.inputBox').last().focus();
	}
	
	/////////////////////////
	// comment ajax 추가 //
	/////////////////////////
	$(".submit-write input[type=submit]").click(addComment);
	
	function addComment(e){
		e.preventDefault();
		var queryString = $(".submit-write").serialize();
		var url = $(".submit-write").attr('action');
		
		$.ajax({
			type : 'post',
			url : url,
			data : queryString,
			dataType : 'json',
			error : function(request,status,error){
				onError();
				alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			},
			success : function(data){
				onSuccess(url);
			}
		});
	}
	
	function onError(){
		alert('Leave a comment [error]');
	}

	function onSuccess(url){
		
		var template = document.querySelector('#comment-template');
		
		$.ajax({
			type : 'get',
			url : url,
			dataType : 'json',
			error : function(request,status,error){
				alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			},
			success : function(data){
				
				$(".comment-list").empty();
				
				$(data).each(function(index){
					
					var clone = document.importNode(template.content, true);
					var name = clone.querySelector("h3");
					var date = clone.querySelector(".abb")
					var content = clone.querySelector("p");
					
					name.textContent = data[index].name;
					date.textContent = data[index].formattedCreatedDate;
					content.textContent = data[index].contents;
					
					$(".comment-list").append(clone);
					$(".submit-write textarea").val("");
				});
				console.log(data.length);
				$(".post-meta>.fa-comments span").text(data.length);
				$(".comment-count").text(data.length+ " Comments");
				$(".comment:last")[0].scrollIntoView({behavior: "smooth", block: "end"});
				$(".comment:last").delay(200).fadeOut(200).fadeIn(200).fadeOut(200).fadeIn(200).fadeOut(200).fadeIn('slow');
			}
		});
	}
	
	/////////////////////////
	// blog like 추가 ////////
	/////////////////////////
	$(".like-icon").click(function(){
		
		var url = "/blog/"+$(".text-center input").val()+"/like";
		
		$(".like-icon").css("color","#dc9a55e3");
		$(".like-animate").css("opacity","1");
		$(".like-animate").animate({
			bottom : "120px",
			opacity : "0"
		},500, function(){
			$(".like-animate").css("bottom","60px").css("opacity","0");
		});
		
		$.ajax({
			type : 'post',
			url : url,
			dataType : 'json',
			error : function(request,status,error){
				onError();
				alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			},
			success : function(data){
				
				console.log(data);
				$(".post-meta>.fa-thumbs-o-up span").text(data);
			}
		});
		
	});
	
})