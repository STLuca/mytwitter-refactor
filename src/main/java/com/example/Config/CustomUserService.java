package com.example.Config;

import com.example.Repositories.UserRepository;
import com.example.Views.UserPrincipalView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class CustomUserService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("--------------------------------------------------------");
        System.out.println("username: " + username);
        UserPrincipalView user = userRepository.findUserPrincipal(username);

        if (user == null) {
            throw new UsernameNotFoundException("User '" + username + "' not found.");
        }

        List<GrantedAuthority> authorities =new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        return new PrincipalUser(
                user.getUsername(),
                user.getPassword(),
                authorities,
                user.getId(),
                user.getProfilePic(),
                user.getNotifications());

    }

}
