$(function(){
	
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
		$(".append").append("<span class='mr-1 ml-2'>#</span><input class='border border-white inputBox' type='text' placeholder='태그 입력'><i class='fa fa-times tagMinus' aria-hidden='true'></i>");
		$('.inputBox').last().focus();
	}
})