package com.timeIsGold.appNow.User.Dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.timeIsGold.appNow.User.Domain.TimeTable;
@Component
public class MybatisTimeTableDao implements TimeTableDao{

	@Autowired
	protected SqlSessionTemplate sqlSession;
	
	@Override
	public void createTtable(TimeTable table) {
		// TODO Auto-generated method stub
		sqlSession.insert("TimeTableMapper.createTtable",table);
	}

	@Override
	public TimeTable readTtable(String phoneNum) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("TimeTableMapper.readTtable", phoneNum);
	}

	@Override
	public int updateTtable(TimeTable table) {
		// TODO Auto-generated method stub
		return sqlSession.update("TimeTableMapper.updateTtable", table);
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		sqlSession.delete("TimeTableMapper.deleteAll");
	}

	@Override
	public int deleteTtable(String phoneNum) {
		// TODO Auto-generated method stub
		return sqlSession.delete("TimeTableMapper.deleteTtable", phoneNum);
	}

	@Override
	public List<TimeTable> readAll() {
		// TODO Auto-generated method stub
		return sqlSession.selectList("TimeTableMapper.readAll");
	}

}
