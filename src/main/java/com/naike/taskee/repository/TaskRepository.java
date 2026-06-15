package com.naike.taskee.repository;

import com.naike.taskee.domain.entities.TaskEntity;
import com.naike.taskee.domain.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository extends CrudRepository<TaskEntity, Long> {
    public Iterable<TaskEntity> findAllByUserId(Long userId);

}
