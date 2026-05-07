package com.naike.taskee.controllers;

import com.naike.taskee.domain.ResponseType;
import com.naike.taskee.domain.dto.LoginDto;
import com.naike.taskee.domain.dto.SignupDto;
import com.naike.taskee.domain.dto.UserDto;
import com.naike.taskee.domain.entities.UserEntity;
import com.naike.taskee.mapper.Mapper;
import com.naike.taskee.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class AuthController {
    private final AuthService authService;
    private final Mapper<UserEntity, UserDto> authMapper;
    public AuthController(AuthService authService,  Mapper<UserEntity, UserDto> authMapper) {
        this.authService = authService;
        this.authMapper = authMapper;
    }

    @GetMapping()
    public String index(){
        return "Welcome to Taskee";
    }

    @PostMapping("/signup")
    public ResponseEntity<ResponseType<UserDto>> signup(
            @Valid @RequestBody SignupDto signupDto
            ){
        UserEntity userEntity = null;
        userEntity = authService.signup(signupDto);

        ResponseType<UserDto> response = new ResponseType<>(
                "Successful",
                authMapper.mapToDTO(userEntity)
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseType<String>> login(
            @RequestBody LoginDto loginDto
    ){
            authService.login(loginDto);
            ResponseType<String> response = new ResponseType<>(
                    "Successful",
                    "You can now plan with Taskee"
            );
            return new ResponseEntity<>(response, HttpStatus.OK);

    }


}
