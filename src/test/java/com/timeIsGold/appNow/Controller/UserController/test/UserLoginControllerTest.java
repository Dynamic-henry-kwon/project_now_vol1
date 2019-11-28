package com.timeIsGold.appNow.Controller.UserController.test;

import java.text.ParseException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ModelAndView;

import com.timeIsGold.appNow.Controller.UserController.UserLoginController;
import com.timeIsGold.appNow.User.Domain.TimeTable;
import com.timeIsGold.appNow.User.Service.TimeTableService;
import com.timeIsGold.appNow.User.Service.UserService;
import com.timeIsGold.appNow.User.Service.Security.CustomAuthenticationProvider;

import lombok.extern.log4j.Log4j;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {
		"file:src/main/webapp/WEB-INF/spring/appServlet/test-context.xml",
		"file:src/main/webapp/WEB-INF/spring/root-context.xml"
	})
@Log4j
public class UserLoginControllerTest {
	@Mock UserService userService;
	@Mock TimeTableService tService;
	@Mock CustomAuthenticationProvider authProvider;
	
	@InjectMocks 
	UserLoginController loginController = new UserLoginController();
	
	private ModelAndView model;
	private MockMvc mockMvc;
	@Before
	public void before() throws ParseException {
		MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
	
	}
	
	@Test
	public void getTimeTest() throws Exception {
		List<TimeTable> tTables = tService.getAllTables();
		System.out.println(tTables.size());
		//mockMvc.perform(get("/common/login")).andExpect(status().isOk()).andExpect(view().name("test"));
	}
	
}
