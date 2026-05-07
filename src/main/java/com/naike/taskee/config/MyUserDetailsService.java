package com.naike.taskee.config;

import com.naike.taskee.repository.UserRepository;
import org.antlr.v4.runtime.misc.NotNull;
import org.jspecify.annotations.NullMarked;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    @NullMarked
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(email).map(user ->
                        User.builder()
                                .username(user.getEmail())
                                .password(user.getPassword())
                                .build()
                ).orElseThrow(() -> new UsernameNotFoundException("UserEntity not found"));
    }

}
