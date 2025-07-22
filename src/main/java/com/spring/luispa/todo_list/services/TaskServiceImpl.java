package com.spring.luispa.todo_list.services;

import java.util.List;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.luispa.todo_list.dtos.TaskCreateRequest;
import com.spring.luispa.todo_list.dtos.TaskReponse;
import com.spring.luispa.todo_list.dtos.TaskUpdateRequest;
import com.spring.luispa.todo_list.entities.Task;
import com.spring.luispa.todo_list.entities.TaskStatus;
import com.spring.luispa.todo_list.exceptions.ResourceNotFoundException;
import com.spring.luispa.todo_list.mappers.TaskMapper;
import com.spring.luispa.todo_list.repositories.TaskRepository;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    private final TaskMapper taskMapper;

    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskReponse> findAll() {
        Iterable<Task> taskIterable = taskRepository.findAll();

        List<Task> tasks = StreamSupport.stream(taskIterable.spliterator(), false)
                .toList();

        return tasks.stream().map(taskMapper::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public TaskReponse findById(Long id) throws ResourceNotFoundException {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task with id: " + id + " not found."));

        return taskMapper.toResponse(task);
    }

    @Override
    public TaskReponse create(TaskCreateRequest taskRequest) {
        Task task = taskMapper.toEntity(taskRequest);

        taskRepository.save(task);

        return taskMapper.toResponse(task);
    }

    @Override
    public TaskReponse update(Long id, TaskUpdateRequest task) throws ResourceNotFoundException {
        if (!id.equals(task.id())) {
            throw new IllegalArgumentException("ID mismatch.");
        }

        Task taskDb = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id " + id + "."));

        if (taskDb.getStatus() == TaskStatus.COMPLETED) {
            throw new IllegalArgumentException("A completed task cannot be modified.");
        }

        taskMapper.updateFromDto(task, taskDb);

        // taskRepository.save(taskDb); // No hace falta, porque la entidad ya está en
        // el contexto de persistencia, pero
        // // agrega claridad

        return taskMapper.toResponse(taskDb);
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        if (!taskRepository.existsById(id)) {
            throw new ResourceNotFoundException("Task not found with id " + id + ".");
        }

        taskRepository.deleteById(id);
    }

}
