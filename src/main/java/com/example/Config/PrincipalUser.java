package com.example.Config;

import lombok.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public class PrincipalUser extends User {

    private final Long id;
    private final String profilePic;

    public PrincipalUser(
            String username,
            String password,
            List<GrantedAuthority> authorities,
            Long id,
            String proflePic) {
        super(username, password, authorities);
        this.id = id;
        this.profilePic = proflePic;
    }

    public Long getId() {
        return id;
    }

    public String getProfilePic() {
        return profilePic;
    }
}
