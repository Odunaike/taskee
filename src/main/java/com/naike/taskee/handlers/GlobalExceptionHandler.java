package com.naike.taskee.handlers;

import com.naike.taskee.domain.ResponseType;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>>  handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        Map<String,Object> map = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach((error)->{
            map.put(error.getField(), error.getDefaultMessage());
        });
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ResponseType<String>>  handleBadCredentialsException(BadCredentialsException e){
        ResponseType<String> res = new ResponseType<>(
            "unsuccessful",
                e.getMessage()
        );
        return new ResponseEntity<>(res , HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ResponseType<String>>  handleDataIntegrityViolationException(DataIntegrityViolationException e){
        ResponseType<String> res = new ResponseType<>(
                "unsuccessful",
                e.getMessage()
        );
        return new ResponseEntity<>(res, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseType<String>>  handleServerException(Exception e){
        ResponseType<String> res = new ResponseType<>(
                "unsuccessful",
                e.getMessage()
        );
        return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
