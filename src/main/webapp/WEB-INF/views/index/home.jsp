<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>		
<html>
<head>
<title>Home</title>
<script src="//code.jquery.com/jquery-3.2.1.min.js"></script>
<link rel="stylesheet" href="/resources/css/common.css" type="text/css">
<link rel="stylesheet" href="/resources/css/sub.css" type="text/css">
</head>
<body>
	<div id="boxWrapp">
		<sec:authorize access="isAuthenticated()">
				<input type="hidden" id="isLogined" value ="true"></a>
		</sec:authorize> 
		<sec:authorize access="isAnonymous()">
				<input type="hidden" id="isLogined" value ="false"></a>
		</sec:authorize> 
		<script>
			//로그인된 상태로 home 으로 접근하면 authoredHome으로 강제이동
			if($("#isLogined").val() == "true"){
				location.replace("/permit/home?status=index");
			}
		</script>
		<section id="container">
			<div class ="common_area">
				<div class="common_wrap">
					<P>현재 시간은 ${serverTime}.</P>
					<p>시간은 금이다. 지금</p>
					<p>직장인을 위한 시간관리 어플리케이션</p>
					<div class="id_pw">
					<dl>
						<dd>
							<a class="BtnRed" id="go_login">로그인</a>	
						</dd>
				
						<dd>
							<a class="BtnGray_Dark" id = "go_regist" >회원가입</a>	
						</dd> 
					</dl>
					</div>	
				</div>
				
			</div>

		</section>
	</div>
	<script src="/resources/js/includeHTML.js"></script>
	<script src="/resources/js/now_common.js"></script>
	<script>
		function move_page(url, type){
			if(type == "href"){
				location.href(url);
			}else{
				location.replace(url);
			}
		}
		
		$(document).on("click", "#go_login", function(){
			now_common.move_page("href", "/common/login");
		});
		
		$(document).on("click", "#go_regist", function(){
			now_common.move_page("replace", "/common/regist");
		});
		
	</script>
</body>
</html>
