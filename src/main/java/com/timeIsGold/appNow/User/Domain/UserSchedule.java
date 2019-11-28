package com.timeIsGold.appNow.User.Domain;

import lombok.Data;

@Data
/**
 * morningPrepareTime : 0
 * morningCommuteTime : 1
 * morningWorkTime : 2
 * luanchTime : 3
 * afterNoonWorkTime : 4
 * afterNoonCommuteTime : 5
 * nightFreeTime : 6
 * */
public class UserSchedule {
	Long sleepTime;
	Long awakeTime;
	Long morningPrepareTime;
	Long morningCommuteTime;
	Long morningWorkTime;
	Long luanchTime;
	Long afterNoonWorkTime;
	Long afterNoonCommuteTime;
	Long nightFreeTime;
}
