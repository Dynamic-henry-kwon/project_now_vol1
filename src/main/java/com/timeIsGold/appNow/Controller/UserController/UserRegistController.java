package com.timeIsGold.appNow.Controller.UserController;

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
@RequestMapping("/common")
public class UserRegistController {
	
	@Autowired UserService userService;
	@Autowired TimeTableService timeTableService;
	
		
	/**회원가입 폼 load
	 * @param model
	 * @return regist.jsp
	 */
	@RequestMapping(value = "/regist", method = RequestMethod.GET)
	public String registGet(Model model) {
		return "regist";
	}
	
	/**회원가입 처리 controller
	 * @param user
	 * @return map
	 */
	@RequestMapping(value = "/regist", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> registPost(User user) {
		System.out.println(user.toString());
		userService.removeAllUser();
		//regist User
		userService.registUser(user);
		User getUser = userService.getUser(user.getPhoneNum());
		Map<String, Object> map = new HashMap<String, Object>();
		if(getUser != null && getUser.getPhoneNum().equals(user.getPhoneNum())) {
			map.put("result", true);
			map.put("phoneNum", getUser.getPhoneNum());
			map.put("password", getUser.getPassword());
			
		} else {
			map.put("result", false);
		}
		return map;
	}
}
