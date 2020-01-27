$(function(){

	/////////////
	// tag 추가 //
	/////////////
	
	$('.tags').on("keydown",'input[type="text"]',function(event){
		
	    
		// input text width 조절
		var spanElm = this.nextElementSibling;
		spanElm.textContent = this.value;  
		this.style.width = spanElm.offsetWidth +20+ 'px'; 
		$(this).attr("style", "width:"+this.style.width);
		var inputBox = $('.tags input[type="text"]');
		
		// enter key fn
		if (event.keyCode === 13) {
			event.preventDefault();
			
			var notEmpty = true;
			inputBox.each(function(index){
				if(this.value == ""){
					notEmpty = false;
				}
			});
			if(notEmpty){
				addTags();
			}
		};
	}); 
	
	$('.tags').on("blur",'input[type="text"]',function(event){
		
		// blur fn
		var inputBox = $('.tags input[type="text"]');
		var notEmpty = true;
		inputBox.each(function(index){
			if(this.value == ""){
				notEmpty = false;
			}
		});
		if(notEmpty){
			addTags();
		}
	});
	
	$('.tags').on("click",'.tagMinus',function(event){
			if($('.inputBox').length == 1) {
				alert("모든 태그를 지울 수 없습니다.");
			}

			if($('.inputBox').length > 1) {
				$(this).prev().prev().prev().remove();
				$(this).prev().prev().remove();
				$(this).prev().remove();
				$(this).remove();
			}
	});
	
	function addTags(){
		$(".append").append("<div class='d-inline-block'><span class='mr-1 ml-2'>#</span><input style='width: 55px' class='border border-white inputBox' type='text' name='tagName' placeholder='tag'><span class='measure'></span><i class='fa fa-times tagMinus' aria-hidden='true'></i></div>");
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
					var id = clone.querySelector("input")
					var name = clone.querySelector("h3");
					var date = clone.querySelector(".comment-date")
					var content = clone.querySelector("p");
					
					id.value = data[index].id;
					name.textContent = data[index].name;
					date.textContent = data[index].formattedCreatedDate;
					content.textContent = data[index].contents;
					
					$(".comment-list").append(clone);
					$(".submit-write textarea").val("");
				});
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
	
	/////////////////////////
	//// comment 삭제 /////
	/////////////////////////
	
	var clone= $('.input-group').clone();
	$('.comment-list').on("click",".comment-delete-icon",function(event){
		clone
		.css("display","flex")
		.css("top","-30px")
		.insertAfter($(this).parent().parent());
		
	}); 
	
	$('.comment-list').on("click",".comment-delete-cancel",function(event){
		clone
		.css("display","none")
		.css("top","0px")
		.insertAfter($('.comment-list'));
	});
	
	$('.comment-list').on("click",".comment-delete",function(event){
		var id = $(this).parent().parent().prev().find('input').val();
		var password = $(this).parent().prev().val();
		var json = { "id":id, "password":password};
		
		$.ajax({
			type:'post',
			url:'/blog/comment/delete',
			data:JSON.stringify(json),
			contentType:"application/json;charset=UTF-8",
			dataType:'json',
			error:function(){
				console.log("에러")
			},
			success:function(result){
				if(result){
					var url = $(".submit-write").attr('action');
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
								var id = clone.querySelector("input")
								var name = clone.querySelector("h3");
								var date = clone.querySelector(".comment-date")
								var content = clone.querySelector("p");
								
								id.value = data[index].id;
								name.textContent = data[index].name;
								date.textContent = data[index].formattedCreatedDate;
								content.textContent = data[index].contents;
								
								$(".comment-list").append(clone);
							});
						}
					});
				}else{
					alert("비밀번호가 일치하지 않습니다.");
				}
			}
		});
		
	});
	
})