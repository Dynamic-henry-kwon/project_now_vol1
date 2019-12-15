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
	
//	@RequestMapping(value="/permit/logout", method = RequestMethod.GET)
//	public 
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
		model.addAttribute("user", user);
		model.addAttribute("userSchedule", userSchedule);
		model.addAttribute("serverTime", formattedDate);
		model.addAttribute("userTodoList", userTodoList);
		model.addAttribute("selector", status);
		
		return "index/authoredHome";
	}
	

}
