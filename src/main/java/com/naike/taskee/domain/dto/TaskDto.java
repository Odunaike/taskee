package com.naike.taskee.domain.dto;

import com.naike.taskee.domain.TaskStatus;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    private Long taskId;
    @NotEmpty
    private long userId;
    @NotEmpty
    private String title;
    private TaskStatus status;
}
