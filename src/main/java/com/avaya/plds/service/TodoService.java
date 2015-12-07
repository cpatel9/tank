package com.avaya.plds.service;

import java.util.List;


public interface TodoService {
    public List<String> allTodos();
    public void addTodo(String todo);
    public void deleteTodo(String todo);
    public void deleteAll();
    public void updateTodo(int position, String todo);
}
