package com.ontrack.taskservice.service;


import com.ontrack.taskservice.modal.Task;
import com.ontrack.taskservice.modal.TaskStatus;

import java.util.List;

public interface TaskService {
    Task createTask(Task task, String requesterRole) throws Exception;

    Task getTaskById(Long id) throws Exception;

    List<Task> getAllTasks(TaskStatus status) throws Exception;

    Task updateTask(Long id, Task updatedTask, Long userId) throws Exception;

    void deleteTask(Long id) throws Exception;

    Task assignToUser(Long userId, Long taskId) throws Exception;

    List<Task> assignedUserTasks(Long userId, TaskStatus status) throws Exception;

    Task completeTask(Long taskId) throws Exception;
}

