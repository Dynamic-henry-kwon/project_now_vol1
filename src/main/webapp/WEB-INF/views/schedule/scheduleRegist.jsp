<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<script src="/resources/js/timedropper.js"></script>
<link rel="stylesheet" type="text/css"
	href="/resources/css/timedropper.css">
</head>
<body>
	<div id="boxWrapp">
		<div include-html="/common/header"></div>
		<section id="container">
			<form id="frm" name="frm" action method="post">
				<div class="common_area regist_form">
					<div class="common_wrap">
						<dl>
							<dt>수면 패턴 등록</dt>
							<dd>회원님의 수면 패턴을 등록해주세요.</dd>
							<dd id = "Registelater">나중에하기</dd>
						</dl>
						<ul>
							<li>
								<p>잠자는 시간</p> <input type="text" id="subTwo"
								class="require placeholder" />
							</li>
						</ul>
						<ul>
							<li>
								<p>일어나는 시간</p> <input type="text" id="subOne"
								class="require placeholder" />
							</li>
						</ul>

						<div class="id_pw">
							<dl>
								<dd>
									<a class="BtnGray_Dark" id="cancle">취소</a>
								</dd>
								<dd>
									<a class="BtnRed" id="setMrn">다음</a>
								</dd>
							</dl>
						</div>
					</div>
					<!-- //common_wrap -->
				</div>
				<!-- //common_area -->
			</form>
		</section>
		<!-- //container -->
	</div>
	<script src="/resources/js/userRegist.js"></script>
	<script src="/resources/js/timeTableTemplate.js"></script>
	<script src="/resources/js/includeHTML.js"></script>
	<script>
		$("#subOne").timeDropper({
			setCurrentTime : false
		});
	</script>
	<script>
		$("#subTwo").timeDropper({
			setCurrentTime : false
		});
	</script>
	<script type="text/javascript">
		$(document).on('click', '#Registelater', function(){
			location.replace("/permit/home?status=index");
		});
		
		var timeTable = new Object();
		timeTable.timeWakeUp = '';
		timeTable.timeGoToSleep = '';
		timeTable.timeLeaveHome = '';
		timeTable.timeStartWork = '';
		timeTable.timeLunchBreak = '';
		timeTable.timeStartAfterNoonWork = '';
		timeTable.timeQuttingWork = '';
		timeTable.timeComeBackHome = '';

		$(document).on('click', '#cancle', function() {
			location.replace("/permit/home");
		});

		$(document).on(
				'click',
				'#setAns',
				function() {
					registTimeTableService.setAwakeAndSleep(function(data) {
						if (data.result) {
							$('.regist_form').html(
									registTimeTableService.makeForm(data.title,
											data.subOne, data.subTwo,
											data.subThree, data.leftBtnId,
											data.rigthBtnId));
							$('#subOne').timeDropper({
								setCurrentTime : false
							});
							$('#subTwo').timeDropper({
								setCurrentTime : false
							});
							$('#subThree').closest('ul').hide();
							$('#cancle').text('취소');
							timeTable.timeWakeUp = '';
							timeTable.timeGoToSleep = '';
						} else {
							console.log("false!!")
						}
					})
				})

		$(document)
				.on(
						'click',
						"#setMrn",
						function() {
							if (timeTable.timeWakeUp === ''
									&& timeTable.timeGoToSleep === '') {
								timeTable.timeWakeUp = timeProvider($('#subOne')
										.val());
								timeTable.timeGoToSleep = timeProvider($(
										'#subTwo').val());
								console.log(timeTable.timeGoToSleep);
							} else {
								console.log('back');
							}

							registTimeTableService.setMorningSchedule(function(
									data) {
								if (data.result) {
									$('.regist_form').html(
											registTimeTableService.makeForm(
													data.title, data.subOne,
													data.subTwo, data.subThree,
													data.leftBtnId,
													data.rigthBtnId));
									$('#subOne').timeDropper({
										setCurrentTime : false
									});
									$('#subTwo').timeDropper({
										setCurrentTime : false
									});
									$('#subThree').timeDropper({
										setCurrentTime : false
									});
								} else {
									console.log("false!!")
								}
							})
						});

		$(document).on(
				'click',
				'#setAtn',
				function() {
					if (timeTable.timeLeaveHome === ''
							&& timeTable.timeStartWork === ''
							&& timeTable.timeLunchBreak === '') {
						timeTable.timeLeaveHome = timeProvider($('#subOne')
								.val());
						timeTable.timeStartWork = timeProvider($('#subTwo')
								.val());
						timeTable.timeLunchBreak = timeProvider($('#subThree')
								.val());
					} else {
						timeTable.timeLeaveHome = '';
						timeTable.timeStartWork = '';
						timeTable.timeLunchBreak = '';
					}

					registTimeTableService.setAfternoonSchedule(function(data) {
						if (data.result) {
							$('.regist_form').html(
									registTimeTableService.makeForm(data.title,
											data.subOne, data.subTwo,
											data.subThree, data.leftBtnId,
											data.rigthBtnId));
							$('#subOne').timeDropper({
								setCurrentTime : false
							});
							$('#subTwo').timeDropper({
								setCurrentTime : false
							});
							$('#subThree').timeDropper({
								setCurrentTime : false
							});
							$('#regist').text('등록하기');
						} else {
							console.log("false!!")
						}
					})
				});

		$(document).on(
						'click',
						'#regist',
						function() {
							timeTable.timeStartAfterNoonWork = timeProvider($(
									'#subOne').val());
							timeTable.timeQuttingWork = timeProvider($(
									'#subTwo').val());
							timeTable.timeComeBackHome = timeProvider($(
									'#subThree').val());
							var json_timeTable = JSON.stringify(timeTable);
							registTimeTableService.registTimeTable(
									json_timeTable, function(data) {
										if (data.result) {
											alert("일정이 등록되었습니다.");
											location.replace("/permit/home?status=index");
										} else {
											alert("일정등록에 실패하였습니다.");
										}
									})

						});

		var timeProvider = function(data) {
			var timeArray = data.split(' ');
			if(timeArray[0] === '시간을'){
				return "00:00";
			}else{
				parsingTime = timeArray[0] + ':' + timeArray[1]
				timeArray = parsingTime.split(':');
				if(timeArray[2] === 'am' && timeArray[0] == "12" ){
					timeArray[0] = '0';
				}
				if (timeArray[2] === 'pm' && parseInt(timeArray[0]) < 12) {
					timeArray[0] = String(parseInt(timeArray[0]) + 12);
				}
				if (parseInt(timeArray[0]) < 10) {
					timeArray[0] = "0" + String(timeArray[0]);
				}
			}
			return timeArray[0] + ':' + timeArray[1];
		}
	</script>

</body>
</html>