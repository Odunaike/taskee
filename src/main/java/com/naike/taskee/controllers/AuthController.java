package com.naike.taskee.controllers;

import com.naike.taskee.domain.ResponseType;
import com.naike.taskee.domain.dto.LoginDto;
import com.naike.taskee.domain.dto.SignupDto;
import com.naike.taskee.domain.dto.UserDto;
import com.naike.taskee.domain.entities.UserEntity;
import com.naike.taskee.mapper.impl.AuthMapper;
import com.naike.taskee.services.UserService;
import com.naike.taskee.services.AuthService;
import jakarta.servlet.http.HttpServletRequest;
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
    private final UserService userService;
    private final AuthMapper authMapper;
    public AuthController(AuthService authService, UserService userService,  AuthMapper authMapper) {
        this.authService = authService;
        this.authMapper = authMapper;
        this.userService = userService;
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
    public ResponseEntity<ResponseType<UserDto>> login(
            @RequestBody LoginDto loginDto,
            HttpServletRequest request
    ){
            UserDto userDto = userService.isUserExist(loginDto.getEmail());

            authService.login(loginDto, request);
            ResponseType<UserDto> response = new ResponseType<UserDto>(
                    "Successful",
                    userDto
            );
            return new ResponseEntity<>(response, HttpStatus.OK);

    }


}
