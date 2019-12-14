<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html >
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
			<form id="frm" name="frm">

				<div class="login_area">
					<div class="login_wrap">
						<dl>
							<dt>스프링 로그인</dt>
							<dd>시간관리앱 지금으로 규칙적인 생활</dd>
						</dl>
						<ul>
							<li>
								<label for="login" class="sr-only">휴대전화</label>
								<input id="phoneNum" name="phoneNum" type="text" class="id placeholder" style="ime-mode:disabled;"
								placeholder="휴대전화번호(-없이)">
							</li>
							<li>
								<label for="password" class="sr-only">패스워드</label>
								<input id="password" name= "password" type="password" class="pw placeholder" placeholder="패스워드" style="ime-mode:disabled;">
							</li>
						</ul>
						<p>
							<input type="checkbox" name="save_id" id="save_id" value="1">
							<label for="save_id">휴대전화번호 저장</label>
						</p>
						<div class="loginBtn">
							<a id="loginBtn" class="BtnRed">로그인</a>
						</div>
						<div class="id_pw">
							<span class="hidden-xs">
								비밀번호를 잊어버리셨나요?
							</span>
							<a href="/">비밀번호 찾기</a>
						</div>
					</div>
					<div class="memberjoin">
							<div class="gojoin">
								아직 회원이 아니신가요?
								<a href="/common/regist">회원가입</a>	
							</div>
						</div>
					<!-- //login_wrap -->
				</div>
				<!-- //login_area -->
			</form>
		</section>
		<!-- //container -->
		<div include-html="/common/footer"></div>
	</div>	
	<script src="/resources/js/includeHTML.js"></script>
	<script src="/resources/js/userRegist.js"></script>
	<script type="text/javascript">
		var phoneNum;
		var password;
		var identifier;
		$(document).ready(function(){
			//로그인 후  home 화면으로 이동
			$("#loginBtn").click(function(){
				var json_param = $('#frm').serialize();
				console.log(json_param);
				nav = "/permit/home?status=index";
				registService.selectNextPcsAfterLogin(json_param, fn_movePage, nav);
				return false;
			});// end memberBtn click
			//로그인 후 지정된 화면 nav로 이동
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