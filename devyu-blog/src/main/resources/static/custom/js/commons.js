$(function(){
	
	
	$(".aaaa").click(function (e){
		e.preventDefault();
		if($(".aaaa").val().length > 0){
			$(".aaaa").keydown(function(key) {
				if (key.keyCode == 13) {
					$(".aaaa").css("background-color","blue");
					$(".aaaa").css("color","white");
				}
			});
		
			$('html').click(function(e) { 
				alert("asd");
				if(!$(e.target).hasClass("aaaa")) { 
					alert("asd");
					$(".tags").append("ㅋㅋㅋㅋㅋㅋㅋㅋ");
				} 
			});
		}
	});


	/*$(".aaaa").click(function (e) {
		e.preventDefault ();
		alert($(".aaaa").val().length);
	});*/
})