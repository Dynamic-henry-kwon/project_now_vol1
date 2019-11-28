package com.timeIsGold.appNow.Todo.Domain;

import lombok.Data;

@Data
public class ToDo {
	private String phoneNum;
	private String scheduleCode;
	private String subScheduleCode;
	private int allocatiedPoint;
	private Long allocatedTime;
	private String toDoCode;
	private String etcName;
	
	public ToDo(String phoneNum, String scheduleCode, String subScheduleCode, int allocatiedPoint, Long allocatedTime,
			String toDoCode, String etcName) {
		this.phoneNum = phoneNum;
		this.scheduleCode = scheduleCode;
		this.subScheduleCode = subScheduleCode;
		this.allocatiedPoint = allocatiedPoint;
		this.allocatedTime = allocatedTime;
		this.toDoCode = toDoCode;
		this.etcName = etcName;
	}
	
	public ToDo(String phoneNum, String scheduleCode) {
		super();
		this.phoneNum = phoneNum;
		this.scheduleCode = scheduleCode;
	}

	public ToDo(String phoneNum, String scheduleCode, String subScheduleCode) {
		super();
		this.phoneNum = phoneNum;
		this.scheduleCode = scheduleCode;
		this.subScheduleCode = subScheduleCode;
	}
	
	
	
	
}
