package com.example.task_management.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import com.example.task_management.model.Task;
import com.example.task_management.model.TaskStatus;
import com.example.task_management.repository.TaskRepository;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

class TaskServiceTest {

    private TaskRepository repo;
    private TaskService svc;

    @BeforeEach
    void setUp() {
        repo = mock(TaskRepository.class);
        svc = new TaskService(repo);
    }

 

    @Test
    void markComplete_transitionsToCompleted() {
        Task t = new Task();
        t.setId(2L);
        t.setTitle("old");
        t.setStatus(TaskStatus.IN_PROGRESS);
        when(repo.findById(2L)).thenReturn(Optional.of(t));
        when(repo.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Optional<Task> updated = svc.markComplete(2L);

        assertThat(updated).isPresent();
        assertThat(updated.get().getStatus()).isEqualTo(TaskStatus.COMPLETED);
        verify(repo).save(updated.get());
    }
}
