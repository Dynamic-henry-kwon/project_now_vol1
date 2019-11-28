package com.timeIsGold.appNow.User.Service.Security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.timeIsGold.appNow.User.Dao.UserDao;
import com.timeIsGold.appNow.User.Domain.Level;
import com.timeIsGold.appNow.User.Domain.User;
import com.timeIsGold.appNow.User.Service.UserLevelUpgradePolicy;
import com.timeIsGold.appNow.User.Service.UserService;

import lombok.Setter;

@Component
public class SecurityUserService implements UserService, UserDetailsService {
	@Setter(onMethod_= {@Autowired})
	UserDao userDao;

	@Setter(onMethod_= {@Autowired})
	UserLevelUpgradePolicy userLevelUpgradePolicy;
	
	public void registUser(User user) {
		if(user.getLevel() == null) {
			user.setLevel(Level.STONE);
		}
		userDao.createUser(user);

	}

	public int updateUser(User user) {
		return userDao.updateUser(user);
	}

	public User getUser(String phoneNum) {
		User readUser = userDao.readUser(phoneNum);
		return readUser;
	}

	public List<User> getAllUser() {
		return userDao.readAll();
	}

	public int removeUser(String phoneNum) {
		return userDao.deleteUser(phoneNum);
	}

	public void removeAllUser() {
		userDao.deleteAll();
	}
	
	public static final int MIN_CUMULATIVE_COUNT_FOR_BRONZE = 30;
	public static final int MIN_CUMULATIVE_COUNT_FOR_SILVER = 60;
	public static final int MIN_CUMULATIVE_COUNT_FOR_GOLD = 90;
	public static final int MIN_CUMULATIVE_COUNT_FOR_DIAMOND = 120;
	
	public void upgradeLevels() {
		List<User> users = userDao.readAll();
		for (User user : users) {
			if(userLevelUpgradePolicy.canUpgradeLevel(user)) {
				userLevelUpgradePolicy.upgradeLevel(user);
			}
		}
	}

	@Override
	public User loadUserByUsername(String phoneNum) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user = null;
		user = userDao.readUser(phoneNum);
		return user;
	}
	
}
