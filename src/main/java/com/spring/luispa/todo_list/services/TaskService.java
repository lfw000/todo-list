package com.spring.luispa.todo_list.services;

import java.util.List;

import com.spring.luispa.todo_list.dtos.TaskCreateRequest;
import com.spring.luispa.todo_list.dtos.TaskReponse;
import com.spring.luispa.todo_list.dtos.TaskUpdateRequest;
import com.spring.luispa.todo_list.exceptions.ResourceNotFoundException;

public interface TaskService {

    List<TaskReponse> findAll();

    TaskReponse findById(Long id) throws ResourceNotFoundException;

    TaskReponse create(TaskCreateRequest task);

    TaskReponse update(Long id, TaskUpdateRequest task) throws ResourceNotFoundException;

    void delete(Long id) throws ResourceNotFoundException;
}
