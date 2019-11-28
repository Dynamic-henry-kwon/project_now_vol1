package com.timeIsGold.appNow.Todo.Dao;

import java.util.List;

import com.timeIsGold.appNow.Todo.Domain.ToDo;

public interface TodoDao {
	public void createTodo(ToDo toDo);
	public List<ToDo> readTodo(ToDo toDo);
	public ToDo readTodoBysub(ToDo toDo);
	public int updateTodo(ToDo toDo);
	public int deleteTodo(ToDo toDo);
	public void deleteAll();
	public List<ToDo> readAll();
}
