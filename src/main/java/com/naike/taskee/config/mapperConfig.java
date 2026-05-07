package com.naike.taskee.config;

import com.naike.taskee.mapper.Mapper;
import com.naike.taskee.mapper.impl.AuthMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class mapperConfig {

    @Bean
    public Mapper mapper(){
        return new AuthMapper();
    }
}
