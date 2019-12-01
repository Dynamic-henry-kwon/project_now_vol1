package com.timeIsGold.appNow.Controller.UserController;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.timeIsGold.appNow.Todo.Domain.ToDo;
import com.timeIsGold.appNow.Todo.Service.TodoService;
import com.timeIsGold.appNow.User.Domain.User;
import com.timeIsGold.appNow.User.Domain.UserSchedule;
import com.timeIsGold.appNow.User.Service.TimeTableService;
import com.timeIsGold.appNow.User.Service.UserService;
import com.timeIsGold.appNow.User.Service.Security.CustomAuthenticationProvider;

@Controller
public class UserLoginController {

	@Autowired UserService userService;
	@Autowired TimeTableService tService;
	@Autowired CustomAuthenticationProvider authProvider;
	@Autowired TodoService todoService;

	@RequestMapping(value="/common/login", method = RequestMethod.GET)
	public String getSpringLoginForm(Model model) {
		System.out.println("here");
		return "userLogin/securityLogin";
	}
	
	@RequestMapping(value = "/common/login", method= RequestMethod.POST)
	public @ResponseBody Map<String, Object> springLogin(@RequestBody Map<String, String> map, HttpServletRequest resq) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		User user = null;
		user = userService.getUser(map.get("phoneNum"));
		boolean passwordVarify = false;
		if(user != null) {
				passwordVarify = map.get("password").equals(user.getPassword());
				if(passwordVarify) {
					//회원조회하여 session으로 등록
					SecurityContext sc = SecurityContextHolder.getContext();
					sc.setAuthentication(new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()));
					HttpSession session = resq.getSession(true);
					session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, sc);
					resultMap.put("result", true);
					resultMap.put("nextPage", map.get("identifier"));
				}else {
					resultMap.put("result", false);
				}
		}
		return resultMap;
	}
	
	@RequestMapping(value ="/permit/home", method = RequestMethod.GET)
	public String authoredHome(Locale locale, String status, Model model) throws ParseException {
		System.out.println(status);
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(date);
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserSchedule userSchedule = tService.getTimePlan(user.getPhoneNum());
		ToDo userTodo = new ToDo(user.getPhoneNum(), null); 
		List<ToDo> userTodoList = todoService.readTodo(userTodo);
		
		model.addAttribute("userSchedule", userSchedule);
		model.addAttribute("serverTime", formattedDate);
		model.addAttribute("userTodoList", userTodoList);
		model.addAttribute("selector", status);
		
		return "index/authoredHome";
	}
	

}
