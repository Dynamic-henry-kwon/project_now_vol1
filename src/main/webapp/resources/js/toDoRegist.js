/**
 * javascript source for user regist
 */

var totalIndex = ['수면시간', '활동시간', "준비", "출근", "오전업무", "점심시간", "오후업무", "퇴근", "저녁시간", "미등록"];
var toDoContext =  ["영어", "중국어", "기타어학", "인강듣기", "운동", "독서", "신문읽기", "기타"];
var toDoService = (function() {
	
	function createForm(id, url, method, params){
		var form = document.createElement('form');
		form.setAttribute('id', id);
		form.setAttribute('action', url);
		form.setAttribute('method', method);
		//document.characterSet = 'UTF-8';
		
		for( var key in params) {
			var hiddenField = document.createElement('input');
			hiddenField.setAttribute('type', 'hidden');
			hiddenField.setAttribute('name', key);
			hiddenField.setAttribute('value', params[key]);
			form.appendChild(hiddenField)
		}
		return form;
	}
	
	function registTodoBySchedule(data, callback, error){
		$.ajax({
			type : 'post',
			url :  '/permit/registTodo',
			data : data,
			contentType : "application/json; charset=utf-8",
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
	}

	return {
		createForm: createForm,
		registTodoBySchedule : registTodoBySchedule
	};
	
})();
