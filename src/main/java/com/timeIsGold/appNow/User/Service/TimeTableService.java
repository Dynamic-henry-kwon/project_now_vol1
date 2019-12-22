package com.timeIsGold.appNow.User.Service;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.timeIsGold.appNow.User.Dao.TimeTableDao;
import com.timeIsGold.appNow.User.Domain.TimeTable;
import com.timeIsGold.appNow.User.Domain.UserSchedule;
@Component
public class TimeTableService {
	private final String MIDNIGHT = "24:00:00";
	private final String AFTERNOON = "12:00:00";
	
	@Autowired
	TimeTableDao timeTableDao;
	UserSchedule userSchedule;
	public void registTable(TimeTable table) {
		timeTableDao.createTtable(table);
	}
	public int updateTable(TimeTable table) {
		return timeTableDao.updateTtable(table);
	};
	public TimeTable getTable(String phoneNum) {
		return timeTableDao.readTtable(phoneNum);
	};
	public List<TimeTable> getAllTables(){
		return timeTableDao.readAll();
	};
	public int removeTable(String phoneNum) {
		return timeTableDao.deleteTtable(phoneNum);
	};
	public  void removeAllTables() {
		timeTableDao.deleteAll();
	};
	
	public UserSchedule getTimePlan(String phoneNum) {
		TimeTable timeTable = null;
		userSchedule = new UserSchedule();
			if((timeTable = timeTableDao.readTtable(phoneNum)) != null) {
				System.out.println("timeTable: " + timeTable.toString());
				userSchedule.setSleepTime(getTime(timeTable.getTimeGoToSleep(), timeTable.getTimeWakeUp()));
				userSchedule.setAwakeTime(1440 - userSchedule.getSleepTime());
				userSchedule.setMorningPrepareTime(getTime(timeTable.getTimeWakeUp(), timeTable.getTimeLeaveHome()));
				userSchedule.setMorningCommuteTime(getTime(timeTable.getTimeLeaveHome(), timeTable.getTimeStartWork()));
				userSchedule.setMorningWorkTime(getTime(timeTable.getTimeStartWork(), timeTable.getTimeLunchBreak()));
				userSchedule.setLuanchTime(getTime(timeTable.getTimeLunchBreak(), timeTable.getTimeStartAfterNoonWork()));
				userSchedule.setAfterNoonWorkTime(getTime(timeTable.getTimeStartAfterNoonWork(), timeTable.getTimeQuttingWork()));
				userSchedule.setAfterNoonCommuteTime(getTime(timeTable.getTimeQuttingWork(), timeTable.getTimeComeBackHome()));
				userSchedule.setNightFreeTime(getTime(timeTable.getTimeComeBackHome(), timeTable.getTimeGoToSleep()));
			} else {
				userSchedule = null;
			}
			return userSchedule;
	}
	
	private Long localeTimeParser(String time) {
		SimpleDateFormat f = new SimpleDateFormat("HH:mm:ss", Locale.KOREA);
		Long parsedTime = null;
		try {
			parsedTime =  (f.parse(time).getTime() + (9 * 1000 * 60 * 60)) / (1000 * 60) ;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return parsedTime;
	}

	
	private Long getTime(Time before, Time after) {
		Long afterNoon = localeTimeParser(AFTERNOON);
		Long midNight = localeTimeParser(MIDNIGHT);
		Long parsedBefore = localeTimeParser(before.toString());
		Long parsedAfter= localeTimeParser(after.toString());

		Long result = null;
		//오후 오전
		if(parsedBefore >= afterNoon && parsedAfter < afterNoon) {
			result = getMinuteGap(MIDNIGHT, before.toString()) + parsedAfter;
		} else {
			result = getMinuteGap(before, after);
		}
		return  result;
	}
	
	private int timeCompare(String one, String two) throws ParseException {
		SimpleDateFormat f = new SimpleDateFormat("HH:mm:ss", Locale.KOREA);
		Date dateOne = f.parse(one);
		Date dateTwo = f.parse(two);
		return dateOne.compareTo(dateTwo);
	}
	
	private Long getMinuteGap(String minuteParamOne, String minuteParamTwo) {
		Long parsedOne = localeTimeParser(minuteParamOne);
		Long parsedTwo = localeTimeParser(minuteParamTwo);
		return (parsedOne >= parsedTwo) ? parsedOne - parsedTwo : parsedOne - parsedTwo;
	}
	
	private Long getMinuteGap(Time minuteParamOne, Time minuteParamTwo) {
		Long parsedOne = localeTimeParser(minuteParamOne.toString());
		Long parsedTwo = localeTimeParser(minuteParamTwo.toString());
		return (parsedOne >= parsedTwo) ? parsedOne - parsedTwo : parsedTwo - parsedOne;
	}

}
