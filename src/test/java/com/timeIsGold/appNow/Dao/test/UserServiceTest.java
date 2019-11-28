package com.timeIsGold.appNow.Dao.test;

import static com.timeIsGold.appNow.User.Service.Security.SecurityUserService.MIN_CUMULATIVE_COUNT_FOR_BRONZE;
import static com.timeIsGold.appNow.User.Service.Security.SecurityUserService.MIN_CUMULATIVE_COUNT_FOR_DIAMOND;
import static com.timeIsGold.appNow.User.Service.Security.SecurityUserService.MIN_CUMULATIVE_COUNT_FOR_GOLD;
import static com.timeIsGold.appNow.User.Service.Security.SecurityUserService.MIN_CUMULATIVE_COUNT_FOR_SILVER;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.timeIsGold.appNow.User.Dao.UserDao;
import com.timeIsGold.appNow.User.Domain.Level;
import com.timeIsGold.appNow.User.Domain.User;
import com.timeIsGold.appNow.User.Service.UserLevelUpgradePolicy;
import com.timeIsGold.appNow.User.Service.UserService;
import com.timeIsGold.appNow.User.Service.Security.SecurityUserService;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml",
		"file:src/main/webapp/WEB-INF/spring/root-context.xml"
	})
@Log4j
public class UserServiceTest {
	@Setter(onMethod_= {@Autowired})
	UserService userService;
	
	@Setter(onMethod_= {@Autowired})
	UserDao userDao;
	
	User userT, user2, userT3, userT4, user5;
	List<User> users;
	@Before
	public void before() {
		userT = new User("01063001823", "jukdo1000", "강영선", "엄마의삶", "2", "1000", MIN_CUMULATIVE_COUNT_FOR_GOLD , Level.SILVER,  "1", "1");
		user2 = new User("01025243026", "jukdo1833", "권신철", "아빠의청춘", "1", "3010", MIN_CUMULATIVE_COUNT_FOR_SILVER - 1 , Level.BRONZE,  "1", "1");
		userT3 = new User("01089261833", "ekfkawnl1!", "권현우", "골룸의습격", "2","2020", MIN_CUMULATIVE_COUNT_FOR_BRONZE,  "1", "1");
		userT4 = new User("01063001221", "jukdo66", "권현준", "취준생", "1", "0000", MIN_CUMULATIVE_COUNT_FOR_SILVER , Level.BRONZE,  "1", "1");
		user5 = new User("01030129750", "dlthdus1234", "이소연", "또니월드", "2", "1010", MIN_CUMULATIVE_COUNT_FOR_DIAMOND + 1 , Level.GOLD,  "1", "1");
		users = new ArrayList<User>();
		users.add(userT);
		users.add(user2);
		users.add(userT3);
		users.add(userT4);
		users.add(user5);
		
		
	}
	
	//@Test
	public void serviceInject() {
		assertThat(userService, is(notNullValue()));
	}
	//@Test
	public void nullUserTest() {
		for (User user : users) userService.registUser(user);
	}
	
	@Test
	public void upgradeLevels() {
		userService.removeAllUser();
		for (User user : users) userService.registUser(user);
		users = userService.getAllUser();
		
		userService.upgradeLevels();
		
		checkLevelUpgraded(users.get(0), true);
		checkLevelUpgraded(users.get(1), false);
		checkLevelUpgraded(users.get(2), true);
		checkLevelUpgraded(users.get(3), true);
		checkLevelUpgraded(users.get(4), true);
		
	}
	
	private void checkLevelUpgraded(User user, boolean upgraded) {
		User userUpdate = userService.getUser(user.getPhoneNum());
		if(upgraded) {
			assertThat(userUpdate.getLevel(), is(user.getLevel().nextLevel()));
		}else {
			assertThat(userUpdate.getLevel(), is(user.getLevel()));
		}
	}
	
	@Autowired
	UserLevelUpgradePolicy userLevelUpgradePolicy;
	
	class TestUserService extends SecurityUserService {
		private String phoneNum;
		private UserLevelUpgradePolicy userLevelUpgradePolicy;
		
		public void setUserLevelUpgradePolicy(UserLevelUpgradePolicy userLevelUpgradePolicy) {
			this.userLevelUpgradePolicy = userLevelUpgradePolicy;
		}

		private TestUserService(String phoneNum) {
			this.phoneNum = phoneNum;
		}
		
		@Override
		public void upgradeLevels() {
			List<User> users = userDao.readAll();
			for (User user : users) {
				if(userLevelUpgradePolicy.canUpgradeLevel(user)) {
					this.upgradeLevel(user);
				}
			}
		}
		
		protected void upgradeLevel(User user) {
			if(user.getPhoneNum().equals(this.phoneNum)) {
				throw new TestUserServiceException();
			}
			this.userLevelUpgradePolicy.upgradeLevel(user);
		}
		

	}
	
	static public class TestUserServiceException extends RuntimeException {
		
	}
	
	//@Test
	public void injectionTest() {
		TestUserService testService = new TestUserService(users.get(3).getPhoneNum());
		//testService.setUserDao(userDao);
		testService.setUserLevelUpgradePolicy(userLevelUpgradePolicy);
		
		userService.removeAllUser();
		for (User user : users) {
			userService.registUser(user);
		}
		users = userService.getAllUser();
		
		try {
			testService.upgradeLevels();
			fail("TestUserServiceException expected");
		} 
		catch (TestUserServiceException e) {
			
		}
		
		checkLevelUpgraded(users.get(0), false);
	}
	

}
