package com.naike.taskee.services;

import com.naike.taskee.domain.dto.UserDto;
import com.naike.taskee.domain.entities.UserEntity;
import com.naike.taskee.mapper.impl.AuthMapper;
import com.naike.taskee.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final AuthMapper authMapper;
    public UserService(UserRepository userRepository, AuthMapper authMapper) {
        this.userRepository = userRepository;
        this.authMapper = authMapper;
    }

    public UserDto isUserExist(String email) {
        return userRepository.findUserByEmail(email).map(authMapper::mapToDTO)
                .orElseThrow(() -> new NoSuchElementException("Invalid email or password"));
    }

    public UserDto findUserByEmail(String email) {
        Optional<UserEntity> userEntity = userRepository.findUserByEmail(email);
        return userEntity.map(authMapper::mapToDTO).orElseThrow(() -> new NoSuchElementException("User Not Found"));
    }
}
