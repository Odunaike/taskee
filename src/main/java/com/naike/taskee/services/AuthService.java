package com.naike.taskee.services;

import com.naike.taskee.config.SecurityConfig;
import com.naike.taskee.domain.dto.LoginDto;
import com.naike.taskee.domain.dto.SignupDto;
import com.naike.taskee.domain.entities.UserEntity;
import com.naike.taskee.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder,  AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }


    public UserEntity signup(SignupDto signupDto) {
        if (userRepository.findUserByEmail(signupDto.getEmail()).isPresent()){
            throw new DataIntegrityViolationException("Email already exists");
        }

        UserEntity userEntity = UserEntity.builder()
                .firstName(signupDto.getFirstName())
                .lastName(signupDto.getLastName())
                .email(signupDto.getEmail())
                .password(passwordEncoder.encode(signupDto.getPassword()))
                .build();
        return userRepository.save(userEntity);
    }


    public void login(LoginDto loginDto, HttpServletRequest request) {
        try {
            Authentication auth =  authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getEmail(),
                            loginDto.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(auth);
            HttpSession session = request.getSession(true);
            session.setAttribute(
                    HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                    SecurityContextHolder.getContext()
            );
        }catch (Exception e){
            throw new BadCredentialsException("Invalid email or password");
        }
    }
}
