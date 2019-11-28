package com.timeIsGold.appNow.User.Service;

import java.util.List;

import com.timeIsGold.appNow.User.Domain.User;

public interface UserService {
	public void registUser(User user);
	public int updateUser(User user);
	public User getUser(String phoneNum);
	public List<User> getAllUser();
	public int removeUser(String phoneNum);
	public  void removeAllUser();
	public void upgradeLevels();
}
