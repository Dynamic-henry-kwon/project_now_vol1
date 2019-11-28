/**
 * javascript source for user regist
 */

var registService = (function() {
	//회원가입
	function registUser(data, callback, error){
		$.ajax({
			type:'post',
			url : '/common/regist',
			data : data,
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
	function  selectNextPcs(data, callback, nav, error){
		console.log(nav);
		$.ajax({
			type:'post',
			url : '/signin_ok',
			data: data,
			dataType : 'json',
			beforeSend: function(xhr) {
				xhr.setRequestHeader("Accept", "application/json");
				xhr.setRequestHeader("nav", nav);
				console.log(xhr);
										},
			success: function(result, status, xhr){
				
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
		selectNextPcs : selectNextPcs

	};
	
})();
