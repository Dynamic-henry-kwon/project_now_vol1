package com.timeIsGold.appNow.User.Domain;

import java.sql.Time;

import lombok.Data;

@Data
public class TimeTable {
	private String phoneNum;
	private Time timeWakeUp;
	private Time timeGoToSleep;
	
	private Time timeLeaveHome;
	private Time timeStartWork;
	private Time timeLunchBreak;
	private Time timeStartAfterNoonWork;
	private Time timeQuttingWork;
	private Time timeComeBackHome;
	
	public TimeTable() {
		// TODO Auto-generated constructor stub
	}

	
	public TimeTable(String phoneNum, Time timeWakeUp, Time timeGoToSleep, Time timeLeaveHome, Time timeStartWork,
			Time timeLunchBreak, Time timeStartAfterNoonWork, Time timeQuttingWork, Time timeComeBackHome) {
		this.phoneNum = phoneNum;
		this.timeWakeUp = timeWakeUp;
		this.timeGoToSleep = timeGoToSleep;
		this.timeLeaveHome = timeLeaveHome;
		this.timeStartWork = timeStartWork;
		this.timeLunchBreak = timeLunchBreak;
		this.timeStartAfterNoonWork = timeStartAfterNoonWork;
		this.timeQuttingWork = timeQuttingWork;
		this.timeComeBackHome = timeComeBackHome;
	}
}
