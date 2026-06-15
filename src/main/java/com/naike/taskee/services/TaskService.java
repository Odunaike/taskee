package com.naike.taskee.services;

import com.naike.taskee.domain.TaskStatus;
import com.naike.taskee.domain.dto.TaskDto;
import com.naike.taskee.domain.dto.UserDto;
import com.naike.taskee.domain.entities.TaskEntity;
import com.naike.taskee.domain.entities.UserEntity;
import com.naike.taskee.mapper.impl.TaskMapper;
import com.naike.taskee.repository.TaskRepository;
import com.naike.taskee.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserService userService;
    private final TaskMapper taskMapper;
    public TaskService(TaskRepository taskRepository, UserService userService, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.userService = userService;
    }
    public TaskEntity createTask(TaskDto dto) throws Exception{
        try{

            return taskRepository.save(
                    taskMapper.mapToEntity(dto)
            );
        }catch (Exception e){
            throw new Exception("Unable to create task now");
        }
    }
    public List<TaskDto> findAll() throws Exception{
        try {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            UserDto currentUser = userService.findUserByEmail(email);

            return StreamSupport.stream(taskRepository.findAllByUserId(currentUser.getId()).spliterator(), false)
                    .map(taskMapper::mapToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public TaskDto findById(long id) {
       return  taskRepository.findById(id).map(taskMapper::mapToDTO).orElseThrow();
    }

    public boolean isTaskExist(long taskId) {
        return taskRepository.existsById(taskId);
    }

    public TaskDto markTaskCompleted(long taskId) {

        return taskRepository.findById(taskId).map(
                taskEntity -> {
                    taskEntity.setStatus(TaskStatus.COMPLETED);
                    taskRepository.save(taskEntity);
                    return taskMapper.mapToDTO(taskEntity);
                }
        ).orElseThrow();
    }

    public void deleteById(Long id) {
        taskRepository.deleteById(id);
    }
}
