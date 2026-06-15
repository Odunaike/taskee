package com.naike.taskee.config;

import com.naike.taskee.mapper.Mapper;
import com.naike.taskee.mapper.impl.AuthMapper;
import com.naike.taskee.mapper.impl.TaskMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class mapperConfig {

    @Bean
    public AuthMapper authMapper(){
        return new AuthMapper();
    }

    @Bean
    public TaskMapper taskMMapper() {
        return new TaskMapper();
    }
}
