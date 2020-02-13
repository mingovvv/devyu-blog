$(function(){
	
	// control-bar
	$(window).scroll(function(){
		  var percentage = ($(window).scrollTop() / ($(document).height() - $(window).height())) * 100;
		  $('.percentage').text(Math.round(percentage) +  '%');
	});
	$('.fa-arrow-up').click(function(){
		$('html').animate({scrollTop : 0})
	});
	$('.fa-undo').click(function(){
		location.href="/blog";
		/*window.history.back();*/
	});
	
	
	// fix-bar
	var offset = $( '.site-section' ).offset();
	$(window).scroll(function() {
		 if ($(document).scrollTop() > offset.top ) {
			 $('#fix-bar').addClass('fixed');
		 }else{
			 $('#fix-bar').removeClass('fixed');
		 }
	});
	
	$('.fa-search').click(function(){
		$('.search-form').submit();
	});



	// tag 추가 (keydown)
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
	
	// tag 추가(blur)
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
	
	// tag 삭제
	$('.tags').on("click",'.tagMinus',function(event){
			if($('.inputBox').length == 1) {
				alert("모든 태그를 지울 수 없습니다.");
			}

			if($('.inputBox').length > 1) {
				$(this).parent().remove();
				$(this).remove();
			}
	});
	
	// tag 추가 fn
	function addTags(){
		$(".append").append("<div class='d-inline-block'><span class='mr-1 ml-2'>#</span><input style='width: 55px' class='border border-white inputBox' type='text' name='tagName' placeholder='tag'><span class='measure'></span><i class='fa fa-times tagMinus' aria-hidden='true'></i></div>");
		$('.inputBox').last().focus();
	}
	
	// comment 추가
	$(".submit-write input[type=submit]").click(addComment);

	function addComment(e){
		e.preventDefault();
		
		if($('form input[name="name"]').val().trim() == ''){
			alert("닉네임을 입력해주세요.");
			return false;
		}else if($('form textarea').val().trim() == ''){
			alert("메시지를 입력해주세요.");
			return false;
		}
		
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
					
					var template;
					if(data[index].isWriter == false){
						template = document.querySelector('#comment-template-left');
					}else{
						template = document.querySelector('#comment-template-right');
					}				
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
					$(".submit-write textarea").val("")
				});
				
				$(".post-meta>.fa-comments span").text(data.length);
				$(".comment-count").text(data.length+ " Comments");
				$(".comment:last")[0].scrollIntoView({behavior: "smooth", block: "end"});
				$(".comment:last").delay(200).fadeOut(200).fadeIn(200).fadeOut(200).fadeIn(200).fadeOut(200).fadeIn('slow');
			}
		});
	}
	
	// comment 삭제창 open
	var clone= $('.input-group').clone();
	$('.comment-list').on("click",".comment-delete-icon",function(event){
		clone
		.css("display","flex")
		.css("top","-30px")
		.insertAfter($(this).parent().parent());
	}); 
	
	// comment 삭제창 close
	$('.comment-list').on("click",".comment-delete-cancel",function(event){
		clone
		.css("display","none")
		.css("top","0px")
		.insertAfter($('.comment-list'));
	});
	
	// 미인증 유저, 삭제(비밀번호 확인)
	$('.comment-list').on("click",".comment-delete",function(event){
		
		var id = $(this).parent().parent().prev().find('input').val();
		var password = $(this).parent().prev().val();
		var blogId = $(".blog_id").val();
		
		var json = { "id":id, "password":password, "auth":"no", "blogId" : blogId};
		
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
								
								var template;
								if(data[index].isWriter == false){
									template = document.querySelector('#comment-template-left');
								}else{
									template = document.querySelector('#comment-template-right');
								}				
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
								$(".submit-write textarea").val("")
							});
							
							$(".post-meta>.fa-comments span").text(data.length);
							$(".comment-count").text(data.length+ " Comments");
						}
					});
				}else{
					alert("비밀번호가 일치하지 않습니다.");
				}
			}
		});
	});
	
	// 인증 유저, 삭제(confirm 확인)
	$('.comment-list').on("click",".comment-delete-icon-auth",function(event){
		
		if(confirm("삭제 하시겠습니까?")){

			var id = $(this).parent().find('input').val();
			var blogId = $(".blog_id").val();
			var json = { "id":id, "auth":"ok", "blogId":blogId};
			
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
									
									var template;
									if(data[index].isWriter == false){
										template = document.querySelector('#comment-template-left');
									}else{
										template = document.querySelector('#comment-template-right');
									}				
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
									$(".submit-write textarea").val("")
								});
								$(".post-meta>.fa-comments span").text(data.length);
								$(".comment-count").text(data.length+ " Comments");
							}
						});
					}else{
						alert("비밀번호가 일치하지 않습니다.");
					}
				}
			});
		}
		
	});
	
	// detail 좋아요 추가 
	$(".like-icon").click(function(){
		
		var url = "/blog/"+$(".text-center input").val()+"/like";
		
		$(".like-icon").css("color","#dc9a55e3").css("border","5px solid #dc9a55e3");
		$(".like-animate").css("opacity","1");
		$(".like-animate").animate({
			bottom : "60px",
			opacity : "0"
		},500, function(){
			$(".like-animate").css("bottom","0px").css("opacity","0");
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
				$(".post-meta>.fa-thumbs-o-up span").text(data);
			}
		});
		
	});
    

})

// blog 게시물 수정 
function fn_update(id){
	if(confirm("수정 하시겠습니까?")){
		var form = $('.edit-button-form');
	    form.attr('action', '/blog/update/' + id);
	    form.attr('method', 'get');
	    form.submit();
	}
}

// blog 게시물 삭제
function fn_delete(id) {
	if(confirm("삭제 하시겠습니까?")){
		var form = $('.edit-button-form');
	    form.attr('action', '/blog/delete/' + id);
	    form.attr('method', 'post');
	    form.submit();
	}
}