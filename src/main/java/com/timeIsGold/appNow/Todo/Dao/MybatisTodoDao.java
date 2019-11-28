package com.timeIsGold.appNow.Todo.Dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.timeIsGold.appNow.Todo.Domain.ToDo;
@Component
public class MybatisTodoDao implements TodoDao {
	@Autowired
	protected SqlSessionTemplate sqlSession;
	
	@Override
	public void createTodo(ToDo toDo) {
		sqlSession.insert("TodoMapper.createTodo", toDo);
	}

	@Override
	public List<ToDo> readTodo(ToDo toDo) {
		return sqlSession.selectList("TodoMapper.readTodo", toDo);
	}
	
	@Override
	public ToDo readTodoBysub(ToDo toDo) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("TodoMapper.readTodoBysub", toDo);
	}

	@Override
	public int updateTodo(ToDo toDo) {
		return sqlSession.update("TodoMapper.updateTodo", toDo);
	}

	@Override
	public int deleteTodo(ToDo toDo) {
		return sqlSession.delete("TodoMapper.deleteTodo", toDo);
	}
	
	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		sqlSession.delete("TodoMapper.deleteAll");
	}

	@Override
	public List<ToDo> readAll() {
		// TODO Auto-generated method stub
		return sqlSession.selectList("TodoMapper.readAll");
	}



}
