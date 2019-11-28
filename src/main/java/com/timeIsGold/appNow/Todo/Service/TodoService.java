package com.timeIsGold.appNow.Todo.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.timeIsGold.appNow.Todo.Dao.TodoDao;
import com.timeIsGold.appNow.Todo.Domain.ToDo;

@Component
public class TodoService {
	@Autowired
	TodoDao todoDao;
	
	public void insertTodo(ToDo toDo) {
		todoDao.createTodo(toDo);
	}
	
	public List<ToDo> readTodo(ToDo toDo) {
		return todoDao.readTodo(toDo);
	}
	
	public ToDo readTodoBysub(ToDo toDo) {
		return todoDao.readTodoBysub(toDo);
	}
	
	public int updateTodo(ToDo todo) {
		return todoDao.updateTodo(todo);
	}
	public int deleteTodo(ToDo todo) {
		return todoDao.deleteTodo(todo);
	}
	public void deleteAll() {
		todoDao.deleteAll();
	}
	
	public List<ToDo> readAll(){
		return todoDao.readAll();
	}
}
