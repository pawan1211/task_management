package com.example.task_management.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.task_management.model.TaskStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private LocalDate dueDate;
    private TaskStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
