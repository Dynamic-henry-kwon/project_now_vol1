package com.timeIsGold.appNow.Controller.ScheduleController;

import java.sql.Time;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.timeIsGold.appNow.User.Domain.TimeTable;
import com.timeIsGold.appNow.User.Domain.User;
import com.timeIsGold.appNow.User.Service.TimeTableService;
import com.timeIsGold.appNow.User.Service.UserService;

/**회원가입 Controller
 * @author kwonhyunwoo
 *
 */
@Controller
@RequestMapping("/permit")
public class ScheduleRegistController {
	
	@Autowired UserService userService;
	@Autowired TimeTableService timeTableService;
	
	@RequestMapping(value = "/regist/time", method = RequestMethod.GET)
	public String enterScheduleRegist(Model model) {
		Authentication authentication = (Authentication) SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		return "schedule/scheduleRegist";
	}
	
	 
	@RequestMapping(value = "/regist/time/ans", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> setAwakeAndSleep() {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", true);
		resultMap.put("title", "수면 패턴");
		resultMap.put("subOne", "일어나는 시간");
		resultMap.put("subTwo", "잠자는 시간");
		resultMap.put("subThree", "비활성화");
		resultMap.put("leftBtnId", "cancle");
		resultMap.put("rigthBtnId", "setMrn");
		return resultMap;
	}
	
	@RequestMapping(value = "/regist/time/mrn", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> setMorningSchedule() {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", true);
		resultMap.put("title", "오전 일정");
		resultMap.put("subOne", "집을 나서는 시간");
		resultMap.put("subTwo", "업무 시작시간");
		resultMap.put("subThree", "점심식사시간");
		resultMap.put("leftBtnId", "setAns");
		resultMap.put("rigthBtnId", "setAtn");
		return resultMap;
	}
	
	@RequestMapping(value = "/regist/time/atn", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> setAfternoonSchedule() {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", true);
		resultMap.put("title", "오후 일정");
		resultMap.put("subOne", "오후업무 시작시간");
		resultMap.put("subTwo", "퇴근시간");
		resultMap.put("subThree", "집에 도착하는 시간");
		resultMap.put("leftBtnId", "setMrn");
		resultMap.put("rigthBtnId", "regist");
		return resultMap;
	}
	
	@RequestMapping(value = "/regist/time/regist", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> registTimeTable(@RequestBody Map<String, String> map) {
		Authentication authentication = (Authentication) SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		String [] splitArray = map.get("timeWakeUp").split(":");
		TimeTable tTable = new TimeTable();
		tTable.setPhoneNum(user.getPhoneNum());
		tTable.setTimeWakeUp(Time.valueOf(LocalTime.of(Integer.parseInt(splitArray[0]), Integer.parseInt(splitArray[1]))));
		
		splitArray = map.get("timeGoToSleep").split(":");
		tTable.setTimeGoToSleep(Time.valueOf(LocalTime.of(Integer.parseInt(splitArray[0]), Integer.parseInt(splitArray[1]))));
		
		splitArray = map.get("timeLeaveHome").split(":");
		tTable.setTimeLeaveHome(Time.valueOf(LocalTime.of(Integer.parseInt(splitArray[0]), Integer.parseInt(splitArray[1]))));
		
		splitArray = map.get("timeStartWork").split(":");
		tTable.setTimeStartWork(Time.valueOf(LocalTime.of(Integer.parseInt(splitArray[0]), Integer.parseInt(splitArray[1]))));
		
		splitArray = map.get("timeLunchBreak").split(":");
		tTable.setTimeLunchBreak(Time.valueOf(LocalTime.of(Integer.parseInt(splitArray[0]), Integer.parseInt(splitArray[1]))));
		
		splitArray = map.get("timeStartAfterNoonWork").split(":");
		tTable.setTimeStartAfterNoonWork(Time.valueOf(LocalTime.of(Integer.parseInt(splitArray[0]), Integer.parseInt(splitArray[1]))));
		
		splitArray = map.get("timeQuttingWork").split(":");
		tTable.setTimeQuttingWork(Time.valueOf(LocalTime.of(Integer.parseInt(splitArray[0]), Integer.parseInt(splitArray[1]))));
		
		splitArray = map.get("timeComeBackHome").split(":");
		tTable.setTimeComeBackHome(Time.valueOf(LocalTime.of(Integer.parseInt(splitArray[0]), Integer.parseInt(splitArray[1]))));
		timeTableService.removeAllTables();
		timeTableService.registTable(tTable);
		boolean resultFlag = false;
		TimeTable registedTable = null;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if((registedTable = timeTableService.getTable(user.getPhoneNum())) != null && registedTable.getPhoneNum().equals(user.getPhoneNum()) ) {
			resultFlag = true;
		}
		
		resultMap.put("result", resultFlag);
		return resultMap;
	}
}
