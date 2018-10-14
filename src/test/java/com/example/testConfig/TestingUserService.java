package com.example.testConfig;

import com.example.Config.PrincipalUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class TestingUserService implements UserDetailsService{

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        List<GrantedAuthority> authorities =new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return new PrincipalUser("bob", "password", authorities, Long.parseLong(s), "", new Long(0));
    }

    /*
    @Bean
    public UserDetailsService userDetailsService(){
        List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("ADMIN"));
        UserDetails userDetails = new PrincipalUser("bob", "password", authorities, new Long(1), "", new Long(0));
        return new InMemoryUserDetailsManager(Arrays.asList(userDetails));
    }
    */

}
