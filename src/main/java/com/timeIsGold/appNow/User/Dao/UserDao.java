package com.timeIsGold.appNow.User.Dao;

import java.util.List;

import com.timeIsGold.appNow.User.Domain.User;


public interface UserDao {
	void createUser (User user);
	User readUser(String phoneNum);
	int updateUser(User user);
	void deleteAll();
	int deleteUser(String phoneNum);
	List<User> readAll();
	
	
}
