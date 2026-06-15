package com.naike.taskee.mapper.impl;

import com.naike.taskee.domain.dto.TaskDto;
import com.naike.taskee.domain.entities.TaskEntity;
import com.naike.taskee.mapper.Mapper;

public class TaskMapper implements Mapper<TaskEntity, TaskDto> {
    @Override
    public TaskEntity mapToEntity(TaskDto dto) {
        return TaskEntity.builder()
                .userId(dto.getUserId())
                .title(dto.getTitle())
                .build();
    }

    @Override
    public TaskDto mapToDTO(TaskEntity entity) {
        return TaskDto.builder()
                .taskId(entity.getTaskId())
                .userId(entity.getUserId())
                .title(entity.getTitle())
                .status(entity.getStatus())
                .build();
    }
}
