/**
 * 공용 함수를 정의한 js page 
 */

var now_common = (function(){
	
	function move_page(type, url){
		if(type == "href"){
			location.href = url;
		}else{
			location.replace(url);
		}
	}
	
	return {
		move_page : move_page
	};
})();