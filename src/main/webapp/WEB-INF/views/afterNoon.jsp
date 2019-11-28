<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
	<script src="//code.jquery.com/jquery-3.2.1.min.js"></script>
	<link rel="stylesheet" href="/resources/css/common.css" type="text/css">
	<link rel="stylesheet" href="/resources/css/sub.css" type="text/css">
	<script src="/resources/js/includeHTML.js"></script>
	
	<script src="/resources/js/timedropper.js"></script>
	<link rel="stylesheet" type="text/css" href="/resources/css/timedropper.css">
</head>
<body>
	<div id="boxWrapp">
		<div include-html="/common/header"></div>
		<section id="container">
			<form id="frm" name="frm" action method="post">
				<input type="hidden" id="csrf_tn" name="csrf_tn" value="">
				<div class="login_area member_form">
					<div class="login_wrap">
						<dl>
							<dt>오후 일정 등록</dt>
							<dd>회원님의 오후 일정을 등록해주세요.</dd>
						</dl>
						<ul>
							<li>
								<p>오후업무 시작시간</p>
								<input type="text" id="alarm" class="require placeholder" />
							</li>
						</ul>
						<ul>
							<li>
								<p>퇴근시간</p>
								<input type="text" id="alarm02" class="require placeholder" />
							</li>
						</ul>
						<ul>
							<li>
								<p>집에 도착하는 시간</p>
								<input type="text" id="alarm03" class="require placeholder" />
							</li>
						</ul>
						
						<div class="id_pw">
							<dl>
								<dd>
									<a href="/" class="BtnGray_Dark">취소</a>
								</dd>
								<dd>
									<a class="BtnRed" id="registBtn">등록</a>
								</dd>
							</dl>
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
	<script>includeHTML();</script>
	<script>
	$( "#alarm" ).timeDropper({
		setCurrentTime: false
	});
	</script>
	
	<script>
	$( "#alarm02" ).timeDropper({
		setCurrentTime: false
	});
	</script>
	<script>
	$( "#alarm03" ).timeDropper({
		setCurrentTime: false
	});
	</script>
</body>
</html>