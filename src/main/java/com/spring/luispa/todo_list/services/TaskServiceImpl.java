package com.spring.luispa.todo_list.services;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.luispa.todo_list.dtos.TaskCreateRequest;
import com.spring.luispa.todo_list.dtos.TaskReponse;
import com.spring.luispa.todo_list.dtos.TaskUpdateRequest;
import com.spring.luispa.todo_list.entities.Task;
import com.spring.luispa.todo_list.entities.TaskStatus;
import com.spring.luispa.todo_list.exceptions.IdMismatchException;
import com.spring.luispa.todo_list.exceptions.InvalidTaskStateTransitionException;
import com.spring.luispa.todo_list.exceptions.ResourceNotFoundException;
import com.spring.luispa.todo_list.exceptions.TaskNotFoundException;
import com.spring.luispa.todo_list.mappers.TaskMapper;
import com.spring.luispa.todo_list.repositories.TaskRepository;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    private final TaskMapper taskMapper;

    private final MessageService messageService;

    private static final Map<TaskStatus, Set<TaskStatus>> allowedTransitions = Map.of(
            TaskStatus.IN_PROGRESS, Set.of(TaskStatus.COMPLETED, TaskStatus.CANCELLED),
            TaskStatus.CANCELLED, Set.of(TaskStatus.IN_PROGRESS),
            TaskStatus.COMPLETED, Set.of());

    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper, MessageService messageService) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.messageService = messageService;
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
                .orElseThrow(() -> new TaskNotFoundException(
                        messageService.getMessage("ResourceNotFound.task.id", "error.not_found")));

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
            throw new IdMismatchException(
                    messageService.getMessage("IdMismatchException.task.id",
                            "IllegalArgumentException.default"));
        }

        Task taskDb = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(
                        messageService.getMessage("ResourceNotFound.task.id", "error.not_found")));

        // if (taskDb.getStatus() == TaskStatus.COMPLETED) {
        // throw new InvalidTaskStateTransitionException(
        // messageService
        // .getMessage("InvalidTaskStateTransitionException.taskUpdateRequest.status.completed"));
        // }

        if (task.status() != null) {
            validateTransition(taskDb.getStatus(), TaskStatus.valueOf(task.status()));
        }

        taskMapper.updateFromDto(task, taskDb);

        return taskMapper.toResponse(taskDb);
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        if (!taskRepository.existsById(id)) {
            throw new TaskNotFoundException(
                    messageService.getMessage("ResourceNotFound.task.id", "error.not_found"));
        }

        taskRepository.deleteById(id);
    }

    private void validateTransition(TaskStatus current, TaskStatus newStatus) {
        if (newStatus == current) {
            return;
        }

        Set<TaskStatus> allowed = allowedTransitions.getOrDefault(current, Set.of());

        if (!allowed.contains(newStatus)) {
            throw new InvalidTaskStateTransitionException(
                    messageService.getMessage(" .taskUpdateRequest.status",
                            new Object[] { current.name(), newStatus.name() },
                            "InvalidTaskStateTransitionException.taskUpdateRequest.default"));
        }
    }

}
