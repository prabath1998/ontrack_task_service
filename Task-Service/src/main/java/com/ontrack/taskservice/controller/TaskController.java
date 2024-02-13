package com.ontrack.taskservice.controller;

import com.ontrack.taskservice.modal.Task;
import com.ontrack.taskservice.modal.TaskStatus;
import com.ontrack.taskservice.modal.UserDTO;
import com.ontrack.taskservice.service.TaskService;
import com.ontrack.taskservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;
    @Autowired
    private UserService userService;

    @PostMapping()
    public ResponseEntity<Task> createTask(@RequestBody Task task, @RequestHeader("Authorization") String jwt) throws Exception {
        UserDTO user = userService.getUserProfile(jwt);
        Task createdTask = taskService.createTask(task, user.getRole());
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@RequestHeader("Authorization") String jwt, @PathVariable Long id) throws Exception {
        UserDTO user = userService.getUserProfile(jwt);
        Task task = taskService.getTaskById(id);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Task>> getAssignedUserTasks(
            @RequestParam(required = false) TaskStatus taskStatus,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        UserDTO user = userService.getUserProfile(jwt);
        List<Task> tasks = taskService.assignedUserTasks(user.getId(), taskStatus);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<Task>> getAllTasks(
            @RequestParam(required = false) TaskStatus taskStatus,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        UserDTO user = userService.getUserProfile(jwt);
        List<Task> tasks = taskService.getAllTasks(taskStatus);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @PostMapping("/{id}/user/{userId}/assign")
    public ResponseEntity<Task> assignTaskToUser(
            @PathVariable Long id,
            @PathVariable Long userId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        UserDTO user = userService.getUserProfile(jwt);
        Task task = taskService.assignToUser(userId,id);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Task> updateTask(
            @PathVariable Long id,
            @RequestBody Task task,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        UserDTO user = userService.getUserProfile(jwt);
        Task updatedTask = taskService.updateTask(id, task, user.getId());
        return new ResponseEntity<>(task, HttpStatus.OK);
    }
}
