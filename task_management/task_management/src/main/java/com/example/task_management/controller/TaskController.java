package com.example.task_management.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.task_management.dto.TaskRequest;
import com.example.task_management.dto.TaskResponse;
import com.example.task_management.model.Task;
import com.example.task_management.service.TaskService;

import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService svc;

    public TaskController(TaskService svc) {
        this.svc = svc;
    }

    private TaskResponse toResponse(Task t) {
        TaskResponse r = new TaskResponse();
        r.setId(t.getId());
        r.setTitle(t.getTitle());
        r.setDescription(t.getDescription());
        r.setDueDate(t.getDueDate());
        r.setStatus(t.getStatus());
        r.setCreatedAt(t.getCreatedAt());
        r.setUpdatedAt(t.getUpdatedAt());
        return r;
    }

    // Get all tasks (no stream used)
    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAll() {
        List<Task> tasks = svc.findAll();
        List<TaskResponse> list = new ArrayList<>();

        for (Task t : tasks) {
            list.add(toResponse(t));
        }

        return ResponseEntity.ok(list);
    }

    // Get a task by ID
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getById(@PathVariable Long id) {
        var optionalTask = svc.findById(id);
        if (optionalTask.isPresent()) {
            TaskResponse response = toResponse(optionalTask.get());
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // Create a new task
    @PostMapping
    public ResponseEntity<TaskResponse> create(@Valid @RequestBody TaskRequest request) {
        Task created = svc.create(request);
        TaskResponse response = toResponse(created);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Update an existing task
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> update(@PathVariable Long id, @Valid @RequestBody TaskRequest request) {
        var optionalTask = svc.update(id, request);
        if (optionalTask.isPresent()) {
            TaskResponse response = toResponse(optionalTask.get());
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // Delete a task
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = svc.delete(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Mark a task as complete
    @PatchMapping("/{id}/complete")
    public ResponseEntity<TaskResponse> complete(@PathVariable Long id) {
        var optionalTask = svc.markComplete(id);
        if (optionalTask.isPresent()) {
            TaskResponse response = toResponse(optionalTask.get());
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
