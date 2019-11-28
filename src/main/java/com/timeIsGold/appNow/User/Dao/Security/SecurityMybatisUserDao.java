package com.timeIsGold.appNow.User.Dao.Security;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import com.timeIsGold.appNow.User.Dao.UserDao;
import com.timeIsGold.appNow.User.Domain.User;
@Component
public class SecurityMybatisUserDao implements UserDao{
	
	@Autowired
	protected SqlSessionTemplate sqlSession;
	@Override
	public void createUser(User user) {
		sqlSession.insert("UserMapper.createUser", user);
	}
	
	@Override
	public User readUser(String phoneNum) {
		// TODO Auto-generated method stub
		User user = sqlSession.selectOne("UserMapper.readUser", phoneNum);
		return user;
	}

	@Override
	public int updateUser(User user) {
		return sqlSession.update("UserMapper.updateUser", user);
	}

	@Override
	public int deleteUser(String phoneNum) {
		return sqlSession.delete("UserMapper.deleteUser",phoneNum);
	}



	@Override
	public void deleteAll() {
		sqlSession.delete("UserMapper.deleteAll");
	}

	@Override
	public List<User> readAll() {
		List<User> list = sqlSession.selectList("UserMapper.readAll");
		return list;
		}
	
	


}
