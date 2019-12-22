package com.timeIsGold.appNow.Dao.test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;

import static org.junit.Assert.assertThat;

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
import com.timeIsGold.appNow.User.Domain.User;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml",
		"file:src/main/webapp/WEB-INF/spring/root-context.xml"
	})
@Log4j
public class UserDaoTest {
	
	@Setter(onMethod_= {@Autowired})
	UserDao mybatisUserDao;
	
	User user, user2, user3;
	@Before
	public void before() {
		user = new User("01089261833", "ekfkawnl1!", "권현우", "골룸의습격", "1","2020", 0 , "1", "1");
		user2 = new User("01025243026", "jukdo1833", "권현우", "아빠의청춘", "1", "3010", 31 , "1", "1") ;
		user3 = new User("01063001823", "jukdo1000", "강영선", "엄마의삶", "2", "1000", 61, "1", "1");
	}
	
	@Test
	public void userCreateAndGetTest() {
		mybatisUserDao.deleteAll();
		mybatisUserDao.createUser(user);
		mybatisUserDao.createUser(user2);
		mybatisUserDao.createUser(user3);
		
		User readUser = mybatisUserDao.readUser(user.getPhoneNum());
		User readUser2 = mybatisUserDao.readUser(user2.getPhoneNum());
		User readUser3  = mybatisUserDao.readUser(user3.getPhoneNum());
		User nonUser = null;
		
		checkSameUser(user, readUser);
		checkSameUser(user2, readUser2);
		checkSameUser(user3, readUser3);
		
		
	}
	
	//@Test
	public void userDeleteAndGetAllTest() {
		mybatisUserDao.deleteAll();
		mybatisUserDao.createUser(user);
		mybatisUserDao.createUser(user2);
		
		List<User> listTwo = mybatisUserDao.readAll();
		assertThat(listTwo.size(), is(2));
		
		int test = mybatisUserDao.deleteUser("01089261833");
		assertThat(test, is(1));
		List<User> listOne = mybatisUserDao.readAll();
		assertThat(listOne.size(), is(1));
	}
	
	//@Test
	public void updateTest() {
		mybatisUserDao.deleteAll();
		mybatisUserDao.createUser(user);
		
		User passwordChangeUser = new User();
		passwordChangeUser.setPassword("rnfnal1!");
		passwordChangeUser.setPhoneNum(user.getPhoneNum());
		int result = mybatisUserDao.updateUser(passwordChangeUser);
		assertThat(result, is(1));
		User changedUser = mybatisUserDao.readUser(user.getPhoneNum());
		assertThat(changedUser.getPassword(), is(passwordChangeUser.getPassword()));
	}
	
	//@Test
	public void updatePoint() {
		mybatisUserDao.deleteAll();
		mybatisUserDao.createUser(user);
		
		User nowUser = mybatisUserDao.readUser(user.getPhoneNum());
		int nowPoint = nowUser.getPointJG();
		User pointUpdatedUser = new User();
		int addPoint = 2;
		pointUpdatedUser.setPhoneNum(user.getPhoneNum());
		pointUpdatedUser.setPointJG(addPoint);
		mybatisUserDao.updateUser(pointUpdatedUser);
		
		User updatedUser = mybatisUserDao.readUser(user.getPhoneNum());
		
		assertThat(updatedUser.getPointJG(), is(nowUser.getPointJG() + addPoint));
		assertThat(updatedUser.getLoginCount(), is(1));
		
	}
	
	
	//@Test
	public void updateValid() {
		mybatisUserDao.deleteAll();
		mybatisUserDao.createUser(user);
		mybatisUserDao.createUser(user2);
		
		User updateUserOne = new User();
		updateUserOne.setPhoneNum(user.getPhoneNum());
		updateUserOne.setName("권현");
		updateUserOne.setNickname("기습공격");
		updateUserOne.setPassword("rnfnal1!");

		mybatisUserDao.updateUser(updateUserOne);
		User updatedUserOne = mybatisUserDao.readUser(user.getPhoneNum());
		User nonUpdatedUserTow = mybatisUserDao.readUser(user2.getPhoneNum());
		
		checkSameUser(updateUserOne, updatedUserOne);
		checkSameUser(user2, nonUpdatedUserTow);	
	}
	
	//@Test
	public void getAll() {
		mybatisUserDao.deleteAll();
		List<User> list = new ArrayList<User>();
		list = mybatisUserDao.readAll();
		assertThat(list.size(), is(3));
	}
	
	
	
	void checkSameUser(User user1, User user2){
		assertThat(user1.getPhoneNum(), is(user2.getPhoneNum()));
		assertThat(user1.getPassword(), is(user2.getPassword()));
		assertThat(user1.getName(), is(user2.getName()));
		assertThat(user1.getNickname(), is(user2.getNickname()));
		assertThat(user1.getGender(), is(user2.getGender()));
		assertThat(user1.getDetailOccupationCode(), is(user2.getDetailOccupationCode()));
		assertThat(user1.getPointJG(), is(user2.getPointJG()));
		assertThat(user1.getLoginCount(), is(user2.getLoginCount()));
		assertThat(user1.getLevel(), is(user2.getLevel()));
	
	}
}
