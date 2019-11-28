package com.timeIsGold.appNow.User.Domain;

public enum Level {
	DIAMOND(4, null), GOLD(3, DIAMOND), SILVER(2, GOLD), BRONZE(1, SILVER), STONE(0, BRONZE);

	private final int value;
	private final Level nextLevel;
	

	Level(int value, Level nextLevel) {
		this.value = value;
		this.nextLevel = nextLevel;
	}

	public int initValue() {
		return value;
	}
	
	public Level nextLevel() {
		return this.nextLevel;
	}

	public static Level valueOf(int value) {
		switch (value) {
		case 0:
			return STONE;
		case 1:
			return BRONZE;
		case 2:
			return SILVER;
		case 3:
			return GOLD;
		case 4:
			return DIAMOND;
		default:
			throw new AssertionError("Unknown value: " + value);
		}
	}
	
}
