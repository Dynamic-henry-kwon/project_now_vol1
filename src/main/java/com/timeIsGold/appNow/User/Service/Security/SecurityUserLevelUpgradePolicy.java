package com.timeIsGold.appNow.User.Service.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.timeIsGold.appNow.User.Dao.UserDao;
import com.timeIsGold.appNow.User.Domain.Level;
import com.timeIsGold.appNow.User.Domain.User;
import com.timeIsGold.appNow.User.Service.UserLevelUpgradePolicy;

@Component
public class SecurityUserLevelUpgradePolicy implements UserLevelUpgradePolicy {
	
	@Autowired
	UserDao userDao;
	
	public static final int MIN_CUMULATIVE_COUNT_FOR_BRONZE = 30;
	public static final int MIN_CUMULATIVE_COUNT_FOR_SILVER = 60;
	public static final int MIN_CUMULATIVE_COUNT_FOR_GOLD = 90;
	public static final int MIN_CUMULATIVE_COUNT_FOR_DIAMOND = 120;
	
	@Override
	public boolean canUpgradeLevel(User user) {
		Level currentLevel = user.getLevel();
		switch (currentLevel) {
			case STONE: return (user.getCumulativeCount() >= MIN_CUMULATIVE_COUNT_FOR_BRONZE);
			case BRONZE: return (user.getCumulativeCount() >= MIN_CUMULATIVE_COUNT_FOR_SILVER);
			case SILVER: return (user.getCumulativeCount() >= MIN_CUMULATIVE_COUNT_FOR_GOLD);
			case GOLD: return (user.getCumulativeCount() >= MIN_CUMULATIVE_COUNT_FOR_DIAMOND);
			case DIAMOND: return false;
			default : throw new IllegalArgumentException("Unknown Level: " + currentLevel );
		}
	}

	@Override
	public void upgradeLevel(User user) {
		user.upgradeLevel();
		userDao.updateUser(user);
	}

}
