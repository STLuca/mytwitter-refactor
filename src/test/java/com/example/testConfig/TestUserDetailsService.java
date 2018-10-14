package com.example.testConfig;

import com.example.Config.PrincipalUser;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Arrays;
import java.util.List;

@TestConfiguration
public class TestUserDetailsService {

    @Bean
    @Primary
    public UserDetailsService userDetailsService(){
        List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("ADMIN"));
        UserDetails userDetails = new PrincipalUser("bob", "password", authorities, new Long(1), "", new Long(0));
        return new InMemoryUserDetailsManager(Arrays.asList(userDetails));
    }

}

