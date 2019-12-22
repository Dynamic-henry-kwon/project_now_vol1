<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="_csrf" content='${_csrf.token}'/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<title>Insert title here</title>
	<script src="//code.jquery.com/jquery-3.2.1.min.js"></script>
	<link rel="stylesheet" href="/resources/css/common.css" type="text/css">
	<link rel="stylesheet" href="/resources/css/sub.css" type="text/css">
</head>
<body>
	<div id="boxWrapp">
		<div include-html="/common/header"></div>
		<section id="container">
			<form id="frm" name="frm" action method="post">
				<div class="common_area regist_form">
					<div class="common_wrap">
						<dl>
							<dt>회원가입</dt>
							<dd>시간관리앱 지금에 오신 것을 환영합니다.</dd>
						</dl>
						<ul>
							<li>
								<input name="phoneNum" type="text" class="require" style="ime-mode:disabled;"
								placeholder="연락처(-없이)입력하세요.">
							</li>
							<li>
								<input name="name" type="text" class="require placeholder" placeholder="이름">
							</li>
							<li>
								<input name="nickname" type="text" class="require placeholder" placeholder="닉네임">
							</li>
							
							<li class="password_list">
								<span>
									<input name="password" type="password" class="require placeholder" style="ime-mode:disabled;"
									placeholder="비밀번호">
								</span>
								<span>
									<input name="passwordConfirm" type="password" class="require placeholder"
									placeholder="비밀번호 확인">
								</span>
							</li>
							
							<li>
								<select name="detailOccupationCode" class="require placeholder">
									<option value="">&nbsp;&nbsp;&nbsp;직군을 선택해주세요.</option>
									<option value="1">&nbsp;&nbsp;&nbsp;경영.사무</option>
									<option value="2">&nbsp;&nbsp;&nbsp;영업.고객상담</option>
									<option value="3">&nbsp;&nbsp;&nbsp;생산.제조</option>
									<option value="4">&nbsp;&nbsp;&nbsp;IT.인터넷</option>
									<option value="5">&nbsp;&nbsp;&nbsp;전문직</option>
									<option value="6">&nbsp;&nbsp;&nbsp;교육</option>
									<option value="7">&nbsp;&nbsp;&nbsp;미디어</option>
									<option value="8">&nbsp;&nbsp;&nbsp;특수계층.공공</option>
									<option value="9">&nbsp;&nbsp;&nbsp;건설</option>
									<option value="10">&nbsp;&nbsp;&nbsp;유통.무역</option>
									<option value="11">&nbsp;&nbsp;&nbsp;서비스</option>
									<option value="12">&nbsp;&nbsp;&nbsp;디자인</option>
									<option value="13">&nbsp;&nbsp;&nbsp;의료</option>
								 </select>
							</li>
							<li>
								<select name="gender" class="require placeholder">
									<option value="">&nbsp;&nbsp;&nbsp;성별을 선택해주세요.</option>
									<option value="1">&nbsp;&nbsp;&nbsp;남자</option>
									<option value="2">&nbsp;&nbsp;&nbsp;여자</option>	
								</select>
							</li>
						</ul>
						<p>
							<input type="checkbox" name="userServiceAgrment" id="userServiceAgrment" value="1">
							<label for="termsService01">회원 서비스 이용동의</label>
							<a href="/" class="btn_agree policy" onclick="window.open(this.href, '', ''); return false;">
								(약관보기)
							</a>
						</p>
						<p>
							<input type="checkbox" name="userInfoAgrment" id="userInfoAgrment" value="1">
							<label for="termsService02">개인정보 수집 및 이용 동의</label>
							<a href="/" class="btn_agree policy" onclick="window.open(this.href, '', ''); return false;">
								(약관보기)
							</a>
						</p>
						<div class="id_pw">
							<dl>
								<dd>
									<a href="/common/login" class="BtnGray_Dark">취소</a>
								</dd>
								<dd>
									<a id="regBtn" class="BtnRed">회원가입</a>
								</dd>
							</dl>
						</div>
					</div>
					<!-- //common_wrap -->
				</div>
				<!-- //common_area -->
			</form>
		</section>
		<div id="boxWapper">
			<div class="black_bg" style="diplay: block;">
				<div id="pop01" class="popup" style="display:block; margin-top: -144px">
					<div class="pop_area memberEnd">
						<div class="member_text">
							<div class="closeBtn completeBtn">닫기</div>
							<div class="member_info">
								<dl>
									<dt>회원가입이 완료되었습니다.</dt>
									<dd>하루일과를 등록해주세요.</dd> 
								</dl>
							</div>
							<div class="member_info_btn">
								<dl>
									<dd class="memberBtn BtnRed" id="registTime" >등록하기</dd>
									<dd class="memberBtn BtnGray_Dark" id="home">나중에하기</dd>
								</dl>
							</div>							
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- //container -->
	</div>	
	<script src="/resources/js/includeHTML.js"></script>
	<script src="/resources/js/userRegist.js"></script>
	<script type="text/javascript">
		var user = new Object();
		user.phoneNum = '';
		user.password = '';
		user.identifier = '';

		$(document).ready(function(){
			//regist
			$("#regBtn").click(function(){
				var json_param = $('#frm').serialize();
			registService.registUser(json_param, fn_popUp);
			return false;
			});//end Regist
			
			//회원가입 결과 팝업	
			var fn_popUp = function(data){
				if(data.result){//등록 성공시
					$('.black_bg').fadeIn();
					$('#pop01').fadeIn();
					$('#pop01').css('margin-top', -($('#pop01').find('.pop_area').innerHeight() / 2));
					user.phoneNum = data.phoneNum;
					user.password = data.password;
				} else {
					alert("fail");
				}
			}	
			
			//가입후 이동할 페이지 선택
			$(".memberBtn").click(function(){
				var nav;
				if(this.id == "registTime"){
					nav = "/permit/regist/time";
				}else if(this.id == "home"){
					nav = "/permit/home?status=index";
				}
				registService.selectNextPcsAfterLogin(user, fn_movePage, nav);
				return false;
			});// end memberBtn click
			

			//회원가입후 이동할 페이지 버튼 선택
			var fn_movePage = function (body){
				var message = body.response.message;
				var error = body.response.error;
				if(error) console.log("로그인 실패");
				if(error == false){
					location.replace(body.response.nav);
				}
		}//end fn_movePage
		
});	
	</script>
</body>
</html>