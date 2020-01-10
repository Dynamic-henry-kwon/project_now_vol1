/**
 * javascript source for user regist
 */


var registService = (function() {
	console.log("right now")
	//회원가입
	function registUser(data, callback, error){
		$.ajax({
			type:'post',
			url : '/common/regist',
			data : data,
			dataType : 'json',
			beforeSend: function(xhr){
				var token = $("meta[name='_csrf']").attr("content");
				var header = $("meta[name='_csrf_header']").attr("content");
				xhr.setRequestHeader("Accept", "application/json");
				xhr.setRequestHeader(header, token);
			},
			success : function (result, status, xhr) {
				if(callback){
					callback(result);
				}
			},
			error : function(xhr, status, er){
				if(error){
					error(er);
				}
			}
		});
		return false;
	}//end regist
	
	
	// 이동 페이지 선택
	function  selectNextPcsAfterLogin(data, callback, nav, error){
		$.ajax({
			type:'post',
			url : '/signin_ok',
			data: data,
			dataType : 'json',
			beforeSend: function(xhr) {
				var token = $("meta[name='_csrf']").attr("content");
				var header = $("meta[name='_csrf_header']").attr("content");
				xhr.setRequestHeader("Accept", "application/json");
				xhr.setRequestHeader("nav", nav);
				xhr.setRequestHeader(header, token);

				console.log(xhr);
										},
			success: function(result, status, xhr){
				alert(JSON.stringify(result));
				if(callback){
					callback(result);
				}
			},
			error: function(xhr, status, err){
				if(error){
					error(err);
				}
			}
		});
		return false;
	}//end selectNextPcs
	
	

	return {
		registUser : registUser,
		selectNextPcsAfterLogin : selectNextPcsAfterLogin

	};
	
})();
