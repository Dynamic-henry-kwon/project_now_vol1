package com.timeIsGold.appNow.Todo.Domain;

import java.util.Date;

import lombok.Data;
@Data
public class TodayRecord {
	private String detail;
	private Date date;
	private String phoneNumber;
	private String avaliableTimeCode;
	private String whatToDoCode;
}
