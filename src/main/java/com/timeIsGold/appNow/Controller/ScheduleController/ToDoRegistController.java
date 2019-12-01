package com.timeIsGold.appNow.Controller.ScheduleController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.timeIsGold.appNow.Todo.Domain.ToDo;
import com.timeIsGold.appNow.Todo.Service.TodoService;
import com.timeIsGold.appNow.User.Domain.User;

/**회원가입 Controller
 * @author kwonhyunwoo
 *
 */
@Controller
@RequestMapping("/permit")
public class ToDoRegistController {
	
	@Autowired TodoService todoService;
	

	@RequestMapping(value = "/todoRegForm", method = RequestMethod.POST)
	public String enterScheduleRegist(@RequestParam ("schedule") String scheduleCode, @RequestParam("value") String value, Model model) {	
		String [] schedules = new String [] {"수면시간", "활동시간", "준비", "출근", "오전업무", "점심시간", "오후업무", "퇴근", "저녁시간"};
		SecurityContext sc = SecurityContextHolder.getContext();
		Authentication at = sc.getAuthentication();
		User user = (User)at.getPrincipal();
		ToDo todoForRead = new ToDo(user.getPhoneNum(), scheduleCode);
		List<ToDo>todoListbySchedule = todoService.readTodo(todoForRead);

		
		model.addAttribute("schedule", scheduleCode);
		model.addAttribute("scheduleCode", scheduleCode);
		model.addAttribute("totalAllocatedTime", value);
		model.addAttribute("todoListbySchedule", todoListbySchedule);
		model.addAttribute("todoListSize", todoListbySchedule.size());
		
		return "todo/todoRegist";
	}
	
	@RequestMapping(value ="/registTodo" , method = RequestMethod.POST)
	public @ResponseBody Map<String,String> registTodoBySchedule(@RequestBody List<Map<String, String>> list){

		SecurityContext sc = SecurityContextHolder.getContext();
		Authentication at = sc.getAuthentication();
		User user = (User)at.getPrincipal();
		String schduleCode = list.get(list.size() - 1).get("value");
		ToDo toDoInfo = new ToDo(user.getPhoneNum(), schduleCode);
		todoService.deleteTodo(toDoInfo);
		for (int i = 0; i < list.size() -1 ; i ++) {
			if(Long.parseLong(list.get(i).get("value")) != 0) {
				toDoInfo.setSubScheduleCode(String.valueOf(i));
				toDoInfo.setAllocatiedPoint(3);
				toDoInfo.setAllocatedTime(Long.parseLong(list.get(i).get("value")));
				toDoInfo.setToDoCode((list.get(i).get("name")));
				todoService.insertTodo(toDoInfo);
			}
		}
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("result", "regist");
		return resultMap;
	}
}
