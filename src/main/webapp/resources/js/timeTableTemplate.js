/**
 * 
 */
var registTimeTableService = (function(){
	
	function setAwakeAndSleep(callback, error){
		$.ajax({
			type:'get',
			url : '/permit/regist/time/ans',
			contentType : "application/json; charset=utf-8",
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
		})
		return false;
	};//end awakeSleepTimeRegister
	
	function setMorningSchedule(callback, error){
		$.ajax({
			type:'get',
			url : '/permit/regist/time/mrn',
			contentType : "application/json; charset=utf-8",
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
		})
		return false;
	};//end awakeSleepTimeRegister
	
	
	function setAfternoonSchedule(callback, error){
		$.ajax({
			type:'get',
			url : '/permit/regist/time/atn',
			contentType : "application/json; charset=utf-8",
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
		})
		return false;
	};//end awakeSleepTimeRegister
	
	function registTimeTable(data ,callback, error){
		$.ajax({
			type: 'post',
			url : '/permit/regist/time/regist',
			data: data,
			contentType : "application/json; charset=utf-8",
			success: function(result, status, xhr){
				if(callback){
					callback(result)
				}
			},
			error: function(xhr, status, err){
				if(error){
					error(err);
				}
			}
		})
		return false;
	}
	
	function makeForm(title, subOne, subTwo, subThree,leftBtnId, rigthBtnId){
		return "\<div class=\"login_wrap\">\n\
					<dl>\n\
						<dt>" + title +" 등록</dt>\n\
							<dd>회원님의 " + title +"을 등록해주세요.</dd>\n\
					</dl>\n\
					<ul>\n\
						<li>\n\
							<p>"+subOne+"</p>\n\
							<input type=\"text\" id=\"subOne\" class=\"require placeholder\" />\n\
						</li>\n\
					</ul>\n\
					<ul>\n\
						<li>\n\
							<p>"+subTwo+"</p>\n\
							<input type=\"text\" id=\"subTwo\" class=\"require placeholder\" />\n\
						</li>\n\
					</ul>\n\
					<ul>\n\
						<li>\n\
							<p>" + subThree +"</p>\n\
							<input type=\"text\" id=\"subThree\" class=\"require placeholder\" />\n\
						</li>\n\
					</ul>\n\
					<div class=\"id_pw\">\n\
						<dl>\n\
							<dd>\n\
							<a class=\"BtnGray_Dark\" id=\""+leftBtnId+"\">뒤로</a>\n\
							</dd>\n\
						<dd>\n\
							<a class=\"BtnRed\" id=\""+rigthBtnId+"\">다음</a>\n\
						</dd>\n\
					</dl>\n\
				</div>\n\
			</div>"
	}
	
	return {
		setAwakeAndSleep:setAwakeAndSleep,
		setMorningSchedule:setMorningSchedule,
		setAfternoonSchedule:setAfternoonSchedule,
		registTimeTable:registTimeTable,
		makeForm : makeForm
	}
})();
			