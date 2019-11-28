package com.timeIsGold.appNow.User.Service;

import com.timeIsGold.appNow.User.Domain.User;

public interface UserLevelUpgradePolicy {

	boolean canUpgradeLevel(User user);
	void upgradeLevel(User user);
}
