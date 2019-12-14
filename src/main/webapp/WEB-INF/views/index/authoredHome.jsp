<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ page session="false" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<meta charset="UTF-8">
<title>Home</title>


<script src="//code.jquery.com/jquery-3.2.1.min.js"></script>
<link rel="stylesheet" href="/resources/css/common.css" type="text/css">
<link rel="stylesheet" href="/resources/css/sub.css" type="text/css">
<!--billboard.js -->
<script src="https://d3js.org/d3.v4.min.js"></script>
<script src="https://naver.github.io/billboard.js/release/latest/dist/billboard.min.js"></script>
<link rel="stylesheet" href="https://naver.github.io/billboard.js/release/latest/dist/billboard.min.css">

</head>
<body>
	<div id="boxWrapp">
		<div include-html="/common/header"></div>
		<section id="container">
				<div class="login_area member_form">
					<div class="login_wrap">
						<dl>
							<dt>하루 분석</dt>
							<sec:authorize access="isAuthenticated()">
									<sec:authentication property="principal" var="user" />
								</sec:authorize>
							<dd>${user.phoneNum}님의 하루를 분석한 결과입니다.</dd>
						</dl>
						<ul>
							<li>
								<div class="timeTable" id="analyzeChart"></div>
							</li>
						</ul>
						<div class="id_pw"></div>
					</div>
					<!-- //login_wrap -->
				</div>
				<!-- //login_area -->
		</section>
			<div id="boxWapper">
				<div class="black_bg" style="diplay: block;">
					<div id="registTodoPop" class="popup" style="display:block; margin-top: -144px">
						<div class="pop_area memberEnd">
							<div class="member_text">
								<div class="closeBtn completeBtn">닫기</div>
								<div class="member_info">
									<dl>
										<dt>자투리 시간을 이용합시다.</dt>
										<dd id="alarm"></dd> 
									</dl>
								</div>
								<div class="member_info_btn">
									<dl>
										<dd class="todoBtn BtnRed" id="goToRegistTodo" ></dd>
										<dd class="todoBtn BtnGray_Dark" id="cancle">취소</dd>
									</dl>
								</div>							
							</div>
						</div>
					</div>
			</div>
		</div>
		<div include-html="/common/footer"></div>
	</div>
	<script src="/resources/js/includeHTML.js"></script>
	<script src="/resources/js/toDoRegist.js"></script>
	<script type="text/javascript">
		
		//서버로부터 내려온 	사용자 schedule에 할당된 시간을 담은 list	
		var scheduleCodeObj = { 
		 		"수면시간" : [ "0", '${userSchedule.sleepTime}' ],
				"활동시간" : [ "1", '${userSchedule.awakeTime}' ],
				"준비" : ["2",'${userSchedule.morningPrepareTime}' ],
				"출근" : ["3", '${userSchedule.morningCommuteTime}'],
				"오전업무" : ["4", '${userSchedule.morningWorkTime}'],
				"점심시간" : ["5", '${userSchedule.luanchTime}'],
				"오후업무" : ["6", '${userSchedule.afterNoonWorkTime}'],
				"퇴근" : ["7", '${userSchedule.afterNoonCommuteTime}'],
				"저녁시간" : ["8",'${userSchedule.nightFreeTime}']
			};
	 	
		//활동시간의 schedule명과 할당 시간을 담은 리스트
	 	var sleepScheduleList =  [];
	 	var scheduleList =  [];
	 	
		for ( var scheduleObj in scheduleCodeObj) {
			if(scheduleObj == "수면시간" || scheduleObj == "활동시간"){
				sleepScheduleList.push([scheduleObj, scheduleCodeObj[scheduleObj][1]]);
			// scheduleCodeObj 파싱하여 수면시간과 활동시간을 제외한 schedule을 scheduleList에 넣어줌	
			}else {
				scheduleList.push([scheduleObj, scheduleCodeObj[scheduleObj][1]]);
			}
		}
		
	 	//schedule 별로 할당된 toDoSchedule 리스트.
	 	var toDoSchedules = [[], [], [], [], [], [], [], [], []];
	 	
		//[toDocontext, allcatedTime] 형식의 데이터를 넣어주는 함수 
		var insertData = function(toDoSchedules, toDoCode, allocatedTime){
			var dataArray = [];
			dataArray[0] = toDoContext[toDoCode];
			dataArray[1] = allocatedTime;
			toDoSchedules.push(dataArray);
		}
		
		// user에 할당된 userTodoList의 데이터를 toDoSchedules에 넣어준다.
		<c:forEach items="${userTodoList}" var="toDo">
			var scheduleCode = Number("${toDo.scheduleCode}");
			var toDoCode = "${toDo.toDoCode}";
			var allocatedTime = "${toDo.allocatedTime}";
			totalIndex.push(toDoContext[toDoCode]);
			
			insertData(toDoSchedules[scheduleCode], toDoCode, allocatedTime);
		</c:forEach>
		
	
		//Chart 생성.
		var analyzeChart = bb.generate({
			bindto : "#analyzeChart",
			data : {
				type : "donut",
				onclick : function(d, i){
					bilboardChartOnclick(d, i);
				},
				order : null,
				columns : []
			},
			legend :{
				position: "right"
			},
			donut : {
				label : {
					format : function(value, ratio, id) {
						return value + " 분";
					},
					ratio : 1
				},
				title : "default"
			}
		});
		
		var schedule, loadList, scheduleName = null;
		var depth = 0;
		//chart onclick event 발생시 구동되는 함수.
		var bilboardChartOnclick = function (d, i){
			// 차트에 미등록이 아닌 다른 요소를 클릭하면.
			if(d.id != '미등록'){
				if(depth == 2){
					popUpTodoRegist( scheduleCodeObj[scheduleName][0],  scheduleCodeObj[scheduleName][1], 1);
				}else{
					depth ++;
					//미등록  toDo를 처리하기위해 미등록 toDo의 상위 scheduleName을 임시저장해 놓는다.
					scheduleName = d.id;
					if (scheduleName == '활동시간') {
						loadList = scheduleList;
						
					} else if (scheduleName == '수면시간') {
						alert("미구현");
						depth --;
						return null;
					} else {
						schedule = scheduleCodeObj[scheduleName][0];
						loadList  = toDoSchedules[schedule];
					}
				}	
			// 미등록 요소를 클릭하면.
			} else {
				// toDoRegist 화면으로 이동하는 팝업창 append
				var scheduleCodeAndTime = scheduleCodeObj[scheduleName];
				//등록
				popUpTodoRegist(scheduleCodeAndTime[0], scheduleCodeAndTime[1], 0);
				return null;
			}
			setDetailTime(d.id, d.value, loadList);
		}//end bilboardChartOnclick
		
		//할당된 toTo를 chart에 등록
		var setDetailTime = function(selectedId, value, loadList, backBtn){
			if( value != ''){
				var restTime = value;
				if(loadList.length != 0){
					loadList.forEach(function(i){
						restTime -= i[1]
					})
				}
				//할당된 시간보다 총시간이 클 경우 그 시간만큼 미등록 항목으로 추가.	
				if (restTime > 0){
					var unregist = []
					unregist[0] = "미등록";
					unregist[1] = restTime;
					loadList.push(unregist)
				}
		}

			if(backBtn == null){
				analyzeChart.$.main.select(".bb-chart-arcs-title").text(selectedId);
			}else{
				analyzeChart.$.main.select(".bb-chart-arcs-title").text(backBtn);
			}

			analyzeChart.load({
				columns:loadList,
				unload :totalIndex
			});
		}

	//로그인 후 loading	
	if("${selector}" == "index"){
		setDetailTime('수면/활동', '', sleepScheduleList);
	//활동시간이 처음 loading되는 화면
	}else if("${selector}" == "activeTime") {
		setDetailTime('활동시간', '', scheduleList);
		depth == 1;
	//그 외 상세 schedule에 대한 toDo list가 보여지는 화면
	}else{
		var id = totalIndex["${selector}"];
		var value = scheduleCodeObj[id][1];
		var scheduleCode = scheduleCodeObj[id][0];
		var loadList  = toDoSchedules[scheduleCode];
		depth = 2;
		setDetailTime(id, value, loadList);
		scheduleName = id;
	}
	
	//toDoRegistPopUp을 띄우는 함수
	var popUpTodoRegist = function ( schedule, timeValue, bizNum ){
		$('.black_bg').fadeIn();
		$('#registTodoPop').fadeIn();
		if(bizNum == 0){
			//등록
			$('#alarm').html("할일을 등록하시겠어요?");
			$('#goToRegistTodo').html("등록하기");
		}else if (bizNum == 1){
			$('#alarm').html("할일을 수정하시겠어요?");
			$('#goToRegistTodo').html("수정하기");
		}
		
		var params = {  "schedule" : schedule  , "value" : timeValue};
		var form = toDoService.createForm('registInfo', "/permit/todoRegForm", "post", params);
		document.body.appendChild(form);
		$('#registTodoPop').css('margin-top', -($('#registTodoPop').find('.pop_area').innerHeight() / 2));	
	}
	// chart가운데 text클릭에 할당된 함수
	$(document).on('click', '.bb-chart-arcs-title', function() {
		var identifier = $(this).html();
		if (identifier !== '수면/활동') {
			setDetailTimeByBtn(identifier);
		}
	});
	
	//chart가운데 button Click 시 가동되는 함수
	function setDetailTimeByBtn(id, value , list){
		depth --;
		console.log(depth);
		beforeBtn = null;
		if( id === '활동시간'){
			list = sleepScheduleList;
			beforeBtn = "수면/활동"
		}else {
			list = scheduleList;
			beforeBtn = "활동시간"
		}
		setDetailTime(id, value, list, beforeBtn);
	};
	
	</script>
	<script type="text/javascript">
		$(document).on('click', '.todoBtn', function() {
			if(this.id ==='goToRegistTodo'){
				var form = document.getElementById("registInfo");
				form.submit();
			}else {
				$('.black_bg').fadeOut();
				$('#registTodoPop').fadeOut();
			}
			
			
		});
	</script>
	
	<script type="text/javascript">
	$(document).ready(function(){

		var sendSignOutPost = function(){
			var form = document.createElement('form');
			form.setAttribute("method", "post");
/* 			form.setAttribute("name", "${_csrf.parameterName}");
			form.setAttribute("value", "${_csrf.token}"); */
			form.action = "/signout_ok"
			var hiddenField = document.createElement("input");
		       hiddenField.setAttribute("type", "hidden");
		       hiddenField.setAttribute("name", "${_csrf.parameterName}");
		       hiddenField.setAttribute("value", "${_csrf.token}");
		       form.appendChild(hiddenField);
				document.body.appendChild(form);
			form.submit();
		}
		
		$('#testBtn').click(function(){
			sendSignOutPost();
		})
	});
	</script>
</body>
</html>
