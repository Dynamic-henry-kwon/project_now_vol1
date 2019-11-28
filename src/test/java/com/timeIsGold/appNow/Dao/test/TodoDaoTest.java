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

import com.timeIsGold.appNow.Todo.Dao.TodoDao;
import com.timeIsGold.appNow.Todo.Domain.ToDo;
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
public class TodoDaoTest {
	
	@Setter(onMethod_= {@Autowired})
	TodoDao mybatisTodoDao;
	
	ToDo toDo01,toDo02, toDo11,toDo12, toDo21,toDo22, toDo31,toDo32, toDo41,toDo42, toDo51,toDo52, toDo61,toDo62, updateTodo;
	@Before
	public void before() {
		//toDoContext =  ["어학(영어)", "어학(중국어)", "어학(기타)", "인강듣기", "운동", "독서", "신문읽기", "기타"];
		toDo01 = new ToDo("01089261833", "2","1", 3,  30L,"0", "");
		toDo02 = new ToDo("01089261833", "2","2", 3,  30L,"1", "");
		
		toDo11 = new ToDo("01089261833", "3", "1", 3, 50L,"2", "");
		toDo12 = new ToDo("01089261833", "3","2", 3,  10L,"3", "");
		
		toDo21 = new ToDo("01089261833", "4", "1", 3,  40L,"4", "");
		toDo22 = new ToDo("01089261833", "4","2", 3,   20L,"1", "");
		
		toDo31 = new ToDo("01089261833", "5", "1", 3, 45L,"2", "");
		toDo32 = new ToDo("01089261833", "5","2", 3,   15L,"3", "");
		
		toDo41 = new ToDo("01089261833", "6", "1", 3, 35L,"4", "");
		toDo42 = new ToDo("01089261833", "6","2", 3,   25L,"1", "");
		
		toDo51 = new ToDo("01089261833", "7", "1", 3,  10L,"2", "");
		toDo52 = new ToDo("01089261833", "7","2", 3,   50L,"3", "");
		
		toDo61 = new ToDo("01089261833", "8", "1", 3, 25L,"4", "");
		toDo62 = new ToDo("01089261833", "8","2",  3,  35L,"3", "");
	}
	
	@Test
	public void makeDummy() {
		mybatisTodoDao.deleteAll();
		mybatisTodoDao.createTodo(toDo01);
		mybatisTodoDao.createTodo(toDo02);
		mybatisTodoDao.createTodo(toDo11);
		mybatisTodoDao.createTodo(toDo12);
		mybatisTodoDao.createTodo(toDo21);
		mybatisTodoDao.createTodo(toDo22);
		mybatisTodoDao.createTodo(toDo31);
		mybatisTodoDao.createTodo(toDo32);
		mybatisTodoDao.createTodo(toDo41);
		mybatisTodoDao.createTodo(toDo42);
		//mybatisTodoDao.createTodo(toDo51);
		//mybatisTodoDao.createTodo(toDo52);
		mybatisTodoDao.createTodo(toDo61);
		mybatisTodoDao.createTodo(toDo62);
		List<ToDo> resultList = mybatisTodoDao.readAll();
		assertThat(resultList.size(), is(12));
		
//		ToDo deleteTodo = new ToDo("01089261833", "4");
//		mybatisTodoDao.deleteTodo(deleteTodo);
//		resultList = mybatisTodoDao.readAll();
//		assertThat(resultList.size(), is(10));
	}
	
	//@Test
	public void deleteAllTest() {
		mybatisTodoDao.deleteAll();
		List<ToDo>toDoList = mybatisTodoDao.readAll();
		assertThat(toDoList.size(), is(0));
		
		mybatisTodoDao.createTodo(toDo11);
		toDoList = mybatisTodoDao.readAll();
		assertThat(toDoList.size(),is(1));
		
		mybatisTodoDao.createTodo(toDo12);
		toDoList = mybatisTodoDao.readAll();
		assertThat(toDoList.size(),is(2));
		
		toDoList = mybatisTodoDao.readTodo(toDo11);
		assertThat(toDoList.size(),is(2));
		
		ToDo searchTodoBySub = new ToDo("01089261833", "0", "1");
		ToDo getTodo = mybatisTodoDao.readTodoBysub(searchTodoBySub);
		checkSameTodo(toDo11, getTodo);
		ToDo searchTodo = new ToDo("01089261833", "0");
		toDoList = mybatisTodoDao.readTodo(searchTodo);
		assertThat(toDoList.size(), is(2));
		checkSameTodo(toDoList.get(0), toDo11);
		checkSameTodo(toDoList.get(1), toDo12);
		
	}
	
	//@Test
	public void updateAnddeleteTest() {
		mybatisTodoDao.deleteAll();
		mybatisTodoDao.createTodo(toDo11);
		mybatisTodoDao.createTodo(toDo12);
		
		List<ToDo>toDoList = mybatisTodoDao.readAll();
		assertThat(toDoList.size(),is(2));
		
		mybatisTodoDao.deleteTodo(toDo11);
		toDoList = mybatisTodoDao.readAll();
		assertThat(toDoList.size(),is(1));
		
		mybatisTodoDao.createTodo(toDo11);
		updateTodo = new ToDo("01089261833", "0","1", 1, 30L,"3", "기타");
		ToDo searchTodoBySub = new ToDo("01089261833", "0", "1");
		mybatisTodoDao.updateTodo(updateTodo);
		ToDo getTodo = mybatisTodoDao.readTodoBysub(searchTodoBySub);
		checkSameTodo(getTodo, updateTodo);
		
	}
	
	void checkSameTodo(ToDo toDo1, ToDo toDo2){
		assertThat(toDo1.getPhoneNum(), is(toDo2.getPhoneNum()));
		assertThat(toDo1.getScheduleCode(), is(toDo2.getScheduleCode()));
		assertThat(toDo1.getAllocatiedPoint(), is(toDo2.getAllocatiedPoint()));
		assertThat(toDo1.getAllocatedTime(), is(toDo2.getAllocatedTime()));
		assertThat(toDo1.getToDoCode(), is(toDo2.getToDoCode()));
		assertThat(toDo1.getEtcName(), is(toDo2.getEtcName()));
		
	}
}
