package com.example.Config;

import com.example.EventHandlers.EventHandlers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class DataRestConfig {

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Bean
    EventHandlers eventHandlers() {
        return new EventHandlers(passwordEncoder);
    }

}
