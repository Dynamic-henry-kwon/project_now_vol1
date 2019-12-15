<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<title>Home</title>
<script src="//code.jquery.com/jquery-3.2.1.min.js"></script>
<link rel="stylesheet" href="/resources/css/common.css" type="text/css">
<link rel="stylesheet" href="/resources/css/sub.css" type="text/css">
</head>
<body>
	<div id="boxWrapp">
		<section id="container">
			<div class ="common_area">
				<div class="common_wrap">
					<P>현재 시간은 ${serverTime}.</P>
					<p>시간은 금이다. 지금</p>
					<p>직장인을 위한 시간관리 어플리케이션</p>
				</div>
				<div class="member_join">
					<div class="go_join">
						로그인하세요.
						<a href="/common/login">로그인</a>	
					</div>
				</div>	
			</div>

		</section>
	</div>
	<script src="/resources/js/includeHTML.js"></script>
</body>
</html>
