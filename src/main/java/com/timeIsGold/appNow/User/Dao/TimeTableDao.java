package com.timeIsGold.appNow.User.Dao;

import java.util.List;

import com.timeIsGold.appNow.User.Domain.TimeTable;

public interface TimeTableDao {
	void createTtable (TimeTable table);
	TimeTable readTtable(String phoneNum);
	int updateTtable(TimeTable table);
	void deleteAll();
	int deleteTtable(String phoneNum);
	List<TimeTable> readAll();
}
