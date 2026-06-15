package com.naike.taskee.controllers;

import com.naike.taskee.domain.ResponseType;
import com.naike.taskee.domain.dto.TaskDto;
import com.naike.taskee.domain.entities.TaskEntity;
import com.naike.taskee.mapper.impl.TaskMapper;
import com.naike.taskee.services.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
public class TaskController {
    private TaskService taskService;
    private TaskMapper taskMapper;

    public TaskController(TaskService taskService, TaskMapper taskMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }

    @PostMapping("/tasks")
    public ResponseEntity<ResponseType<TaskDto>>  createTask(@RequestBody TaskDto taskDto){
        try {
            TaskEntity entity =  taskService.createTask(taskDto);
            ResponseType<TaskDto> response = new ResponseType<>(
                    "Successful",
                    taskMapper.mapToDTO(entity)
            );
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            ResponseType<TaskDto> response = new ResponseType<>(
                    e.getMessage(),
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    };
    @GetMapping("/tasks")
    public ResponseEntity<ResponseType<List<TaskDto>>> getAllTasks() {
        try{
            List<TaskDto> tasks = taskService.findAll();
            ResponseType<List<TaskDto>> response = new ResponseType<>(
                    "successful",
                    tasks
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            ResponseType<List<TaskDto>> response = new ResponseType<>(
                    e.getMessage(),
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/tasks/{taskId}")
    public ResponseEntity<ResponseType<TaskDto>> getTaskById(@PathVariable Long taskId) {
        if(!taskService.isTaskExist(taskId)){
            return new ResponseEntity<>(
                    new ResponseType<>("Task not found", null),
                    HttpStatus.NOT_FOUND
            );
        }
        TaskDto taskDto = taskService.findById(taskId);
        ResponseType<TaskDto> response = new ResponseType<>("Successful", taskDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/tasks/{taskId}")
    public ResponseEntity<ResponseType<TaskDto>> completeTask(@PathVariable Long taskId) {
        if(!taskService.isTaskExist(taskId)){
           return new ResponseEntity<>(
                   new ResponseType<>("Task not found", null),
                   HttpStatus.NOT_FOUND
           );
        }
        TaskDto taskDto = taskService.markTaskCompleted(taskId);
        ResponseType<TaskDto> response = new ResponseType<>(
                "successful",
                taskDto
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("tasks/{taskId}")
    public ResponseEntity<String> deleteTask(@PathVariable long taskId) {
        if(taskService.isTaskExist(taskId)){
            taskService.deleteById(taskId);
            return new ResponseEntity<>("successful", HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
