<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
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
			<form id="frm" name="frm" action method="post">
				<input type="hidden" id="${_csrf.parameterName}" name="${_csrf.token}" value="">
				<div class="common_area member_form">
					<div class="common_wrap">
						<dl>
							<!-- contoller에서 내려오는 schedule명과 총 할당시간 -->
							<dt id="formTitle"></dt>
							<dd>${totalAllocatedTime}분 동안 할일을 등록하세요.</dd>
						</dl>
						<ul>
							<li>
								<div class="timeTable" id="analyzeChart"></div>
							</li>
							<li>
								<select id = "todoSetter" name="todoSetter" class="require placeholder">
									<option value="">&nbsp;&nbsp;&nbsp;할일을 선택해주세요.</option>
									<option value="0">&nbsp;&nbsp;&nbsp;영어</option>
									<option value="1">&nbsp;&nbsp;&nbsp;중국어</option>
									<option value="2">&nbsp;&nbsp;&nbsp;기타어학</option>
									<option value="3">&nbsp;&nbsp;&nbsp;인강듣기</option>
									<option value="4">&nbsp;&nbsp;&nbsp;운동</option>
									<option value="5">&nbsp;&nbsp;&nbsp;독서</option>
									<option value="6">&nbsp;&nbsp;&nbsp;신문</option>
									<option value="7	">&nbsp;&nbsp;&nbsp;기타</option>
								</select>
							</li>
							<li>
								<select id="timeSetter" name="timeSetter" class="require placeholder">
									<option value="">&nbsp;&nbsp;&nbsp;시간을 선택해주세요.(10분단위)</option>
									<c:forEach var = "i" begin = "10" end = "${totalAllocatedTime}" step = "10">
										<option value="${i}">&nbsp;&nbsp;&nbsp;${i}분</option>
									</c:forEach>
								 </select>
							</li>
						</ul>
						<div class="id_pw">
							<dl>
								<dd>
									<a id="cancleBtn" class="BtnGray_Dark">취소</a>
								</dd>
								<dd>
									<a id="regBtn" class="BtnRed">등록</a>
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
	<script src="/resources/js/includeHTML.js"></script>
	<script src="/resources/js/toDoRegist.js"></script>
	<script type="text/javascript">
		
		// 차트에 추가되어있는 사용자toDo리스트 정보를 가지고있는 list
		var chartTodoList = [];
		// 차트에서 제거할 toDo를 담는 리스트;
		var deleteList = [];
		// 차트에 추가할 toDo를 담는 리스트;
		var addList = [];
		// 서버로 송신할 사용자 toDoUpdateList
		var updateTodoList = [];
		//schedule 총 배정시간 
		var totalAllocatedTime = ${totalAllocatedTime};
		//추가 toDo객체 obj	
		function updateTodoObj(name, value){
			this.name = name,
			this.value = value
		}
		//서버로부터 받아온 사용자 toDo리스트를 chartTodoList / updateTodoObjList로 옮겨담음.
		<c:forEach items="${todoListbySchedule}" var="toDo">
			var toDoName = "${toDo.toDoCode}";
			var allocatedTime = "${toDo.allocatedTime}";
			totalAllocatedTime -= allocatedTime;
			//for Chart
			chartTodoList.push([toDoContext[toDoName], allocatedTime]);
			//for Regist
			updateTodoList.push(new updateTodoObj(toDoName, allocatedTime ));
		</c:forEach>
		console.log(chartTodoList);
		console.log(updateTodoList);
		
			//스케줄에 할당된 시간이 모든 사용자 toDo를 합친 값보다 많으면 미등록으로 차트에 등록
			if(totalAllocatedTime > 0){
				chartTodoList.push(["미등록", totalAllocatedTime]);
			}			
			
		var analyzeChart = bb.generate({
			bindto : "#analyzeChart",
			data : {
				type: "donut",
				order : null,
				columns : chartTodoList,
				onclick : function(d, i){
					var deleteId = d.id;
					var deleteValue = d.value;
					//chart 상 삭제
					if(deleteId != "미등록"){
						addList = [];
						deleteList = [];
						//chartTodoList의 미등록 index
						var chartListEmptyIndex = null;
						chartListEmptyIndex = chartTodoList.findIndex(i => i[0] === "미등록");
						//미등록 index가 존재하지 않으면  추가	
						if(chartListEmptyIndex == -1){
							chartTodoList.push(['미등록', 0]);
							chartListEmptyIndex = chartTodoList.length - 1;
						}
						//삭제될 toDo의 chartTodoList index	
						var chartDeleteIndex = chartTodoList.findIndex(i => i[0] === deleteId);
						//미등록시간에 삭제된 toDo의 시간만큼 추가
						chartTodoList[chartListEmptyIndex][1] += Number(chartTodoList[chartDeleteIndex][1]);
						// 해당시간 0으로 세팅
						chartTodoList[chartDeleteIndex][1] = 0;

						//삭제될 toDo의  updateTodoList 삭제되면 해당값을 0 으로 셋팅 
						var registDeleteIndex = updateTodoList.findIndex(i => toDoContext[i.name] === deleteId);
						updateTodoList[registDeleteIndex].value = 0;
						//변경된 미등록값 등록
						addList.push([chartTodoList[chartListEmptyIndex][0], chartTodoList[chartListEmptyIndex][1]]);
						//삭제된 toDo 등록
						deleteList.push(deleteId);
					}
					analyzeChart.load({
						columns:addList,
						unload:deleteList
					});
				}
			},//end data
			legend : {
				position : "right"
			},//end legend
			donut : {
				label : {
					format : function( value, ratio, id){
						return value +  " 분";
					},
					ratio : 1
				},
				title : totalIndex["${schedule}"]
			}// end donut
		});
	
		$(document).ready(function(){
			//regist
			$('#formTitle').text( totalIndex["${schedule}"] + "에 할일 등록하기");
			
			
			$("#regBtn").click(function(){
				updateTodoList.push({name : "scheduleCode", value : "${scheduleCode}" })
				var data = JSON.stringify(updateTodoList);
				console.log(data);
				toDoService.registTodoBySchedule(data, function(result){
				alert("regist success");
				location.replace("/permit/home?status=" + "${scheduleCode}");
				});
			});//end Regist
			
			$("#cancleBtn").click(function(){
				location.replace("/permit/home?status=" + "${scheduleCode}");
			})
			$("#timeSetter").bind("change", function() {
				if($("#todoSetter").val() == ''){
					alert("할일을 선택해주세요.");
					return null;
				}
				//새로 입력된 toDo 값
				var newTodo = $("#todoSetter").val();
				console.log("newTodo = " + newTodo );
		
				var newTime = $(this).val();
				addList = []
				deleteList = []
				// chartTodoList 기등록 toDo index	
				var alreadyRegistedChartIndex = null;
				// updateTodoList 기등록 toDo index	
				var alreadyRegistedUpdateIndex = null
				// 미등록 항목의 index	
				var chartListEmptyIndex = null;
				
				alreadyRegistedChartIndex = chartTodoList.findIndex(i => i[0] === newTodo);
				alreadyRegistedUpdateIndex = updateTodoList.findIndex(i => i.name === newTodo);
				chartListEmptyIndex = chartTodoList.findIndex(i => i[0] === "미등록");
				if(chartListEmptyIndex == -1){
					chartTodoList.push(['미등록', 0]);
					chartListEmptyIndex = chartTodoList.length - 1;
				}
				var afterDelteTotalAllocatedTime = null;
			 	//추가된 toDo가 이미등록되어있던 값인지 판단.	 
				var addStatus = null;
			 	//이미등록된 항목이면
				if(alreadyRegistedChartIndex != -1){
					afterDelteTotalAllocatedTime = (chartTodoList[chartListEmptyIndex][1] + Number(chartTodoList[alreadyRegistedChartIndex][1]));
					addStatus = "update";
				//새로등록한 항목이면
				} else {
					afterDelteTotalAllocatedTime = chartTodoList[chartListEmptyIndex][1];
					addStatus = "regist";
				}
				if(afterDelteTotalAllocatedTime - newTime < 0){
					alert("가용한 시간이 부족합니다. 삭제해주세요.");
					return null;
				}else {
					if(addStatus == "update"){
						chartTodoList[chartListEmptyIndex][1] = afterDelteTotalAllocatedTime - newTime;
						//chartTodoList에 새로지정된 toDo 시간 추가
						chartTodoList[alreadyRegistedChartIndex][1] = newTime;
						//updateTodoList에 새로 지정된 toDo시간 추가
						updateTodoList[alreadyRegistedUpdateIndex].value = newTime;
					}else {
						chartTodoList[chartListEmptyIndex][1] -= newTime;
						chartTodoList.push([newTodo,newTime]);
						updateTodoList.push(new updateTodoObj(newTodo, newTime));
					}
				}
				addList.push([toDoContext[newTodo], newTime]);
				
				if(chartTodoList[chartListEmptyIndex][1]  == 0 ){
					deleteList.push("미등록");
				}else{
					addList.push(["미등록", chartTodoList[chartListEmptyIndex][1]]);
				}

				analyzeChart.load({
					columns:addList,
					unload:deleteList
				});
				$("#todoSetter").val("");
				$("#timeSetter").val("");
				
			});
			
		
});	
	</script>
</body>
</html>