package com.spring.luispa.todo_list.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.luispa.todo_list.dtos.TaskCreateRequest;
import com.spring.luispa.todo_list.dtos.TaskReponse;
import com.spring.luispa.todo_list.dtos.TaskUpdateRequest;
import com.spring.luispa.todo_list.services.TaskService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/tasks")
@Tag(name = "Task", description = "Operacionas relacionadas a las tareas.")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<TaskReponse>> getAll() {

        return ResponseEntity.status(HttpStatus.OK).body(taskService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskReponse> findOne(@PathVariable Long id) {

        return ResponseEntity.status(HttpStatus.OK).body(taskService.findById(id));
    }

    @PostMapping
    public ResponseEntity<TaskReponse> create(@Valid @RequestBody TaskCreateRequest task) {

        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.create(task));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskReponse> update(@PathVariable Long id, @Valid @RequestBody TaskUpdateRequest task) {

        return ResponseEntity.status(HttpStatus.OK).body(taskService.update(id, task));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        taskService.delete(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
