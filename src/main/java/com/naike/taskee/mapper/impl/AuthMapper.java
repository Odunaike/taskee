package com.naike.taskee.mapper.impl;

import com.naike.taskee.domain.dto.UserDto;
import com.naike.taskee.domain.entities.UserEntity;
import com.naike.taskee.mapper.Mapper;

public class AuthMapper implements Mapper<UserEntity, UserDto> {
    @Override
    public UserEntity mapToEntity(UserDto dto) {
        return UserEntity
                .builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();
    }

    @Override
    public UserDto mapToDTO(UserEntity entity) {
        return UserDto
                .builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .email(entity.getEmail())
                .build();
    }
}
