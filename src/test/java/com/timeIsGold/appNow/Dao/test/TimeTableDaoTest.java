package com.timeIsGold.appNow.Dao.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.Time;
import java.text.ParseException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.timeIsGold.appNow.User.Dao.MybatisTimeTableDao;
import com.timeIsGold.appNow.User.Domain.TimeTable;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml",
		"file:src/main/webapp/WEB-INF/spring/root-context.xml"
	})
@Log4j
public class TimeTableDaoTest {
	
	@Setter(onMethod_= {@Autowired})
	MybatisTimeTableDao mybatisTimeTableDao;
	
	TimeTable schedule1,schedule2, schedule3;
	
	@Before
	public void before() throws ParseException {
		Time timeWakeUp = Time.valueOf(LocalTime.of(5, 30));
		Time timeLeaveHome = Time.valueOf(LocalTime.of(6, 10));
		Time timeStartWork = Time.valueOf(LocalTime.of(9, 00));
		Time timeLunchBreak = Time.valueOf(LocalTime.of(11, 30));
		Time timeStartAfterNoonWork = Time.valueOf(LocalTime.of(13, 0));
		Time timeQuttingWork = Time.valueOf(LocalTime.of(18, 00));
		Time timeComeBackHome = Time.valueOf(LocalTime.of(21, 00));
		Time timeGoToSleep = Time.valueOf(LocalTime.of(23, 30));
		
		schedule1 = new TimeTable("01089261833", timeWakeUp , timeGoToSleep, timeLeaveHome,timeStartWork,
				timeLunchBreak,timeStartAfterNoonWork,timeQuttingWork,timeComeBackHome );
		schedule2 = new TimeTable("01025243026", timeWakeUp , timeGoToSleep, timeLeaveHome,timeStartWork,
				timeLunchBreak,timeStartAfterNoonWork,timeQuttingWork,timeComeBackHome );
		schedule3 = new TimeTable("01063001823", timeWakeUp , timeGoToSleep, timeLeaveHome,timeStartWork,
				timeLunchBreak,timeStartAfterNoonWork,timeQuttingWork,timeComeBackHome );
	
	}
	
	@Test
	public void getTimeTest() {
		
	}
	
	//@Test
	public void deleteAllAndInsertAndGetTest() {
		mybatisTimeTableDao.deleteAll();
		mybatisTimeTableDao.createTtable(schedule1);
		TimeTable getTable = mybatisTimeTableDao.readTtable(schedule1.getPhoneNum());
		checkSameTable(schedule1, getTable);
	}
	
	//@Test
	public void updateTest() {
		mybatisTimeTableDao.deleteAll();
		mybatisTimeTableDao.createTtable(schedule1);
		
		Time timeWakeUp = Time.valueOf(LocalTime.of(6, 30));
		Time timeLeaveHome = Time.valueOf(LocalTime.of(7, 10));
		Time timeStartWork = Time.valueOf(LocalTime.of(10, 00));
		Time timeLunchBreak = Time.valueOf(LocalTime.of(12, 30));
		Time timeStartAfterNoonWork = Time.valueOf(LocalTime.of(14, 0));
		Time timeQuttingWork = Time.valueOf(LocalTime.of(19, 00));
		Time timeComeBackHome = Time.valueOf(LocalTime.of(22, 00));
		Time timeGoToSleep = Time.valueOf(LocalTime.of(0, 30));
		
		TimeTable updatedSchedule = new TimeTable("01089261833", timeWakeUp , timeGoToSleep, timeLeaveHome,timeStartWork,
				timeLunchBreak,timeStartAfterNoonWork,timeQuttingWork,timeComeBackHome );
		mybatisTimeTableDao.updateTtable(updatedSchedule);
		TimeTable getTable = mybatisTimeTableDao.readTtable(schedule1.getPhoneNum());
		checkSameTable(updatedSchedule, getTable);
	}
	
	@Test
	public void readAllandDeleteTest() {
		mybatisTimeTableDao.deleteAll();
		mybatisTimeTableDao.createTtable(schedule1);
		mybatisTimeTableDao.createTtable(schedule2);
		mybatisTimeTableDao.createTtable(schedule3);
		
		List<TimeTable> tables = mybatisTimeTableDao.readAll();
		
		assertThat(tables.size(), is(3));
		
		TimeTable getTable = mybatisTimeTableDao.readTtable("01025243026");
		TimeTable getTable2 = mybatisTimeTableDao.readTtable("01063001823");
		TimeTable getTable3 = mybatisTimeTableDao.readTtable("01089261833");
		
		checkSameTable(schedule2, getTable);
		checkSameTable(schedule3, getTable2);
		checkSameTable(schedule1, getTable3);
		
		mybatisTimeTableDao.deleteTtable(getTable.getPhoneNum());
		tables = mybatisTimeTableDao.readAll();
		assertThat(tables.size(), is(2));
		
		mybatisTimeTableDao.deleteTtable(getTable2.getPhoneNum());
		tables = mybatisTimeTableDao.readAll();
		assertThat(tables.size(), is(1));
		
		mybatisTimeTableDao.deleteTtable(getTable3.getPhoneNum());
		tables = mybatisTimeTableDao.readAll();
		assertThat(tables.size(), is(0));
	}
	
	public void checkSameTable(TimeTable t1, TimeTable t2) {
		assertThat(t1.getPhoneNum(), is(t2.getPhoneNum()));
		assertThat(t1.getTimeWakeUp(), is(t2.getTimeWakeUp()));
		assertThat(t1.getTimeLeaveHome(), is(t2.getTimeLeaveHome()));
		assertThat(t1.getTimeStartWork(), is(t2.getTimeStartWork()));
		assertThat(t1.getTimeLunchBreak(), is(t2.getTimeLunchBreak()));
		assertThat(t1.getTimeStartAfterNoonWork(), is(t2.getTimeStartAfterNoonWork()));
		assertThat(t1.getTimeQuttingWork(), is(t2.getTimeQuttingWork()));
		assertThat(t1.getTimeGoToSleep(), is(t2.getTimeGoToSleep()));
		
	}
	
}
