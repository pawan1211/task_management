package com.example.task_management.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.task_management.dto.TaskRequest;
import com.example.task_management.model.Task;
import com.example.task_management.model.TaskStatus;
import com.example.task_management.repository.TaskRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository repo;

    public TaskService(TaskRepository repo) {
        this.repo = repo;
    }

    public List<Task> findAll() {
        return repo.findAll();
    }

    public Optional<Task> findById(Long id) {
        return repo.findById(id);
    }

    @Transactional
    public Task create(TaskRequest request) {
        Task t = new Task();
        t.setTitle(request.getTitle());
        t.setDescription(request.getDescription());
        t.setDueDate(request.getDueDate());
        t.setStatus(TaskStatus.PENDING);
        LocalDateTime now = LocalDateTime.now();
        t.setCreatedAt(now);
        t.setUpdatedAt(now);
        return repo.save(t);
    }

    @Transactional
    public Optional<Task> update(Long id, TaskRequest request) {
        return repo.findById(id).map(existing -> {
            existing.setTitle(request.getTitle());
            existing.setDescription(request.getDescription());
            existing.setDueDate(request.getDueDate());
            existing.setUpdatedAt(LocalDateTime.now());
            return repo.save(existing);
        });
    }

    @Transactional
    public boolean delete(Long id) {
        if (!repo.existsById(id)) return false;
        repo.deleteById(id);
        return true;
    }

    @Transactional
    public Optional<Task> markComplete(Long id) {
        return repo.findById(id).map(t -> {
            t.setStatus(TaskStatus.COMPLETED);
            t.setUpdatedAt(LocalDateTime.now());
            return repo.save(t);
        });
    }

    // small helper: mark in progress
    @Transactional
    public Optional<Task> setInProgress(Long id) {
        return repo.findById(id).map(t -> {
            t.setStatus(TaskStatus.IN_PROGRESS);
            t.setUpdatedAt(LocalDateTime.now());
            return repo.save(t);
        });
    }
}
