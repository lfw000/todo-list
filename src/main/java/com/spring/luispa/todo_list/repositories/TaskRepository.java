package com.spring.luispa.todo_list.repositories;

import org.springframework.data.repository.CrudRepository;

import com.spring.luispa.todo_list.entities.Task;

public interface TaskRepository extends CrudRepository<Task, Long> {

}
